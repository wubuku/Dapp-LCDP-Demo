module sui_contracts::domain_name_register_logic {
    use sui::object::{Self, UID};
    use sui::tx_context::{Self, TxContext};
    use sui_contracts::domain_name;

    friend sui_contracts::domain_name_aggregate;

    public(friend) fun verify(
        domain_name_id: domain_name::DomainNameId,
        registration_period: u64,
        domain_name_id_table: &domain_name::DomainNameIdTable,
        ctx: &mut TxContext,
    ): (domain_name::Registered, UID) {
        domain_name::asset_domain_name_id_not_exists(domain_name_id, domain_name_id_table);
        // ////////////////////////
        // let amount = Account::withdraw<STC::STC>(account, 1000000);
        // Account::deposit(DomainName::genesis_account(), amount);
        // ////////////////////////
        let id = object::new(ctx);
        let e_owner = tx_context::sender(ctx);
        let e_registration_period = registration_period;
        (
            domain_name::new_registered(
                &id,
                domain_name_id,
                e_registration_period,
                e_owner,
            ),
            id,
        )
    }

    public(friend) fun create(
        registered: &domain_name::Registered,
        id: UID,
        domain_name_id_table: &mut domain_name::DomainNameIdTable,
        //ctx: &mut TxContext,
    ): domain_name::DomainName {
        let domain_name = domain_name::create_domain_name(
            id,
            domain_name::registered_domain_name_id(registered),
            //Timestamp::now_milliseconds() + , //TODO to expiration_date
            domain_name::registered_registration_period(registered),
            domain_name_id_table,
            //ctx,
        );
        domain_name
    }
}
