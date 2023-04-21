module hello_table::order_state {
    use hello_table::simple_set;

    struct OrderState has copy, drop, store {
        order_id: vector<u8>,
        total_amount: u128,
        owner: address,
        order_items_id_set: simple_set::SimpleSet<vector<u8>>
    }

    struct OrderItemState has copy, drop, store {
        product_id: vector<u8>,
        quantity: u64,
        unit_price: u128,
    }


    public fun get_order_id(s: &OrderState): vector<u8> {
        *&s.order_id
    }

    public fun set_order_id(s: &mut OrderState, order_id: &vector<u8>) {
        s.order_id = *order_id;
    }

    public fun order_items_length(s: &OrderState): u64 {
        simple_set::length(&s.order_items_id_set)
    }


    // public fun order_items_borrow_mut(
    //     s: &mut OrderState,
    //     product_id: &vector<u8>
    // ): &mut OrderItemState acquires Tables {
    //     let order_item_id = order::new_order_item_id(s.order_id, *product_id);
    //     let tables = borrow_global_mut<Tables>(@hello_table);
    //     table::borrow_mut(&mut tables.order_item_table, order_item_id)
    // }

    public fun order_items_contains(s: &OrderState, product_id: &vector<u8>): bool {
        simple_set::contains(&s.order_items_id_set, product_id)
    }
}
