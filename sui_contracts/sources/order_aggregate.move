module sui_contracts::order_aggregate {

    use sui::tx_context::TxContext;
    use sui_contracts::product::Product;

    public entry fun create(
        product: &mut Product,
        quantity: u64,
        ctx: &mut TxContext,
    ) {
        _ = product;
        _ = quantity;
        _ = ctx;
    }
}
