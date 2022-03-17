address 0x18351d311d32201149a4df2a9fc2db8a {
module DomainNameRenewLogic {
    //use 0x1::Vector;
    //use 0x1::Debug;
    use 0x1::Signer;
    use 0x18351d311d32201149a4df2a9fc2db8a::DomainName;

    public fun verify(
        account: &signer,
        domain_name_state: &DomainName::DomainNameState,
        renew_period: u64,
    ) {
        _ = account;
        _ = domain_name_state;
        _ = renew_period;
    }

    public fun to_event_properties(
        account: &signer,
        domain_name_state: &DomainName::DomainNameState,
        renew_period: u64,
    ): (
        address, // Account
        u64, // RenewPeriod
    ) {
        _ = account;
        _ = domain_name_state;

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
}
