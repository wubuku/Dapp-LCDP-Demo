package main

import (
	"fmt"
	"starcoin-ns-demo/db"
	"starcoin-ns-demo/manager"
	"starcoin-ns-demo/tools"

	"github.com/gin-gonic/gin"
	"github.com/starcoinorg/starcoin-go/client"
)

var (
	starcoinManager *manager.StarcoinManager
)

func main() {
	var err error
	starcoinManager, err = getLocalDevStarcoinManager()
	if err != nil {
		fmt.Println("main - getLocalDevStarcoinManager error.")
		return
	}
	go starcoinManager.MonitorChain()
	go starcoinManager.UpdateDomainNameStates()

	// //////////// Web Services //////////////
	r := gin.Default()
	// r.GET("/ping", func(c *gin.Context) {
	// 	c.JSON(http.StatusOK, gin.H{
	// 		"message": "pong",
	// 	})
	// })
	r.GET("/getDomainNameStateAndSmtProof", getDomainNameStateAndSmtProof)
	r.GET("/domainNameStates", getDomainNameStates)
	r.GET("/domainNameStates/:id", getDomainNameState)
	r.Run("0.0.0.0:8099")
}

func getLocalDevStarcoinManager() (*manager.StarcoinManager, error) {
	url := tools.STARCOIN_LOCAL_DEV_NETWORK_URL
	contractAddress := tools.DEV_CONTRACT_ADDRESS
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
