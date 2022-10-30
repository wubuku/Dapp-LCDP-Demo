//# init -n dev --addresses alice=0xb6D69DD935EDf7f2054acF12eb884df8

//# faucet --addr NSAdmin --amount 10000000000000000

//# faucet --addr alice --amount 10000000000000000

//# faucet --addr bob --amount 10000000000000000

//# run --signers NSAdmin
script {
    use StarcoinFramework::Debug;
    //use StarcoinFramework::Vector;
    use NSAdmin::DomainNameAggregate;
    use NSAdmin::SMTreeHasher;

    fun test_init_genesis(signer: signer) {
        DomainNameAggregate::init_genesis(&signer);
        let smt_root = DomainNameAggregate::get_smt_root();
        assert!(SMTreeHasher::placeholder() == *&smt_root, 1001);
        Debug::print<vector<u8>>(&smt_root)
    }
}
// check: EXECUTED

//# block --timestamp 1647648000000

//# run --signers alice
script {
    use StarcoinFramework::Debug;
    use StarcoinFramework::Vector;
    //use StarcoinFramework::Timestamp;
    use NSAdmin::DomainNameAggregate;

    // --------------------------------------------
    // block-time: 1647648000000,
    // is uint64(time.Date(2022, 3, 19, 0, 0, 0, 0, time.UTC).UnixNano() / 1000000)
    // --------------------------------------------

    fun test_register_a(signer: signer) {
        let domain_name_id_top_level_domain = b"stc";
        let domain_name_id_second_level_domain = b"a";
        let registration_period: u64 = 1000 * 60 * 60 * 24 * 365; // One year
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
        assert!(expected_smt_root == *&smt_root, 1001);
        Debug::print<vector<u8>>(&smt_root);
    }
}
// check: EXECUTED


//# run --signers alice
script {
    use StarcoinFramework::Debug;
    use StarcoinFramework::Vector;
    //use StarcoinFramework::Timestamp;
    use NSAdmin::DomainNameAggregate;


    fun test_register_b(signer: signer) {
        let domain_name_id_top_level_domain = b"stc";
        let domain_name_id_second_level_domain = b"b";
        let registration_period: u64 = 1000 * 60 * 60 * 24 * 365; // One year
        // Debug::print<u64>(&(Timestamp::now_milliseconds() + registration_period));

        // -------------------------------------------------------
        let non_member_root = x"e775f588f3f8e6983a7c0d4dd5bda65fab01176c7c3d7b6edb893762e0157dfd";
        let non_member_leaf_data = x"00da3ba650fe753c7062ce70ef9ec9b4ac04bbb1af0b35cfc6c642912f4c0bc3fb78301c1c83cf9bf2f58989a91cb93fff4f19c75f85598115377b20bb953a46c6";
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

        // -------- Domain name 'stc.b' added. --------

        let expected_smt_root = x"5386e70bd4690321161b87bd25955273aae58253f211ac88468177fed160ff24";
        assert!(expected_smt_root == *&smt_root, 1001);
        Debug::print<vector<u8>>(&smt_root);
    }
}
// check: EXECUTED



//# run --signers alice
script {
    use StarcoinFramework::Debug;
    use StarcoinFramework::Vector;
    //use StarcoinFramework::Timestamp;
    use NSAdmin::DomainNameAggregate;


    fun test_register_c(signer: signer) {
        let domain_name_id_top_level_domain = b"stc";
        let domain_name_id_second_level_domain = b"c";
        let registration_period: u64 = 1000 * 60 * 60 * 24 * 365; // One year
        // Debug::print<u64>(&(Timestamp::now_milliseconds() + registration_period));

        // -------------------------------------------------------
        let non_member_root = x"5386e70bd4690321161b87bd25955273aae58253f211ac88468177fed160ff24";
        let non_member_leaf_data = x"";
        let non_member_side_nodes = Vector::empty<vector<u8>>();
        Vector::push_back(&mut non_member_side_nodes, x"237afd7c7dacd14ded4aa40751f0479d68ce1ace8ad138ef7c435b8046d21832");

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

        // -------- Domain name 'stc.c' added. --------

        let expected_smt_root = x"9cd3a823fb02a2c657b1a8f5a037f45466dd3cc811f383d33135c817c8f6b018";
        assert!(expected_smt_root == *&smt_root, 1001);
        Debug::print<vector<u8>>(&smt_root);
    }
}
// check: EXECUTED


