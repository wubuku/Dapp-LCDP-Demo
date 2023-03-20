module NSAdmin::DomainNameRegisterLogic {
    //use StarcoinFramework::Vector;
    //use StarcoinFramework::Debug;
    use NSAdmin::DomainName;
    use StarcoinFramework::Account;
    use StarcoinFramework::STC;
    use StarcoinFramework::Signer;
    use StarcoinFramework::Timestamp;

    public fun verify(
        account: &signer,
        domain_name_id: &DomainName::DomainNameId,
        registration_period: u64,
    ): (
        address, // Owner
        u64, // RegistrationPeriod
    ) {
        _ = account;
        _ = domain_name_id;
        _ = registration_period;
        // ////////////////////////
        let amount = Account::withdraw<STC::STC>(account, 1000000);
        Account::deposit(DomainName::genesis_account(), amount);
        // ////////////////////////
        let e_owner = Signer::address_of(account);
        let e_registration_period = registration_period;
        (e_owner, e_registration_period)
    }

    public fun mutate(
        domain_name_id: &DomainName::DomainNameId,
        owner: address,
        registration_period: u64,
    ): DomainName::DomainNameState {
        //        Debug::print<vector<u8>>(&x"00000000000000000000000000000000000000000000");
        //        Debug::print<u64>(&Timestamp::now_milliseconds());
        //        Debug::print<u64>(&registration_period);
        //        Debug::print<vector<u8>>(&x"00000000000000000000000000000000000000000000");
        let domain_name_state = DomainName::new_domain_name_state(
            domain_name_id,
            Timestamp::now_milliseconds() + registration_period, // to expiration_date
            owner,
        );
        domain_name_state
    }
}
