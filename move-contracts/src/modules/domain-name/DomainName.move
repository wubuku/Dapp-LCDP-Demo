address 0x18351d311d32201149a4df2a9fc2db8a {
module DomainName {
    struct DomainNameId has store, drop {
        top_level_domain: vector<u8>,
        second_level_domain: vector<u8>,
    }

    struct DomainNameState has store, drop {
        domain_name_id: DomainNameId,
        expiration_date: u64,
        owner: address,
    }

    struct Registerd has store, drop {
        owner: address,
        domain_name_id: DomainNameId,
        registration_period: u64,
        updated_state: DomainNameState,
        smt_leaf_data: vector<u8>,
        updated_smt_root: vector<u8>,
        previous_smt_root: vector<u8>,
    }

    struct Renewed has store, drop {
        account: address,
        domain_name_id: DomainNameId,
        renew_period: u64,
        updated_state: DomainNameState,
        smt_leaf_data: vector<u8>,
        updated_smt_root: vector<u8>,
        previous_smt_root: vector<u8>,
    }

    public fun register(
        account: &signer,
        domain_name_id_top_level_domain: &vector<u8>,
        domain_name_id_second_level_domain: &vector<u8>,
        registration_period: u64,
        smt_root: &vector<u8>,
        smt_non_membership_leaf_data: &vector<u8>,
        smt_side_nodes: &vector<u8>,
    ) {
        let domain_name_id = DomainNameId{
            top_level_domain: *domain_name_id_top_level_domain,
            second_level_domain: *domain_name_id_second_level_domain,
        };
        //todo ...
        _ = domain_name_id;

        _ = account;
        _ = registration_period;
        _ = smt_root;
        _ = smt_non_membership_leaf_data;
        _ = smt_side_nodes;
    }

}
}