//# run --signers alice
script {
    use StarcoinFramework::Debug;
    use StarcoinFramework::Vector;
    //use StarcoinFramework::Timestamp;
    use NSAdmin::DomainNameAggregate;


    fun test_register_d(signer: signer) {
        let domain_name_id_top_level_domain = b"stc";
        let domain_name_id_second_level_domain = b"d";
        let registration_period: u64 = 1000 * 60 * 60 * 24 * 365; // One year
        // Debug::print<u64>(&(Timestamp::now_milliseconds() + registration_period));

        // -------------------------------------------------------
        let non_member_root = x"9cd3a823fb02a2c657b1a8f5a037f45466dd3cc811f383d33135c817c8f6b018";
        let non_member_leaf_data = x"";
        let non_member_side_nodes = Vector::empty<vector<u8>>();
        Vector::push_back(&mut non_member_side_nodes, x"42ce1cd67b80a586a6886ac4dfe8d62d39b50d41ebb29023801c575d75dec340");
        Vector::push_back(&mut non_member_side_nodes, x"11afac5a351885a1b72e2f1ede7e755f50fcb157e5af441811f03e12260f91c2");

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

        // -------- Domain name 'stc.d' added. --------

        let expected_smt_root = x"555043594d41809fd5a91e644f1e6d8b062f9c62bb77bc25100e25a2cfcc2443";
        assert!(expected_smt_root == *&smt_root, 1001);
        Debug::print<vector<u8>>(&smt_root);
    }
}
// check: EXECUTED


//# run --signers alice
script {
    use StarcoinFramework::Debug;
    use StarcoinFramework::Vector;
    //use StarcoinFramework::Timestamp;
    use NSAdmin::DomainNameAggregate;


    fun test_register_e(signer: signer) {
        let domain_name_id_top_level_domain = b"stc";
        let domain_name_id_second_level_domain = b"e";
        let registration_period: u64 = 1000 * 60 * 60 * 24 * 365; // One year
        // Debug::print<u64>(&(Timestamp::now_milliseconds() + registration_period));

        // -------------------------------------------------------
        let non_member_root = x"555043594d41809fd5a91e644f1e6d8b062f9c62bb77bc25100e25a2cfcc2443";
        let non_member_leaf_data = x"007b8767d0cedca549b2d6744e61196a4c00125032c5bcb951955a61e9e580261336fa2a20ed028bc9971e3275f05d8cb38eb12061285bc192f45b9083c659fb32";
        let non_member_side_nodes = Vector::empty<vector<u8>>();
        Vector::push_back(&mut non_member_side_nodes, x"4f106e9e0fd4e061a913e7d6d27dc197e15ff331406f2e045fea332e028e5749");

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

        // -------- Domain name 'stc.e' added. --------

        let expected_smt_root = x"ec693775597dade84e4b72c096467f380cbfb102edebee4644e903e9f798e95f";
        assert!(expected_smt_root == *&smt_root, 1001);
        Debug::print<vector<u8>>(&smt_root);
    }
}
// check: EXECUTED


