module sui_contracts::order_item_quantity_updated {

    use std::string::String;
    use sui::object;
    use sui_contracts::order::{Self, OrderItemQuantityUpdated};

    public fun id(order_item_quantity_updated: &OrderItemQuantityUpdated): object::ID {
        order::order_item_quantity_updated_id(order_item_quantity_updated)
    }

    public fun product_id(order_item_quantity_updated: &OrderItemQuantityUpdated): String {
        order::order_item_quantity_updated_product_id(order_item_quantity_updated)
    }

    public fun quantity(order_item_quantity_updated: &OrderItemQuantityUpdated): u64 {
        order::order_item_quantity_updated_quantity(order_item_quantity_updated)
    }

}