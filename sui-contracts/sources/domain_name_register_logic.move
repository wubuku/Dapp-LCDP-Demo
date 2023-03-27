module sui_contracts::domain_name_register_logic {
    use sui::object::{Self, UID};
    use sui::tx_context::{Self, TxContext};
    use sui_contracts::domain_name::{Self, DomainNameId};
    use sui_contracts::registered;

    friend sui_contracts::domain_name_aggregate;

    public(friend) fun verify(
        domain_name_id: DomainNameId,
        registration_period: u64,
        domain_name_id_table: &domain_name::DomainNameIdTable,
        ctx: &mut TxContext,
    ): (domain_name::Registered, UID) {
        let id = object::new(ctx);
        domain_name::asset_domain_name_id_not_exists(domain_name_id, domain_name_id_table);
        // ...
        //(
        //    domain_name::new_registered(
        //        &id,
        //        // ...
        //    ),
        //    id,
        //)
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

    public(friend) fun mutate(
        registered: &domain_name::Registered,
        id: UID,
        domain_name_id_table: &mut domain_name::DomainNameIdTable,
        ctx: &TxContext,
    ): domain_name::DomainName {
        //let _ = ctx;
        //domain_name = domain_name::create_domain_name(
        //    id,
        //    //...
        //    domain_name_id_table,
        //);
        //domain_name
        let _ = ctx;
        let domain_name = domain_name::create_domain_name(
            id,
            registered::domain_name_id(registered),
            //Timestamp::now_milliseconds() + , //TODO to expiration_date
            registered::registration_period(registered),
            domain_name_id_table,
            //ctx,
        );
        domain_name

    }

}
