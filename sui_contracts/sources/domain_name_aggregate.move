module sui_contracts::domain_name_aggregate {
    use std::string::String;
    use sui::tx_context::TxContext;
    use sui_contracts::domain_name;
    use sui_contracts::domain_name_register_logic;
    use sui_contracts::domain_name_renew_logic;

    public entry fun register(
        domain_name_id_top_level_domain: String,
        domain_name_id_second_level_domain: String,
        registration_period: u64,
        domain_name_id_table: &mut domain_name::DomainNameIdTable,
        ctx: &mut TxContext,
    ) {
        let domain_name_id = domain_name::new_domain_name_id(
            domain_name_id_top_level_domain,
            domain_name_id_second_level_domain,
        );

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
        domain_name::transfer_object(domain_name, domain_name::registered_owner(&registered));
        domain_name::emit_registered(registered);
    }


    public entry fun renew(
        domain_name: domain_name::DomainName,
        renew_period: u64,
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
            ctx,
        );
        domain_name::update_version_and_transfer_object(updated_domain_name, domain_name::renewed_account(&renewed));
        domain_name::emit_renewed(renewed);
    }

}
