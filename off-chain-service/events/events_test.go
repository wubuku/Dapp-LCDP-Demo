package events

import (
	"context"
	"encoding/hex"
	"fmt"
	"starcoin-ns-demo/tools"
	"testing"

	stcclient "github.com/starcoinorg/starcoin-go/client"
)

const (
	STARCOIN_LOCAL_DEV_NETWORK_URL = "http://localhost:9850"
	DEV_CONTRACT_ADDRESS           = "0x18351d311d32201149a4df2a9fc2db8a"
)

func TestFetchDomainRegisterEvent(t *testing.T) {
	starcoinClient := stcclient.NewStarcoinClient(STARCOIN_LOCAL_DEV_NETWORK_URL)
	address := DEV_CONTRACT_ADDRESS
	typeTag := DEV_CONTRACT_ADDRESS + "::DomainName::Registerd"
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
		fmt.Printf("TestFetchDomainRegisterEvent - GetEvents error :%s", err.Error())
		t.FailNow()
	}
	if evts == nil {
		fmt.Printf("TestFetchDomainRegisterEvent - no events found.")
		t.FailNow()
	}

	for _, evt := range evts {
		evtData, err := tools.HexToBytes(evt.Data)
		if err != nil {
			fmt.Printf("TestFetchDomainRegisterEvent - hex.DecodeString error :%s", err.Error())
			t.FailNow()
		}
		regEvt, err := BcsDeserializeDomainNameRegisterd(evtData)
		if err != nil {
			fmt.Println(err)
			t.FailNow()
		}
		fmt.Printf("Block number: %s\n", evt.BlockNumber)
		fmt.Printf("Transaction hash: %s\n", evt.TransactionHash)
		fmt.Println("/////////////// Domain Name Registered(event) info. ///////////////")
		fmt.Println("--------------- DomainNameId.TopLevelDomain ----------------")
		fmt.Println(string(regEvt.DomainNameId.TopLevelDomain))
		fmt.Println("--------------- DomainNameId.SecondLevelDomain ---------------")
		fmt.Println(string(regEvt.DomainNameId.SecondLevelDomain))
		fmt.Println("--------------- Owner ----------------")
		fmt.Println(hex.EncodeToString(regEvt.Owner[:]))
		fmt.Println("--------------- RegistrationPeriod ----------------")
		fmt.Println(regEvt.RegistrationPeriod)
		fmt.Println("--------------- PreviousSmtRoot ----------------")
		fmt.Println(hex.EncodeToString(regEvt.PreviousSmtRoot))
		fmt.Println("--------------- UpdatedSmtRoot ----------------")
		fmt.Println(hex.EncodeToString(regEvt.UpdatedSmtRoot))

		fmt.Println("/////////////// Decoded Updated State ///////////////")
		fmt.Println("---------- UpdatedState.ExpirationDate ----------")
		fmt.Println(regEvt.UpdatedState.ExpirationDate)
		fmt.Println(hex.EncodeToString(regEvt.UpdatedState.Owner[:]))
		fmt.Println(string(regEvt.UpdatedState.DomainNameId.TopLevelDomain))
		fmt.Println(string(regEvt.UpdatedState.DomainNameId.SecondLevelDomain))

	}
}
