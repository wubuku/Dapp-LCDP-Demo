module rooch_demo::order_cancel_order_ship_group_quantity_logic {
    use moveos_std::object::{Object};
    use moveos_std::object_id::ObjectID;
    use moveos_std::storage_context::StorageContext;
    use rooch_demo::order;
    use rooch_demo::order_item_ship_group_association;
    use rooch_demo::order_ship_group;
    use rooch_demo::order_ship_group_quantity_canceled;

    friend rooch_demo::order_aggregate;

    public(friend) fun verify(
        _storage_ctx: &mut StorageContext,
        _account: &signer,
        ship_group_seq_id: u8,
        product_obj_id: ObjectID,
        cancel_quantity: u64,
        order_obj: &Object<order::Order>,
    ): order::OrderShipGroupQuantityCanceled {
        order::new_order_ship_group_quantity_canceled(
            order_obj,
            ship_group_seq_id,
            product_obj_id,
            cancel_quantity,
        )
    }

    public(friend) fun mutate(
        _storage_ctx: &mut StorageContext,
        order_ship_group_quantity_canceled: &order::OrderShipGroupQuantityCanceled,
        order_obj: Object<order::Order>,
    ): Object<order::Order> {
        let order_ship_group = order::borrow_mut_order_ship_group(
            &mut order_obj,
            order_ship_group_quantity_canceled::ship_group_seq_id(order_ship_group_quantity_canceled),
        );
        let assoc = order_ship_group::borrow_mut_order_item_ship_group_association(
            order_ship_group,
            order_ship_group_quantity_canceled::product_obj_id(order_ship_group_quantity_canceled),
        );
        order_item_ship_group_association::set_cancel_quantity(
            assoc,
            order_ship_group_quantity_canceled::cancel_quantity(order_ship_group_quantity_canceled),
        );
        order_obj
    }
}
