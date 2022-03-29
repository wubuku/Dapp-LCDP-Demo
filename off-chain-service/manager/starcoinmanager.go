package manager

import (
	"bytes"
	"context"
	"encoding/hex"
	"fmt"
	"strconv"

	"log"
	"time"

	"starcoin-ns-demo/contract"
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
		log.Printf("StarcoinManager init - start height from flag: %d", this.currentHeight)
		return true
	}
	heightFromDB, err := this.db.GetStarcoinHeight() // TODO: ignore db error???
	if err != nil {
		log.Printf("StarcoinManager init - Get height from DB error: %s", err.Error())
	}
	this.currentHeight = heightFromDB
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
				err := m.handleNewBlock(m.currentHeight + 1)
				if err != nil {
					log.Printf("StarcoinManager.MonitorChain - handle confirmed starcoin block error: %s,  height: %d", err.Error(), m.currentHeight)
					blockHandleResult = false
				}
				if !blockHandleResult {
					// if block handled NOT ok, don't increase starcoin height
					break
				}
				m.currentHeight++
			}
			if err := m.db.UpdateStarcoinHeight(m.currentHeight); err != nil {
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
	// //////////////////////// Filter event type tags ///////////////////////////////
	typeTagRegistered := m.contractAddress + "::DomainName::Registered"
	typeTagRenewed := m.contractAddress + "::DomainName::Renewed"
	// ///////////////////////////////////////////////////////////////////////////////
	fromBlock := height
	toBlock := height
	eventFilter := &stcclient.EventFilter{
		Address:   []string{address},
		TypeTags:  []string{typeTagRegistered, typeTagRenewed},
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
		err := m.handleStarcoinEvent(evt)
		if err != nil {
			return err
		}
	}

	return nil
}

// handle a Starcoin event
func (m *StarcoinManager) handleStarcoinEvent(evt stcclient.Event) error {
	evtData, err := tools.HexToBytes(evt.Data)
	if err != nil {
		return errors.Wrap(err, "handleNewBlock - tools.HexToBytes error")
	}
	// BCS deserialize on-chain Domain-Name-Event
	domainNameEvt, err := events.BcsDeserializeDomainNameEvent(evt.TypeTag, evtData)
	if err != nil {
		return errors.Wrap(err, "handleNewBlock - BcsDeserializeDomainNameRegistered error")
	}
	blockNumber, err := strconv.ParseUint(evt.BlockNumber, 10, 64)
	if err != nil {
		return errors.Wrap(err, "handleNewBlock - ParseUint error")
	}

	domainNameId := db.NewDomainNameId(string(domainNameEvt.GetDomainNameId().TopLevelDomain), string(domainNameEvt.GetDomainNameId().SecondLevelDomain))
	smtKey, err := domainNameId.BcsSerialize()
	if err != nil {
		return errors.Wrap(err, "handleNewBlock - domainNameId.BcsSerialize error")
	}
	updatedSmtValue, err := domainNameEvt.GetUpdatedState().BcsSerialize()
	if err != nil {
		return errors.Wrap(err, "handleNewBlock - UpdatedState.BcsSerialize error")
	}
	// //////////////////// Update SMT ////////////////////
	smt, err := m.getSMTree()
	if err != nil {
		return errors.Wrap(err, "handleNewBlock - getSMTree error")
	}
	offChainSmtRoot, err := smt.UpdateForRoot(smtKey, updatedSmtValue, domainNameEvt.GetPreviousSmtRoot())
	if err != nil {
		return errors.Wrap(err, "handleNewBlock - UpdateForRoot error")
	}
	if !bytes.Equal(offChainSmtRoot, domainNameEvt.GetUpdatedSmtRoot()) {
		return fmt.Errorf("handleNewBlock - offChainSmtRoot(%s) != onChainEvent.UpdatedSmtRoot(%s)", hex.EncodeToString(offChainSmtRoot), hex.EncodeToString(domainNameEvt.GetUpdatedSmtRoot()))
	}
	// ////////////// Save Domain-Name-Event ///////////////
	domainNameEvent := db.NewDomainNameEvent(
		evt.BlockHash,
		evt.EventKey,
		blockNumber,
		evt.TransactionHash,
		evt.TypeTag,
		evtData,
		string(domainNameEvt.GetDomainNameId().TopLevelDomain),
		string(domainNameEvt.GetDomainNameId().SecondLevelDomain),
		domainNameEvt.GetUpdatedState().ExpirationDate,
		domainNameEvt.GetUpdatedState().Owner,
		tools.SmtDigest(smtKey),
		tools.SmtDigest(updatedSmtValue),
		domainNameEvt.GetUpdatedSmtRoot(),
		domainNameEvt.GetPreviousSmtRoot(),
	)
	err = m.db.SaveDomainNameEvent(domainNameEvent)
	if err != nil {
		return errors.Wrap(err, "handleNewBlock - SaveDomainNameEvent error")
	}
	return nil
}

