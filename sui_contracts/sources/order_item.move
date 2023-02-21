module sui_contracts::order_item {
    use std::string::String;

    friend sui_contracts::order_create_logic;
    friend sui_contracts::order_remove_item_logic;
    friend sui_contracts::order_update_item_quantity_logic;
    friend sui_contracts::order;

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
