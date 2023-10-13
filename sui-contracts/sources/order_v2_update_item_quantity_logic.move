module sui_demo_contracts::order_v2_update_item_quantity_logic {
    use std::string::String;
    use sui::tx_context::{TxContext};
    use sui_demo_contracts::order_v2;
    use sui_demo_contracts::order_v2_item::{Self};
    use sui_demo_contracts::order_v2_item_quantity_updated;

    friend sui_demo_contracts::order_v2_aggregate;

    public(friend) fun verify(
        product_id: String,
        quantity: u64,
        order_v2: &order_v2::OrderV2,
        ctx: &TxContext,
    ): order_v2::OrderV2ItemQuantityUpdated {
        let e_product_id = product_id;
        let e_quantity = quantity;
        let _ = ctx;
        order_v2::new_order_v2_item_quantity_updated(
            order_v2,
            e_product_id,
            e_quantity,
        )
    }

    public(friend) fun mutate(
        order_v2_item_quantity_updated: &order_v2::OrderV2ItemQuantityUpdated,
        order_v2: order_v2::OrderV2,
        ctx: &TxContext,
    ): order_v2::OrderV2 {
        let _ = ctx;
        let product_id = order_v2_item_quantity_updated::product_id(order_v2_item_quantity_updated);
        let quantity = order_v2_item_quantity_updated::quantity(order_v2_item_quantity_updated);
        let item = order_v2::borrow_mut_item(&mut order_v2, product_id);
        let unit_price = order_v2_item::item_amount(item) / (order_v2_item::quantity(item) as u128);
        order_v2_item::set_quantity(item, quantity);
        let old_item_amount = order_v2_item::item_amount(item);
        let new_item_amount = unit_price * (order_v2_item_quantity_updated::quantity(
            order_v2_item_quantity_updated
        ) as u128);
        order_v2_item::set_item_amount(
            item,
            new_item_amount,
        );
        let total_amount = order_v2::total_amount(&order_v2);
        // debug::print(&total_amount);
        // debug::print(&item_amount);
        order_v2::set_total_amount(&mut order_v2, total_amount + new_item_amount - old_item_amount);

        //let order_v2_item_item = order_v2_item::borrow_mut_item(item, product_id);
        //order_v2_item::set_order_v2_item_item_desc(order_v2_item_item, product_id);
        order_v2
    }

}