func (m *StarcoinManager) getSMTree() (*smt.SparseMerkleTree, error) {
	nodeStore, err := m.db.NewDomainNameSmtNodeMapStore()
	if err != nil {
		return nil, err
	}
	valueStore := m.db.NewDomainNameSmtValueMapStore()
	smt := smt.NewSparseMerkleTree(nodeStore, valueStore, tools.New256Hasher())
	return smt, nil
}

// Return domain name state and SMT proof by domainNameId.
func (m *StarcoinManager) GetDomainNameStateAndSmtProof(domainNameId *db.DomainNameId) (*db.DomainNameState, *smt.SparseMerkleProof, []byte, error) {
	smtRootStr, err := contract.GetDomainNameSmtRoot(m.starcoinClient, m.contractAddress)
	if err != nil {
		return nil, nil, nil, err
	}
	smtRoot, err := tools.HexToBytes(smtRootStr)
	if err != nil {
		return nil, nil, nil, err
	}
	state, proof, err := m.GetDomainNameStateAndSmtProofForRoot(domainNameId, smtRoot)
	return state, proof, smtRoot, err
}

// Return domain name state and SMT proof by domainNameId and SMT root.
// If corresponding domain name dosen't exist(key is non-member of SMT), return nil and non-membership proof.
func (m *StarcoinManager) GetDomainNameStateAndSmtProofForRoot(domainNameId *db.DomainNameId, smtRoot []byte) (*db.DomainNameState, *smt.SparseMerkleProof, error) {
	var err error
	var key []byte
	key, err = domainNameId.BcsSerialize()
	if err != nil {
		return nil, nil, err
	}
	smTree, err := m.getSMTree()
	if err != nil {
		return nil, nil, errors.Wrap(err, "GetDomainNameStateForSmtRoot - getSMTree error")
	}
	proof, leafData, err := smTree.ProveForRootAndGetLeafData(key, smtRoot)
	if err != nil {
		return nil, nil, errors.Wrap(err, "GetDomainNameStateForSmtRoot - ProveForRootAndGetLeafData error")
	}
	if len(leafData) == 0 || len(proof.NonMembershipLeafData) != 0 {
		return nil, &proof, nil
	}
	if !tools.IsSmtKeyAndLeafDataRelated(key, leafData) {
		return nil, nil, fmt.Errorf("Key(%s) and leaf data(%s) are NOT related", hex.EncodeToString(key), hex.EncodeToString(leafData))
	}
	leafPath, leafValueHash := tools.ParseSmtLeaf(leafData)
	domainNameSmtValue, err := m.db.GetDomainNameSmtValue(hex.EncodeToString(leafPath), hex.EncodeToString(leafValueHash))
	if err != nil {
		return nil, nil, errors.Wrap(err, "GetDomainNameStateForSmtRoot - GetDomainNameSmtValue error")
	}
	value, err := hex.DecodeString(domainNameSmtValue.Value)
	if err != nil {
		return nil, nil, errors.Wrap(err, "GetDomainNameStateForSmtRoot - hex.DecodeString(domainNameSmtValue.Value) error")
	}
	if !bytes.Equal(leafValueHash, tools.SmtDigest(value)) {
		return nil, nil, fmt.Errorf("failed to verify value by valueHash(%s). key: %s", hex.EncodeToString(leafValueHash), hex.EncodeToString(key))
	}
	state, err := domainNameSmtValue.GetDomainNameState()
	if err != nil {
		return nil, nil, errors.Wrap(err, "GetDomainNameStateForSmtRoot - domainNameSmtValue.GetDomainNameState error")
	}
	return state, &proof, nil
}
