package contract

import (
	"context"
	"fmt"

	stcclient "github.com/starcoinorg/starcoin-go/client"
)

func GetDomainNameSmtRoot(starcoinClient *stcclient.StarcoinClient, contractAddress string) (string, error) {
	c := stcclient.ContractCall{
		FunctionId: contractAddress + "::DomainNameScripts::get_smt_root",
		TypeArgs:   []string{},
		Args:       []string{},
	}
	r, err := starcoinClient.CallContract(context.Background(), c)
	if err != nil {
		return "", err
	}
	if r == nil {
		return "", fmt.Errorf("get_smt_root() return nil.")
	}
	array := r.([]interface{})
	if len(array) != 1 {
		return "", fmt.Errorf("get_smt_root()  return error result.")
	}
	a1 := array[0].(string)
	return a1, nil
}
