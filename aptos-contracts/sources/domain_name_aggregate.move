// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

module aptos_demo::domain_name_aggregate {
    use aptos_demo::domain_name::{Self, DomainNameId};
    use aptos_demo::domain_name_register_logic;
    use aptos_demo::domain_name_renew_logic;
    use std::string::String;

    public entry fun register(
        account: &signer,
        domain_name_id_top_level_domain: String,
        domain_name_id_second_level_domain: String,
        registration_period: u64,
    ) {
        let domain_name_id: DomainNameId = domain_name::new_domain_name_id(
            domain_name_id_top_level_domain,
            domain_name_id_second_level_domain,
        );

        let registered = domain_name_register_logic::verify(
            account,
            domain_name_id,
            registration_period,
        );
        let domain_name = domain_name_register_logic::mutate(
            &registered,
        );
        domain_name::add_domain_name(domain_name);
        domain_name::emit_registered(registered);
    }


    public entry fun renew(
        account: &signer,
        domain_name_id_top_level_domain: String,
        domain_name_id_second_level_domain: String,
        renew_period: u64,
    ) {
        let domain_name_id: DomainNameId = domain_name::new_domain_name_id(
            domain_name_id_top_level_domain,
            domain_name_id_second_level_domain,
        );

        let domain_name = domain_name::remove_domain_name(domain_name_id);
        let renewed = domain_name_renew_logic::verify(
            account,
            renew_period,
            &domain_name,
        );
        let updated_domain_name = domain_name_renew_logic::mutate(
            &renewed,
            domain_name,
        );
        domain_name::update_version_and_add(updated_domain_name);
        domain_name::emit_renewed(renewed);
    }

}
