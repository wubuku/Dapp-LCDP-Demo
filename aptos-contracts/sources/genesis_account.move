module aptos_demo::genesis_account {
    use std::error;
    use std::signer;

    use aptos_framework::account;
    use aptos_framework::event;

    use aptos_demo::resource_account;

    friend aptos_demo::product;
    friend aptos_demo::order;
    friend aptos_demo::domain_name;
    friend aptos_demo::day_summary;

    const ENOT_GENESIS_ACCOUNT: u64 = 100;

    struct Events has key {
        resource_account_created_handle: event::EventHandle<ResourceAccountCreated>,
    }

    struct ResourceAccountCreated has store, drop {
        address: address,
    }

    public fun initialize(account: &signer) acquires Events {
        assert_genesis_account(account);

        move_to(account, Events {
            resource_account_created_handle: account::new_event_handle<ResourceAccountCreated>(account),
        });

        resource_account::initialize(account);

        let events = borrow_global_mut<Events>(signer::address_of(account));
        event::emit_event(&mut events.resource_account_created_handle, ResourceAccountCreated {
            address: resouce_account_address(),
        });
    }

    public fun assert_genesis_account(account: &signer) {
        assert!(signer::address_of(account) == @aptos_demo, error::invalid_argument(ENOT_GENESIS_ACCOUNT));
    }

    public(friend) fun resource_account_signer(): signer {
        resource_account::resource_account_signer(@aptos_demo)
    }

    public fun resouce_account_address(): address {
        let res_account = resource_account::resource_account_signer(@aptos_demo);
        signer::address_of(&res_account)
    }
}
