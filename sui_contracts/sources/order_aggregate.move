module sui_contracts::order_aggregate {

    use sui::tx_context::TxContext;
    use sui_contracts::order;
    use sui_contracts::order_create_logic;
    use sui_contracts::product::Product;

    public entry fun create(
        product: &mut Product,
        quantity: u64,
        ctx: &mut TxContext,
    ) {
        let (order_created, id) = order_create_logic::verify(
            product,
            quantity,
            ctx,
        );
        let order_ = order_create_logic::mutate(
            &order_created,
            id,
            ctx,
        );
        order::transfer(order_, order::order_created_owner(&order_created));
        order::emit_order_created(order_created);
    }
}
