module sui_contracts::order_item {
    use std::string::String;

    friend sui_contracts::order;
    friend sui_contracts::order_create_logic;
    friend sui_contracts::order_remove_item_logic;
    friend sui_contracts::order_update_item_quantity_logic;

    struct OrderItem has store {
        product_id: String,
        quantity: u64,
        item_amount: u128,
        //items: table::Table<String, OrderItemItem>,
    }

    // struct OrderItemItem has store {
    //     description: String,
    // }

    // public(friend) fun borrow_mut_item(order_item: &mut OrderItem, item_id: String): &mut OrderItemItem {
    //     table::borrow_mut(&mut order_item.items, item_id)
    // }
    //
    // public(friend) fun set_order_item_item_desc(order_item_item: &mut OrderItemItem, desc: String) {
    //     order_item_item.description = desc
    // }

    public(friend) fun new_order_item(
        product_id: String,
        quantity: u64,
        item_amount: u128,
        //ctx: &mut TxContext
    ): OrderItem {
        OrderItem {
            product_id,
            quantity,
            item_amount,
            //items: table::new(ctx)
        }
    }

    public(friend) fun drop_order_item(item: OrderItem) {
        let OrderItem {
            product_id: _,
            quantity: _,
            item_amount: _,
            //items,
        } = item;
        //table::destroy_empty<String , OrderItemItem>(items);
    }

    public fun product_id(order_item: &OrderItem): String {
        order_item.product_id
    }

    public fun quantity(order_item: &OrderItem): u64 {
        order_item.quantity
    }

    public fun item_amount(order_item: &OrderItem): u128 {
        order_item.item_amount
    }

    public(friend) fun set_quantity(order_item: &mut OrderItem, quantity: u64) {
        order_item.quantity = quantity;
    }

    public(friend) fun set_item_amount(order_item: &mut OrderItem, item_amount: u128) {
        order_item.item_amount = item_amount;
    }
}
