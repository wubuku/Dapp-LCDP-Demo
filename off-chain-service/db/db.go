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

// Encode event IDs to JSON string.
func EncodeEventIds(eventIds []uint64) (string, error) {
	r, err := json.Marshal(eventIds)
	return string(r), err
}

// Decode JSON string to event IDs.
func DecodeEventIds(s string) ([]uint64, error) {
	ss := new([]uint64)
	err := json.Unmarshal([]byte(s), ss)
	if err != nil {
		return nil, err
	}
	return *ss, nil
}

func reverseUint64Slices(slices [][]uint64) [][]uint64 {
	for left, right := 0, len(slices)-1; left < right; left, right = left+1, right-1 {
		slices[left], slices[right] = slices[right], slices[left]
	}
	return slices
}
