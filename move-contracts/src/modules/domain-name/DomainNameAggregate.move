address 0x18351d311d32201149a4df2a9fc2db8a {
module DomainNameAggregate {
    use 0x1::Event;
    use 0x1::Signer;
    use 0x1::Vector;
    use 0x1::Errors;
    use 0x1::BCS;
    use 0x18351d311d32201149a4df2a9fc2db8a::SMTreeHasher;
    use 0x18351d311d32201149a4df2a9fc2db8a::SMTProofs;
    use 0x18351d311d32201149a4df2a9fc2db8a::DomainName;
    use 0x18351d311d32201149a4df2a9fc2db8a::DomainNameRegisterLogic;
    use 0x18351d311d32201149a4df2a9fc2db8a::DomainNameRenewLogic;

    const ERR_GENESIS_INITIALIZED: u64 = 101;
    const ERR_INVALID_SMT_ROOT: u64 = 102;
    const ERR_INVALID_SMT_NON_MEMBERSHIP_PROOF: u64 = 103;

    struct EventStore has key, store {
        registerd_event_handle: Event::EventHandle<DomainName::Registerd>,
        renew_event_handle: Event::EventHandle<DomainName::Renewed>,
    }

    /// SMT
    struct SMTStore has key, store {
        /// root
        root: vector<u8>
    }

    public fun init_genesis(account: &signer) {
        let account_address = Signer::address_of(account);
        DomainName::require_genesis_account(account_address);

        init_smt_store(account);
        init_event_store(account);
    }

    public fun init_smt_store(account: &signer) {
        let account_address = Signer::address_of(account);
        DomainName::require_genesis_account(account_address);
        assert(!exists<SMTStore>(Signer::address_of(account)), Errors::invalid_state(ERR_GENESIS_INITIALIZED));
        move_to(account, SMTStore{
            root: *&SMTreeHasher::placeholder()
        });
    }

    public fun init_event_store(account: &signer) {
        let account_address = Signer::address_of(account);
        DomainName::require_genesis_account(account_address);
        assert(!exists<EventStore>(Signer::address_of(account)), Errors::invalid_state(ERR_GENESIS_INITIALIZED));
        move_to(account, EventStore{
            registerd_event_handle: Event::new_event_handle<DomainName::Registerd>(account),
            renew_event_handle: Event::new_event_handle<DomainName::Renewed>(account),
        });
    }

    public fun register(
        account: &signer,
        domain_name_id_top_level_domain: &vector<u8>,
        domain_name_id_second_level_domain: &vector<u8>,
        registration_period: u64,
        smt_root: &vector<u8>,
        smt_non_membership_leaf_data: &vector<u8>,
        smt_side_nodes: &vector<vector<u8>>,
    ) acquires EventStore, SMTStore {
        _ = account;
        _ = registration_period;
        _ = smt_root;
        _ = smt_non_membership_leaf_data;
        _ = smt_side_nodes;

        let domain_name_id = DomainName::new_domain_name_id(
                domain_name_id_top_level_domain,
                domain_name_id_second_level_domain,
            );

        let store_smt_root = get_smt_root();
        assert(*&store_smt_root == *smt_root, Errors::invalid_state(ERR_INVALID_SMT_ROOT));
        let leaf_path = SMTreeHasher::digest(&BCS::to_bytes<DomainName::DomainNameId>(&domain_name_id));
        let proof_ok = SMTProofs::verify_non_membership_proof_by_leaf_path(&store_smt_root, smt_non_membership_leaf_data, smt_side_nodes, &leaf_path);
        assert(proof_ok, Errors::invalid_state(ERR_INVALID_SMT_NON_MEMBERSHIP_PROOF));

        // ///////////// Call business logic module start ///////////////
        DomainNameRegisterLogic::verify(account, &domain_name_id, registration_period);

        let (e_owner, e_registration_period) = DomainNameRegisterLogic::to_event_properties(account, &domain_name_id, registration_period);

        let updated_domain_name_state = DomainNameRegisterLogic::mutate(&domain_name_id, e_owner, e_registration_period);
        // ///////////// Call business logic module end ///////////////

        let updated_smt_root = update_smt_root_by_new_leaf_path_and_value(&leaf_path, &updated_domain_name_state, smt_non_membership_leaf_data, smt_side_nodes);
        let registered = DomainName::new_registerd(
            &domain_name_id,
            Signer::address_of(account),
            registration_period,
            &updated_domain_name_state,
            &updated_smt_root,
            &store_smt_root, // previouse SMT root
        );
        let event_store = borrow_global_mut<EventStore>(DomainName::genesis_account());
        Event::emit_event(
            &mut event_store.registerd_event_handle,
            registered,
        );
    }

    public fun renew(
        account: &signer,
        domain_name_id_top_level_domain: &vector<u8>,
        domain_name_id_second_level_domain: &vector<u8>,
        renew_period: u64,
        state_expiration_date: u64,
        state_owner: address,
        smt_root: &vector<u8>,
        smt_side_nodes: &vector<vector<u8>>,
    ) acquires SMTStore {
        _ = account;
        _ = renew_period;
        _ = smt_root;
        _ = smt_side_nodes;

        let domain_name_id = DomainName::new_domain_name_id(
            domain_name_id_top_level_domain,
            domain_name_id_second_level_domain,
        );
        let domain_name_state = DomainName::new_domain_name_state(
            &domain_name_id,
            state_expiration_date,
            *&state_owner,
        );

        let store_smt_root = get_smt_root();
        assert(*&store_smt_root == *smt_root, Errors::invalid_state(ERR_INVALID_SMT_ROOT));
        let leaf_path = SMTreeHasher::digest(&BCS::to_bytes<DomainName::DomainNameId>(&domain_name_id));
        let leaf_value_hash = SMTreeHasher::digest(&BCS::to_bytes<DomainName::DomainNameState>(&domain_name_state));
        let proof_ok = SMTProofs::verify_membership_proof(&store_smt_root, smt_side_nodes, &leaf_path, &leaf_value_hash);
        assert(proof_ok, Errors::invalid_state(ERR_INVALID_SMT_NON_MEMBERSHIP_PROOF));

        // ///////////// Call business logic module start ///////////////
        DomainNameRenewLogic::verify(account, &domain_name_state, renew_period);

        let (e_account, e_renew_period) = DomainNameRenewLogic::to_event_properties(account, &domain_name_state, renew_period);

        let updated_domain_name_state = DomainNameRenewLogic::mutate(&domain_name_state, e_account, e_renew_period);
        // ///////////// Call business logic module end ///////////////

        //todo
        //let updated_smt_root = update_smt_root_by_leaf_path_and_value(&leaf_path, &domain_name_state, smt_non_membership_leaf_data, smt_side_nodes);

        let renewed = DomainName::new_renewed(
            &domain_name_id,
            Signer::address_of(account),
            renew_period,
            &updated_domain_name_state,
            &Vector::empty<u8>(), // todo &updated_smt_root,
            &store_smt_root, // previouse SMT root
        );
        _ = renewed;
    }

    public fun get_smt_root(): vector<u8> acquires SMTStore {
        let smt = borrow_global<SMTStore>(DomainName::genesis_account());
        *&smt.root
    }

    /// update SMT root
    fun update_smt_root_by_new_leaf_path_and_value(
        leaf_path: &vector<u8>,
        //domain_name_id: &DomainName::DomainNameId,
        domain_name_state: &DomainName::DomainNameState,
        non_membership_leaf_data: &vector<u8>,
        side_nodes: &vector<vector<u8>>
    ): vector<u8> acquires SMTStore {
        //let leaf_path = SMTreeHasher::digest(&BCS::to_bytes<DomainName::DomainNameId>(domain_name_id));
        let leaf_value_hash = SMTreeHasher::digest(&BCS::to_bytes<DomainName::DomainNameState>(domain_name_state));
        let smt = borrow_global_mut<SMTStore>(DomainName::genesis_account());
        smt.root = SMTProofs::compute_root_hash_new_leaf_included(
            leaf_path,
            &leaf_value_hash,
            non_membership_leaf_data,
            side_nodes
        );
        *&smt.root
    }

}
}
