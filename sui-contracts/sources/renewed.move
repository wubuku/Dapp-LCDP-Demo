module sui_contracts::renewed {

    use sui::object;
    use sui_contracts::domain_name::{Self, DomainNameId, Renewed};

    public fun id(renewed: &Renewed): object::ID {
        domain_name::renewed_id(renewed)
    }

    public fun domain_name_id(renewed: &Renewed): DomainNameId {
        domain_name::renewed_domain_name_id(renewed)
    }

    public fun renew_period(renewed: &Renewed): u64 {
        domain_name::renewed_renew_period(renewed)
    }

    public fun account(renewed: &Renewed): address {
        domain_name::renewed_account(renewed)
    }

}