module sui_demo_contracts::order_delete_logic {
    use sui::tx_context::TxContext;

    use sui_demo_contracts::order;

    friend sui_demo_contracts::order_aggregate;

    public(friend) fun verify(
        order: &order::Order,
        ctx: &TxContext,
    ): order::OrderDeleted {
        let _ = ctx;
        order::new_order_deleted(
            order
        )
    }

    public(friend) fun mutate(
        order_deleted: &order::OrderDeleted,
        order: order::Order,
        ctx: &TxContext, // modify the reference to mutable if needed
    ): order::Order {
        let id = order::id(&order);
        let _ = ctx;
        let _ = id;
        let _ = order_deleted;
        order
    }
}
