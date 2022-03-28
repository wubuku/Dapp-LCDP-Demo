package db

import (
	"encoding/hex"
	"fmt"
	"testing"

	"github.com/google/uuid"
)

func TestNewDomainNameStateHead(t *testing.T) {
	database, err := localDevDB()
	if err != nil {
		fmt.Println(err)
		t.FailNow()
	}
	uuid, _ := uuid.NewUUID()
	k, _ := uuid.MarshalBinary()
	blockHash := hex.EncodeToString(k)
	eventKey := "TESTEVETNKEY"
	smtRoot := blockHash
	head := NewDomainNameStateHead(DOMAIN_NAME_STATE_HEAD_ID_DEFAULT, blockHash, eventKey, smtRoot, DOMAIN_NAME_STATE_DEFAULT_TABLE_NAME)
	err = database.db.Save(head).Error
	if err != nil {
		fmt.Println(err)
		t.FailNow()
	}
}

func localDevDB() (*MySqlDB, error) {
	//CREATE SCHEMA `starcoin_ns_demo` DEFAULT CHARACTER SET utf8mb4 ;
	var dsn string = "root:123456@tcp(127.0.0.1:3306)/starcoin_ns_demo?charset=utf8mb4&parseTime=True&loc=Local"
	db, err := NewMySqlDB(dsn)
	if err != nil {
		return nil, err
	}
	return db, nil
}
