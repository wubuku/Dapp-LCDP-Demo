module rooch_demo::order_update_item_quantity_logic {
    use moveos_std::object::{Object};
    use moveos_std::object_id::ObjectID;
    use moveos_std::storage_context::StorageContext;
    use rooch_demo::order;
    use rooch_demo::order_item;
    use rooch_demo::order_item_quantity_updated;

    friend rooch_demo::order_aggregate;

    public(friend) fun verify(
        _storage_ctx: &mut StorageContext,
        _account: &signer,
        product_obj_id: ObjectID,
        quantity: u64,
        order_obj: &Object<order::Order>,
    ): order::OrderItemQuantityUpdated {
        order::new_order_item_quantity_updated(
            order_obj,
            product_obj_id,
            quantity,
        )
    }

    public(friend) fun mutate(
        _storage_ctx: &mut StorageContext,
        order_item_quantity_updated: &order::OrderItemQuantityUpdated,
        order_obj: Object<order::Order>,
    ): Object<order::Order> {
        let product_obj_id = order_item_quantity_updated::product_obj_id(order_item_quantity_updated);
        let quantity = order_item_quantity_updated::quantity(order_item_quantity_updated);
        let item = order::borrow_mut_item(&mut order_obj, product_obj_id);
        let unit_price = order_item::item_amount(item) / (order_item::quantity(item) as u128);
        order_item::set_quantity(item, quantity);
        let old_item_amount = order_item::item_amount(item);
        let new_item_amount = unit_price * (order_item_quantity_updated::quantity(
            order_item_quantity_updated
        ) as u128);
        order_item::set_item_amount(
            item,
            new_item_amount,
        );
        let total_amount = order::total_amount(&order_obj);
        // debug::print(&total_amount);
        // debug::print(&item_amount);
        order::set_total_amount(&mut order_obj, total_amount + new_item_amount - old_item_amount);
        //let order_item_item = order_item::borrow_mut_item(item, product_id);
        //order_item::set_order_item_item_desc(order_item_item, product_id);
        order_obj
    }
}
