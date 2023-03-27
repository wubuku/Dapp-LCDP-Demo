module sui_contracts::order_ship_group_quantity_canceled {

    use std::string::String;
    use sui::object;
    use sui_contracts::order_v2::{Self, OrderShipGroupQuantityCanceled};

    public fun id(order_ship_group_quantity_canceled: &OrderShipGroupQuantityCanceled): object::ID {
        order_v2::order_ship_group_quantity_canceled_id(order_ship_group_quantity_canceled)
    }

    public fun order_id(order_ship_group_quantity_canceled: &OrderShipGroupQuantityCanceled): String {
        order_v2::order_ship_group_quantity_canceled_order_id(order_ship_group_quantity_canceled)
    }

    public fun ship_group_seq_id(order_ship_group_quantity_canceled: &OrderShipGroupQuantityCanceled): u8 {
        order_v2::order_ship_group_quantity_canceled_ship_group_seq_id(order_ship_group_quantity_canceled)
    }

    public fun product_id(order_ship_group_quantity_canceled: &OrderShipGroupQuantityCanceled): String {
        order_v2::order_ship_group_quantity_canceled_product_id(order_ship_group_quantity_canceled)
    }

    public fun cancel_quantity(order_ship_group_quantity_canceled: &OrderShipGroupQuantityCanceled): u64 {
        order_v2::order_ship_group_quantity_canceled_cancel_quantity(order_ship_group_quantity_canceled)
    }

}
