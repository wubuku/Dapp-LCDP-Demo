//! account: admin, 0x18351d311d32201149a4df2a9fc2db8a, 10000000000 0x1::STC::STC
//! account: alice, 0xb6D69DD935EDf7f2054acF12eb884df8, 10000000000 0x1::STC::STC
//! account: bob, 0x49156896A605F092ba1862C50a9036c9, 10000000000 0x1::STC::STC

//! new-transaction
//! sender: admin
address admin = {{admin}};
script {
    use 0x1::Debug;
    //use 0x1::Vector;
    use 0x18351d311d32201149a4df2a9fc2db8a::DomainNameAggregate;
    use 0x18351d311d32201149a4df2a9fc2db8a::SMTreeHasher;

    fun test_init_genesis(signer: signer) {
        DomainNameAggregate::init_genesis(&signer);
        let smt_root = DomainNameAggregate::get_smt_root();
        assert(SMTreeHasher::placeholder() == *&smt_root, 1001);
        Debug::print<vector<u8>>(&smt_root)
    }
}
// check: EXECUTED

//! block-prologue
//! author: genesis
//! block-number: 1
//! block-time: 1679174000000

//! new-transaction
//! sender: alice
address alice = {{alice}};
script {
    use 0x1::Debug;
    use 0x1::Vector;
    //use 0x1::Timestamp;
    use 0x18351d311d32201149a4df2a9fc2db8a::DomainNameAggregate;

    fun test_register_a(signer: signer) {
        let domain_name_id_top_level_domain = b"stc";
        let domain_name_id_second_level_domain = b"a";
        let registration_period: u64 = 10000000; // need to set: //! block-time: 1679174000000
        // Debug::print<u64>(&(Timestamp::now_milliseconds() + registration_period));

        let non_member_root = x"0000000000000000000000000000000000000000000000000000000000000000";
        let non_member_leaf_data = x"";
        let non_member_side_nodes = Vector::empty<vector<u8>>();

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
        let expected_smt_root = x"e775f588f3f8e6983a7c0d4dd5bda65fab01176c7c3d7b6edb893762e0157dfd";
        assert(expected_smt_root == *&smt_root, 1001);
        Debug::print<vector<u8>>(&smt_root);
    }
}
// check: EXECUTED



