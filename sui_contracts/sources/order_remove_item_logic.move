module sui_contracts::order_remove_item_logic {
    use sui_contracts::order;
    use sui::tx_context::TxContext;
    use std::string::String;
    friend sui_contracts::order_aggregate;

    public(friend) fun verify(
        product_id: String,
        order_: &order::Order,
        ctx: &TxContext,
    ): order::OrderItemRemoved {
        let e_product_id = product_id;
        let _ = ctx;
        order::new_order_item_removed(
            order_,
            e_product_id,
        )
    }

    public(friend) fun mutate(
        order_item_removed: &order::OrderItemRemoved,
        order_: order::Order,
        //ctx: &TxContext,
    ): order::Order {
        let product_id = order::order_item_removed_product_id(order_item_removed);
        order::remove_item(&mut order_, product_id);
        order_
    }
}
