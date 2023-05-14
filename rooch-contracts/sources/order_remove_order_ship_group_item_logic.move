module rooch_demo::order_remove_order_ship_group_item_logic {
    use moveos_std::object::{Object, ObjectID};
    use moveos_std::storage_context::StorageContext;
    use rooch_demo::order;
    use rooch_demo::order_ship_group_item_removed;

    friend rooch_demo::order_aggregate;

    public(friend) fun verify(
        _storage_ctx: &mut StorageContext,
        _account: &signer,
        ship_group_seq_id: u8,
        product_obj_id: ObjectID,
        order_obj: &Object<order::Order>,
    ): order::OrderShipGroupItemRemoved {
        order::new_order_ship_group_item_removed(
            order_obj,
            ship_group_seq_id,
            product_obj_id,
        )
    }

    public(friend) fun mutate(
        _storage_ctx: &mut StorageContext,
        order_ship_group_item_removed: &order::OrderShipGroupItemRemoved,
        order_obj: Object<order::Order>,
    ): Object<order::Order> {
        let _order_ship_group = order::borrow_mut_order_ship_group(
            &mut order_obj,
            order_ship_group_item_removed::ship_group_seq_id(order_ship_group_item_removed),
        );
        //todo remove_order_item_ship_group_association is not implemented yet
        // order_ship_group::remove_order_item_ship_group_association(
        //     order_ship_group,
        //     order_ship_group_item_removed::product_id(order_ship_group_item_removed),
        // );
        order_obj
    }
}
