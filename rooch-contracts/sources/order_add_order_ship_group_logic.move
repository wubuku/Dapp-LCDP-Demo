module rooch_demo::order_add_order_ship_group_logic {
    use std::string::String;

    use moveos_std::object::{Object};
    use moveos_std::object_id::ObjectID;
    use moveos_std::storage_context::{Self, StorageContext};
    use rooch_demo::order;
    use rooch_demo::order_item;
    use rooch_demo::order_item_ship_group_association;
    use rooch_demo::order_ship_group;
    use rooch_demo::order_ship_group_added;

    friend rooch_demo::order_aggregate;

    public(friend) fun verify(
        _storage_ctx: &mut StorageContext,
        _account: &signer,
        ship_group_seq_id: u8,
        shipment_method: String,
        product_obj_id: ObjectID,
        quantity: u64,
        order_obj: &Object<order::Order>,
    ): order::OrderShipGroupAdded {
        assert!(order::items_contains(order_obj, product_obj_id), 1001);//item not found
        let order_item = order::borrow_item(order_obj, product_obj_id);
        assert!(order_item::quantity(order_item) >= quantity, 1002);//quantity not enough
        assert!(!order::order_ship_groups_contains(order_obj, ship_group_seq_id), 1003);//ship group already exists

        order::new_order_ship_group_added(
            order_obj,
            ship_group_seq_id,
            shipment_method,
            product_obj_id,
            quantity,
        )
    }

    public(friend) fun mutate(
        storage_ctx: &mut StorageContext,
        order_ship_group_added: &order::OrderShipGroupAdded,
        order_obj: Object<order::Order>,
    ): Object<order::Order> {
        let tx_ctx = storage_context::tx_context_mut(storage_ctx);
        let order_ship_group = order_ship_group::new_order_ship_group(
            tx_ctx,
            order_ship_group_added::ship_group_seq_id(order_ship_group_added),
            order_ship_group_added::shipment_method(order_ship_group_added),
        );
        let assc = order_item_ship_group_association::new_order_item_ship_group_association(
            tx_ctx,
            order_ship_group_added::product_obj_id(order_ship_group_added),
            order_ship_group_added::quantity(order_ship_group_added),
            0,
        );
        order_ship_group::add_order_item_ship_group_association(storage_ctx, &mut order_ship_group, assc);
        order::add_order_ship_group(
            storage_ctx,
            &mut order_obj,
            order_ship_group
        );
        order_obj
    }
}
