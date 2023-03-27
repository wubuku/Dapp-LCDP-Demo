module sui_contracts::order_ship_group_item_removed {

    use std::string::String;
    use sui::object;
    use sui_contracts::order_v2::{Self, OrderShipGroupItemRemoved};

    public fun id(order_ship_group_item_removed: &OrderShipGroupItemRemoved): object::ID {
        order_v2::order_ship_group_item_removed_id(order_ship_group_item_removed)
    }

    public fun order_id(order_ship_group_item_removed: &OrderShipGroupItemRemoved): String {
        order_v2::order_ship_group_item_removed_order_id(order_ship_group_item_removed)
    }

    public fun ship_group_seq_id(order_ship_group_item_removed: &OrderShipGroupItemRemoved): u8 {
        order_v2::order_ship_group_item_removed_ship_group_seq_id(order_ship_group_item_removed)
    }

    public fun product_id(order_ship_group_item_removed: &OrderShipGroupItemRemoved): String {
        order_v2::order_ship_group_item_removed_product_id(order_ship_group_item_removed)
    }

}
