module sui_contracts::order_create_logic {
    use sui::object::{Self, UID};
    use sui::tx_context::{Self, TxContext};
    use sui_contracts::order;
    use sui_contracts::product::{Self, Product};

    public(friend) fun verify(
        product: &Product,
        quantity: u64,
        ctx: &mut TxContext,
    ): (order::OrderCreated, UID) {
        let id = object::new(ctx);
        let e_quantity = quantity;
        let e_owner = tx_context::sender(ctx);
        let unit_price = product::unit_price(product);
        (
            order::new_order_created(
                &id,
                product::id(product),
                e_quantity,
                unit_price,
                unit_price * (e_quantity as u128),
                e_owner,
            ),
            id,
        )
    }
}
