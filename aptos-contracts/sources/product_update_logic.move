module aptos_demo::product_update_logic {
    use aptos_demo::product;
    use aptos_demo::product_updated;
    use std::string::String;

    friend aptos_demo::product_aggregate;

    public(friend) fun verify(
        account: &signer,
        name: String,
        unit_price: u128,
        product: &product::Product,
    ): product::ProductUpdated {
        let _ = account;
        product::new_product_updated(
            product,
            name,
            unit_price,
        )
    }

    public(friend) fun mutate(
        _account: &signer,
        product_updated: &product::ProductUpdated,
        product: product::Product,
    ): product::Product {
        let name = product_updated::name(product_updated);
        let unit_price = product_updated::unit_price(product_updated);
        let product_id = product::product_id(&product);
        let _ = product_id;
        product::set_name(&mut product, name);
        product::set_unit_price(&mut product, unit_price);
        product
    }

}
