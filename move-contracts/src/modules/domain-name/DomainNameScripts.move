address 0x18351d311d32201149a4df2a9fc2db8a {
module DomainNameScripts {
    use 0x18351d311d32201149a4df2a9fc2db8a::DomainNameAggregate;
    use 0x18351d311d32201149a4df2a9fc2db8a::SMTProofUtils;

    public(script) fun init_genesis(account: signer) {
        DomainNameAggregate::init_genesis(&account);
    }

    public(script) fun get_smt_root(): vector<u8> {
        DomainNameAggregate::get_smt_root()
    }

    public(script) fun register(
        account: signer,
        domain_name_id_top_level_domain: vector<u8>,
        domain_name_id_second_level_domain: vector<u8>,
        registration_period: u64,
        smt_root: vector<u8>,
        smt_non_membership_leaf_data: vector<u8>,
        smt_side_nodes: vector<u8>,
    ) {
        DomainNameAggregate::register(
            &account,
            &domain_name_id_top_level_domain,
            &domain_name_id_second_level_domain,
            registration_period,
            &smt_root,
            &smt_non_membership_leaf_data,
            &SMTProofUtils::split_side_nodes_data(&smt_side_nodes),
        );
    }

    public(script) fun renew(
        account: signer,
        domain_name_id_top_level_domain: vector<u8>,
        domain_name_id_second_level_domain: vector<u8>,
        renew_period: u64,
        state_expiration_date: u64,
        state_owner: address,
        smt_root: vector<u8>,
        smt_side_nodes: vector<u8>,
    ) {
        DomainNameAggregate::renew(
            &account,
            &domain_name_id_top_level_domain,
            &domain_name_id_second_level_domain,
            renew_period,
            state_expiration_date,
            state_owner,
            &smt_root,
            &SMTProofUtils::split_side_nodes_data(&smt_side_nodes),
        );
    }
}
}
