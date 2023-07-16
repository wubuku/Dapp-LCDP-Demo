module aptos_demo::product_create_logic {
    use aptos_demo::product;
    use aptos_demo::product_event;
    use std::string::String;

    friend aptos_demo::product_aggregate;

    public(friend) fun verify(
        account: &signer,
        name: String,
        unit_price: u128,
    ): product::ProductEvent {
        let _ = account;
        product::new_product_created(
            name,
            unit_price,
        )
    }

    public(friend) fun mutate(
        _account: &signer,
        product_created: &product::ProductEvent,
    ): product::Product {
        let name = product_event::name(product_created);
        let unit_price = product_event::unit_price(product_created);
        product::create_product(
            name,
            unit_price,
        )
    }

}
