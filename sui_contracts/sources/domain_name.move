module sui_contracts::domain_name {
    use sui::event;
    use sui::object::{Self, UID};
    use sui::table;
    use sui::transfer;
    use sui::tx_context::TxContext;
    use std::string::String;

    friend sui_contracts::domain_name_register_logic;
    friend sui_contracts::domain_name_renew_logic;
    
    friend sui_contracts::domain_name_aggregate;

    const EID_ALREADY_EXISTS: u64 = 101;

    struct DomainNameId has store, drop, copy {
        top_level_domain: String,
        second_level_domain: String,
    }

    public fun new_domain_name_id(
        top_level_domain: String,
        second_level_domain: String,
    ): DomainNameId {
        DomainNameId {
            top_level_domain,
            second_level_domain,
        }
    }

    struct DomainNameIdTable has key {
        id: UID,
        table: table::Table<DomainNameId, object::ID>,
    }

    struct DomainNameIdTableCreated has copy, drop {
        id: object::ID,
    }

    fun init(ctx: &mut TxContext) {
        let id_generator_table = DomainNameIdTable {
            id: object::new(ctx),
            table: table::new(ctx),
        };
        let id_generator_table_id = object::uid_to_inner(&id_generator_table.id);
        transfer::share_object(id_generator_table);
        event::emit(DomainNameIdTableCreated {
            id: id_generator_table_id,
        });
    }

    struct DomainName has key {
        id: UID,
        domain_name_id: DomainNameId,
        version: u64,
        expiration_date: u64,
    }

    public fun id(domain_name: &DomainName): object::ID {
        object::uid_to_inner(&domain_name.id)
    }

    public fun domain_name_id(domain_name: &DomainName): DomainNameId {
        domain_name.domain_name_id
    }

    public fun version(domain_name: &DomainName): u64 {
        domain_name.version
    }

    public fun expiration_date(domain_name: &DomainName): u64 {
        domain_name.expiration_date
    }

    public(friend) fun set_expiration_date(domain_name: &mut DomainName, expiration_date: u64) {
        domain_name.expiration_date = expiration_date;
    }

    fun new_domain_name(
        id: UID,
        domain_name_id: DomainNameId,
        expiration_date: u64,
    ): DomainName {
        DomainName {
            id,
            domain_name_id,
            version: 0,
            expiration_date,
        }
    }

    struct Registered has copy, drop {
        id: object::ID,
        domain_name_id: DomainNameId,
        registration_period: u64,
        owner: address,
    }

    public fun registered_domain_name_id(registered: &Registered): DomainNameId {
        registered.domain_name_id
    }

    public fun registered_registration_period(registered: &Registered): u64 {
        registered.registration_period
    }

    public fun registered_owner(registered: &Registered): address {
        registered.owner
    }

    public(friend) fun new_registered(
        id: &UID,
        domain_name_id: DomainNameId,
        registration_period: u64,
        owner: address,
    ): Registered {
        Registered {
            id: object::uid_to_inner(id),
            domain_name_id,
            registration_period,
            owner,
        }
    }

    struct Renewed has copy, drop {
        id: object::ID,
        domain_name_id: DomainNameId,
        version: u64,
        renew_period: u64,
        account: address,
    }

    public fun renewed_domain_name_id(renewed: &Renewed): DomainNameId {
        renewed.domain_name_id
    }

    public fun renewed_renew_period(renewed: &Renewed): u64 {
        renewed.renew_period
    }

    public fun renewed_account(renewed: &Renewed): address {
        renewed.account
    }

    public(friend) fun new_renewed(
        domain_name: &DomainName,
        renew_period: u64,
        account: address,
    ): Renewed {
        Renewed {
            id: id(domain_name),
            domain_name_id: domain_name_id(domain_name),
            version: version(domain_name),
            renew_period,
            account,
        }
    }


    public(friend) fun create_domain_name(
        id: UID,
        domain_name_id: DomainNameId,
        expiration_date: u64,
        domain_name_id_table: &mut DomainNameIdTable,
    ): DomainName {
        asset_domain_name_id_not_exists_then_add(domain_name_id, domain_name_id_table, object::uid_to_inner(&id));
        let domain_name = new_domain_name(
            id,
            domain_name_id,
            expiration_date,
        );
        domain_name
    }

    public(friend) fun asset_domain_name_id_not_exists(
        domain_name_id: DomainNameId,
        domain_name_id_table: &DomainNameIdTable,
    ) {
        assert!(!table::contains(&domain_name_id_table.table, domain_name_id), EID_ALREADY_EXISTS);
    }

    fun asset_domain_name_id_not_exists_then_add(
        domain_name_id: DomainNameId,
        domain_name_id_table: &mut DomainNameIdTable,
        id: object::ID,
    ) {
        asset_domain_name_id_not_exists(domain_name_id, domain_name_id_table);
        table::add(&mut domain_name_id_table.table, domain_name_id, id);
    }

    public(friend) fun transfer_object(domain_name: DomainName, recipient: address) {
        transfer::transfer(domain_name, recipient);
    }

    public(friend) fun update_version_and_transfer_object(domain_name: DomainName, recipient: address) {
        domain_name.version = domain_name.version + 1;
        transfer::transfer(domain_name, recipient);
    }

    public(friend) fun share_object(domain_name: DomainName) {
        transfer::share_object(domain_name);
    }

    public(friend) fun freeze_object(domain_name: DomainName) {
        transfer::freeze_object(domain_name);
    }

    public(friend) fun emit_registered(registered: Registered) {
        event::emit(registered);
    }

    public(friend) fun emit_renewed(renewed: Renewed) {
        event::emit(renewed);
    }

    #[test_only]
    /// Wrapper of module initializer for testing
    public fun test_init(ctx: &mut TxContext) {
        init(ctx)
    }
}
