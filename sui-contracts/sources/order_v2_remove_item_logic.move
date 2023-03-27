module sui_contracts::order_v2_remove_item_logic {
    use std::string::String;
    use sui::tx_context::{TxContext};
    use sui_contracts::order_v2;
    use sui_contracts::order_v2_item::{Self};
    use sui_contracts::order_v2_item_removed;

    friend sui_contracts::order_v2_aggregate;

    public(friend) fun verify(
        product_id: String,
        order_v2: &order_v2::OrderV2,
        ctx: &TxContext,
    ): order_v2::OrderV2ItemRemoved {
        let e_product_id = product_id;
        let _ = ctx;
        order_v2::new_order_v2_item_removed(
            order_v2,
            e_product_id,
        )
    }

    public(friend) fun mutate(
        order_v2_item_removed: &order_v2::OrderV2ItemRemoved,
        order_v2: order_v2::OrderV2,
        ctx: &TxContext,
    ): order_v2::OrderV2 {
        let _ = ctx;
        let product_id = order_v2_item_removed::product_id(order_v2_item_removed);
        let order_item = order_v2::borrow_item(&order_v2, product_id);
        let item_amount = order_v2_item::item_amount(order_item);
        order_v2::remove_item(&mut order_v2, product_id);
        let total_amount = order_v2::total_amount(&order_v2);
        // debug::print(&total_amount);
        // debug::print(&item_amount);
        order_v2::set_total_amount(&mut order_v2, total_amount - item_amount);
        order_v2
    }

}
