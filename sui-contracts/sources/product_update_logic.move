module sui_demo_contracts::product_update_logic {
    use std::string::String;
    use sui::tx_context::TxContext;
    use sui_demo_contracts::product;
    use sui_demo_contracts::product_crud_event;

    friend sui_demo_contracts::product_aggregate;

    public(friend) fun verify(
        name: String,
        unit_price: u128,
        owner: address,
        product: &product::Product,
        ctx: &TxContext,
    ): product::ProductCrudEvent {
        let _ = ctx;
        assert!(sui::tx_context::sender(ctx) == product::owner(product), 111);
        product::new_product_updated(
            product,
            name,
            unit_price,
            owner,
        )
    }

    public(friend) fun mutate(
        product_updated: &product::ProductCrudEvent,
        product: &mut product::Product,
        ctx: &TxContext, // modify the reference to mutable if needed
    ) {
        let name = product_crud_event::name(product_updated);
        let unit_price = product_crud_event::unit_price(product_updated);
        let owner = product_crud_event::owner(product_updated);
        let product_id = product::product_id(product);
        let _ = ctx;
        let _ = product_id;
        product::set_name(product, name);
        product::set_unit_price(product, unit_price);
        product::set_owner(product, owner);
    }

}
