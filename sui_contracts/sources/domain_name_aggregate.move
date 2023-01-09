module sui_contracts::domain_name_aggregate {
    use sui::tx_context::TxContext;
    use sui_contracts::domain_name;
    use sui_contracts::domain_name_register_logic;
    use sui_contracts::domain_name_renew_logic;

    public entry fun register(
        domain_name_id_top_level_domain: vector<u8>,
        domain_name_id_second_level_domain: vector<u8>,
        registration_period: u64,
        domain_name_id_table: &mut domain_name::DomainNameIdTable,
        ctx: &mut TxContext,
    ) {
        let domain_name_id = domain_name::new_domain_name_id(
            domain_name_id_top_level_domain,
            domain_name_id_second_level_domain,
        );
        // //assert domain_name_id does not exist in domain_name_id_table
        // domain_name::asset_domain_name_id_not_exists(domain_name_id, domain_name_id_table);
        let (registered, id) = domain_name_register_logic::verify(
            domain_name_id,
            registration_period,
            domain_name_id_table,
            ctx,
        );
        let domain_name = domain_name_register_logic::mutate(
            &registered,
            id,
            domain_name_id_table,
            ctx,
        );
        //domain_name::fill_registered_id(&mut registered, domain_name::id(&domain_name));
        domain_name::transfer_object(domain_name, domain_name::registered_owner(&registered));
        domain_name::emit_registered(registered);
    }

    public entry fun renew(
        renew_period: u64,
        domain_name: domain_name::DomainName,
        ctx: &mut TxContext,
    ) {
        let renewed = domain_name_renew_logic::verify(
            renew_period,
            &domain_name,
            ctx,
        );
        let updated_domain_name = domain_name_renew_logic::mutate(
            &renewed,
            domain_name,
        );
        domain_name::update_version_and_transfer_object(updated_domain_name, domain_name::renewed_account(&renewed));
        domain_name::emit_renewed(renewed);
    }
}

