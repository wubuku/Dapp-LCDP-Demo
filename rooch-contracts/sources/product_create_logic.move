module rooch_demo::product_create_logic {
    use moveos_std::object::Object;
    use moveos_std::storage_context::StorageContext;
    use rooch_demo::product;
    use rooch_demo::product_crud_event;
    use std::string::String;

    friend rooch_demo::product_aggregate;

    public(friend) fun verify(
        storage_ctx: &mut StorageContext,
        account: &signer,
        name: String,
        unit_price: u128,
    ): product::ProductCrudEvent {
        let _ = storage_ctx;
        let _ = account;
        product::new_product_created(
            storage_ctx,
            name,
            unit_price,
        )
    }

    public(friend) fun mutate(
        storage_ctx: &mut StorageContext,
        _account: &signer,
        product_created: &product::ProductCrudEvent,
    ): Object<product::Product> {
        let name = product_crud_event::name(product_created);
        let unit_price = product_crud_event::unit_price(product_created);
        product::create_product(
            storage_ctx,
            name,
            unit_price,
        )
    }

}
