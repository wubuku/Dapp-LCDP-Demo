module rooch_demo::order_remove_item_logic {
    use moveos_std::object::{Object};
    use moveos_std::object_id::ObjectID;
    use moveos_std::storage_context::StorageContext;
    use rooch_demo::order;
    use rooch_demo::order_item;

    friend rooch_demo::order_aggregate;

    public(friend) fun verify(
        _storage_ctx: &mut StorageContext,
        _account: &signer,
        product_obj_id: ObjectID,
        order_obj: &Object<order::Order>,
    ): order::OrderItemRemoved {
        order::new_order_item_removed(
            order_obj,
            product_obj_id,
        )
    }

    public(friend) fun mutate(
        _storage_ctx: &mut StorageContext,
        order_item_removed: &order::OrderItemRemoved,
        order_obj: Object<order::Order>,
    ): Object<order::Order> {
        let product_obj_id = order::order_item_removed_product_obj_id(order_item_removed);
        let order_item = order::borrow_item(&order_obj, product_obj_id);
        let item_amount = order_item::item_amount(order_item);
        order::remove_item(&mut order_obj, product_obj_id);
        let total_amount = order::total_amount(&order_obj);
        // debug::print(&total_amount);
        // debug::print(&item_amount);
        order::set_total_amount(&mut order_obj, total_amount - item_amount);
        order_obj
    }
}
