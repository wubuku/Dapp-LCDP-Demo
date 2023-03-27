module sui_contracts::order_v2_add_order_ship_group_logic {
    use std::string::String;

    use sui::tx_context::TxContext;
    use sui_contracts::order_v2;
    use sui_contracts::order_v2_item;
    use sui_contracts::order_ship_group;
    use sui_contracts::order_item_ship_group_association;
    use sui_contracts::order_ship_group_added;

    friend sui_contracts::order_v2_aggregate;

    public(friend) fun verify(
        ship_group_seq_id: u8,
        shipment_method: String,
        product_id: String,
        quantity: u64,
        order_v2: &order_v2::OrderV2,
        ctx: &TxContext,
    ): order_v2::OrderShipGroupAdded {
        assert!(order_v2::items_contains(order_v2, product_id), 1001);//item not found
        let order_item = order_v2::borrow_item(order_v2, product_id);
        assert!(order_v2_item::quantity(order_item) >= quantity, 1002);//quantity not enough
        assert!(!order_v2::order_ship_groups_contains(order_v2, ship_group_seq_id), 1003);//ship group already exists
        let _ = ctx;
        order_v2::new_order_ship_group_added(
            order_v2,
            ship_group_seq_id,
            shipment_method,
            product_id,
            quantity,
        )
    }

    public(friend) fun mutate(
        order_ship_group_added: &order_v2::OrderShipGroupAdded,
        order_v2: order_v2::OrderV2,
        ctx: &mut TxContext,
    ): order_v2::OrderV2 {
        let order_ship_group = order_ship_group::new_order_ship_group(
            order_ship_group_added::ship_group_seq_id(order_ship_group_added),
            order_ship_group_added::shipment_method(order_ship_group_added),
            ctx,
        );
        let assc = order_item_ship_group_association::new_order_item_ship_group_association(
            order_ship_group_added::product_id(order_ship_group_added),
            order_ship_group_added::quantity(order_ship_group_added),
            0,
            ctx,
        );
        order_ship_group::add_order_item_ship_group_association(&mut order_ship_group, assc);
        order_v2::add_order_ship_group(
            &mut order_v2,
            order_ship_group
        );
        order_v2
    }
}
