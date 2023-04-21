module hello_table::order_state_po {
    use hello_table::simple_set;

    friend hello_table::order_state;

    struct OrderStatePO has store {
        order_id: vector<u8>,
        total_amount: u128,
        owner: address,
        order_items_id_set: simple_set::SimpleSet<vector<u8>>
    }


    public fun get_order_id(s: &OrderStatePO): vector<u8> {
        *&s.order_id
    }

    public fun set_order_id(s: &mut OrderStatePO, order_id: &vector<u8>) {
        s.order_id = *order_id;
    }

    public fun order_items_length(s: &OrderStatePO): u64 {
        simple_set::length(&s.order_items_id_set)
    }


    public fun order_items_contains(s: &OrderStatePO, product_id: &vector<u8>): bool {
        simple_set::contains(&s.order_items_id_set, product_id)
    }
}
