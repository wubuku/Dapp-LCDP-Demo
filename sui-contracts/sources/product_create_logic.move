#[allow(unused_mut_parameter)]
module sui_demo_contracts::product_create_logic {
    use std::string::String;
    use sui::tx_context::TxContext;
    use sui_demo_contracts::product;
    use sui_demo_contracts::product_crud_event;

    friend sui_demo_contracts::product_aggregate;

    public(friend) fun verify(
        name: String,
        unit_price: u128,
        owner: address,
        product_id_generator: &mut product::ProductIdGenerator,
        ctx: &mut TxContext,
    ): product::ProductCrudEvent {
        let _ = ctx;
        product::new_product_created(
            name,
            unit_price,
            owner,
            product_id_generator,
        )
    }

    public(friend) fun mutate(
        product_created: &product::ProductCrudEvent,
        product_id_generator: &product::ProductIdGenerator,
        ctx: &mut TxContext,
    ): product::Product {
        let name = product_crud_event::name(product_created);
        let unit_price = product_crud_event::unit_price(product_created);
        let owner = product_crud_event::owner(product_created);
        product::create_product(
            name,
            unit_price,
            owner,
            product_id_generator,
            ctx,
        )
    }

}
