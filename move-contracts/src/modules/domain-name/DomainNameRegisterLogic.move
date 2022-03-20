address 0x18351d311d32201149a4df2a9fc2db8a {
module DomainNameRegisterLogic {
    //use 0x1::Vector;
    //use 0x1::Debug;
    use 0x1::Signer;
    use 0x1::Timestamp;
    use 0x18351d311d32201149a4df2a9fc2db8a::DomainName;

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

        let e_owner = Signer::address_of(account);
        let e_registration_period = registration_period;
        (e_owner, e_registration_period)
    }

    public fun mutate(
        domain_name_id: &DomainName::DomainNameId,
        owner: address,
        registration_period: u64,
    ): DomainName::DomainNameState {
        let domain_name_state = DomainName::new_domain_name_state(
            domain_name_id,
            Timestamp::now_milliseconds() + registration_period, // to expiration_date
            owner,
        );
        domain_name_state
    }
}
}
