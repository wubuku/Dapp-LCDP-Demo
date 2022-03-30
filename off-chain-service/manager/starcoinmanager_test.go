package manager

import (
	"encoding/hex"
	"encoding/json"
	"fmt"
	"starcoin-ns-demo/db"
	"starcoin-ns-demo/tools"
	"testing"

	"github.com/starcoinorg/starcoin-go/client"
)

const (
	STARCOIN_LOCAL_DEV_NETWORK_URL = "http://localhost:9850"
	DEV_CONTRACT_ADDRESS           = "0x18351d311d32201149a4df2a9fc2db8a"
)

func TestHandleNewBlock(t *testing.T) {
	starcoinManager := testGetLocalDevStarcoinManager(t)
	err := starcoinManager.handleNewBlock(14)
	if err != nil {
		fmt.Println(err)
		t.FailNow()
	}
}

func TestGetDomainNameStateAndSmtProof(t *testing.T) {
	starcoinManager := testGetLocalDevStarcoinManager(t)
	domainNameId := db.NewDomainNameId("stc", "d")
	state, proof, smtRoot, err := starcoinManager.GetDomainNameStateAndSmtProof(domainNameId)
	if err != nil {
		fmt.Println(err)
		t.FailNow()
	}
	fmt.Println("---------- state ----------")
	fmt.Println(state)
	fmt.Println("---------- proof ----------")
	j, _ := json.Marshal(proof)
	fmt.Println(string(j))
	fmt.Println("---------- root -----------")
	fmt.Println(hex.EncodeToString(smtRoot))
}

func TestBuildDomainNameEventSequenceForLastEventId(t *testing.T) {
	starcoinManager := testGetLocalDevStarcoinManager(t)
	es, err := starcoinManager.BuildDomainNameEventSequenceForLastEventId(21)
	if err != nil {
		fmt.Println(err)
		t.FailNow()
	}
	fmt.Println(es)
}

func TestBuildDomainNameEventSequence(t *testing.T) {
	starcoinManager := testGetLocalDevStarcoinManager(t)
	es, err := starcoinManager.BuildDomainNameEventSequence()
	if err != nil {
		fmt.Println(err)
		t.FailNow()
	}
	fmt.Println(es)
}

func TestGetLastAvailableDomainNameEvent(t *testing.T) {
	starcoinManager := testGetLocalDevStarcoinManager(t)
	e, err := starcoinManager.GetLastAvailableDomainNameEvent()
	if err != nil {
		fmt.Println(err)
		t.FailNow()
	}
	fmt.Println(e)
	// GET sequnece IDs
	ids, err := starcoinManager.getDomainNameEventSequenceElementIds(e, 4)
	if err != nil {
		fmt.Println(err)
		t.FailNow()
	}
	fmt.Println(ids)
}

func TestGetLastAvailableDomainNameEventSequence(t *testing.T) {
	starcoinManager := testGetLocalDevStarcoinManager(t)
	//eventId := uint64(1000000000)
	allEventIds, err := starcoinManager.GetLastAvailableDomainNameEventSequenceeAllElementIds()
	if err != nil {
		fmt.Println(err)
		t.FailNow()
	}
	fmt.Println(allEventIds)
	fmt.Println(len(allEventIds))
}

func testGetLocalDevStarcoinManager(t *testing.T) *StarcoinManager {
	url := STARCOIN_LOCAL_DEV_NETWORK_URL
	contractAddress := DEV_CONTRACT_ADDRESS
	starcoinClient := client.NewStarcoinClient(url)
	restClient := tools.NewRestClient()
	db, err := localDevDB()
	if err != nil {
		fmt.Println(err)
		t.FailNow()
	}
	starcoinManager, err := NewStarcoinManager(url, &starcoinClient, restClient, contractAddress, db)
	if err != nil {
		fmt.Println(err)
		t.FailNow()
	}
	return starcoinManager
}

func localDevDB() (*db.MySqlDB, error) {
	//CREATE SCHEMA `starcoin_ns_demo` DEFAULT CHARACTER SET utf8mb4 ;
	var dsn string = "root:123456@tcp(127.0.0.1:3306)/starcoin_ns_demo?charset=utf8mb4&parseTime=True&loc=Local"
	db, err := db.NewMySqlDB(dsn)
	if err != nil {
		return nil, err
	}
	return db, nil
}
