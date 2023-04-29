module aptos_demo::pass_object {
    friend aptos_demo::product;
    friend aptos_demo::order;
    friend aptos_demo::domain_name;
    friend aptos_demo::day_summary;


    /// read-only 'hot potato' wrapper.
    struct PassObject<T: store> {
        value: T,
    }

    public(friend) fun new<T: store>(value: T): PassObject<T> {
        PassObject { value }
    }

    public(friend) fun extract<T: store>(pass_object: PassObject<T>): T {
        let PassObject { value } = pass_object;
        value
    }

    public fun borrow<T: store>(pass_object: &PassObject<T>): &T {
        &pass_object.value
    }
}
