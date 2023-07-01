module sui_contracts::product_delete_logic {
    use sui::tx_context::TxContext;
    use sui_contracts::product;

    friend sui_contracts::product_aggregate;

    public(friend) fun verify(
        product: &product::Product,
        ctx: &TxContext,
    ): product::ProductDeleted {
        let _ = ctx;
        product::new_product_deleted(
            product,
        )
    }

    public(friend) fun mutate(
        product_deleted: &product::ProductDeleted,
        product: product::Product,
        ctx: &TxContext, // modify the reference to mutable if needed
    ): product::Product {
        let product_id = product::product_id(&product);
        let _ = ctx;
        let _ = product_id;
        let _ = product_deleted;
        product
    }

}
