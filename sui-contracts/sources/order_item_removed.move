module sui_contracts::order_item_removed {

    use std::string::String;
    use sui::object;
    use sui_contracts::order::{Self, OrderItemRemoved};

    public fun id(order_item_removed: &OrderItemRemoved): object::ID {
        order::order_item_removed_id(order_item_removed)
    }

    public fun product_id(order_item_removed: &OrderItemRemoved): String {
        order::order_item_removed_product_id(order_item_removed)
    }

}
