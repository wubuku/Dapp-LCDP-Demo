package client

import (
	"encoding/json"
	"fmt"
	"net/url"
	"starcoin-ns-demo/tools"
	"starcoin-ns-demo/transactions"
	"starcoin-ns-demo/vo"
	"time"

	stcclient "github.com/starcoinorg/starcoin-go/client"
)

type DomainNameClient struct {
	baseUrl          string                    // Off-chain service base URL
	restClient       *tools.RestClient         // REST client to communicate with off-chain service
	starcoinClient   *stcclient.StarcoinClient // StarcoinClient to communicate with on-chain contract
	privateKeyConfig map[string]string         // Private key config
	contractAddress  string                    // On-chain contract address
}

func NewDomainNameClient(baseUrl string, starcoinClient *stcclient.StarcoinClient, privateKeyConfig map[string]string, contractAddress string) *DomainNameClient {
	return &DomainNameClient{
		baseUrl:          baseUrl,
		restClient:       tools.NewRestClient(),
		starcoinClient:   starcoinClient,
		privateKeyConfig: privateKeyConfig,
		contractAddress:  contractAddress,
	}
}

func (client *DomainNameClient) Register(topLevelDomain string, secondLevelDomain string, registrationPeriod uint64, smtRoot string, sparseMerkleProof *vo.SparseMerkleProof) error {
	register_arg_smt_root, err := tools.HexToBytes(smtRoot)
	if err != nil {
		return err
	}
	register_arg_smt_non_membership_leaf_data, err := tools.HexToBytes(sparseMerkleProof.NonMembershipLeafData)
	if err != nil {
		return err
	}
	register_arg_smt_side_nodes, err := vo.HexSliceToBytesSlice(sparseMerkleProof.SideNodes)
	if err != nil {
		return err
	}
	register_tx_payload := transactions.EncodeDomainNameRegisterTxPaylaod(client.contractAddress,
		topLevelDomain,
		secondLevelDomain,
		registrationPeriod,
		register_arg_smt_root,
		register_arg_smt_non_membership_leaf_data,
		tools.ConcatBytesSlices(register_arg_smt_side_nodes),
	)
	txHash, err := tools.SubmitStarcoinTransaction(client.starcoinClient, client.privateKeyConfig, &register_tx_payload)
	if err != nil {
		return err
	}
	//fmt.Printf("Wating transaction(%s) to be confirmed...\n", txHash)
	ok, err := tools.WaitTransactionConfirm(client.starcoinClient, txHash, time.Minute*2)
	//fmt.Println(ok, err)
	if err != nil {
		return err
	}
	if !ok {
		return fmt.Errorf("unknown starcoin transaction error, hash: %s", txHash)
	}
	return nil
}

func (client *DomainNameClient) GetDomainNameStateAndSmtProof(topLevelDomain string, secondLevelDomain string, smtRoot string) (*vo.DomainNameStateAndSmtProof, error) {
	uRL, err := client.getServiceURL("getDomainNameStateAndSmtProof")
	if err != nil {
		return nil, err
	}
	q := uRL.Query()
	q.Set("top_level_domain", topLevelDomain)
	q.Set("second_level_domain", secondLevelDomain)
	q.Set("smt_root", smtRoot)
	uRL.RawQuery = q.Encode()
	body, err := client.restClient.SendGetRequest(uRL.String())
	if err != nil {
		return nil, err
	}
	stateAndProof := new(vo.DomainNameStateAndSmtProof)
	err = json.Unmarshal(body, stateAndProof)
	if err != nil {
		return nil, err
	}
	return stateAndProof, nil
}

// Get off-chain service URL by relative path.
func (client *DomainNameClient) getServiceURL(relativePath string) (*url.URL, error) {
	u, err := url.Parse(relativePath)
	if err != nil {
		return nil, err
	}
	base, err := url.Parse(client.baseUrl)
	if err != nil {
		return nil, err
	}
	return base.ResolveReference(u), nil
}