//# run --signers alice
script {
    use StarcoinFramework::Debug;
    use StarcoinFramework::Vector;
    //use StarcoinFramework::Timestamp;
    use NSAdmin::DomainNameAggregate;


    fun test_register_f(signer: signer) {
        let domain_name_id_top_level_domain = b"stc";
        let domain_name_id_second_level_domain = b"f";
        let registration_period: u64 = 1000 * 60 * 60 * 24 * 365; // One year
        // Debug::print<u64>(&(Timestamp::now_milliseconds() + registration_period));

        // -------------------------------------------------------
        let non_member_root = x"ec693775597dade84e4b72c096467f380cbfb102edebee4644e903e9f798e95f";
        let non_member_leaf_data = x"0090e3b801701d5df368daea9c8b4738afe1e18d491824431b538ce4aa76dbcb994efd6c3f7192a46b4eb31183d886759e2611d9dff4ed4256c8b91407bacf9297";
        let non_member_side_nodes = Vector::empty<vector<u8>>();
        Vector::push_back(&mut non_member_side_nodes, x"42ce1cd67b80a586a6886ac4dfe8d62d39b50d41ebb29023801c575d75dec340");
        Vector::push_back(&mut non_member_side_nodes, x"b36de233f7b6ecf32d1714f6c869c4c78c5c964275e5a72d3ab0ecf0d1c37c1e");

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

        // -------- Domain name 'stc.f' added. --------

        let expected_smt_root = x"771810f2c466af5ca6bd340cdb42d8dff9647fa8f8c678c5ad4264494c1f3f90";
        assert!(expected_smt_root == *&smt_root, 1001);
        Debug::print<vector<u8>>(&smt_root);
    }
}
// check: EXECUTED


//# run --signers alice
script {
    use StarcoinFramework::Debug;
    use StarcoinFramework::Vector;
    //use StarcoinFramework::Timestamp;
    use NSAdmin::DomainNameAggregate;


    fun test_register_g(signer: signer) {
        let domain_name_id_top_level_domain = b"stc";
        let domain_name_id_second_level_domain = b"g";
        let registration_period: u64 = 1000 * 60 * 60 * 24 * 365; // One year
        // Debug::print<u64>(&(Timestamp::now_milliseconds() + registration_period));

        // -------------------------------------------------------
        let non_member_root = x"771810f2c466af5ca6bd340cdb42d8dff9647fa8f8c678c5ad4264494c1f3f90";
        let non_member_leaf_data = x"";
        let non_member_side_nodes = Vector::empty<vector<u8>>();
        Vector::push_back(&mut non_member_side_nodes, x"356bd1ff7027fd81fc69ec943796404af4226dc95e04784f2b8f4bcee03a37f7");
        Vector::push_back(&mut non_member_side_nodes, x"e0431ca32d842482ad438aa9e5ba8a2ba39725f53c8bd097a9910b7bb2437b07");
        Vector::push_back(&mut non_member_side_nodes, x"b36de233f7b6ecf32d1714f6c869c4c78c5c964275e5a72d3ab0ecf0d1c37c1e");

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

        // -------- Domain name 'stc.g' added. --------

        let expected_smt_root = x"57f0d0786736ee370a8718c54e0cf87e3709b91ef618c9f1d444d208420e16dd";
        assert!(expected_smt_root == *&smt_root, 1001);
        Debug::print<vector<u8>>(&smt_root);
    }
}
// check: EXECUTED



