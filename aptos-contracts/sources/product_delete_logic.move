module aptos_demo::product_delete_logic {
    use aptos_demo::product;

    friend aptos_demo::product_aggregate;

    public(friend) fun verify(
        account: &signer,
        product: &product::Product,
    ): product::ProductEvent {
        let _ = account;
        product::new_product_deleted(
            product,
        )
    }

    public(friend) fun mutate(
        _account: &signer,
        product_deleted: &product::ProductEvent,
        product: product::Product,
    ): product::Product {
        let product_id = product::product_id(&product);
        let _ = product_id;
        let _ = product_deleted;
        product
    }

}
