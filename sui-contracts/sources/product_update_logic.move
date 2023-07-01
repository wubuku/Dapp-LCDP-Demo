module sui_contracts::product_update_logic {
    use std::string::String;

    use sui::tx_context::TxContext;

    use sui_contracts::product;
    use sui_contracts::product_updated;

    friend sui_contracts::product_aggregate;

    public(friend) fun verify(
        name: String,
        unit_price: u128,
        product: &product::Product,
        ctx: &TxContext,
    ): product::ProductUpdated {
        let _ = ctx;
        product::new_product_updated(
            product,
            name,
            unit_price,
        )
    }

    public(friend) fun mutate(
        product_updated: &product::ProductUpdated,
        product: product::Product,
        ctx: &TxContext, // modify the reference to mutable if needed
    ): product::Product {
        let name = product_updated::name(product_updated);
        let unit_price = product_updated::unit_price(product_updated);
        let product_id = product::product_id(&product);
        let _ = ctx;
        let _ = product_id;
        product::set_name(&mut product, name);
        product::set_unit_price(&mut product, unit_price);
        product
    }
}
