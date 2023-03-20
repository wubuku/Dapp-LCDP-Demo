package db

import (
	"encoding/hex"
	"starcoin-ns-demo/tools"
)

const (
	DOMAIN_NAME_STATE_HEAD_ID_DEFAULT                   string = "DEFAULT"
	DOMAIN_NAME_STATE_DEFAULT_TABLE_NAME                string = "domain_name_state"
	DOMAIN_NAME_EVENT_SEQUENCE_STATUS_STATE_TABLE_BUILT        = "STATE_TABLE_BUILT"
)

type DomainNameSmtNode struct {
	Hash string `gorm:"primaryKey;size:66"`
	Data string `gorm:"size:132"`

	CreatedAt uint64 `gorm:"autoCreateTime:milli"`
}

type DomainNameSmtValue struct {
	Path      string `gorm:"primaryKey;size:66"` //;uniqueIndex:uni_smt_leaf_path_vhash
	ValueHash string `gorm:"primaryKey;size:66"` //;uniqueIndex:uni_smt_leaf_path_vhash
	Value     string `gorm:"size:36000"`
	// //////////////////// decoded DomainNameState /////////////////////
	DomainNameIdTopLevelDomain    string `gorm:"size:100"`
	DomainNameIdSecondLevelDomain string `gorm:"size:100"`
	ExpirationDate                uint64
	Owner                         string `gorm:"size:66"`
	// //////////////////////////////////////////////////////////////////
	CreatedAt uint64 `gorm:"autoCreateTime:milli"`
}

func (v *DomainNameSmtValue) GetDomainNameState() (*DomainNameState, error) {
	stateOwner, err := tools.HexToStarcoinAccountAddress(v.Owner)
	if err != nil {
		return nil, err
	}
	return NewDomainNameState(NewDomainNameId(v.DomainNameIdTopLevelDomain, v.DomainNameIdSecondLevelDomain), v.ExpirationDate, stateOwner), nil
}

type DomainNameId struct {
	TopLevelDomain    string
	SecondLevelDomain string
}

func NewDomainNameId(tld string, sld string) *DomainNameId {
	return &DomainNameId{
		TopLevelDomain:    tld,
		SecondLevelDomain: sld,
	}
}

type DomainNameEvent struct {
	Id              uint64 `gorm:"primaryKey;autoIncrement:true"`
	BlockHash       string `gorm:"size:66;uniqueIndex:uni_block_hash_evt_key"`
	EventKey        string `gorm:"size:100;uniqueIndex:uni_block_hash_evt_key"`
	BlockNumber     uint64
	TransactionHash string `gorm:"size:66"`
	EventType       string `gorm:"size:500"`
	BcsData         string `gorm:"size:36000"`
	// //////////// On-Chain DomainNameEvent properties ////////////
	//   DomainNameId       DomainNameId
	//   ...
	//   UpdatedState       DomainNameState
	// 	 UpdatedSmtRoot     []byte
	// 	 PreviousSmtRoot    []byte
	DomainNameIdTopLevelDomain    string `gorm:"size:100"`
	DomainNameIdSecondLevelDomain string `gorm:"size:100"`
	UpdatedStateExpirationDate    uint64 // state property
	UpdatedStateOwner             string `gorm:"size:66"` // state property
	// /////////////// SMT info. ///////////////////
	DomainNameIdSmtHash string `gorm:"size:66"`       // SMT leaf path
	UpdatedStateSmtHash string `gorm:"size:66"`       // SMT leaf value hash
	UpdatedSmtRoot      string `gorm:"size:66;index"` // SMT root after event occurred
	PreviousSmtRoot     string `gorm:"size:66;index"` // SMT root befor event occurred

	CreatedAt uint64 `gorm:"autoCreateTime:milli"`
}

func NewDomainNameEvent(
	blockHash string,
	eventKey string,
	blockNumber uint64,
	transactionHash string,
	eventType string,
	bcsData []byte,
	domainNameIdTopLevelDomain string,
	domainNameIdSecondLevelDomain string,
	updatedStateExpirationDate uint64,
	updatedStateOwner [16]uint8,
	domainNameIdSmtHash []byte,
	updatedStateSmtHash []byte,
	smtRoot []byte,
	previousSmtRoot []byte,
) *DomainNameEvent {
	return &DomainNameEvent{
		BlockHash:                     blockHash,
		EventKey:                      eventKey,
		BlockNumber:                   blockNumber,
		TransactionHash:               transactionHash,
		EventType:                     eventType,
		BcsData:                       hex.EncodeToString(bcsData),
		DomainNameIdTopLevelDomain:    domainNameIdTopLevelDomain,
		DomainNameIdSecondLevelDomain: domainNameIdSecondLevelDomain,
		UpdatedStateExpirationDate:    updatedStateExpirationDate,
		UpdatedStateOwner:             hex.EncodeToString(updatedStateOwner[:]),
		DomainNameIdSmtHash:           hex.EncodeToString(domainNameIdSmtHash),
		UpdatedStateSmtHash:           hex.EncodeToString(updatedStateSmtHash),
		UpdatedSmtRoot:                hex.EncodeToString(smtRoot),
		PreviousSmtRoot:               hex.EncodeToString(previousSmtRoot),
	}
}

