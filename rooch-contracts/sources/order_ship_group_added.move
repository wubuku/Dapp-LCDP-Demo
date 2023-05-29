// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

module rooch_demo::order_ship_group_added {

    use moveos_std::object_id::ObjectID;
    use rooch_demo::order::{Self, OrderShipGroupAdded};
    use std::string::String;

    public fun id(order_ship_group_added: &OrderShipGroupAdded): ObjectID {
        order::order_ship_group_added_id(order_ship_group_added)
    }

    public fun order_id(order_ship_group_added: &OrderShipGroupAdded): String {
        order::order_ship_group_added_order_id(order_ship_group_added)
    }

    public fun ship_group_seq_id(order_ship_group_added: &OrderShipGroupAdded): u8 {
        order::order_ship_group_added_ship_group_seq_id(order_ship_group_added)
    }

    public fun shipment_method(order_ship_group_added: &OrderShipGroupAdded): String {
        order::order_ship_group_added_shipment_method(order_ship_group_added)
    }

    public fun product_obj_id(order_ship_group_added: &OrderShipGroupAdded): ObjectID {
        order::order_ship_group_added_product_obj_id(order_ship_group_added)
    }

    public fun quantity(order_ship_group_added: &OrderShipGroupAdded): u64 {
        order::order_ship_group_added_quantity(order_ship_group_added)
    }

}
