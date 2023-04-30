module aptos_demo::order_add_order_ship_group_logic {
    use std::string::String;

    use aptos_demo::order;
    use aptos_demo::order_item;
    use aptos_demo::order_item_ship_group_association;
    use aptos_demo::order_ship_group;
    use aptos_demo::order_ship_group_added;

    friend aptos_demo::order_aggregate;

    public(friend) fun verify(
        account: &signer,
        ship_group_seq_id: u8,
        shipment_method: String,
        product_id: String,
        quantity: u64,
        order: &order::Order,
    ): order::OrderShipGroupAdded {
        let _ = account;
        assert!(order::items_contains(order, product_id), 1001);//item not found
        let order_item = order::borrow_item(order, product_id);
        assert!(order_item::quantity(order_item) >= quantity, 1002);//quantity not enough
        assert!(!order::order_ship_groups_contains(order, ship_group_seq_id), 1003);//ship group already exists

        order::new_order_ship_group_added(
            order,
            ship_group_seq_id,
            shipment_method,
            product_id,
            quantity,
        )
    }

    public(friend) fun mutate(
        order_ship_group_added: &order::OrderShipGroupAdded,
        order: order::Order,
    ): order::Order {
        let order_ship_group = order_ship_group::new_order_ship_group(
            order_ship_group_added::ship_group_seq_id(order_ship_group_added),
            order_ship_group_added::shipment_method(order_ship_group_added),
            //
        );
        let assc = order_item_ship_group_association::new_order_item_ship_group_association(
            order_ship_group_added::product_id(order_ship_group_added),
            order_ship_group_added::quantity(order_ship_group_added),
            0,
            //
        );
        order_ship_group::add_order_item_ship_group_association(&mut order_ship_group, assc);
        order::add_order_ship_group(
            &mut order,
            order_ship_group
        );
        order
    }
}
