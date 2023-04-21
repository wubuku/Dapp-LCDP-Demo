module hello_table::order {
    use std::signer;

    use aptos_std::table::{Self, Table};

    struct OrderState has copy, store {
        order_id: vector<u8>,
        total_amount: u128,
        owner: address,
    }

    // struct OrderStateV2 has store {
    //     order_id: vector<u8>,
    //     total_amount: u128,
    //     owner: address,
    //     version: u64,
    //     order_items: vector<OrderItemState>,
    // }

    struct OrderItemId has drop, copy {
        order_id: vector<u8>,
        product_id: vector<u8>,
    }

    struct OrderItemState has copy, store {
        product_id: vector<u8>,
        quantity: u64,
        unit_price: u128,
    }

    struct Tables has key {
        order_table: Table<vector<u8>, OrderState>,
        order_item_table: Table<OrderItemId, OrderItemState>,
    }

    public fun new_order_item_id (order_id: vector<u8>, product_id: vector<u8>): OrderItemId {
        OrderItemId {
            order_id,
            product_id,
        }
    }

    public entry fun init(account: signer) {
        assert!(signer::address_of(&account) == @hello_table, 1);
        if (!exists<Tables>(@hello_table)) {
            move_to(
                &account,
                Tables {
                    order_table: table::new(),
                    order_item_table: table::new(),
                },
            )
        };
    }

    public entry fun test_copy_order_state_and_modify(account: signer,
                                         order_id: vector<u8>,
                                         total_amount: u128
    ) acquires Tables {
        assert!(signer::address_of(&account) == @hello_table, 1);
        let tables = borrow_global<Tables>(@hello_table);
        let order = table::borrow(&tables.order_table, order_id);
        let order_copy = *order;
        let order_mut = &mut order_copy;
        order_mut.total_amount = total_amount;
        let OrderState { order_id: _, total_amount: _ , owner: _} = *order;
        let OrderState { order_id: _, total_amount: _ , owner: _} = order_copy;
    }

    public entry fun update_total_amount(account: signer,
                                         order_id: vector<u8>,
                                         total_amount: u128
    ) acquires Tables {
        assert!(signer::address_of(&account) == @hello_table, 1);
        let tables = borrow_global_mut<Tables>(@hello_table);
        let order = table::borrow_mut(&mut tables.order_table, order_id);
        order.total_amount = total_amount;
    }

    public entry fun create_order(
        account: signer,
        order_id: vector<u8>,
        product_id: vector<u8>,
        quantity: u64,
        unit_price: u128,
    ) acquires Tables {
        let tables = borrow_global_mut<Tables>(@hello_table);
        let total_amount = unit_price * (quantity as u128);
        let order_state = OrderState {
            order_id,
            total_amount,
            owner: signer::address_of(&account),
        };
        table::add(&mut tables.order_table, order_id, order_state);

        let order_item_id = OrderItemId {
            order_id,
            product_id,
        };
        let order_item_state = OrderItemState {
            product_id,
            quantity,
            unit_price,
        };
        table::add(&mut tables.order_item_table, order_item_id, order_item_state);
    }
}