//# run --signers alice
script {
    use StarcoinFramework::Debug;
    use StarcoinFramework::Vector;
    //use StarcoinFramework::Timestamp;
    use NSAdmin::DomainNameAggregate;


    fun test_renew_a(signer: signer) {
        let domain_name_id_top_level_domain = b"stc";
        let domain_name_id_second_level_domain = b"a";
        let renew_period: u64 = 1000 * 60 * 60 * 24 * 365; // One year
        let state_expiration_date: u64 = 1679184000000;
        let state_owner: address = @0xb6d69dd935edf7f2054acf12eb884df8;

        // -------------------------------------------------------
        let member_root = x"57f0d0786736ee370a8718c54e0cf87e3709b91ef618c9f1d444d208420e16dd";
        let member_side_nodes = Vector::empty<vector<u8>>();
        Vector::push_back(&mut member_side_nodes, x"4aff52d81907e3ffc71e6523ce2cb4059c631ab4c0a93f486add6061d420241c");
        Vector::push_back(&mut member_side_nodes, x"0000000000000000000000000000000000000000000000000000000000000000");
        Vector::push_back(&mut member_side_nodes, x"3879bc88ee3eb90df3b2ec5e9fe00082f3392fe2f429e9ec0011eaee8de42e78");
        Vector::push_back(&mut member_side_nodes, x"e0431ca32d842482ad438aa9e5ba8a2ba39725f53c8bd097a9910b7bb2437b07");
        Vector::push_back(&mut member_side_nodes, x"b36de233f7b6ecf32d1714f6c869c4c78c5c964275e5a72d3ab0ecf0d1c37c1e");

        DomainNameAggregate::renew(
            &signer,
            &domain_name_id_top_level_domain,
            &domain_name_id_second_level_domain,
            renew_period,
            state_expiration_date,
            state_owner,
            &member_root,
            &member_side_nodes,
        );
        let smt_root = DomainNameAggregate::get_smt_root();


        let expected_smt_root = x"d45a8321c06b7d71d5cbe489b426244b6faa9e32130f93a9eae4b535ff283dde";
        assert!(expected_smt_root == *&smt_root, 1001);
        Debug::print<vector<u8>>(&smt_root);
    }
}
// check: EXECUTED


//# run --signers alice
script {
    use StarcoinFramework::Debug;
    use StarcoinFramework::Vector;
    //use StarcoinFramework::Timestamp;
    use NSAdmin::DomainNameAggregate;


    fun test_renew_b(signer: signer) {
        let domain_name_id_top_level_domain = b"stc";
        let domain_name_id_second_level_domain = b"b";
        let renew_period: u64 = 1000 * 60 * 60 * 24 * 365; // One year
        let state_expiration_date: u64 = 1679184000000;
        let state_owner: address = @0xb6d69dd935edf7f2054acf12eb884df8;

        // -------------------------------------------------------
        let member_root = x"d45a8321c06b7d71d5cbe489b426244b6faa9e32130f93a9eae4b535ff283dde";
        let member_side_nodes = Vector::empty<vector<u8>>();
        Vector::push_back(&mut member_side_nodes, x"bac017ea373e5ac14c059cd221ebb6724a4f6ca5430500c8aec66a709a758ac5");
        Vector::push_back(&mut member_side_nodes, x"0000000000000000000000000000000000000000000000000000000000000000");
        Vector::push_back(&mut member_side_nodes, x"3879bc88ee3eb90df3b2ec5e9fe00082f3392fe2f429e9ec0011eaee8de42e78");
        Vector::push_back(&mut member_side_nodes, x"e0431ca32d842482ad438aa9e5ba8a2ba39725f53c8bd097a9910b7bb2437b07");
        Vector::push_back(&mut member_side_nodes, x"b36de233f7b6ecf32d1714f6c869c4c78c5c964275e5a72d3ab0ecf0d1c37c1e");

        DomainNameAggregate::renew(
            &signer,
            &domain_name_id_top_level_domain,
            &domain_name_id_second_level_domain,
            renew_period,
            state_expiration_date,
            state_owner,
            &member_root,
            &member_side_nodes,
        );
        let smt_root = DomainNameAggregate::get_smt_root();


        let expected_smt_root = x"673ea6675cd99b5c0360202ea9397e906da784b6443145c2ef1d3cce8c4d8e7c";
        assert!(expected_smt_root == *&smt_root, 1001);
        Debug::print<vector<u8>>(&smt_root);
    }
}
// check: EXECUTED


