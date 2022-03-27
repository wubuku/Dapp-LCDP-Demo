package db

import (
	"encoding/hex"
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
	stateOwner, err := HexToAccountAddress(v.Owner)
	if err != nil {
		return nil, err
	}
	return NewDomainNameState(NewDomainNameId(v.DomainNameIdTopLevelDomain, v.DomainNameIdSecondLevelDomain), v.ExpirationDate, stateOwner[:]), nil
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
	// SMT info.
	DomainNameIdSmtHash string `gorm:"size:66"` // SMT leaf path
	UpdatedStateSmtHash string `gorm:"size:66"` // SMT leaf value hash
	UpdatedSmtRoot      string `gorm:"size:66;index"`
	PreviousSmtRoot     string `gorm:"size:66;index"`

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
		DomainNameIdTopLevelDomain:    domainNameIdSecondLevelDomain,
		DomainNameIdSecondLevelDomain: domainNameIdSecondLevelDomain,
		UpdatedStateExpirationDate:    updatedStateExpirationDate,
		UpdatedStateOwner:             hex.EncodeToString(updatedStateOwner[:]),
		DomainNameIdSmtHash:           hex.EncodeToString(domainNameIdSmtHash),
		UpdatedStateSmtHash:           hex.EncodeToString(updatedStateSmtHash),
		UpdatedSmtRoot:                hex.EncodeToString(smtRoot),
		PreviousSmtRoot:               hex.EncodeToString(previousSmtRoot),
	}
}

type DomainNameState struct {
	DomainNameIdTopLevelDomain    string `gorm:"primaryKey;size:100"`
	DomainNameIdSecondLevelDomain string `gorm:"primaryKey;size:100"`
	ExpirationDate                uint64
	Owner                         string `gorm:"size:66"`

	CreatedAt uint64 `gorm:"autoCreateTime:milli"`
	UpdatedAt int64  `gorm:"autoUpdateTime:milli;index"`
}

func NewDomainNameState(domainNameId *DomainNameId, expirationDate uint64, owner []byte) *DomainNameState {
	return &DomainNameState{
		DomainNameIdTopLevelDomain:    domainNameId.TopLevelDomain,
		DomainNameIdSecondLevelDomain: domainNameId.SecondLevelDomain,
		ExpirationDate:                expirationDate,
		Owner:                         hex.EncodeToString(owner),
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
	return HexToAccountAddress(domainNameState.Owner)
}

func (domainNameState *DomainNameState) SetOwner(owner [16]uint8) {
	domainNameState.Owner = hex.EncodeToString(owner[:])
}

type DomainNameStateHead struct {
	HeadId    string `gorm:"primaryKey;size:100"`
	SmtRoot   string `gorm:"size:66"`
	TableName string `gorm:"size:100"`

	CreatedAt uint64 `gorm:"autoCreateTime:milli"`
	UpdatedAt int64  `gorm:"autoUpdateTime:milli;index"`
}

type ChainHeight struct {
	Key    string `gorm:"primaryKey;size:66"`
	Height uint64

	CreatedAt uint64 `gorm:"autoCreateTime:milli"`
	UpdatedAt int64  `gorm:"autoUpdateTime:milli;index"`
}
