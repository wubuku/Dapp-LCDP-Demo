module rooch_demo::product_create_logic {
    use std::string::String;

    use moveos_std::object::Object;
    use moveos_std::storage_context::StorageContext;
    use rooch_demo::product;
    use rooch_demo::product_created;

    friend rooch_demo::product_aggregate;

    public(friend) fun verify(
        storage_ctx: &mut StorageContext,
        _account: &signer,
        name: String,
        unit_price: u128,
    ): product::ProductCreated {
        product::new_product_created(
            storage_ctx,
            name,
            unit_price,
        )
    }

    public(friend) fun mutate(
        storage_ctx: &mut StorageContext,
        _account: &signer,
        product_created: &product::ProductCreated,
    ): Object<product::Product> {
        let product = product::create_product(
            storage_ctx,
            product_created::name(product_created),
            product_created::unit_price(product_created),
        );
        product
    }
}
