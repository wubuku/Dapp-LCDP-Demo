// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

module aptos_demo::order_ship_group_removed {

    use aptos_demo::order::{Self, OrderShipGroupRemoved};
    use std::string::String;

    public fun order_id(order_ship_group_removed: &OrderShipGroupRemoved): String {
        order::order_ship_group_removed_order_id(order_ship_group_removed)
    }

    public fun ship_group_seq_id(order_ship_group_removed: &OrderShipGroupRemoved): u8 {
        order::order_ship_group_removed_ship_group_seq_id(order_ship_group_removed)
    }

}
