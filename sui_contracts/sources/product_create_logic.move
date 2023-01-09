module sui_contracts::product_create_logic {
    use std::string::String;

    use sui::object::{Self, UID};
    use sui::tx_context::TxContext;
    use sui_contracts::product;

    friend sui_contracts::product_aggregate;

    public(friend) fun verify(
        name: String,
        unit_price: u128,
        product_id_generator: &mut product::ProductIdGenerator,
        ctx: &mut TxContext,
    ): (product::ProductCreated, UID) {
        let id = object::new(ctx);
        let e_name = name;
        let e_unit_price = unit_price;
        let _ = product_id_generator;
        //let e_owner = tx_context::sender(ctx);
        (
            product::new_product_created(
                &id,
                e_name,
                e_unit_price,
                //e_owner,
                product_id_generator,
            ),
            id,
        )
    }

    public(friend) fun mutate(
        product_created: &product::ProductCreated,
        id: UID,
        product_id_generator: &product::ProductIdGenerator,
        ctx: &mut TxContext,
    ): product::Product {
        let _ = ctx;
        let product = product::create_product(
            id,
            product::product_created_name(product_created),
            product::product_created_unit_price(product_created),
            product_id_generator,
        );
        product
    }
}
