module aptos_demo::order_cancel_order_ship_group_quantity_logic {
    use std::string::String;

    use aptos_demo::order;
    use aptos_demo::order_item_ship_group_association;
    use aptos_demo::order_ship_group;
    use aptos_demo::order_ship_group_quantity_canceled;

    friend aptos_demo::order_aggregate;

    public(friend) fun verify(
        account: &signer,
        ship_group_seq_id: u8,
        product_id: String,
        cancel_quantity: u64,
        order: &order::Order,
    ): order::OrderShipGroupQuantityCanceled {
        let _ = account;
        order::new_order_ship_group_quantity_canceled(
            order,
            ship_group_seq_id,
            product_id,
            cancel_quantity,
        )
    }

    public(friend) fun mutate(
        _account: &signer,
        order_ship_group_quantity_canceled: &order::OrderShipGroupQuantityCanceled,
        order: order::Order,
    ): order::Order {
        let order_ship_group = order::borrow_mut_order_ship_group(
            &mut order,
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
        order
    }
}
