module NSAdmin::DomainNameRenewLogic {
    //use StarcoinFramework::Vector;
    //use StarcoinFramework::Debug
    use NSAdmin::DomainName;
    use StarcoinFramework::Account;
    use StarcoinFramework::STC;
    use StarcoinFramework::Signer;

    public fun verify(
        account: &signer,
        domain_name_state: &DomainName::DomainNameState,
        renew_period: u64,
    ): (
        address, // Account
        u64, // RenewPeriod
    ) {
        _ = account;
        _ = domain_name_state;
        _ = renew_period;
        // ////////////////////////
        let amount = Account::withdraw<STC::STC>(account, 1000000);
        Account::deposit(DomainName::genesis_account(), amount);
        // ////////////////////////
        let e_account = Signer::address_of(account);
        let e_renew_period = renew_period;
        (e_account, e_renew_period)
    }

    public fun mutate(
        domain_name_state: &DomainName::DomainNameState,
        account: address,
        renew_period: u64,
    ): DomainName::DomainNameState {
        _ = domain_name_state;
        _ = account;
        _ = renew_period;

        let updated_domain_name_state = DomainName::new_domain_name_state(
            &DomainName::get_domain_name_state_domain_name_id(domain_name_state),
            DomainName::get_domain_name_state_expiration_date(domain_name_state) + renew_period,
            DomainName::get_domain_name_state_owner(domain_name_state),
        );

        updated_domain_name_state
    }
}
