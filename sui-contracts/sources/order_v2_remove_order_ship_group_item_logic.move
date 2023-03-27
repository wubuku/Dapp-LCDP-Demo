module sui_contracts::order_v2_remove_order_ship_group_item_logic {
    use std::string::String;

    use sui::tx_context::TxContext;
    use sui_contracts::order_ship_group;
    use sui_contracts::order_v2;
    use sui_contracts::order_ship_group_item_removed;

    friend sui_contracts::order_v2_aggregate;

    public(friend) fun verify(
        ship_group_seq_id: u8,
        product_id: String,
        order_v2: &order_v2::OrderV2,
        ctx: &TxContext,
    ): order_v2::OrderShipGroupItemRemoved {
        let _ = ctx;
        order_v2::new_order_ship_group_item_removed(
            order_v2,
            ship_group_seq_id,
            product_id,
        )
    }

    public(friend) fun mutate(
        order_ship_group_item_removed: &order_v2::OrderShipGroupItemRemoved,
        order_v2: order_v2::OrderV2,
        ctx: &TxContext, // modify the reference to mutable if needed
    ): order_v2::OrderV2 {
        let _ = ctx;
        let order_ship_group = order_v2::borrow_mut_order_ship_group(
            &mut order_v2,
            order_ship_group_item_removed::ship_group_seq_id(order_ship_group_item_removed),
        );
        order_ship_group::remove_order_item_ship_group_association(
            order_ship_group,
            order_ship_group_item_removed::product_id(order_ship_group_item_removed),
        );
        order_v2
    }
}
