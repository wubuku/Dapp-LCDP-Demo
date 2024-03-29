// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

module sui_demo_contracts::order_v2_item {
    use std::string::String;
    friend sui_demo_contracts::order_v2_create_logic;
    friend sui_demo_contracts::order_v2_remove_item_logic;
    friend sui_demo_contracts::order_v2_update_item_quantity_logic;
    friend sui_demo_contracts::order_v2_update_estimated_ship_date_logic;
    friend sui_demo_contracts::order_v2_add_order_ship_group_logic;
    friend sui_demo_contracts::order_v2_cancel_order_ship_group_quantity_logic;
    friend sui_demo_contracts::order_v2_remove_order_ship_group_item_logic;
    friend sui_demo_contracts::order_v2_remove_order_ship_group_logic;
    friend sui_demo_contracts::order_v2;

    #[allow(unused_const)]
    const EDataTooLong: u64 = 102;

    struct OrderV2Item has store {
        product_id: String,
        quantity: u64,
        item_amount: u128,
    }

    public fun product_id(order_v2_item: &OrderV2Item): String {
        order_v2_item.product_id
    }

    public fun quantity(order_v2_item: &OrderV2Item): u64 {
        order_v2_item.quantity
    }

    public(friend) fun set_quantity(order_v2_item: &mut OrderV2Item, quantity: u64) {
        order_v2_item.quantity = quantity;
    }

    public fun item_amount(order_v2_item: &OrderV2Item): u128 {
        order_v2_item.item_amount
    }

    public(friend) fun set_item_amount(order_v2_item: &mut OrderV2Item, item_amount: u128) {
        order_v2_item.item_amount = item_amount;
    }

    public(friend) fun new_order_v2_item(
        product_id: String,
        quantity: u64,
        item_amount: u128,
    ): OrderV2Item {
        assert!(std::string::length(&product_id) <= 100, EDataTooLong);
        OrderV2Item {
            product_id,
            quantity,
            item_amount,
        }
    }

    public(friend) fun drop_order_v2_item(order_v2_item: OrderV2Item) {
        let OrderV2Item {
            product_id: _,
            quantity: _,
            item_amount: _,
        } = order_v2_item;
    }


}
