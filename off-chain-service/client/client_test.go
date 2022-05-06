package client

import (
	"encoding/json"
	"errors"
	"fmt"
	"os"
	"starcoin-ns-demo/tools"
	"testing"

	stcclient "github.com/starcoinorg/starcoin-go/client"
)

const DEV_CONTRACT_ADDRESS = "0x18351d311d32201149a4df2a9fc2db8a"
const LOCAL_OFF_CHAIN_SERVICE_BASE_URL = "http://localhost:8099"

func TestGetDomainNameStateAndSmtProof(t *testing.T) {
	client := DomainNameClient{
		baseUrl:    LOCAL_OFF_CHAIN_SERVICE_BASE_URL,
		restClient: tools.NewRestClient(),
	}
	stateAndProof, err := client.GetDomainNameStateAndSmtProof("stc", "l", "")
	if err != nil {
		fmt.Println(err)
		t.FailNow()
	}
	j, err := json.Marshal(stateAndProof)
	if err != nil {
		fmt.Println(err)
		t.FailNow()
	}
	fmt.Println(string(j))
}

func TestRegisterDomainName(t *testing.T) {
	sld := "jy"
	client, err := localDevDomainNameClient()
	if err != nil {
		fmt.Println(err)
		t.FailNow()
	}
	stateAndProof, err := client.GetDomainNameStateAndSmtProof("stc", sld, "")
	if err != nil {
		fmt.Println(err)
		t.FailNow()
	}
	if stateAndProof.DomainNameState != nil {
		fmt.Println("DomainName already exists.")
		t.FailNow()
	}
	err = client.Register("stc", sld, 10000000, stateAndProof.SmtRoot, stateAndProof.SparseMerkleProof)
	if err != nil {
		fmt.Println("client.Register error: " + err.Error())
		t.FailNow()
	}
}

func TestRenewDomainName(t *testing.T) {
	sld := "jy"
	client, err := localDevDomainNameClient()
	if err != nil {
		fmt.Println(err)
		t.FailNow()
	}
	stateAndProof, err := client.GetDomainNameStateAndSmtProof("stc", sld, "")
	if err != nil {
		fmt.Println(err)
		t.FailNow()
	}
	if stateAndProof.DomainNameState == nil {
		fmt.Println("DomainName dosen't exist.")
		t.FailNow()
	}
	err = client.Renew("stc", sld, 10000000, stateAndProof.DomainNameState, stateAndProof.SmtRoot, stateAndProof.SparseMerkleProof)
	if err != nil {
		fmt.Println("client.Renew error: " + err.Error())
		t.FailNow()
	}
}

func localDevDomainNameClient() (*DomainNameClient, error) {
	starcoinClient := localDevStarcoinClient()
	privateKeyConfig, err := localDevAlicePrivateKeyConfig()
	if err != nil {
		return nil, err
	}
	client := NewDomainNameClient(
		LOCAL_OFF_CHAIN_SERVICE_BASE_URL,
		&starcoinClient,
		privateKeyConfig,
		DEV_CONTRACT_ADDRESS,
	)
	return client, nil
}

func localDevStarcoinClient() stcclient.StarcoinClient {
	starcoinClient := stcclient.NewStarcoinClient("http://localhost:9850")
	return starcoinClient
}

func localDevAlicePrivateKeyConfig() (map[string]string, error) {
	privateKeyConfig := make(map[string]string)
	account, privateKey, err := localDevAliceAccountAddressAndPrivateKey()
	if err != nil {
		return nil, err
	}
	privateKeyConfig[account] = privateKey
	return privateKeyConfig, nil
}

func localDevAliceAccountAddressAndPrivateKey() (string, string, error) {
	account := "0xb6d69dd935edf7f2054acf12eb884df8" //os.Getenv("0xb6d69dd935edf7f2054acf12eb884df8")
	if account == "" {
		return "", "", errors.New("Plz. provide account address.")
	}
	privateKey := os.Getenv("PRIVATE_KEY_b6d69dd")
	if privateKey == "" {
		return "", "", errors.New("Plz. privide private key.")
	}
	return account, privateKey, nil
}