//# run --signers alice
script {
    use StarcoinFramework::Debug;
    use StarcoinFramework::Vector;
    //use StarcoinFramework::Timestamp;
    use NSAdmin::DomainNameAggregate;


    fun test_renew_c(signer: signer) {
        let domain_name_id_top_level_domain = b"stc";
        let domain_name_id_second_level_domain = b"c";
        let renew_period: u64 = 1000 * 60 * 60 * 24 * 365; // One year
        let state_expiration_date: u64 = 1679184000000;
        let state_owner: address = @0xb6d69dd935edf7f2054acf12eb884df8;

        // -------------------------------------------------------
        let member_root = x"673ea6675cd99b5c0360202ea9397e906da784b6443145c2ef1d3cce8c4d8e7c";
        let member_side_nodes = Vector::empty<vector<u8>>();
        Vector::push_back(&mut member_side_nodes, x"e67eac0b9fb429f9c9f4bd98925f1b93c0e7152b1a991e6ef813dffdbc3f94f1");
        Vector::push_back(&mut member_side_nodes, x"cce64e778827e64a0063c0b99893ed7bfade5f908b6016489919be04a0b653f3");

        DomainNameAggregate::renew(
            &signer,
            &domain_name_id_top_level_domain,
            &domain_name_id_second_level_domain,
            renew_period,
            state_expiration_date,
            state_owner,
            &member_root,
            &member_side_nodes,
        );
        let smt_root = DomainNameAggregate::get_smt_root();


        let expected_smt_root = x"fc70e205742a40e8c22a2a2eeaf749b8d2f8669330d343adc9c29f74c4fe06da";
        assert!(expected_smt_root == *&smt_root, 1001);
        Debug::print<vector<u8>>(&smt_root);
    }
}
// check: EXECUTED


//# run --signers alice
script {
    use StarcoinFramework::Debug;
    use StarcoinFramework::Vector;
    //use StarcoinFramework::Timestamp;
    use NSAdmin::DomainNameAggregate;


    fun test_renew_d(signer: signer) {
        let domain_name_id_top_level_domain = b"stc";
        let domain_name_id_second_level_domain = b"d";
        let renew_period: u64 = 1000 * 60 * 60 * 24 * 365; // One year
        let state_expiration_date: u64 = 1679184000000;
        let state_owner: address = @0xb6d69dd935edf7f2054acf12eb884df8;

        // -------------------------------------------------------
        let member_root = x"fc70e205742a40e8c22a2a2eeaf749b8d2f8669330d343adc9c29f74c4fe06da";
        let member_side_nodes = Vector::empty<vector<u8>>();
        Vector::push_back(&mut member_side_nodes, x"2c99017f825686b8f0fc93eb6c1099b417f0f68bfd4cd18ef17850a437768d42");
        Vector::push_back(&mut member_side_nodes, x"0000000000000000000000000000000000000000000000000000000000000000");
        Vector::push_back(&mut member_side_nodes, x"0000000000000000000000000000000000000000000000000000000000000000");
        Vector::push_back(&mut member_side_nodes, x"0000000000000000000000000000000000000000000000000000000000000000");
        Vector::push_back(&mut member_side_nodes, x"671c672fe3397a5b44ce78e48f41aafe895dc082aa7187d2f04b351b162c3175");
        Vector::push_back(&mut member_side_nodes, x"5029c28b782a2834232ed6d1cdb0b5f22225fd7ef7911a69dc1ba0e668aca360");

        DomainNameAggregate::renew(
            &signer,
            &domain_name_id_top_level_domain,
            &domain_name_id_second_level_domain,
            renew_period,
            state_expiration_date,
            state_owner,
            &member_root,
            &member_side_nodes,
        );
        let smt_root = DomainNameAggregate::get_smt_root();


        let expected_smt_root = x"b3a524e7922ebd177cde9fc54ca257fbfb02e03541e3e7c3de75e79ab50ad198";
        assert!(expected_smt_root == *&smt_root, 1001);
        Debug::print<vector<u8>>(&smt_root);
    }
}
// check: EXECUTED
