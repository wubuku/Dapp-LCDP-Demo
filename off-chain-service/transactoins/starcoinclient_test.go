package transactoins

import (
	"errors"
	"fmt"
	"os"
	"starcoin-ns-demo/tools"
	"testing"
	"time"

	"github.com/starcoinorg/starcoin-go/client"
)

const DEV_CONTRACT_ADDRESS = "0x18351d311d32201149a4df2a9fc2db8a"
const ONE_YEAR_MILLS uint64 = 1000 * 60 * 60 * 24 * 365

var SMT_PLACEHOLDER []byte

func init() {
	SMT_PLACEHOLDER = make([]byte, 32)
}

func TestDomainNameInitGenesis(t *testing.T) {
	starcoinClient := localDevStarcoinClient()
	privateKeyConfig, err := localDevAdminPrivateKeyConfig()
	if err != nil {
		fmt.Println(err)
		t.FailNow()
	}
	init_genesis_tx_payload := EncodeEmptyArgsTxPaylaod(DEV_CONTRACT_ADDRESS+"::DomainNameScripts", "init_genesis")
	txHash, err := tools.SubmitStarcoinTransaction(&starcoinClient, privateKeyConfig, &init_genesis_tx_payload)
	if err != nil {
		fmt.Println(err)
		t.FailNow()
	}
	ok, err := tools.WaitTransactionConfirm(&starcoinClient, txHash, time.Minute*2)
	fmt.Println(ok, err)
	if !ok {
		t.FailNow()
	}
}

func TestDomainNameRegisterFirstDomain(t *testing.T) {
	starcoinClient := localDevStarcoinClient()
	privateKeyConfig, err := localDevAdminPrivateKeyConfig()
	if err != nil {
		fmt.Println(err)
		t.FailNow()
	}
	tld := "stc"
	sld := "a"
	register_tx_payload := EncodeDomainNameRegisterTxPaylaod(DEV_CONTRACT_ADDRESS, tld, sld, ONE_YEAR_MILLS, SMT_PLACEHOLDER, []byte{}, []byte{})
	txHash, err := tools.SubmitStarcoinTransaction(&starcoinClient, privateKeyConfig, &register_tx_payload)
	if err != nil {
		fmt.Println(err)
		t.FailNow()
	}
	ok, err := tools.WaitTransactionConfirm(&starcoinClient, txHash, time.Minute*2)
	fmt.Println(ok, err)
	if !ok {
		t.FailNow()
	}
}

func localDevStarcoinClient() client.StarcoinClient {
	starcoinClient := client.NewStarcoinClient("http://localhost:9850")
	return starcoinClient
}

func localDevAdminPrivateKeyConfig() (map[string]string, error) {
	privateKeyConfig := make(map[string]string)
	account, privateKey, err := localDevAdminAccountAddressAndPrivateKey()
	if err != nil {
		return nil, err
	}
	privateKeyConfig[account] = privateKey
	return privateKeyConfig, nil
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

func localDevAdminAccountAddressAndPrivateKey() (string, string, error) {
	account := "0x18351d311d32201149a4df2a9fc2db8a"
	if account == "" {
		return "", "", errors.New("Plz. provide account address.")
	}
	privateKey := os.Getenv("PRIVATE_KEY_18351d3")
	if privateKey == "" {
		return "", "", errors.New("Plz. privide private key.")
	}
	return account, privateKey, nil
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
