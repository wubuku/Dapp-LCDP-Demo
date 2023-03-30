module sui_contracts::product_aggregate {
    use std::string::String;
    use sui::tx_context;
    use sui_contracts::product;
    use sui_contracts::product_create_logic;

    public entry fun create(
        name: String,
        unit_price: u128,
        product_id_generator: &mut product::ProductIdGenerator,
        ctx: &mut tx_context::TxContext,
    ) {
        let product_created = product_create_logic::verify(
            name,
            unit_price,
            product_id_generator,
            ctx,
        );
        let product = product_create_logic::mutate(
            &product_created,
            product_id_generator,
            ctx,
        );
        product::set_product_created_id(&mut product_created, product::id(&product));
        product::freeze_object(product);
        product::emit_product_created(product_created);
    }

}
