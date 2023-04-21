module hello_table::order_item_state_dao {
    use aptos_std::table::{Self, Table};

    use hello_table::order;
    use hello_table::order_item_state_po::{Self, OrderItemStatePO};

    struct Tables has key {
        order_item_state_table: Table<order::OrderItemId, OrderItemStatePO>,
    }

    public fun remove(
        order_id: &vector<u8>,
        product_id: &vector<u8>
    ): OrderItemStatePO acquires Tables {
        let order_item_id = order::new_order_item_id(*order_id, *product_id);
        let tables = borrow_global_mut<Tables>(@hello_table);
        let order_item_state = table::remove(&mut tables.order_item_state_table, order_item_id);
        order_item_state
    }

    public fun add(order_item: OrderItemStatePO) acquires Tables {
        let order_item_id = order::new_order_item_id(order_item_state_po::get_order_id(&order_item),
            order_item_state_po::get_product_id(&order_item));
        let tables = borrow_global_mut<Tables>(@hello_table);
        table::add(&mut tables.order_item_state_table, order_item_id, order_item);
    }

    public fun get(order_id: &vector<u8>, product_id: &vector<u8>): OrderItemStatePO acquires Tables {
        let order_item_id = order::new_order_item_id(*order_id, *product_id);
        let tables = borrow_global<Tables>(@hello_table);
        *table::borrow(&tables.order_item_state_table, order_item_id)
    }

    // public fun order_items_borrow_mut(
    //     s: &mut OrderState,
    //     product_id: &vector<u8>
    // ): &mut OrderItemState acquires Tables {
    //     let order_item_id = order::new_order_item_id(s.order_id, *product_id);
    //     let tables = borrow_global_mut<Tables>(@hello_table);
    //     table::borrow_mut(&mut tables.order_item_table, order_item_id)
    // }
}