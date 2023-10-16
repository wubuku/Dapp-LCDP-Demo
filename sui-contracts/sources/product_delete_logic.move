module sui_demo_contracts::product_delete_logic {
    use sui::tx_context::TxContext;
    use sui_demo_contracts::product;

    friend sui_demo_contracts::product_aggregate;

    public(friend) fun verify(
        product: &product::Product,
        ctx: &TxContext,
    ): product::ProductCrudEvent {
        let _ = ctx;
        product::new_product_deleted(
            product,
        )
    }

    public(friend) fun mutate(
        product_deleted: &product::ProductCrudEvent,
        product: &mut product::Product,
        ctx: &TxContext, // modify the reference to mutable if needed
    ) {
        let product_id = product::product_id(product);
        let _ = ctx;
        let _ = product_id;
        let _ = product_deleted;
    }

}
