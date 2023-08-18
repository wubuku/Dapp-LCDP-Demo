module rooch_demo::product_update_logic {
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
        product_obj: &Object<product::Product>,
    ): product::ProductCrudEvent {
        let _ = storage_ctx;
        let _ = account;
        product::new_product_updated(
            product_obj,
            name,
            unit_price,
        )
    }

    public(friend) fun mutate(
        storage_ctx: &mut StorageContext,
        _account: &signer,
        product_updated: &product::ProductCrudEvent,
        product_obj: Object<product::Product>,
    ): Object<product::Product> {
        let name = product_crud_event::name(product_updated);
        let unit_price = product_crud_event::unit_price(product_updated);
        let product_id = product::product_id(&product_obj);
        let _ = storage_ctx;
        let _ = product_id;
        product::set_name(&mut product_obj, name);
        product::set_unit_price(&mut product_obj, unit_price);
        product_obj
    }

}
