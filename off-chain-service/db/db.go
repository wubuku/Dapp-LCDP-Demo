package db

import (
	"encoding/hex"
	"encoding/json"
	"fmt"
	"starcoin-ns-demo/tools"
)

// Hex string to Starcoin account address.
func HexToAccountAddress(value string) ([16]uint8, error) {
	bytes, err := hex.DecodeString(value)
	if err != nil {
		return [16]uint8{}, err
	}
	if len(bytes) != 16 {
		return [16]uint8{}, fmt.Errorf("account address length err: %v", len(bytes))
	}
	var addr [16]uint8
	for i := 0; i < 16; i++ {
		addr[i] = bytes[i]
	}
	return addr, nil
}

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
