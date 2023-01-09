module sui_contracts::product_aggregate {

    use std::string::String;

    use sui::tx_context::TxContext;
    use sui_contracts::product;
    use sui_contracts::product_create_logic;

    public entry fun create(
        name: String,
        unit_price: u128,
        product_id_generator: &mut product::ProductIdGenerator,
        ctx: &mut TxContext,
    ) {
        let (product_created, id) = product_create_logic::verify(
            name,
            unit_price,
            product_id_generator,
            ctx,
        );
        let product_ = product_create_logic::mutate(
            &product_created,
            id,
            product_id_generator,
            ctx,
        );
        product::freeze_object(
            product_,
        );
        product::emit_product_created(product_created);
    }
}
