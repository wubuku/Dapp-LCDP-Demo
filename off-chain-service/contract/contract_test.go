package contract

import (
	"fmt"
	"testing"

	"github.com/starcoinorg/starcoin-go/client"
)

const DEV_CONTRACT_ADDRESS = "0x18351d311d32201149a4df2a9fc2db8a"

func TestGetDomainNameSmtRoot(t *testing.T) {
	starcoinClient := localDevStarcoinClient()
	smtRoot, err := GetDomainNameSmtRoot(&starcoinClient, DEV_CONTRACT_ADDRESS)
	if err != nil {
		fmt.Println(err)
		t.FailNow()
	}
	fmt.Println(smtRoot)
}

func localDevStarcoinClient() client.StarcoinClient {
	starcoinClient := client.NewStarcoinClient("http://localhost:9850")
	return starcoinClient
}