func (domainNameEvent *DomainNameEvent) GetDomainNameId() *DomainNameId {
	domainNameId := DomainNameId{
		TopLevelDomain:    domainNameEvent.DomainNameIdTopLevelDomain,
		SecondLevelDomain: domainNameEvent.DomainNameIdSecondLevelDomain,
	}
	return &domainNameId
}

func (domainNameEvent *DomainNameEvent) GetUpdatedStateOwner() ([16]uint8, error) {
	return tools.HexToStarcoinAccountAddress(domainNameEvent.UpdatedStateOwner)
}

type DomainNameEventSequence struct {
	SequenceId  string `gorm:"primaryKey;size:100"` // event sequence ID
	LastEventId uint64 `gorm:"not null"`            // last event ID of this sequence
	//SmtRoot            string `gorm:"size:66;index"`                         // SMT root after last event occurred
	BlockHash          string `gorm:"size:66;index:idx_block_hash_evt_key"`  // last event block hash
	EventKey           string `gorm:"size:100;index:idx_block_hash_evt_key"` // last event EventKey
	BlockNumber        uint64 `gorm:"not null"`                              // last event block number
	PreviousSequenceId string `gorm:"size:100"`                              // previous event sequence ID.
	ElementIds         string `gorm:"size:10000"`                            // element(event) IDs of this sequence
	StateTableName     string `gorm:"size:100"`                              // state table name built by this sequence
	Status             string `gorm:"size:20"`                               // status
}

func NewDomainNameEventSequence(
	sequenceId string,
	lastEventId uint64,
	//smtRoot string,
	blockHash string,
	eventKey string,
	blockNumber uint64,
	previousSequenceId string,
	elementIds string,
) *DomainNameEventSequence {
	return &DomainNameEventSequence{
		SequenceId:  sequenceId,
		LastEventId: lastEventId,
		//SmtRoot:            smtRoot,
		BlockHash:          blockHash,
		EventKey:           eventKey,
		BlockNumber:        blockNumber,
		PreviousSequenceId: previousSequenceId,
		ElementIds:         elementIds,
	}
}

type DomainNameState struct {
	DomainNameIdTopLevelDomain    string `gorm:"primaryKey;size:100"`
	DomainNameIdSecondLevelDomain string `gorm:"primaryKey;size:100"`
	ExpirationDate                uint64
	Owner                         string `gorm:"size:66"`

	CreatedAtBlockNumber uint64 `gorm:"not null"`       // use block number as timestamp? //autoCreateTime:milli
	UpdatedAtBlockNumber uint64 `gorm:"not null;index"` // use block number as timestamp? //autoUpdateTime:milli;
}

func NewDomainNameState(domainNameId *DomainNameId, expirationDate uint64, owner [16]uint8) *DomainNameState {
	return &DomainNameState{
		DomainNameIdTopLevelDomain:    domainNameId.TopLevelDomain,
		DomainNameIdSecondLevelDomain: domainNameId.SecondLevelDomain,
		ExpirationDate:                expirationDate,
		Owner:                         hex.EncodeToString(owner[:]),
	}
}

func (domainNameState *DomainNameState) GetDomainNameId() *DomainNameId {
	domainNameId := DomainNameId{
		TopLevelDomain:    domainNameState.DomainNameIdTopLevelDomain,
		SecondLevelDomain: domainNameState.DomainNameIdSecondLevelDomain,
	}
	return &domainNameId
}

func (domainNameState *DomainNameState) SetDomainNameId(domainNameId *DomainNameId) {
	domainNameState.DomainNameIdTopLevelDomain = domainNameId.TopLevelDomain
	domainNameState.DomainNameIdSecondLevelDomain = domainNameId.SecondLevelDomain
}

func (domainNameState *DomainNameState) GetOwner() ([16]uint8, error) {
	return tools.HexToStarcoinAccountAddress(domainNameState.Owner)
}

func (domainNameState *DomainNameState) SetOwner(owner [16]uint8) {
	domainNameState.Owner = hex.EncodeToString(owner[:])
}

type DomainNameStateHead struct {
	HeadId    string `gorm:"primaryKey;size:100"`
	BlockHash string `gorm:"size:66;index:idx_block_hash_evt_key"`
	EventKey  string `gorm:"size:100;index:idx_block_hash_evt_key"`
	SmtRoot   string `gorm:"size:66"`
	TableName string `gorm:"size:100"`

	CreatedAt uint64 `gorm:"autoCreateTime:milli"`
	UpdatedAt int64  `gorm:"autoUpdateTime:milli;index"`
}

func NewDomainNameStateHead(headId string, blockHash string, eventKey string, smtRoot string, tableName string) *DomainNameStateHead {
	return &DomainNameStateHead{
		HeadId:    headId,
		BlockHash: blockHash,
		EventKey:  eventKey,
		SmtRoot:   smtRoot,
		TableName: tableName,
	}
}

type ChainHeight struct {
	Key    string `gorm:"primaryKey;size:66"`
	Height uint64

	CreatedAt uint64 `gorm:"autoCreateTime:milli"`
	UpdatedAt int64  `gorm:"autoUpdateTime:milli;index"`
}
