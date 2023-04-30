module aptos_demo::domain_name_renew_logic {
    use std::signer;

    use aptos_demo::domain_name;

    friend aptos_demo::domain_name_aggregate;

    public(friend) fun verify(
        account: &signer,
        renew_period: u64,
        domain_name: &domain_name::DomainName,
    ): domain_name::Renewed {
        let e_account = signer::address_of(account);//tx_context::sender(ctx);
        let e_renew_period = renew_period;
        domain_name::new_renewed(
            domain_name,
            e_renew_period,
            e_account,
        )
    }

    public(friend) fun mutate(
        renewed: &domain_name::Renewed,
        domain_name: domain_name::DomainName,
    ): domain_name::DomainName {
        let new_expiration_date = domain_name::expiration_date(&domain_name) + domain_name::renewed_renew_period(
            renewed
        );
        domain_name::set_expiration_date(&mut domain_name, new_expiration_date);
        domain_name
    }
}
