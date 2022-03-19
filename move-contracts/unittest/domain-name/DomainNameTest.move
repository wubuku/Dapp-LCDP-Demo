address 0x18351d311d32201149a4df2a9fc2db8a {
module DomainNameTest {
    use 0x1::Debug;
    use 0x1::BCS;
    use 0x1::Vector;
    use 0x18351d311d32201149a4df2a9fc2db8a::DomainName;
    use 0x18351d311d32201149a4df2a9fc2db8a::DomainNameAggregate;
    use 0x18351d311d32201149a4df2a9fc2db8a::SMTProofs;

    const TEST_EXPIRATION_DATE: u64 = 1679184000000;
    const TEST_OWNER: address = @0xb6D69DD935EDf7f2054acF12eb884df8;

    fun get_test_domain_name_state(domain_name_id: &DomainName::DomainNameId): DomainName::DomainNameState {
        DomainName::new_domain_name_state(domain_name_id, TEST_EXPIRATION_DATE, TEST_OWNER)
    }

    #[test]
    public fun test_bcs_serialize() {
        let tld = b"stc";
        let sld = b"a";
        let domain_name_id = DomainName::new_domain_name_id(&tld, &sld);
        let domain_name_state = DomainName::new_domain_name_state(&domain_name_id, TEST_EXPIRATION_DATE, TEST_OWNER);

        let key = BCS::to_bytes<DomainName::DomainNameId>(&domain_name_id);
        let value = BCS::to_bytes<DomainName::DomainNameState>(&domain_name_state);

        Debug::print<vector<u8>>(&key);
        Debug::print<vector<u8>>(&value);

        let expected_value = x"03737463016100742af786010000b6d69dd935edf7f2054acf12eb884df8";
        assert(expected_value == value, 1166)
    }

    #[test]
    public fun test_smt_verify_proofs_a() {
        let tld = b"stc";
        let sld = b"a";
        let domain_name_id = DomainName::new_domain_name_id(&tld, &sld);
        let key = DomainNameAggregate::get_smt_key(&domain_name_id);
        let domain_name_state = get_test_domain_name_state(&domain_name_id);
        let value = DomainNameAggregate::get_smt_value(&domain_name_state);

        let non_member_root = x"0000000000000000000000000000000000000000000000000000000000000000";
        let non_member_leaf_data = x"";
        let non_member_side_nodes = Vector::empty<vector<u8>>();
        let non_member_v_ok = SMTProofs::verify_non_membership_proof_by_key(&non_member_root, &non_member_leaf_data, &non_member_side_nodes, &key);
        assert(non_member_v_ok, 1167);

        let member_root = x"e775f588f3f8e6983a7c0d4dd5bda65fab01176c7c3d7b6edb893762e0157dfd";
        let member_side_nodes = Vector::empty<vector<u8>>();
        let member_v_ok = SMTProofs::verify_membership_proof_by_key_value(&member_root, &member_side_nodes, &key, &value, true);
        assert(member_v_ok, 1168);
    }


    #[test]
    public fun test_smt_verify_proofs_b() {
        let tld = b"stc";
        let sld = b"b";
        let domain_name_id = DomainName::new_domain_name_id(&tld, &sld);
        let key = DomainNameAggregate::get_smt_key(&domain_name_id);
        let domain_name_state = get_test_domain_name_state(&domain_name_id);
        let value = DomainNameAggregate::get_smt_value(&domain_name_state);
        // -------------------------------------------------------
        let non_member_root = x"e775f588f3f8e6983a7c0d4dd5bda65fab01176c7c3d7b6edb893762e0157dfd";
        let non_member_leaf_data = x"00da3ba650fe753c7062ce70ef9ec9b4ac04bbb1af0b35cfc6c642912f4c0bc3fb78301c1c83cf9bf2f58989a91cb93fff4f19c75f85598115377b20bb953a46c6";
        let non_member_side_nodes = Vector::empty<vector<u8>>();

        let non_member_v_ok = SMTProofs::verify_non_membership_proof_by_key(&non_member_root, &non_member_leaf_data, &non_member_side_nodes, &key);
        assert(non_member_v_ok, 1167);

        // -------- Domain name 'stc.b' added. --------
        // -------------------------------------------------------
        let member_root = x"5386e70bd4690321161b87bd25955273aae58253f211ac88468177fed160ff24";
        let member_side_nodes = Vector::empty<vector<u8>>();
        Vector::push_back(&mut member_side_nodes, x"e775f588f3f8e6983a7c0d4dd5bda65fab01176c7c3d7b6edb893762e0157dfd");
        Vector::push_back(&mut member_side_nodes, x"0000000000000000000000000000000000000000000000000000000000000000");
        Vector::push_back(&mut member_side_nodes, x"0000000000000000000000000000000000000000000000000000000000000000");
        Vector::push_back(&mut member_side_nodes, x"0000000000000000000000000000000000000000000000000000000000000000");
        Vector::push_back(&mut member_side_nodes, x"0000000000000000000000000000000000000000000000000000000000000000");

        let member_v_ok = SMTProofs::verify_membership_proof_by_key_value(&member_root, &member_side_nodes, &key, &value, true);
        assert(member_v_ok, 1168);
    }


    #[test]
    public fun test_smt_verify_proofs_c() {
        let tld = b"stc";
        let sld = b"c";
        let domain_name_id = DomainName::new_domain_name_id(&tld, &sld);
        let key = DomainNameAggregate::get_smt_key(&domain_name_id);
        let domain_name_state = get_test_domain_name_state(&domain_name_id);
        let value = DomainNameAggregate::get_smt_value(&domain_name_state);
        // -------------------------------------------------------
        let non_member_root = x"5386e70bd4690321161b87bd25955273aae58253f211ac88468177fed160ff24";
        let non_member_leaf_data = x"";
        let non_member_side_nodes = Vector::empty<vector<u8>>();
        Vector::push_back(&mut non_member_side_nodes, x"237afd7c7dacd14ded4aa40751f0479d68ce1ace8ad138ef7c435b8046d21832");

        let non_member_v_ok = SMTProofs::verify_non_membership_proof_by_key(&non_member_root, &non_member_leaf_data, &non_member_side_nodes, &key);
        assert(non_member_v_ok, 1167);

        // -------- Domain name 'stc.c' added. --------
        // -------------------------------------------------------
        let member_root = x"9cd3a823fb02a2c657b1a8f5a037f45466dd3cc811f383d33135c817c8f6b018";
        let member_side_nodes = Vector::empty<vector<u8>>();
        Vector::push_back(&mut member_side_nodes, x"237afd7c7dacd14ded4aa40751f0479d68ce1ace8ad138ef7c435b8046d21832");

        let member_v_ok = SMTProofs::verify_membership_proof_by_key_value(&member_root, &member_side_nodes, &key, &value, true);
        assert(member_v_ok, 1168);
    }


    #[test]
    public fun test_smt_verify_proofs_d() {
        let tld = b"stc";
        let sld = b"d";
        let domain_name_id = DomainName::new_domain_name_id(&tld, &sld);
        let key = DomainNameAggregate::get_smt_key(&domain_name_id);
        let domain_name_state = get_test_domain_name_state(&domain_name_id);
        let value = DomainNameAggregate::get_smt_value(&domain_name_state);
        // -------------------------------------------------------
        let non_member_root = x"9cd3a823fb02a2c657b1a8f5a037f45466dd3cc811f383d33135c817c8f6b018";
        let non_member_leaf_data = x"";
        let non_member_side_nodes = Vector::empty<vector<u8>>();
        Vector::push_back(&mut non_member_side_nodes, x"42ce1cd67b80a586a6886ac4dfe8d62d39b50d41ebb29023801c575d75dec340");
        Vector::push_back(&mut non_member_side_nodes, x"11afac5a351885a1b72e2f1ede7e755f50fcb157e5af441811f03e12260f91c2");

        let non_member_v_ok = SMTProofs::verify_non_membership_proof_by_key(&non_member_root, &non_member_leaf_data, &non_member_side_nodes, &key);
        assert(non_member_v_ok, 1167);

        // -------- Domain name 'stc.d' added. --------
        // -------------------------------------------------------
        let member_root = x"555043594d41809fd5a91e644f1e6d8b062f9c62bb77bc25100e25a2cfcc2443";
        let member_side_nodes = Vector::empty<vector<u8>>();
        Vector::push_back(&mut member_side_nodes, x"42ce1cd67b80a586a6886ac4dfe8d62d39b50d41ebb29023801c575d75dec340");
        Vector::push_back(&mut member_side_nodes, x"11afac5a351885a1b72e2f1ede7e755f50fcb157e5af441811f03e12260f91c2");

        let member_v_ok = SMTProofs::verify_membership_proof_by_key_value(&member_root, &member_side_nodes, &key, &value, true);
        assert(member_v_ok, 1168);
    }


    #[test]
    public fun test_smt_verify_proofs_e() {
        let tld = b"stc";
        let sld = b"e";
        let domain_name_id = DomainName::new_domain_name_id(&tld, &sld);
        let key = DomainNameAggregate::get_smt_key(&domain_name_id);
        let domain_name_state = get_test_domain_name_state(&domain_name_id);
        let value = DomainNameAggregate::get_smt_value(&domain_name_state);
        // -------------------------------------------------------
        let non_member_root = x"555043594d41809fd5a91e644f1e6d8b062f9c62bb77bc25100e25a2cfcc2443";
        let non_member_leaf_data = x"007b8767d0cedca549b2d6744e61196a4c00125032c5bcb951955a61e9e580261336fa2a20ed028bc9971e3275f05d8cb38eb12061285bc192f45b9083c659fb32";
        let non_member_side_nodes = Vector::empty<vector<u8>>();
        Vector::push_back(&mut non_member_side_nodes, x"4f106e9e0fd4e061a913e7d6d27dc197e15ff331406f2e045fea332e028e5749");

        let non_member_v_ok = SMTProofs::verify_non_membership_proof_by_key(&non_member_root, &non_member_leaf_data, &non_member_side_nodes, &key);
        assert(non_member_v_ok, 1167);

        // -------- Domain name 'stc.e' added. --------
        // -------------------------------------------------------
        let member_root = x"ec693775597dade84e4b72c096467f380cbfb102edebee4644e903e9f798e95f";
        let member_side_nodes = Vector::empty<vector<u8>>();
        Vector::push_back(&mut member_side_nodes, x"11afac5a351885a1b72e2f1ede7e755f50fcb157e5af441811f03e12260f91c2");
        Vector::push_back(&mut member_side_nodes, x"4f106e9e0fd4e061a913e7d6d27dc197e15ff331406f2e045fea332e028e5749");

        let member_v_ok = SMTProofs::verify_membership_proof_by_key_value(&member_root, &member_side_nodes, &key, &value, true);
        assert(member_v_ok, 1168);
    }


    #[test]
    public fun test_smt_verify_proofs_f() {
        let tld = b"stc";
        let sld = b"f";
        let domain_name_id = DomainName::new_domain_name_id(&tld, &sld);
        let key = DomainNameAggregate::get_smt_key(&domain_name_id);
        let domain_name_state = get_test_domain_name_state(&domain_name_id);
        let value = DomainNameAggregate::get_smt_value(&domain_name_state);
        // -------------------------------------------------------
        let non_member_root = x"ec693775597dade84e4b72c096467f380cbfb102edebee4644e903e9f798e95f";
        let non_member_leaf_data = x"0090e3b801701d5df368daea9c8b4738afe1e18d491824431b538ce4aa76dbcb994efd6c3f7192a46b4eb31183d886759e2611d9dff4ed4256c8b91407bacf9297";
        let non_member_side_nodes = Vector::empty<vector<u8>>();
        Vector::push_back(&mut non_member_side_nodes, x"42ce1cd67b80a586a6886ac4dfe8d62d39b50d41ebb29023801c575d75dec340");
        Vector::push_back(&mut non_member_side_nodes, x"b36de233f7b6ecf32d1714f6c869c4c78c5c964275e5a72d3ab0ecf0d1c37c1e");

        let non_member_v_ok = SMTProofs::verify_non_membership_proof_by_key(&non_member_root, &non_member_leaf_data, &non_member_side_nodes, &key);
        assert(non_member_v_ok, 1167);

        // -------- Domain name 'stc.f' added. --------
        // -------------------------------------------------------
        let member_root = x"771810f2c466af5ca6bd340cdb42d8dff9647fa8f8c678c5ad4264494c1f3f90";
        let member_side_nodes = Vector::empty<vector<u8>>();
        Vector::push_back(&mut member_side_nodes, x"27422972ae2b1e5b72ec4ed49c5303df174077f5df8b41cc01abea42b410f727");
        Vector::push_back(&mut member_side_nodes, x"0000000000000000000000000000000000000000000000000000000000000000");
        Vector::push_back(&mut member_side_nodes, x"0000000000000000000000000000000000000000000000000000000000000000");
        Vector::push_back(&mut member_side_nodes, x"0000000000000000000000000000000000000000000000000000000000000000");
        Vector::push_back(&mut member_side_nodes, x"42ce1cd67b80a586a6886ac4dfe8d62d39b50d41ebb29023801c575d75dec340");
        Vector::push_back(&mut member_side_nodes, x"b36de233f7b6ecf32d1714f6c869c4c78c5c964275e5a72d3ab0ecf0d1c37c1e");

        let member_v_ok = SMTProofs::verify_membership_proof_by_key_value(&member_root, &member_side_nodes, &key, &value, true);
        assert(member_v_ok, 1168);
    }


    #[test]
    public fun test_smt_verify_proofs_g() {
        let tld = b"stc";
        let sld = b"g";
        let domain_name_id = DomainName::new_domain_name_id(&tld, &sld);
        let key = DomainNameAggregate::get_smt_key(&domain_name_id);
        let domain_name_state = get_test_domain_name_state(&domain_name_id);
        let value = DomainNameAggregate::get_smt_value(&domain_name_state);
        // -------------------------------------------------------
        let non_member_root = x"771810f2c466af5ca6bd340cdb42d8dff9647fa8f8c678c5ad4264494c1f3f90";
        let non_member_leaf_data = x"";
        let non_member_side_nodes = Vector::empty<vector<u8>>();
        Vector::push_back(&mut non_member_side_nodes, x"356bd1ff7027fd81fc69ec943796404af4226dc95e04784f2b8f4bcee03a37f7");
        Vector::push_back(&mut non_member_side_nodes, x"e0431ca32d842482ad438aa9e5ba8a2ba39725f53c8bd097a9910b7bb2437b07");
        Vector::push_back(&mut non_member_side_nodes, x"b36de233f7b6ecf32d1714f6c869c4c78c5c964275e5a72d3ab0ecf0d1c37c1e");

        let non_member_v_ok = SMTProofs::verify_non_membership_proof_by_key(&non_member_root, &non_member_leaf_data, &non_member_side_nodes, &key);
        assert(non_member_v_ok, 1167);

        // -------- Domain name 'stc.g' added. --------
        // -------------------------------------------------------
        let member_root = x"57f0d0786736ee370a8718c54e0cf87e3709b91ef618c9f1d444d208420e16dd";
        let member_side_nodes = Vector::empty<vector<u8>>();
        Vector::push_back(&mut member_side_nodes, x"356bd1ff7027fd81fc69ec943796404af4226dc95e04784f2b8f4bcee03a37f7");
        Vector::push_back(&mut member_side_nodes, x"e0431ca32d842482ad438aa9e5ba8a2ba39725f53c8bd097a9910b7bb2437b07");
        Vector::push_back(&mut member_side_nodes, x"b36de233f7b6ecf32d1714f6c869c4c78c5c964275e5a72d3ab0ecf0d1c37c1e");

        let member_v_ok = SMTProofs::verify_membership_proof_by_key_value(&member_root, &member_side_nodes, &key, &value, true);
        assert(member_v_ok, 1168);
    }
}
}
