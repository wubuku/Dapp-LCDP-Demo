module NSAdmin::DomainName {
    use StarcoinFramework::Errors;

    const ERR_INVALID_ACCOUNT: u64 = 101;
    //const ERR_INVALID_TOKEN_TYPE: u64 = 102;

    struct DomainNameId has store, drop, copy {
        top_level_domain: vector<u8>,
        second_level_domain: vector<u8>,
    }

    public fun new_domain_name_id(
        domain_name_id_top_level_domain: &vector<u8>,
        domain_name_id_second_level_domain: &vector<u8>,
    ): DomainNameId {
        DomainNameId {
            top_level_domain: *domain_name_id_top_level_domain,
            second_level_domain: *domain_name_id_second_level_domain,
        }
    }

    struct DomainNameState has store, drop, copy {
        domain_name_id: DomainNameId,
        expiration_date: u64,
        owner: address,
    }

    public fun new_domain_name_state(
        domain_name_id: &DomainNameId,
        expiration_date: u64,
        owner: address,
    ): DomainNameState {
        DomainNameState {
            domain_name_id: *domain_name_id,
            expiration_date: expiration_date,
            owner: owner,
        }
    }

    public fun get_domain_name_state_domain_name_id(domain_name_state: &DomainNameState): DomainNameId {
        *&domain_name_state.domain_name_id
    }

    public fun get_domain_name_state_expiration_date(domain_name_state: &DomainNameState): u64 {
        *&domain_name_state.expiration_date
    }

    public fun get_domain_name_state_owner(domain_name_state: &DomainNameState): address {
        *&domain_name_state.owner
    }

    struct Registered has store, drop {
        domain_name_id: DomainNameId,
        owner: address,
        registration_period: u64,
        updated_state: DomainNameState,
        //smt_leaf_data: vector<u8>,
        updated_smt_root: vector<u8>,
        previous_smt_root: vector<u8>,
    }

    public fun new_registered(
        domain_name_id: &DomainNameId,
        owner: address,
        registration_period: u64,
        updated_state: &DomainNameState,
        //smt_leaf_data: &vector<u8>,
        updated_smt_root: &vector<u8>,
        previous_smt_root: &vector<u8>,
    ): Registered {
        Registered {
            domain_name_id: *domain_name_id,
            owner: owner,
            registration_period: registration_period,
            updated_state: *updated_state,
            //smt_leaf_data: *smt_leaf_data,
            updated_smt_root: *updated_smt_root,
            previous_smt_root: *previous_smt_root,
        }
    }

    struct Renewed has store, drop {
        domain_name_id: DomainNameId,
        account: address,
        renew_period: u64,
        updated_state: DomainNameState,
        //smt_leaf_data: vector<u8>,
        updated_smt_root: vector<u8>,
        previous_smt_root: vector<u8>,
    }

    public fun new_renewed(
        domain_name_id: &DomainNameId,
        account: address,
        renew_period: u64,
        updated_state: &DomainNameState,
        //smt_leaf_data: &vector<u8>,
        updated_smt_root: &vector<u8>,
        previous_smt_root: &vector<u8>,
    ): Renewed {
        Renewed {
            domain_name_id: *domain_name_id,
            account: account,
            renew_period: renew_period,
            updated_state: *updated_state,
            //smt_leaf_data: *smt_leaf_data,
            updated_smt_root: *updated_smt_root,
            previous_smt_root: *previous_smt_root,
        }
    }


    /// Account permission check
    public fun require_genesis_account(address: address) {
        assert!(address == genesis_account(), Errors::invalid_argument(ERR_INVALID_ACCOUNT));
    }

    public fun genesis_account(): address {
        @NSAdmin
    }
}
