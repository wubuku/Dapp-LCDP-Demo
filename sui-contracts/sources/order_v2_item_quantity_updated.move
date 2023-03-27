module sui_contracts::order_v2_item_quantity_updated {

    use std::string::String;
    use sui::object;
    use sui_contracts::order_v2::{Self, OrderV2ItemQuantityUpdated};

    public fun id(order_v2_item_quantity_updated: &OrderV2ItemQuantityUpdated): object::ID {
        order_v2::order_v2_item_quantity_updated_id(order_v2_item_quantity_updated)
    }

    public fun order_id(order_v2_item_quantity_updated: &OrderV2ItemQuantityUpdated): String {
        order_v2::order_v2_item_quantity_updated_order_id(order_v2_item_quantity_updated)
    }

    public fun product_id(order_v2_item_quantity_updated: &OrderV2ItemQuantityUpdated): String {
        order_v2::order_v2_item_quantity_updated_product_id(order_v2_item_quantity_updated)
    }

    public fun quantity(order_v2_item_quantity_updated: &OrderV2ItemQuantityUpdated): u64 {
        order_v2::order_v2_item_quantity_updated_quantity(order_v2_item_quantity_updated)
    }

}
