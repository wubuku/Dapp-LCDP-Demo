package vo

import (
	"starcoin-ns-demo/tools"
)

type DomainNameId struct {
	TopLevelDomain    string `json:"top_level_domain"`
	SecondLevelDomain string `json:"second_level_domain"`
}

func NewDomainNameId(tld string, sld string) *DomainNameId {
	return &DomainNameId{
		TopLevelDomain:    tld,
		SecondLevelDomain: sld,
	}
}

type DomainNameState struct {
	DomainNameId   DomainNameId `json:"domain_name_id"`
	ExpirationDate uint64       `json:"expiration_date"`
	Owner          string       `json:"owner"`
}

func NewDomainNameState(domainNameId *DomainNameId, expirationDate uint64, owner string) *DomainNameState {
	return &DomainNameState{
		DomainNameId:   *domainNameId,
		ExpirationDate: expirationDate,
		Owner:          owner,
	}
}

type SparseMerkleProof struct {
	SideNodes             []string `json:"side_nodes"`
	NonMembershipLeafData string   `json:"non_membership_leaf_data"`
}

func NewSparseMerkleProof(sideNodes []string, nonMembershipLeafData string) *SparseMerkleProof {
	return &SparseMerkleProof{
		SideNodes:             sideNodes,
		NonMembershipLeafData: nonMembershipLeafData,
	}
}

type DomainNameStateAndSmtProof struct {
	DomainNameState   *DomainNameState   `json:"domain_name_state"`
	SparseMerkleProof *SparseMerkleProof `json:"sparse_merkle_proof"`
	SmtRoot           string             `json:"smt_root"`
}

func NewDomainNameStateAndSmtProof(domainNameState *DomainNameState, sparseMerkleProof *SparseMerkleProof, smtRoot string) *DomainNameStateAndSmtProof {
	return &DomainNameStateAndSmtProof{
		DomainNameState:   domainNameState,
		SparseMerkleProof: sparseMerkleProof,
		SmtRoot:           smtRoot,
	}
}

func BytesSliceToHexSlice(a [][]byte) []string {
	ss := make([]string, 0, len(a))
	for _, s := range a {
		ss = append(ss, tools.EncodeToHex(s))
	}
	return ss
}

func HexSliceToBytesSlice(a []string) ([][]byte, error) {
	ss := make([][]byte, 0, len(a))
	for _, s := range a {
		bs, err := tools.HexToBytes(s)
		if err != nil {
			return nil, err
		}
		ss = append(ss, bs)
	}
	return ss, nil
}
