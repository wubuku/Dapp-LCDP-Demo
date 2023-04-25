module aptos_demo::order_remove_item_logic {
    use std::string::String;

    use aptos_demo::order;
    use aptos_demo::order_item;

    //use sui::tx_context::TxContext;
    friend aptos_demo::order_aggregate;

    public(friend) fun verify(
        account: &signer,
        product_id: String,
        order: &order::Order,
        //ctx: &TxContext,
    ): order::OrderItemRemoved {
        let _ = account;
        let e_product_id = product_id;
        //let _ = ctx;
        order::new_order_item_removed(
            order,
            e_product_id,
        )
    }

    public(friend) fun mutate(
        order_item_removed: &order::OrderItemRemoved,
        order: order::Order,
        //ctx: &TxContext, // keep this for future use?
    ): order::Order {
        //let _ = ctx;
        let product_id = order::order_item_removed_product_id(order_item_removed);
        let order_item = order::borrow_item(&order, product_id);
        let item_amount = order_item::item_amount(order_item);
        order::remove_item(&mut order, product_id);
        let total_amount = order::total_amount(&order);
        // debug::print(&total_amount);
        // debug::print(&item_amount);
        order::set_total_amount(&mut order, total_amount - item_amount);
        order
    }
}
