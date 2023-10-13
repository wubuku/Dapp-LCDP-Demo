module sui_demo_contracts::order_update_item_quantity_logic {
    use std::string::String;

    use sui::tx_context::TxContext;
    use sui_demo_contracts::order;
    use sui_demo_contracts::order_item;
    use sui_demo_contracts::order_item_quantity_updated;

    friend sui_demo_contracts::order_aggregate;

    public(friend) fun verify(
        product_id: String,
        quantity: u64,
        order: &order::Order,
        ctx: &TxContext,
    ): order::OrderItemQuantityUpdated {
        let e_product_id = product_id;
        let e_quantity = quantity;
        let _ = ctx;
        order::new_order_item_quantity_updated(
            order,
            e_product_id,
            e_quantity,
        )
    }

    public(friend) fun mutate(
        order_item_quantity_updated: &order::OrderItemQuantityUpdated,
        order: order::Order,
        ctx: &TxContext, // keep this for future use?
    ): order::Order {
        let _ = ctx;
        let product_id = order_item_quantity_updated::product_id(order_item_quantity_updated);
        let quantity = order_item_quantity_updated::quantity(order_item_quantity_updated);
        let item = order::borrow_mut_item(&mut order, product_id);
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
        let total_amount = order::total_amount(&order);
        // debug::print(&total_amount);
        // debug::print(&item_amount);
        order::set_total_amount(&mut order, total_amount + new_item_amount - old_item_amount);

        //let order_item_item = order_item::borrow_mut_item(item, product_id);
        //order_item::set_order_item_item_desc(order_item_item, product_id);
        order
    }
}
