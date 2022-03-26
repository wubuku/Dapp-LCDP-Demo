package tools

import (
	"bytes"
	"encoding/hex"
	"fmt"
	"hash"
	"math/big"
	"strings"

	"golang.org/x/crypto/sha3"
	//"github.com/leekchan/accounting"
)

// encode big int to hex string
func EncodeBigInt(b *big.Int) string {
	if b.Uint64() == 0 {
		return "00"
	}
	return hex.EncodeToString(b.Bytes())
}

// encode bytes to hex with prefix
func EncodeToHex(b []byte) string {
	return "0x" + hex.EncodeToString(b)
}

func HexWithPrefixToBytes(str string) ([]byte, error) {
	if !strings.HasPrefix(str, "0x") {
		return nil, fmt.Errorf("it does not have 0x prefix")
	}
	return hex.DecodeString(str[2:])
}

func HexToBytes(str string) ([]byte, error) {
	if !strings.HasPrefix(str, "0x") {
		return hex.DecodeString(str[:])
	}
	return hex.DecodeString(str[2:])
}

// func FormatTokenAmount(symbol string, precision int, amount *big.Int) string {
// 	ac := accounting.Accounting{
// 		Symbol:    symbol,
// 		Precision: precision,
// 	}
// 	return ac.FormatMoneyBigFloat(new(big.Float).Quo(new(big.Float).SetInt(amount), new(big.Float).SetInt(new(big.Int).Exp(big.NewInt(10), big.NewInt(int64(precision)), nil))))
// }

// func FormatUsdAmount(amount *big.Float) string {
// 	ac := accounting.Accounting{
// 		Symbol:    "$",
// 		Precision: 2,
// 	}
// 	return ac.FormatMoneyBigFloat(amount)
// }

func SmtDigest(data []byte) []byte {
	hasher := New256Hasher()
	hasher.Write(data)
	sum := hasher.Sum(nil)
	hasher.Reset()
	return sum
}

func New256Hasher() hash.Hash {
	return sha3.New256() //sha256.New()
}

func ParseSmtLeaf(data []byte) ([]byte, []byte) {
	return data[len(SmtLeafPrefix()) : SmtPathSize()+len(SmtLeafPrefix())], data[len(SmtLeafPrefix())+SmtPathSize():]
}

func SmtLeafPrefix() []byte {
	leafPrefix := []byte{0}
	//nodePrefix = []byte{1}
	return leafPrefix
}

func SmtPathSize() int {
	return 32
}

func IsSmtKeyAndLeafDataRelated(key []byte, leafData []byte) bool {
	return !IsSmtKeyAndLeafDataUnrelated(key, leafData)
}

func IsSmtKeyAndLeafDataUnrelated(key []byte, nonMembershipLeafData []byte) bool {
	//fmt.Println("NON-MEMBERSHIP-LEAF-DATA: " + hex.EncodeToString(nonMembershipLeafData))
	if len(nonMembershipLeafData) == 0 {
		return true
	}
	path := SmtDigest(key)
	return IsSmtPathAndLeafDataUnrelated(path, nonMembershipLeafData)
}

func IsSmtPathAndLeafDataUnrelated(path []byte, nonMembershipLeafData []byte) bool {
	//fmt.Println("LEAF-PATH: " + hex.EncodeToString(path))
	unrelated := false
	if len(nonMembershipLeafData) == 0 {
		unrelated = true
	} else {
		nonMemberPath, _ := ParseSmtLeaf(nonMembershipLeafData)
		if !bytes.Equal(nonMemberPath, path) {
			unrelated = true
		}
	}
	//fmt.Println("IS-UNRELATED-LEAF-DATA: " + strconv.FormatBool(unrelated))
	return unrelated
}
