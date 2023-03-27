module sui_contracts::order_v2_item_removed {

    use std::string::String;
    use sui::object;
    use sui_contracts::order_v2::{Self, OrderV2ItemRemoved};

    public fun id(order_v2_item_removed: &OrderV2ItemRemoved): object::ID {
        order_v2::order_v2_item_removed_id(order_v2_item_removed)
    }

    public fun order_id(order_v2_item_removed: &OrderV2ItemRemoved): String {
        order_v2::order_v2_item_removed_order_id(order_v2_item_removed)
    }

    public fun product_id(order_v2_item_removed: &OrderV2ItemRemoved): String {
        order_v2::order_v2_item_removed_product_id(order_v2_item_removed)
    }

}
