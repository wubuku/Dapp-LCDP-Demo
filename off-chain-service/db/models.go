package db

import (
	"encoding/hex"
)

type DomainNameSmtNode struct {
	Hash string `gorm:"primaryKey;size:66"`
	Data string `gorm:"size:132"`
}

type DomainNameSmtValue struct {
	Path                          string `gorm:"primaryKey;size:66"`
	ValueHash                     string `gorm:"primaryKey;size:66"`
	DomainNameIdTopLevelDomain    string `gorm:"size:100"`
	DomainNameIdSecondLevelDomain string `gorm:"size:100"`
	ExpirationDate                uint64
	Owner                         string `gorm:"size:66"`
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
	SmtRoot         string `gorm:"size:66;uniqueIndex"`
	PreviousSmtRoot string `gorm:"size:66;uniqueIndex"`
	BlockNumber     uint64
	TransactionHash string `gorm:"size:66"`
	BcsData         string `gorm:"size:36000"`
	CreatedAt       uint64 `gorm:"autoCreateTime:milli"`
}

type DomainNameState struct {
	DomainNameIdTopLevelDomain    string `gorm:"primaryKey;size:100"`
	DomainNameIdSecondLevelDomain string `gorm:"primaryKey;size:100"`
	ExpirationDate                uint64
	Owner                         string `gorm:"size:66"`
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

func (domainNameState *DomainNameState) GetOwner() ([16]uint8, error) {
	return HexToAccountAddress(domainNameState.Owner)
}

type DomainNameStateHead struct {
	HeadId    string `gorm:"primaryKey;size:100"`
	SmtRoot   string `gorm:"size:66"`
	TableName string `gorm:"size:100"`
}
