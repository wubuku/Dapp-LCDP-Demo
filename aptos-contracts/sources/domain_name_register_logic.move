module aptos_demo::domain_name_register_logic {
    use aptos_demo::domain_name::{Self, DomainNameId};
    use aptos_demo::registered;
    use std::signer;

    friend aptos_demo::domain_name_aggregate;

    public(friend) fun verify(
        account: &signer,
        domain_name_id: DomainNameId,
        registration_period: u64,
    ): domain_name::Registered {
        domain_name::asset_domain_name_not_exists(domain_name_id);

        let e_registration_period = registration_period;
        let e_owner = signer::address_of(account);
        domain_name::new_registered(
            domain_name_id,
            e_registration_period,
            e_owner,
        )
    }

    public(friend) fun mutate(
        registered: &domain_name::Registered,
    ): domain_name::DomainName {
        let domain_name = domain_name::create_domain_name(
            registered::domain_name_id(registered),
            //Timestamp::now_milliseconds() + , //TODO to expiration_date
            registered::registration_period(registered),
            //domain_name_id_table,
            //ctx,
        );
        domain_name
    }

}
