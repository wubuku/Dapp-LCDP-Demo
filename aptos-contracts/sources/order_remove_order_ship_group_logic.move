module aptos_demo::order_remove_order_ship_group_logic {
    use aptos_demo::order;

    friend aptos_demo::order_aggregate;

    public(friend) fun verify(
        account: &signer,
        ship_group_seq_id: u8,
        order: &order::Order,
    ): order::OrderShipGroupRemoved {
        let _ = account;
        order::new_order_ship_group_removed(
            order,
            ship_group_seq_id,
        )
    }

    public(friend) fun mutate(
        order_ship_group_removed: &order::OrderShipGroupRemoved,
        order: order::Order,
    ): order::Order {
        let _ = order_ship_group_removed;

        order
    }
}
