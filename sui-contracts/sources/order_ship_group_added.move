module sui_contracts::order_ship_group_added {

    use std::string::String;
    use sui::object;
    use sui_contracts::order_v2::{Self, OrderShipGroupAdded};

    public fun id(order_ship_group_added: &OrderShipGroupAdded): object::ID {
        order_v2::order_ship_group_added_id(order_ship_group_added)
    }

    public fun order_id(order_ship_group_added: &OrderShipGroupAdded): String {
        order_v2::order_ship_group_added_order_id(order_ship_group_added)
    }

    public fun ship_group_seq_id(order_ship_group_added: &OrderShipGroupAdded): u8 {
        order_v2::order_ship_group_added_ship_group_seq_id(order_ship_group_added)
    }

    public fun shipment_method(order_ship_group_added: &OrderShipGroupAdded): String {
        order_v2::order_ship_group_added_shipment_method(order_ship_group_added)
    }

    public fun product_id(order_ship_group_added: &OrderShipGroupAdded): String {
        order_v2::order_ship_group_added_product_id(order_ship_group_added)
    }

    public fun quantity(order_ship_group_added: &OrderShipGroupAdded): u64 {
        order_v2::order_ship_group_added_quantity(order_ship_group_added)
    }

}
