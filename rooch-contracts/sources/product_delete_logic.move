module rooch_demo::product_delete_logic {
    use moveos_std::object::Object;
    use moveos_std::storage_context::StorageContext;
    use rooch_demo::product;

    friend rooch_demo::product_aggregate;

    public(friend) fun verify(
        storage_ctx: &mut StorageContext,
        account: &signer,
        product_obj: &Object<product::Product>,
    ): product::ProductCrudEvent {
        let _ = storage_ctx;
        let _ = account;
        product::new_product_deleted(
            product_obj,
        )
    }

    public(friend) fun mutate(
        storage_ctx: &mut StorageContext,
        _account: &signer,
        product_deleted: &product::ProductCrudEvent,
        product_obj: Object<product::Product>,
    ): Object<product::Product> {
        let product_id = product::product_id(&product_obj);
        let _ = storage_ctx;
        let _ = product_id;
        let _ = product_deleted;
        product_obj
    }

}
