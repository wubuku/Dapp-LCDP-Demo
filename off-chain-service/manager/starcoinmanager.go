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
	WAITING_STARCOIN_CONFIRMATIONS = 0 // waiting STARCOIN CONFIRMATION block count
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

func (m *StarcoinManager) init() bool {
	if m.currentHeight > 0 {
		log.Printf("StarcoinManager init - start height from flag: %d", m.currentHeight)
		return true
	}
	heightFromDB, err := m.db.GetStarcoinHeight() // TODO: ignore db error???
	if err != nil {
		log.Printf("StarcoinManager init - Get height from DB error: %s", err.Error())
	}
	m.currentHeight = heightFromDB
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
			if height-m.currentHeight <= WAITING_STARCOIN_CONFIRMATIONS {
				if e, err := m.db.GetLastDomainNameEvent(); err == nil && e != nil {
					if b, err := m.isDomainNameEventAvailable(e); err == nil {
						if !b {
							m.rollbackToAvailableHeight(e.BlockNumber)
						}
					}
				}
				continue
			}
			log.Printf("StarcoinManager.MonitorChain - starcoin height is %d", height)
			blockHandleResult = true
			for m.currentHeight < height-WAITING_STARCOIN_CONFIRMATIONS {
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
	smTree, err := m.getSMTree()
	if err != nil {
		return errors.Wrap(err, "handleNewBlock - getSMTree error")
	}
	offChainSmtRoot, err := smTree.UpdateForRoot(smtKey, updatedSmtValue, domainNameEvt.GetPreviousSmtRoot())
	if err != nil {
		var invalidKeyError *smt.InvalidKeyError
		if errors.As(err, &invalidKeyError) {
			blockNumber, err := strconv.ParseUint(evt.BlockNumber, 10, 64)
			if err != nil {
				return err
			}
			m.rollbackToAvailableHeight(blockNumber)
		}
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
	err = m.db.CreateDomainNameEvent(domainNameEvent)
	if err != nil {
		return errors.Wrap(err, "handleNewBlock - CreateDomainNameEvent error")
	}
	return nil
}

// Rollback to available height from 'fromHeight'.
func (m *StarcoinManager) rollbackToAvailableHeight(fromHeight uint64) error {
	e, err := m.GetLastAvailableDomainNameEventByBlockNumberLessThan(fromHeight)
	if err != nil {
		log.Printf("StarcoinManager.rollbackToAvailableHeight - GetLastAvailableDomainNameEventByBlockNumberLessThan error: %s", err.Error())
		return err
	}
	m.currentHeight = e.BlockNumber - 1 // Maybe the event's block is NOT processed completely, rollback one more block
	log.Printf("StarcoinManager.rollbackToAvailableHeight - currentHeight: %d", m.currentHeight)
	err = m.db.UpdateStarcoinHeight(m.currentHeight)
	if err != nil {
		log.Printf("StarcoinManager.rollbackToAvailableHeight - db.UpdateStarcoinHeight error: %s", err.Error())
		return err
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
		return nil, &proof, nil // Non-Membership proof
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

func (m *StarcoinManager) BuildDomainNameEventSequence() (*db.DomainNameEventSequence, error) {
	lastEvent, err := m.GetLastAvailableDomainNameEvent()
	if err != nil {
		return nil, err
	}
	return m.doBuildDomainNameEventSequence(lastEvent)
}

func (m *StarcoinManager) BuildDomainNameEventSequenceForLastEventId(lastEventId uint64) (*db.DomainNameEventSequence, error) {
	lastEvent, err := m.db.GetDomainNameEvent(lastEventId)
	if err != nil {
		return nil, err
	}
	return m.doBuildDomainNameEventSequence(lastEvent)
}

func (m *StarcoinManager) doBuildDomainNameEventSequence(lastEvent *db.DomainNameEvent) (*db.DomainNameEventSequence, error) {
	lastAvailableEventSeq, err := m.doGetLastAvailableDomainNameEventSequence(lastEvent.Id)
	if err != nil {
		return nil, err
	}
	if lastAvailableEventSeq != nil && lastAvailableEventSeq.LastEventId == lastEvent.Id {
		return nil, nil // already exists
	}
	var previousSeqId string
	if lastAvailableEventSeq != nil {
		previousSeqId = lastAvailableEventSeq.SequenceId
	}
	var firstEventId uint64 = 0
	if lastAvailableEventSeq != nil {
		firstEventId = lastAvailableEventSeq.LastEventId
	}
	ids, err := m.getDomainNameEventSequenceElementIds(lastEvent, firstEventId)
	if err != nil {
		return nil, err
	}
	idsJson, err := db.EncodeEventIds(ids)
	if err != nil {
		return nil, err
	}
	seqId := getDomainNameEventSequenceId(lastEvent)
	es := db.NewDomainNameEventSequence(seqId,
		lastEvent.Id,
		lastEvent.UpdatedSmtRoot,
		lastEvent.BlockHash,
		lastEvent.EventKey,
		lastEvent.BlockNumber,
		previousSeqId,
		idsJson,
	)
	err = m.db.CreateDomainNameEventSequence(es)
	if err != nil {
		return nil, err
	}
	return es, nil
}

func (m *StarcoinManager) GetLastAvailableDomainNameEventByBlockNumberLessThan(blockNumber uint64) (*db.DomainNameEvent, error) {
	currentE, err := m.db.GetLastDomainNameEventByBlockNumberLessThan(blockNumber)
	if err != nil {
		return nil, err
	}
	if currentE == nil {
		return nil, nil
	}
	return m.doGetLastAvailableDomainNameEvent(currentE)
}

func (m *StarcoinManager) GetLastAvailableDomainNameEvent() (*db.DomainNameEvent, error) {
	currentE, err := m.db.GetLastDomainNameEvent()
	if err != nil {
		return nil, err
	}
	if currentE == nil {
		return nil, nil
	}
	return m.doGetLastAvailableDomainNameEvent(currentE)
}

func (m *StarcoinManager) doGetLastAvailableDomainNameEvent(fromEvent *db.DomainNameEvent) (*db.DomainNameEvent, error) {
	currentE := fromEvent
	for {
		b, err := m.isDomainNameEventAvailable(currentE)
		if err != nil {
			return nil, err
		}
		if b {
			break
		}
		currentE, err = m.db.GetLastDomainNameEventByIdLessThan(currentE.Id)
		if err != nil {
			return nil, err
		}
		if currentE == nil {
			return nil, nil
		}
	}
	return currentE, nil
}

func (m *StarcoinManager) isDomainNameEventAvailable(e *db.DomainNameEvent) (bool, error) {
	blockInfo, err := m.starcoinClient.GetBlockInfoByNumber(context.Background(), e.BlockNumber)
	if err != nil {
		return false, err
	}
	if blockInfo.BlockHash == e.BlockHash {
		return true, nil
	}
	return false, nil
}

func (m *StarcoinManager) GetLastAvailableDomainNameEventSequenceeAllElementIds() ([]uint64, error) {
	e, err := m.GetLastAvailableDomainNameEvent()
	if err != nil {
		return nil, err
	}
	es, err := m.doGetLastAvailableDomainNameEventSequence(e.Id)
	if err != nil {
		return nil, err
	}
	allEventIds, err := m.db.GetDomainNameEventSequenceAllElementIds(es)
	if err != nil {
		return nil, err
	}
	return allEventIds, nil
}

func (m *StarcoinManager) GetLastAvailableDomainNameEventSequence() (*db.DomainNameEventSequence, error) {
	e, err := m.GetLastAvailableDomainNameEvent()
	if err != nil {
		return nil, err
	}
	return m.doGetLastAvailableDomainNameEventSequence(e.Id)
}

func (m *StarcoinManager) doGetLastAvailableDomainNameEventSequence(eventId uint64) (*db.DomainNameEventSequence, error) {
	currentEventId := eventId
	var currentES *db.DomainNameEventSequence
	for {
		var err error
		currentES, err = m.db.GetLastDomainNameEventSequenceByLastEventIdLessThanOrEquals(currentEventId)
		if err != nil {
			return nil, err
		}
		if currentES == nil {
			return nil, nil
		}
		blockInfo, err := m.starcoinClient.GetBlockInfoByNumber(context.Background(), currentES.BlockNumber)
		if err != nil {
			return nil, err
		}
		if blockInfo.BlockHash == currentES.BlockHash {
			e, err := m.db.GetDomainNameEvent(currentES.LastEventId) // GetDomainNameEventBlock by Id
			if err == nil && e != nil && e.BlockHash == currentES.BlockHash {
				break
			}
			// continue
		}
		currentEventId--
	}
	return currentES, nil
}

func (m *StarcoinManager) getDomainNameEventSequenceElementIds(lastEvent *db.DomainNameEvent, firstEventId uint64) ([]uint64, error) {
	ids := make([]uint64, 0, lastEvent.Id-firstEventId)
	currentE := lastEvent
	ids = append(ids, currentE.Id)
	for {
		var err error
		previousE, err := m.db.GetPreviousDomainNameEvent(currentE)
		if err != nil {
			return nil, err
		}
		if previousE == nil {
			if currentE.PreviousSmtRoot != "" {
				preRoot, _ := tools.HexToBytes(currentE.PreviousSmtRoot)
				if !bytes.Equal(preRoot, tools.SmtPlaceholder()) {
					return nil, fmt.Errorf("cannot find previous DomainNameSequence by SMT root: %s", currentE.PreviousSmtRoot)
				}
			}
			break // PreviousSmtRoot is empty or placeholder
		}
		ids = append(ids, previousE.Id)
		if previousE.Id == firstEventId {
			break
		}
		currentE = previousE
	}
	return reverseUint64Slice(ids), nil
}

func getDomainNameEventSequenceId(lastEvent *db.DomainNameEvent) string {
	bs, err := tools.HexToBytes(lastEvent.UpdatedSmtRoot)
	if err != nil {
		return lastEvent.UpdatedSmtRoot
	}
	endIdx := 4
	if len(bs) < 4 {
		endIdx = len(bs)
	}
	return "ES_" + hex.EncodeToString(bs[0:endIdx])
}

func reverseUint64Slice(slice []uint64) []uint64 {
	for left, right := 0, len(slice)-1; left < right; left, right = left+1, right-1 {
		slice[left], slice[right] = slice[right], slice[left]
	}
	return slice
}
