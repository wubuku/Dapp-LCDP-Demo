package manager

import (
	"bytes"
	"context"
	"strconv"

	"log"
	"time"

	"starcoin-ns-demo/db"
	"starcoin-ns-demo/events"
	"starcoin-ns-demo/tools"

	"github.com/celestiaorg/smt"
	stcclient "github.com/starcoinorg/starcoin-go/client"
)

const (
	STARCOIN_USEFUL_BLOCK_NUM = 1
)

type StarcoinManager struct {
	currentHeight   uint64
	jsonRpcUrl      string
	starcoinClient  *stcclient.StarcoinClient
	restClient      *tools.RestClient
	contractAddress string
	db              *db.MySqlDB
}

func NewStarcoinManager(
	currentHeight uint64,
	jsonRpcUrl string,
	starcoinClient *stcclient.StarcoinClient,
	restClient *tools.RestClient,
	contractAddress string,
	db *db.MySqlDB,
) (*StarcoinManager, error) {
	m := StarcoinManager{
		currentHeight:   currentHeight,
		jsonRpcUrl:      jsonRpcUrl,
		starcoinClient:  starcoinClient,
		restClient:      restClient,
		contractAddress: contractAddress,
		db:              db,
	}
	return &m, nil
}

func (m *StarcoinManager) MonitorChain() {
	fetchBlockTicker := time.NewTicker(time.Second * 2) //time.Duration(this.config.StarcoinConfig.MonitorInterval) * time.Second)
	var blockHandleResult bool
	for {
		select {
		case <-fetchBlockTicker.C:
			height, err := tools.GetStarcoinNodeHeight(m.jsonRpcUrl, m.restClient)
			if err != nil {
				log.Printf("StarcoinManager.MonitorChain - cannot get node height, err: %s", err.Error())
				continue
			}
			// if height-this.currentHeight <= config.STARCOIN_USEFUL_BLOCK_NUM {
			// 	continue
			// }
			log.Printf("StarcoinManager.MonitorChain - starcoin height is %d", height)
			blockHandleResult = true
			for m.currentHeight < height-STARCOIN_USEFUL_BLOCK_NUM {
				if m.currentHeight%10 == 0 {
					log.Printf("StarcoinManager.MonitorChain - handle confirmed starcoin block height: %d", m.currentHeight)
				}
				blockHandleResult = m.handleNewBlock(m.currentHeight + 1)
				if !blockHandleResult {
					break
				}
				m.currentHeight++
			}
			if err = m.db.UpdateStarcoinHeight(m.currentHeight - 1); err != nil {
				log.Printf("PolyManager.MonitorChain - failed to save height of poly: %v", err)
			}
			// case <-this.exitChan:
			// 	return
		}
	}
}

func (m *StarcoinManager) handleNewBlock(height uint64) bool {

	starcoinClient := m.starcoinClient
	address := m.contractAddress
	typeTag := m.contractAddress + "::DomainName::Registerd"
	fromBlock := uint64(1)
	toBlock := uint64(20)
	eventFilter := &stcclient.EventFilter{
		Address:   []string{address},
		TypeTags:  []string{typeTag},
		FromBlock: fromBlock,
		ToBlock:   &toBlock,
	}

	evts, err := starcoinClient.GetEvents(context.Background(), eventFilter)
	if err != nil {
		log.Printf("handleNewBlock - GetEvents error :%s", err.Error())
		return false
	}
	if evts == nil {
		log.Printf("handleNewBlock - GetEvents return nil.")
		return true
	}

	for _, evt := range evts {
		evtData, err := tools.HexToBytes(evt.Data)
		if err != nil {
			log.Printf("handleNewBlock - tools.HexToBytes error :%s", err.Error())
			return false
		}
		regEvt, err := events.BcsDeserializeDomainNameRegisterd(evtData)
		if err != nil {
			log.Printf("handleNewBlock - BcsDeserializeDomainNameRegisterd error :%s", err.Error())
			return false
		}
		blockNumber, err := strconv.ParseUint(evt.BlockNumber, 10, 64)
		if err != nil {
			log.Printf("handleNewBlock - ParseUint error :%s", err.Error())
			return false
		}
		domainNameEvent := db.NewDomainNameEvent(regEvt.UpdatedSmtRoot, regEvt.PreviousSmtRoot, blockNumber, evt.TransactionHash, evtData)
		err = m.db.SaveDomainNameEvent(domainNameEvent)
		if err != nil {
			log.Printf("handleNewBlock - SaveDomainNameEvent error :%s", err.Error())
			return false
		}

		// Update SMT
		nodeStore, err := m.db.NewDomainNameSmtNodeMapStore()
		if err != nil {
			log.Printf("handleNewBlock - NewDomainNameSmtNodeMapStore error :%s", err.Error())
			return false
		}
		valueStore := m.db.NewDomainNameSmtValueMapStore()

		smt := smt.NewSparseMerkleTree(nodeStore, valueStore, db.New256Hasher())
		domainNameId := db.NewDomainNameId(string(regEvt.DomainNameId.TopLevelDomain), string(regEvt.DomainNameId.SecondLevelDomain))
		key, err := domainNameId.BcsSerialize()
		if err != nil {
			log.Printf("handleNewBlock - domainNameId.BcsSerialize error :%s", err.Error())
			return false
		}
		value, err := regEvt.UpdatedState.BcsSerialize()
		if err != nil {
			log.Printf("handleNewBlock - UpdatedState.BcsSerialize error :%s", err.Error())
			return false
		}
		offChainSmtRoot, err := smt.UpdateForRoot(key, value, regEvt.PreviousSmtRoot)
		if err != nil {
			log.Printf("handleNewBlock - UpdateForRoot error :%s", err.Error())
			return false
		}
		if !bytes.Equal(offChainSmtRoot, regEvt.UpdatedSmtRoot) {
			log.Printf("handleNewBlock - offChainSmtRoot != regEvt.UpdatedSmtRoot")
			return false
		}
	}

	return true
}
