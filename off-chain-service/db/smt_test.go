package db

import (
	"bytes"
	"encoding/hex"
	"fmt"
	"testing"

	"github.com/celestiaorg/smt"
)

// CONST var for test
var (
	testDomainNameExpirationDate uint64 = 1679184000000 // uint64(time.Date(2023, 3, 19, 0, 0, 0, 0, time.UTC).UnixNano() / 1000000)
	testDomainNameOwner          []byte
)

func init() {
	testDomainNameOwner, _ = hex.DecodeString("b6D69DD935EDf7f2054acF12eb884df8")
}

func TestSmtGetValue(t *testing.T) {
	nodeStore := smt.NewSimpleMap()
	// db, err := devNetDB()
	// if err != nil {
	// 	fmt.Println(err)
	// 	t.FailNow()
	// }
	// nodeStore, err := db.NewDomainNameSmtNodeMapStore()
	// if err != nil {
	// 	fmt.Println(err)
	// 	t.FailNow()
	// }
	valueStore := smt.NewSimpleMap()
	smt := smt.NewSparseMerkleTree(nodeStore, valueStore, New256Hasher())
	var key []byte
	var value []byte
	var err error
	var domainNameId *DomainNameId
	// ///////////////////////
	domainNameId, key = testGetDomainNameIdAndKey("stc", "a", t)
	testUpdateDomainNameSmt(domainNameId, smt, t)

	// ////////// Get SMT get value by key /////////////
	fmt.Println(smt.Root())
	expectedValue, _ := hex.DecodeString("03737463016100742af786010000b6d69dd935edf7f2054acf12eb884df8")
	value, err = smt.Get(key)
	if err != nil {
		fmt.Println(err)
		t.FailNow()
	}
	fmt.Println("value:")
	fmt.Println(hex.EncodeToString(value))
	fmt.Println("expectedValue:")
	fmt.Println(hex.EncodeToString(expectedValue))
	if !bytes.Equal(expectedValue, value) {
		fmt.Println("!bytes.Equal(expectedRoot, smt.Root())")
		t.FailNow()
	}
}

func TestSmtProveAndPrintMoveUnitTest(t *testing.T) {
	// Initialise two new key-value store to store the nodes and values of the tree
	nodeStore := smt.NewSimpleMap()
	// db, err := devNetDB()
	// if err != nil {
	// 	fmt.Println(err)
	// 	t.FailNow()
	// }
	// nodeStore, err := db.NewDomainNameSmtNodeMapStore()
	// if err != nil {
	// 	fmt.Println(err)
	// 	t.FailNow()
	// }
	valueStore := smt.NewSimpleMap()
	smt := smt.NewSparseMerkleTree(nodeStore, valueStore, New256Hasher())
	var key []byte
	var domainNameId *DomainNameId

	// ///////////////////////
	domainNameId, key = testGetDomainNameIdAndKey("stc", "a", t)
	testSmtProofAndPrintMoveUnitTestFun(domainNameId, key, smt, t)

	domainNameId, key = testGetDomainNameIdAndKey("stc", "b", t)
	testSmtProofAndPrintMoveUnitTestFun(domainNameId, key, smt, t)

	domainNameId, key = testGetDomainNameIdAndKey("stc", "c", t)
	testSmtProofAndPrintMoveUnitTestFun(domainNameId, key, smt, t)

	domainNameId, key = testGetDomainNameIdAndKey("stc", "d", t)
	testSmtProofAndPrintMoveUnitTestFun(domainNameId, key, smt, t)

	domainNameId, key = testGetDomainNameIdAndKey("stc", "e", t)
	testSmtProofAndPrintMoveUnitTestFun(domainNameId, key, smt, t)

	domainNameId, key = testGetDomainNameIdAndKey("stc", "f", t)
	testSmtProofAndPrintMoveUnitTestFun(domainNameId, key, smt, t)

	domainNameId, key = testGetDomainNameIdAndKey("stc", "g", t)
	testSmtProofAndPrintMoveUnitTestFun(domainNameId, key, smt, t)
}

func testSmtProofAndPrintMoveUnitTestFun(domainNameId *DomainNameId, key []byte, smt *smt.SparseMerkleTree, t *testing.T) {
	testPrintMoveUnitTestFunStart(domainNameId)
	testPrintMoveNonMembershipRootAndProof(smt, key, t)
	testPrintMoveUnitTestFunVerifyNonMembership()
	testUpdateDomainNameSmt(domainNameId, smt, t)
	testPrintMoveMembershipRootAndProof(smt, key, t)
	testPrintMoveUnitTestFunEnd()

}

