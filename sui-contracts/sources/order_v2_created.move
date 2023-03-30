module sui_contracts::order_v2_created {

    use std::option;
    use std::string::String;
    use sui::object;
    use sui_contracts::order_v2::{Self, OrderV2Created};

    public fun id(order_v2_created: &OrderV2Created): option::Option<object::ID> {
        order_v2::order_v2_created_id(order_v2_created)
    }

    public fun order_id(order_v2_created: &OrderV2Created): String {
        order_v2::order_v2_created_order_id(order_v2_created)
    }

    public fun product(order_v2_created: &OrderV2Created): String {
        order_v2::order_v2_created_product(order_v2_created)
    }

    public fun quantity(order_v2_created: &OrderV2Created): u64 {
        order_v2::order_v2_created_quantity(order_v2_created)
    }

    public fun unit_price(order_v2_created: &OrderV2Created): u128 {
        order_v2::order_v2_created_unit_price(order_v2_created)
    }

    public fun total_amount(order_v2_created: &OrderV2Created): u128 {
        order_v2::order_v2_created_total_amount(order_v2_created)
    }

    public fun owner(order_v2_created: &OrderV2Created): address {
        order_v2::order_v2_created_owner(order_v2_created)
    }

}
