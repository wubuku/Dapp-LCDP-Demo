package tools

import (
	"bytes"
	"hash"

	"golang.org/x/crypto/sha3"
)

func SmtDigest(data []byte) []byte {
	hasher := NewSmt256Hasher()
	hasher.Write(data)
	sum := hasher.Sum(nil)
	hasher.Reset()
	return sum
}

func NewSmt256Hasher() hash.Hash {
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

func SmtPlaceholder() []byte {
	return make([]byte, 32)
}

func IsSmtKeyRelatedWithLeafData(key []byte, leafData []byte) bool {
	return !IsSmtKeyUnrelatedWithLeafData(key, leafData)
}

func IsSmtKeyUnrelatedWithLeafData(key []byte, nonMembershipLeafData []byte) bool {
	//fmt.Println("NON-MEMBERSHIP-LEAF-DATA: " + hex.EncodeToString(nonMembershipLeafData))
	if len(nonMembershipLeafData) == 0 {
		return true
	}
	path := SmtDigest(key)
	return IsSmtPathUnrelatedWithLeafData(path, nonMembershipLeafData)
}

func IsSmtPathUnrelatedWithLeafData(path []byte, nonMembershipLeafData []byte) bool {
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
