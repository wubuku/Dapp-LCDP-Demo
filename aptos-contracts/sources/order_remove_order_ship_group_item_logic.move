module aptos_demo::order_remove_order_ship_group_item_logic {
    use std::string::String;

    use aptos_demo::order;
    use aptos_demo::order_ship_group;

    friend aptos_demo::order_aggregate;

    public(friend) fun verify(
        account: &signer,
        ship_group_seq_id: u8,
        product_id: String,
        order: &order::Order,
    ): order::OrderShipGroupItemRemoved {
        let _ = account;
        order::new_order_ship_group_item_removed(
            order,
            ship_group_seq_id,
            product_id,
        )
    }

    public(friend) fun mutate(
        _account: &signer,
        order_ship_group_item_removed: &order::OrderShipGroupItemRemoved,
        order: order::Order,
    ): order::Order {
        let order_ship_group = order::borrow_mut_order_ship_group(
            &mut order,
            order::order_ship_group_item_removed_ship_group_seq_id(order_ship_group_item_removed),
        );
        order_ship_group::remove_order_item_ship_group_association(
            order_ship_group,
            order::order_ship_group_item_removed_product_id(order_ship_group_item_removed),
        );
        order
    }
}
