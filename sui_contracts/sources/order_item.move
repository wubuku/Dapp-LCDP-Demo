module sui_contracts::order_item {
    use std::string::String;

    friend sui_contracts::order_aggregate;
    friend sui_contracts::order;

    struct OrderItem has store {
        product_id: String,
        quantity: u64,
        item_amount: u128,
    }
}
