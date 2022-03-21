package main

import (
	"fmt"
	"starcoin-ns-demo/db"
	"starcoin-ns-demo/manager"
	"starcoin-ns-demo/tools"
	"time"

	"github.com/starcoinorg/starcoin-go/client"
)

const (
	STARCOIN_LOCAL_DEV_NETWORK_URL = "http://localhost:9850"
	DEV_CONTRACT_ADDRESS           = "0x18351d311d32201149a4df2a9fc2db8a"
)

func main() {
	starcoinManager, err := getLocalDevStarcoinManager()
	if err != nil {
		fmt.Println("main - getLocalDevStarcoinManager error.")
		return
	}
	go starcoinManager.MonitorChain()
	for {
		time.Sleep(time.Hour)
	}
}

func getLocalDevStarcoinManager() (*manager.StarcoinManager, error) {
	url := STARCOIN_LOCAL_DEV_NETWORK_URL
	contractAddress := DEV_CONTRACT_ADDRESS
	starcoinClient := client.NewStarcoinClient(url)
	restClient := tools.NewRestClient()
	db, err := localDevDB()
	if err != nil {
		return nil, err
	}
	starcoinManager, err := manager.NewStarcoinManager(url, &starcoinClient, restClient, contractAddress, db)
	if err != nil {
		return nil, err
	}
	return starcoinManager, nil
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
