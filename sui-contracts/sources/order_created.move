module sui_contracts::order_created {

    use std::string::String;
    use sui::object;
    use sui_contracts::order::{Self, OrderCreated};
    use std::option;

    public fun id(order_created: &OrderCreated): option::Option<object::ID> {
        order::order_created_id(order_created)
    }

    public fun product(order_created: &OrderCreated): String {
        order::order_created_product(order_created)
    }

    public fun quantity(order_created: &OrderCreated): u64 {
        order::order_created_quantity(order_created)
    }

    public fun unit_price(order_created: &OrderCreated): u128 {
        order::order_created_unit_price(order_created)
    }

    public fun total_amount(order_created: &OrderCreated): u128 {
        order::order_created_total_amount(order_created)
    }

    public fun owner(order_created: &OrderCreated): address {
        order::order_created_owner(order_created)
    }

}
