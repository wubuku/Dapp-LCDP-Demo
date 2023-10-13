module sui_demo_contracts::order_v2_remove_order_ship_group_logic {
    use sui::tx_context::TxContext;
    use sui_demo_contracts::order_v2;

    friend sui_demo_contracts::order_v2_aggregate;

    public(friend) fun verify(
        ship_group_seq_id: u8,
        order_v2: &order_v2::OrderV2,
        ctx: &TxContext,
    ): order_v2::OrderShipGroupRemoved {
        let _ = ctx;
        order_v2::new_order_ship_group_removed(
            order_v2,
            ship_group_seq_id,
        )
    }

    public(friend) fun mutate(
        order_ship_group_removed: &order_v2::OrderShipGroupRemoved,
        order_v2: order_v2::OrderV2,
        ctx: &TxContext, // modify the reference to mutable if needed
    ): order_v2::OrderV2 {
        let _ = ctx;
        // let order_ship_group = order_v2::borrow_mut_order_ship_group(
        //     &mut order_v2,
        //     order_v2::order_ship_group_removed_ship_group_seq_id(order_ship_group_removed),
        // );

        /*
        order_v2::remove_order_ship_group(&mut order_v2,
            order_v2::order_ship_group_removed_ship_group_seq_id(order_ship_group_removed),
        );
        */
        let _ = order_ship_group_removed;

        order_v2
    }
}
