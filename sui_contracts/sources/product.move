module sui_contracts::product {
    use std::string::String;

    use sui::object::{Self, UID};

    #[test_only]
    use sui::transfer;
    #[test_only]
    use sui::tx_context::TxContext;

    struct Product has key {
        /// surrogate Id. First field name must be 'id'
        id: UID,
        version: u64,
        product_id: String,
        unit_price: u128,
    }

    public fun id(product: &Product): object::ID {
        object::uid_to_inner(&product.id)
    }

    public fun version(product: &Product): u64 {
        *&product.version
    }

    public fun product_id(product: &Product): String {
        *&product.product_id
    }

    public fun unit_price(product: &Product): u128 {
        *&product.unit_price
    }

    #[test_only]
    /// Wrapper of module initializer for testing
    public fun test_create_product(ctx: &mut TxContext, product_id: String, unit_price: u128) {
        let id = object::new(ctx);
        let product = Product {
            id,
            version: 0,
            product_id,
            unit_price,
        };
        transfer::share_object(product);
    }
}
