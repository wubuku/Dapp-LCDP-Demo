// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

module aptos_demo::order_item {
    use std::string::String;
    friend aptos_demo::order_create_logic;
    friend aptos_demo::order_remove_item_logic;
    friend aptos_demo::order_update_item_quantity_logic;
    friend aptos_demo::order_update_estimated_ship_date_logic;
    friend aptos_demo::order_add_order_ship_group_logic;
    friend aptos_demo::order_cancel_order_ship_group_quantity_logic;
    friend aptos_demo::order_remove_order_ship_group_item_logic;
    friend aptos_demo::order_remove_order_ship_group_logic;
    friend aptos_demo::order;

    const EID_ALREADY_EXISTS: u64 = 101;
    const EID_DATA_TOO_LONG: u64 = 102;

    struct OrderItem has store {
        product_id: String,
        quantity: u64,
        item_amount: u128,
    }

    public fun product_id(order_item: &OrderItem): String {
        order_item.product_id
    }

    public fun quantity(order_item: &OrderItem): u64 {
        order_item.quantity
    }

    public(friend) fun set_quantity(order_item: &mut OrderItem, quantity: u64) {
        order_item.quantity = quantity;
    }

    public fun item_amount(order_item: &OrderItem): u128 {
        order_item.item_amount
    }

    public(friend) fun set_item_amount(order_item: &mut OrderItem, item_amount: u128) {
        order_item.item_amount = item_amount;
    }

    public(friend) fun new_order_item(
        product_id: String,
        quantity: u64,
        item_amount: u128,
    ): OrderItem {
        assert!(std::string::length(&product_id) <= 100, EID_DATA_TOO_LONG);
        OrderItem {
            product_id,
            quantity,
            item_amount,
        }
    }

    public(friend) fun drop_order_item(order_item: OrderItem) {
        let OrderItem {
            product_id: _,
            quantity: _,
            item_amount: _,
        } = order_item;
    }


}
