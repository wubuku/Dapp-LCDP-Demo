module sui_contracts::order_v2_cancel_order_ship_group_quantity_logic {
    use std::string::String;

    use sui::tx_context::TxContext;
    use sui_contracts::order_item_ship_group_association;
    use sui_contracts::order_ship_group;
    use sui_contracts::order_v2;
    use sui_contracts::order_ship_group_quantity_canceled;

    friend sui_contracts::order_v2_aggregate;

    public(friend) fun verify(
        ship_group_seq_id: u8,
        product_id: String,
        cancel_quantity: u64,
        order_v2: &order_v2::OrderV2,
        ctx: &TxContext,
    ): order_v2::OrderShipGroupQuantityCanceled {
        let _ = ctx;
        order_v2::new_order_ship_group_quantity_canceled(
            order_v2,
            ship_group_seq_id,
            product_id,
            cancel_quantity,
        )
    }

    public(friend) fun mutate(
        order_ship_group_quantity_canceled: &order_v2::OrderShipGroupQuantityCanceled,
        order_v2: order_v2::OrderV2,
        ctx: &TxContext, // modify the reference to mutable if needed
    ): order_v2::OrderV2 {
        let _ = ctx;
        let order_ship_group = order_v2::borrow_mut_order_ship_group(
            &mut order_v2,
            order_ship_group_quantity_canceled::ship_group_seq_id(order_ship_group_quantity_canceled),
        );
        let assoc = order_ship_group::borrow_mut_order_item_ship_group_association(
            order_ship_group,
            order_ship_group_quantity_canceled::product_id(order_ship_group_quantity_canceled),
        );
        order_item_ship_group_association::set_cancel_quantity(
            assoc,
            order_ship_group_quantity_canceled::cancel_quantity(order_ship_group_quantity_canceled),
        );
        order_v2
    }
}
