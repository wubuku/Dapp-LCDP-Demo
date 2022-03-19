package db

import (
	"encoding/hex"
	"fmt"
	"hash"

	"golang.org/x/crypto/sha3"
)

func New256Hasher() hash.Hash {
	return sha3.New256() //sha256.New()
}

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