func testPrintMoveUnitTestFunStart(domainNameId *DomainNameId) {
	fmt.Printf(`
    #[test]
    public fun test_smt_verify_proofs_%s() {
        let tld = b"%s";
        let sld = b"%s";	
        let domain_name_id = DomainName::new_domain_name_id(&tld, &sld);
        let key = DomainNameAggregate::get_smt_key(&domain_name_id);
        let domain_name_state = get_test_domain_name_state(&domain_name_id);
        let value = DomainNameAggregate::get_smt_value(&domain_name_state);
		`, domainNameId.SecondLevelDomain, domainNameId.TopLevelDomain, domainNameId.SecondLevelDomain)
	fmt.Println()
}

func testPrintMoveUnitTestFunVerifyNonMembership() {
	fmt.Println(`
        let non_member_v_ok = SMTProofs::verify_non_membership_proof_by_key(&non_member_root, &non_member_leaf_data, &non_member_side_nodes, &key);
        assert(non_member_v_ok, 1167);	
	`)
}

func testPrintMoveUnitTestFunEnd() {
	fmt.Println(`
        let member_v_ok = SMTProofs::verify_membership_proof_by_key_value(&member_root, &member_side_nodes, &key, &value, true);
        assert(member_v_ok, 1168);
    }	
	`)
}

func testPrintMoveNonMembershipRootAndProof(smt *smt.SparseMerkleTree, key []byte, t *testing.T) {
	fmt.Println("        // -------------------------------------------------------")
	fmt.Printf("        let non_member_root = x\"%s\";\n", hex.EncodeToString(smt.Root()))
	proof, err := smt.Prove(key)
	if err != nil {
		fmt.Println(err)
		t.FailNow()
	}
	fmt.Printf("        let non_member_leaf_data = x\"%s\";\n", hex.EncodeToString(proof.NonMembershipLeafData))
	fmt.Println("        let non_member_side_nodes = Vector::empty<vector<u8>>();")
	for _, s := range proof.SideNodes {
		fmt.Printf("        Vector::push_back(&mut non_member_side_nodes, x\"%s\");\n", hex.EncodeToString(s))
	}
}

func testPrintMoveMembershipRootAndProof(smt *smt.SparseMerkleTree, key []byte, t *testing.T) {
	fmt.Println("        // -------------------------------------------------------")
	fmt.Printf("        let member_root = x\"%s\";\n", hex.EncodeToString(smt.Root()))
	proof, err := smt.Prove(key)
	if err != nil {
		fmt.Println(err)
		t.FailNow()
	}
	//fmt.Printf("        let member_leaf_data = x\"%s\";\n", hex.EncodeToString(proof.NonMembershipLeafData))
	fmt.Println("        let member_side_nodes = Vector::empty<vector<u8>>();")
	for _, s := range proof.SideNodes {
		fmt.Printf("        Vector::push_back(&mut member_side_nodes, x\"%s\");\n", hex.EncodeToString(s))
	}
}

func testGetDomainNameIdAndKey(tld string, sld string, t *testing.T) (*DomainNameId, []byte) {
	domainNameId := NewDomainNameId(tld, sld)
	key := testGetDomainNameKey(domainNameId, t)
	// fmt.Println("key:")
	// fmt.Println(key)
	return domainNameId, key
}

func testGetDomainNameKey(domainNameId *DomainNameId, t *testing.T) []byte {
	key, err := domainNameId.BcsSerialize()
	if err != nil {
		fmt.Println(err)
		t.FailNow()
	}
	return key
}

func testUpdateDomainNameSmt(domainNameId *DomainNameId, smt *smt.SparseMerkleTree, t *testing.T) ([]byte, []byte) {
	key := testGetDomainNameKey(domainNameId, t)
	var value []byte
	var err error
	domainNameState := NewDomainNameState(domainNameId, testDomainNameExpirationDate, testDomainNameOwner)
	value, err = domainNameState.BcsSerialize()
	if err != nil {
		fmt.Println(err)
		t.FailNow()
	}
	//fmt.Println("value:")
	//fmt.Println(value)
	smt.Update(key, value)

	fmt.Printf("        // -------- Domain name '%s.%s' added. --------\n", domainNameId.TopLevelDomain, domainNameId.SecondLevelDomain)
	return key, value
}
