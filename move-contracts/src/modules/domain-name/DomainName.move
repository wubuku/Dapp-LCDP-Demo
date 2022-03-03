address 0x18351d311d32201149a4df2a9fc2db8a {
module DomainName {
    use 0x1::Event;
    use 0x1::Signer;
    use 0x1::Vector;

    struct DomainNameId has store, drop, copy {
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

    struct EventStore has key, store {
        registerd_event_handle: Event::EventHandle<Registerd>,
        renew_event_handle: Event::EventHandle<Renewed>,
    }

    public fun init_event_store(account: &signer) {
        let account_address = Signer::address_of(account);
        _ = account_address;//require_genesis_account(account_address); //todo...
        move_to(account, EventStore{
            registerd_event_handle: Event::new_event_handle<Registerd>(account),
            renew_event_handle: Event::new_event_handle<Renewed>(account),
        });
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

    public fun renew(
        account: &signer,
        domain_name_id_top_level_domain: &vector<u8>,
        domain_name_id_second_level_domain: &vector<u8>,
        renew_period: u64,
        state_expiration_date: u64,
        state_owner: &address,
        smt_root: &vector<u8>,
        smt_side_nodes: &vector<u8>,
    ) {
        let domain_name_id = DomainNameId{
            top_level_domain: *domain_name_id_top_level_domain,
            second_level_domain: *domain_name_id_second_level_domain,
        };
        let domain_name_state = DomainNameState {
            domain_name_id: *&domain_name_id,
            expiration_date: state_expiration_date,
            owner: *state_owner,
        };
        //todo ...
        //_ = domain_name_state;

        _ = account;
        _ = renew_period;
        _ = smt_root;
        _ = smt_side_nodes;

        let renewed = Renewed{
            account: Signer::address_of(account),
            domain_name_id: domain_name_id,
            renew_period: renew_period,
            updated_state: domain_name_state,
            smt_leaf_data: Vector::empty<u8>(), //todo
            updated_smt_root: Vector::empty<u8>(), //todo
            previous_smt_root: Vector::empty<u8>(), //todo
        };
        _ = renewed;
    }

}
}
