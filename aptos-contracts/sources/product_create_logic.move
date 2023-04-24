module aptos_demo::product_create_logic {
    use std::string::String;

    use aptos_demo::product::{Self, Product};
    use aptos_demo::product_created;

    //use sui::tx_context::TxContext;
    //use aptos_demo::product_created;

    friend aptos_demo::product_aggregate;

    public(friend) fun verify(
        name: String,
        unit_price: u128,
        //product_id_generator: &mut product::ProductIdGenerator, // use mutable reference to generate next id
        //ctx: &mut TxContext,
    ): product::ProductCreated {
        //let _ = ctx;
        let e_name = name;
        let e_unit_price = unit_price;
        //let _ = product_id_generator;
        //let e_owner = tx_context::sender(ctx);
        product::new_product_created(
            //product_id, // generated by ProductIdGenerator
            e_name,
            e_unit_price,
            //e_owner,
            //product_id_generator,
        )
    }

    public(friend) fun mutate(
        product_created: &product::ProductCreated,
        //product_id_generator: &product::ProductIdGenerator,
        //ctx: &mut TxContext,
    ): Product {
        let product = product::create_product(
            product_created::name(product_created),
            product_created::unit_price(product_created),
            //product_id_generator,
            //ctx,
        );
        product
    }
}
