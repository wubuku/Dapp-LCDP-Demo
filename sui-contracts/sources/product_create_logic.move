module sui_contracts::product_create_logic {
    use std::string::String;
    use sui::tx_context::TxContext;
    use sui_contracts::product;
    use sui_contracts::product_created;

    friend sui_contracts::product_aggregate;

    public(friend) fun verify(
        name: String,
        unit_price: u128,
        owner: address,
        product_id_generator: &mut product::ProductIdGenerator,
        ctx: &mut TxContext,
    ): product::ProductCreated {
        let _ = ctx;
        product::new_product_created(
            name,
            unit_price,
            owner,
            product_id_generator,
        )
    }

    public(friend) fun mutate(
        product_created: &product::ProductCreated,
        product_id_generator: &product::ProductIdGenerator,
        ctx: &mut TxContext,
    ): product::Product {
        let name = product_created::name(product_created);
        let unit_price = product_created::unit_price(product_created);
        let owner = product_created::owner(product_created);
        product::create_product(
            name,
            unit_price,
            owner,
            product_id_generator,
            ctx,
        )
    }

}
