package events

import (
	"fmt"
	"strings"
)

type OnChainDomainNameEvent interface {
	GetDomainNameId() *DomainNameId
	GetPreviousSmtRoot() []byte
	GetUpdatedSmtRoot() []byte
	GetUpdatedState() *DomainNameState
}

func BcsDeserializeDomainNameEvent(eventType string, evtData []byte) (OnChainDomainNameEvent, error) {
	trimedEventType := trimTypeTagContractAddress(eventType)
	if trimedEventType == "::DomainName::Registered" {
		regEvt, err := BcsDeserializeDomainNameRegistered(evtData)
		if err != nil {
			return nil, err
		}
		return &regEvt, nil
	}
	if trimedEventType == "::DomainName::Renewed" {
		e, err := BcsDeserializeDomainNameRenewed(evtData)
		if err != nil {
			return nil, err
		}
		return &e, nil
	}
	return nil, fmt.Errorf("unknown event type: %s", eventType)
}

func trimTypeTagContractAddress(typeTag string) string {
	if strings.Index(typeTag, "0x") == 0 {
		i := strings.Index(typeTag, "::")
		if i != -1 {
			return typeTag[i:]
		}
	}
	return typeTag
}

func (e *DomainNameRegistered) GetDomainNameId() *DomainNameId {
	return &e.DomainNameId
}

func (e *DomainNameRegistered) GetPreviousSmtRoot() []byte {
	return e.PreviousSmtRoot
}

func (e *DomainNameRegistered) GetUpdatedSmtRoot() []byte {
	return e.UpdatedSmtRoot
}

func (e *DomainNameRegistered) GetUpdatedState() *DomainNameState {
	return &e.UpdatedState
}

func (e *DomainNameRenewed) GetDomainNameId() *DomainNameId {
	return &e.DomainNameId
}

func (e *DomainNameRenewed) GetPreviousSmtRoot() []byte {
	return e.PreviousSmtRoot
}

func (e *DomainNameRenewed) GetUpdatedSmtRoot() []byte {
	return e.UpdatedSmtRoot
}

func (e *DomainNameRenewed) GetUpdatedState() *DomainNameState {
	return &e.UpdatedState
}
