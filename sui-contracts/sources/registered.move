module sui_contracts::registered {

    use sui::object;
    use sui_contracts::domain_name::{Self, DomainNameId, Registered};

    public fun id(registered: &Registered): object::ID {
        domain_name::registered_id(registered)
    }

    public fun domain_name_id(registered: &Registered): DomainNameId {
        domain_name::registered_domain_name_id(registered)
    }

    public fun registration_period(registered: &Registered): u64 {
        domain_name::registered_registration_period(registered)
    }

    public fun owner(registered: &Registered): address {
        domain_name::registered_owner(registered)
    }

}
