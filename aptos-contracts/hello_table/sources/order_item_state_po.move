module hello_table::order_item_state_po {
    friend hello_table::order_item_state_dao;

    struct OrderItemStatePO has copy, drop, store {
        order_id: vector<u8>,
        product_id: vector<u8>,
        quantity: u64,
        unit_price: u128,
    }

    public(friend) fun get_order_id(order_item_state: &OrderItemStatePO): vector<u8> {
        *&order_item_state.order_id
    }

    public(friend) fun get_product_id(order_item_state: &OrderItemStatePO): vector<u8> {
        *&order_item_state.product_id
    }

    public(friend) fun get_quantity(order_item_state: &OrderItemStatePO): u64 {
        *&order_item_state.quantity
    }

    public(friend) fun set_quantity(order_item_state: &mut OrderItemStatePO, quantity: u64) {
        *&mut order_item_state.quantity = quantity;
    }
}