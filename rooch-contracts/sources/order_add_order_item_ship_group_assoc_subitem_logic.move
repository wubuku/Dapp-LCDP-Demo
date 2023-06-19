module rooch_demo::order_add_order_item_ship_group_assoc_subitem_logic {
    use std::string::String;

    use moveos_std::object::Object;
    use moveos_std::object_id::ObjectID;
    use moveos_std::storage_context::StorageContext;
    use rooch_demo::day::Day;
    use rooch_demo::order::{Self, OrderItemShipGroupAssocSubitemAdded};
    use rooch_demo::order_item_ship_group_assoc_subitem;
    use rooch_demo::order_item_ship_group_assoc_subitem_added;
    use rooch_demo::order_item_ship_group_association;
    use rooch_demo::order_ship_group;

    friend rooch_demo::order_aggregate;

    public(friend) fun verify(
        _storage_ctx: &mut StorageContext,
        _account: &signer,
        ship_group_seq_id: u8,
        product_obj_id: ObjectID,
        day: Day,
        description: String,
        order_obj: &Object<order::Order>,
    ): OrderItemShipGroupAssocSubitemAdded {
        order::new_order_item_ship_group_assoc_subitem_added(
            order_obj,
            ship_group_seq_id,
            product_obj_id,
            day,
            description,
        )
    }

    public(friend) fun mutate(
        storage_ctx: &mut StorageContext,
        order_item_ship_group_assoc_subitem_added: &order::OrderItemShipGroupAssocSubitemAdded,
        order_obj: Object<order::Order>,
    ): Object<order::Order> {
        let ship_group_seq_id = order_item_ship_group_assoc_subitem_added::ship_group_seq_id(
            order_item_ship_group_assoc_subitem_added
        );
        let product_obj_id = order_item_ship_group_assoc_subitem_added::product_obj_id(
            order_item_ship_group_assoc_subitem_added
        );
        let day = order_item_ship_group_assoc_subitem_added::day(order_item_ship_group_assoc_subitem_added);
        let description = order_item_ship_group_assoc_subitem_added::description(
            order_item_ship_group_assoc_subitem_added
        );
        let order_id = order::order_id(&order_obj);
        let order_ship_group = order::borrow_mut_order_ship_group(&mut order_obj, ship_group_seq_id);
        let order_item_ship_group_association = order_ship_group::borrow_mut_order_item_ship_group_association(
            order_ship_group,
            product_obj_id
        );
        let subitem = order_item_ship_group_assoc_subitem::new_order_item_ship_group_assoc_subitem(
            day,
            description,
        );
        order_item_ship_group_association::add_subitem(
            storage_ctx,
            order_id,
            ship_group_seq_id,
            order_item_ship_group_association,
            subitem,
        );
        order_obj
    }
}
