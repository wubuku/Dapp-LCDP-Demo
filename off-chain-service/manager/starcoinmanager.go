package manager

import (
	"bytes"
	"context"
	"fmt"
	"strconv"

	"log"
	"time"

	"starcoin-ns-demo/db"
	"starcoin-ns-demo/events"
	"starcoin-ns-demo/tools"

	"github.com/celestiaorg/smt"
	"github.com/pkg/errors"
	stcclient "github.com/starcoinorg/starcoin-go/client"
)

const (
	STARCOIN_USEFUL_BLOCK_NUM = 0
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
	jsonRpcUrl string,
	starcoinClient *stcclient.StarcoinClient,
	restClient *tools.RestClient,
	contractAddress string,
	db *db.MySqlDB,
) (*StarcoinManager, error) {
	m := StarcoinManager{
		jsonRpcUrl:      jsonRpcUrl,
		starcoinClient:  starcoinClient,
		restClient:      restClient,
		contractAddress: contractAddress,
		db:              db,
	}
	m.init()
	return &m, nil
}

func (this *StarcoinManager) init() bool {
	if this.currentHeight > 0 {
		log.Println("PolyManager init - start height from flag: %d", this.currentHeight)
		return true
	}
	this.currentHeight, _ = this.db.GetStarcoinHeight() // TODO: ignore db error???
	return true
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
				err = m.handleNewBlock(m.currentHeight + 1)
				if err != nil {
					log.Printf("StarcoinManager.MonitorChain - handle confirmed starcoin block error: %s,  height: %d", err.Error(), m.currentHeight)
					blockHandleResult = false
				}
				if !blockHandleResult {
					break
				}
				m.currentHeight++
			}
			if err = m.db.UpdateStarcoinHeight(m.currentHeight - 1); err != nil {
				log.Printf("StarcoinManager.MonitorChain - failed to save height of Starcoin: %v", err)
			}
			// case <-this.exitChan:
			// 	return
		}
	}
}

func (m *StarcoinManager) handleNewBlock(height uint64) error {
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
		return errors.Wrap(err, "handleNewBlock - GetEvents error")
	}
	if evts == nil {
		return nil
	}

	for _, evt := range evts {
		evtData, err := tools.HexToBytes(evt.Data)
		if err != nil {
			return errors.Wrap(err, "handleNewBlock - tools.HexToBytes error")
		}
		regEvt, err := events.BcsDeserializeDomainNameRegisterd(evtData)
		if err != nil {
			return errors.Wrap(err, "handleNewBlock - BcsDeserializeDomainNameRegisterd error")
		}
		blockNumber, err := strconv.ParseUint(evt.BlockNumber, 10, 64)
		if err != nil {
			return errors.Wrap(err, "handleNewBlock - ParseUint error")
		}
		domainNameEvent := db.NewDomainNameEvent(regEvt.UpdatedSmtRoot, regEvt.PreviousSmtRoot, blockNumber, evt.TransactionHash, evtData)
		err = m.db.SaveDomainNameEvent(domainNameEvent)
		if err != nil {
			return errors.Wrap(err, "handleNewBlock - SaveDomainNameEvent error")
		}

		// Update SMT
		nodeStore, err := m.db.NewDomainNameSmtNodeMapStore()
		if err != nil {
			return errors.Wrap(err, "handleNewBlock - NewDomainNameSmtNodeMapStore error")
		}
		valueStore := m.db.NewDomainNameSmtValueMapStore()
		smt := smt.NewSparseMerkleTree(nodeStore, valueStore, db.New256Hasher())
		domainNameId := db.NewDomainNameId(string(regEvt.DomainNameId.TopLevelDomain), string(regEvt.DomainNameId.SecondLevelDomain))
		key, err := domainNameId.BcsSerialize()
		if err != nil {
			return errors.Wrap(err, "handleNewBlock - domainNameId.BcsSerialize error")
		}
		value, err := regEvt.UpdatedState.BcsSerialize()
		if err != nil {
			return errors.Wrap(err, "handleNewBlock - UpdatedState.BcsSerialize error")
		}
		offChainSmtRoot, err := smt.UpdateForRoot(key, value, regEvt.PreviousSmtRoot)
		if err != nil {
			return errors.Wrap(err, "handleNewBlock - UpdateForRoot error")
		}
		if !bytes.Equal(offChainSmtRoot, regEvt.UpdatedSmtRoot) {
			return fmt.Errorf("handleNewBlock - offChainSmtRoot != regEvt.UpdatedSmtRoot")
		}
	}

	return nil
}
