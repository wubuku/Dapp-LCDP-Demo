package db

import (
	"encoding/json"
	"starcoin-ns-demo/tools"
)

// Encode SMT proof side nodes to JSON string.
func EncodeSmtProofSideNodes(sideNodes [][]byte) (string, error) {
	ss := make([]string, 0, len(sideNodes))
	for _, s := range sideNodes {
		ss = append(ss, tools.EncodeToHex(s))
	}
	r, err := json.Marshal(ss)
	return string(r), err
}

// Decode JSON string to SMT proof side nodes.
func DecodeSmtProofSideNodes(s string) ([][]byte, error) {
	ss := &[]string{}
	err := json.Unmarshal([]byte(s), ss)
	if err != nil {
		return nil, err
	}
	bs := make([][]byte, 0, len(*ss))
	for _, v := range *ss {
		b, err := tools.HexToBytes(v)
		if err != nil {
			return nil, err
		}
		bs = append(bs, b)
	}
	return bs, nil
}
