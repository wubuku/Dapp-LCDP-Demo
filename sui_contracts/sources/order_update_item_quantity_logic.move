module sui_contracts::order_update_item_quantity_logic {
    use std::string::String;

    use sui::tx_context::TxContext;
    use sui_contracts::order;
    use sui_contracts::order_item;

    friend sui_contracts::order_aggregate;

    public(friend) fun verify(
        product_id: String,
        quantity: u64,
        order_: &order::Order,
        ctx: &TxContext,
    ): order::OrderItemQuantityUpdated {
        let e_product_id = product_id;
        let e_quantity = quantity;
        let _ = ctx;
        order::new_order_item_quantity_updated(
            order_,
            e_product_id,
            e_quantity,
        )
    }

    public(friend) fun mutate(
        order_item_quantity_updated: &order::OrderItemQuantityUpdated,
        order_: order::Order,
        //ctx: &TxContext,
    ): order::Order {
        let product_id = order::order_item_quantity_updated_product_id(order_item_quantity_updated);
        let quantity = order::order_item_quantity_updated_quantity(order_item_quantity_updated);
        let item = order::borrow_mut_item(&mut order_, product_id);
        let unit_price = order_item::item_amount(item) / (order_item::quantity(item) as u128);
        order_item::set_quantity(item, quantity);
        order_item::set_item_amount(
            item, unit_price * (order::order_item_quantity_updated_quantity(order_item_quantity_updated) as u128)
        );
        //let order_item_item = order_item::borrow_mut_item(item, product_id);
        //order_item::set_order_item_item_desc(order_item_item, product_id);
        order_
    }
}
