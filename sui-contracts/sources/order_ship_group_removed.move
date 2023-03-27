module sui_contracts::order_ship_group_removed {

    use std::string::String;
    use sui::object;
    use sui_contracts::order_v2::{Self, OrderShipGroupRemoved};

    public fun id(order_ship_group_removed: &OrderShipGroupRemoved): object::ID {
        order_v2::order_ship_group_removed_id(order_ship_group_removed)
    }

    public fun order_id(order_ship_group_removed: &OrderShipGroupRemoved): String {
        order_v2::order_ship_group_removed_order_id(order_ship_group_removed)
    }

    public fun ship_group_seq_id(order_ship_group_removed: &OrderShipGroupRemoved): u8 {
        order_v2::order_ship_group_removed_ship_group_seq_id(order_ship_group_removed)
    }

}
