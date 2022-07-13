package manager

import (
	"encoding/hex"
	"encoding/json"
	"fmt"
	"starcoin-ns-demo/db"
	"starcoin-ns-demo/tools"
	"strconv"
	"testing"
	"time"

	"github.com/starcoinorg/starcoin-go/client"
)

func TestBuildDomainNameEventSequencesAndStates(t *testing.T) {
	starcoinManager := testGetLocalDevStarcoinManager(t)
	e, err := starcoinManager.GetLastAvailableDomainNameEvent()
	if err != nil {
		t.FailNow()
	}
	eId_1 := e.Id / 2
	eId_2 := e.Id - 1
	// Build first event sequence and state snapshot table
	es_1, err := starcoinManager.BuildDomainNameEventSequenceForLastEventId(eId_1)
	if err != nil {
		t.FailNow()
	}
	tableNameSuffix_1 := strconv.FormatInt(time.Now().UnixNano()/1000000, 10)
	tableName_1 := getDomainNameStateTableNameByEventSequence(es_1.SequenceId, tableNameSuffix_1)
	_, err = starcoinManager.BuildDomainNameStateTableByEventSequence(tableName_1, es_1)
	if err != nil {
		t.FailNow()
	}
	fmt.Println("built state snapshot table: " + tableName_1)
	// Build second event sequence and state snapshot table
	es_2, err := starcoinManager.BuildDomainNameEventSequenceForLastEventId(eId_2)
	if err != nil {
		t.FailNow()
	}
	tableNameSuffix_2 := strconv.FormatInt(time.Now().UnixNano()/1000000, 10)
	tableName_2 := getDomainNameStateTableNameByEventSequence(es_2.SequenceId, tableNameSuffix_2)
	_, err = starcoinManager.BuildDomainNameStateTableByEventSequence(tableName_2, es_2)
	if err != nil {
		t.FailNow()
	}
	fmt.Println("built state snapshot table: " + tableName_2)
	// Will rebuild current domain_name_state view based on the second snapshot table
	headId := db.DOMAIN_NAME_STATE_HEAD_ID_DEFAULT
	basedOnTableName, err := starcoinManager.RebuildDomainNameStates(headId)
	if err != nil {
		t.FailNow()
	}
	fmt.Println("built state view based on the table: " + basedOnTableName)
}

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
	// then GET the sequnece's event IDs
	ids, err := starcoinManager.getDomainNameEventSequenceElementIds(e, 4)
	if err != nil {
		fmt.Println(err)
		t.FailNow()
	}
	fmt.Println(ids)
}

func TestGetLastAvailableDomainNameEventSequenceeAllElementIds(t *testing.T) {
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

func TestRebuildDomainNameStates(t *testing.T) {
	starcoinManager := testGetLocalDevStarcoinManager(t)
	//ts := strconv.FormatInt(time.Now().UnixNano()/1000000, 10) // timestamp as table suffix
	headId := db.DOMAIN_NAME_STATE_HEAD_ID_DEFAULT
	tableName, err := starcoinManager.RebuildDomainNameStates(headId)
	if err != nil {
		fmt.Println(err)
		t.FailNow()
	}
	fmt.Println("built state table name: " + tableName)
}

func testGetLocalDevStarcoinManager(t *testing.T) *StarcoinManager {
	url := tools.STARCOIN_LOCAL_DEV_NETWORK_URL
	contractAddress := tools.DEV_CONTRACT_ADDRESS
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
