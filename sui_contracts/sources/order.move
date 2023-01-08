module sui_contracts::order {
    use std::ascii::String;

    use sui::object::{Self, UID};
    use sui::table::Table;
    use sui_contracts::order_item::OrderItem;

    friend sui_contracts::order_create_logic;

    struct Order has key {
        id: UID,
        version: u64,
        total_amount: u128,
        items: Table<String, OrderItem>
    }

    struct OrderCreated has copy, drop {
        id: object::ID,
        //version: u64,
        product: object::ID,
        quantity: u64,
        unit_price: u128,
        total_amount: u128,
        // derived from ctx
        owner: address,
    }

    public(friend) fun new_order_created(
        id: &UID,
        //version: u64,
        product: object::ID,
        quantity: u64,
        unit_price: u128,
        total_amount: u128,
        // derived from ctx
        owner: address,
    ): OrderCreated {
        OrderCreated {
            id: object::uid_to_inner(id),
            product,
            quantity,
            unit_price,
            total_amount,
            owner,
        }
    }
}

