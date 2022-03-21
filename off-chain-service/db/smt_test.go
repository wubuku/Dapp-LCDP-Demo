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

// func TestPrintDate(t *testing.T) {
// 	d1 := uint64(time.Date(2023, 3, 19, 0, 0, 0, 0, time.UTC).UnixNano() / 1000000)
// 	d2 := uint64(time.Date(2022, 3, 19, 0, 0, 0, 0, time.UTC).UnixNano() / 1000000)
// 	fmt.Println(d1)
// 	fmt.Println(d2)
// 	fmt.Println(d1 - d2)
// 	fmt.Println(1000 * 60 * 60 * 24 * 365)
// }

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
	// //////////// New MySQL node MapStore /////////////////
	//nodeStore := smt.NewSimpleMap()
	db, err := devNetDB()
	if err != nil {
		fmt.Println(err)
		t.FailNow()
	}
	nodeStore, err := db.NewDomainNameSmtNodeMapStore()
	if err != nil {
		fmt.Println(err)
		t.FailNow()
	}
	// //////////// New MySQL value MapStore /////////////////
	//valueStore := smt.NewSimpleMap()
	valueStore := db.NewDomainNameSmtValueMapStore()

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

// func TestPrintHex(t *testing.T) {
// 	//SMT root with only one leaf(DomainName):
// 	//   TLD: stc
// 	//   SLD: a
// 	// 	 Expiration Date: 1679184000000
// 	//   Owner: b6D69DD935EDf7f2054acF12eb884df8
// 	bs := []byte{231, 117, 245, 136, 243, 248, 230, 152, 58, 124, 13, 77, 213, 189, 166, 95, 171, 1, 23, 108, 124, 61, 123, 110, 219, 137, 55, 98, 224, 21, 125, 253}
// 	fmt.Println(hex.EncodeToString(bs))
// 	fmt.Println("e775f588f3f8e6983a7c0d4dd5bda65fab01176c7c3d7b6edb893762e0157dfd")
// }

func TestSmtProveAndPrintMoveRegisterFunctionalTest(t *testing.T) {
	nodeStore := smt.NewSimpleMap()
	valueStore := smt.NewSimpleMap()
	smt := smt.NewSparseMerkleTree(nodeStore, valueStore, New256Hasher())
	var key []byte
	var domainNameId *DomainNameId

	// ///////////////////////
	domainNameId, key = testGetDomainNameIdAndKey("stc", "a", t)
	testSmtProofAndPrintMoveRegisterFunctionalTestFun(domainNameId, key, smt, t)

	domainNameId, key = testGetDomainNameIdAndKey("stc", "b", t)
	testSmtProofAndPrintMoveRegisterFunctionalTestFun(domainNameId, key, smt, t)

	domainNameId, key = testGetDomainNameIdAndKey("stc", "c", t)
	testSmtProofAndPrintMoveRegisterFunctionalTestFun(domainNameId, key, smt, t)

	domainNameId, key = testGetDomainNameIdAndKey("stc", "d", t)
	testSmtProofAndPrintMoveRegisterFunctionalTestFun(domainNameId, key, smt, t)

	domainNameId, key = testGetDomainNameIdAndKey("stc", "e", t)
	testSmtProofAndPrintMoveRegisterFunctionalTestFun(domainNameId, key, smt, t)

	domainNameId, key = testGetDomainNameIdAndKey("stc", "f", t)
	testSmtProofAndPrintMoveRegisterFunctionalTestFun(domainNameId, key, smt, t)

	domainNameId, key = testGetDomainNameIdAndKey("stc", "g", t)
	testSmtProofAndPrintMoveRegisterFunctionalTestFun(domainNameId, key, smt, t)
}

//
//! account: alice, 0xb6D69DD935EDf7f2054acF12eb884df8, 10000000000 0x1::STC::STC
//
//
// ! block-prologue
// ! author: genesis
// ! block-number: 1
// ! block-time: 1647648000000
//

func testSmtProofAndPrintMoveRegisterFunctionalTestFun(domainNameId *DomainNameId, key []byte, smt *smt.SparseMerkleTree, t *testing.T) {
	testPrintMoveRegisterFunctionalTestScriptStart()
	testPrintMoveRegisterFunctionalTestFunStart(domainNameId.TopLevelDomain, domainNameId.SecondLevelDomain, t)
	testPrintMoveNonMembershipRootAndProof(smt, key, t)
	testPrintMoveRegisterFunctionalTestFunDoRegisterAndGetSmtRoot()
	testUpdateDomainNameSmt(domainNameId, smt, t)
	testPrintMoveRegisterFunctionalTestFunEnd(smt, key, t)
	testPrintMoveRegisterFunctionalTestScriptEnd()
}

func testPrintMoveRegisterFunctionalTestScriptStart() {
	fmt.Println(`
//! new-transaction
//! sender: alice
address alice = {{alice}};
script {
	use 0x1::Debug;
	use 0x1::Vector;
	//use 0x1::Timestamp;
	use 0x18351d311d32201149a4df2a9fc2db8a::DomainNameAggregate;
	`)
}

func testPrintMoveRegisterFunctionalTestScriptEnd() {
	fmt.Println(`
}
// check: EXECUTED
	`)
}

func testPrintMoveRegisterFunctionalTestFunStart(tld string, sld string, t *testing.T) {
	fmt.Printf(`
    fun test_register_%s(signer: signer) {
        let domain_name_id_top_level_domain = b"%s";
        let domain_name_id_second_level_domain = b"%s";
        let registration_period: u64 = 1000 * 60 * 60 * 24 * 365; // One year
        // Debug::print<u64>(&(Timestamp::now_milliseconds() + registration_period));	
	`, sld, tld, sld)
	fmt.Println()
}

func testPrintMoveRegisterFunctionalTestFunDoRegisterAndGetSmtRoot() {
	fmt.Println(`
        DomainNameAggregate::register(
            &signer,
            &domain_name_id_top_level_domain,
            &domain_name_id_second_level_domain,
            registration_period,
            &non_member_root,
            &non_member_leaf_data,
            &non_member_side_nodes,
        );
        let smt_root = DomainNameAggregate::get_smt_root();
	`)
}

func testPrintMoveRegisterFunctionalTestFunEnd(smt *smt.SparseMerkleTree, key []byte, t *testing.T) {
	proof, err := smt.Prove(key)
	if err != nil {
		fmt.Println(err)
		t.FailNow()
	}
	_ = proof
	var expected_smt_root string = hex.EncodeToString(smt.Root())
	fmt.Printf(`
        let expected_smt_root = x"%s";
        assert(expected_smt_root == *&smt_root, 1001);
        Debug::print<vector<u8>>(&smt_root);
    }
	`, expected_smt_root)
	fmt.Println()
}
