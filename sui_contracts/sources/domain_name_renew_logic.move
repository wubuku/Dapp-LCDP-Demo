module sui_contracts::domain_name_renew_logic {
    use sui::tx_context::{Self, TxContext};
    use sui_contracts::domain_name;

    friend sui_contracts::domain_name_aggregate;

    public(friend) fun verify(
        renew_period: u64,
        domain_name: &domain_name::DomainName,
        ctx: &TxContext,
    ): domain_name::Renewed {
        // ////////////////////////
        // let amount = Account::withdraw<STC::STC>(account, 1000000);
        // Account::deposit(DomainName::genesis_account(), amount);
        // ////////////////////////
        let e_account = tx_context::sender(ctx);
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
        //ctx: &mut TxContext,
    ): domain_name::DomainName {
        //_ = ctx;
        // let updated_domain_name_state = DomainName::new_domain_name_state(
        //     &DomainName::get_domain_name_state_domain_name_id(domain_name_state),
        //     DomainName::get_domain_name_state_expiration_date(domain_name_state) + renew_period,
        //     DomainName::get_domain_name_state_owner(domain_name_state),
        // );
        let new_expiration_date = domain_name::expiration_date(&domain_name) + domain_name::renewed_renew_period(
            renewed
        );
        domain_name::set_expiration_date(&mut domain_name, new_expiration_date);
        domain_name
    }
}
