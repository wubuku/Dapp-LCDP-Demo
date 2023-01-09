module sui_contracts::product {
    use std::bcs;
    use std::string::{Self, String};

    use sui::event;
    use sui::hex;
    use sui::object::{Self, UID};
    use sui::transfer;
    use sui::tx_context::TxContext;

    struct ProductIdGenerator has key {
        id: UID,
        sequence: u128,
    }

    fun init(ctx: &mut TxContext) {
        let product_id_generator = ProductIdGenerator {
            id: object::new(ctx),
            sequence: 0,
        };
        transfer::share_object(product_id_generator);
    }

    struct Product has key {
        /// surrogate Id. First field name must be 'id'
        id: UID,
        product_id: String,
        version: u64,
        name: String,
        unit_price: u128,
    }

    public fun id(product: &Product): object::ID {
        object::uid_to_inner(&product.id)
    }

    public fun version(product: &Product): u64 {
        product.version
    }

    public fun product_id(product: &Product): String {
        product.product_id
    }

    public fun unit_price(product: &Product): u128 {
        product.unit_price
    }

    fun new_product(
        id: UID,
        product_id: String,
        name: String,
        unit_price: u128,
    ): Product {
        Product {
            id,
            product_id,
            version: 0,
            name,
            unit_price,
        }
    }

    struct ProductCreated has copy, drop {
        id: object::ID,
        product_id: String,
        name: String,
        unit_price: u128,
    }

    public fun product_created_product_id(product_created: &ProductCreated): String {
        product_created.product_id
    }

    public fun product_created_name(product_created: &ProductCreated): String {
        product_created.name
    }

    public(friend) fun new_product_created(
        id: &UID,
        product_id: String,
        name: String,
        unit_price: u128,
    ): ProductCreated {
        ProductCreated {
            id: object::uid_to_inner(id),
            product_id,
            name,
            unit_price,
        }
    }

    // -------------------------------

    public(friend) fun create_product(
        id: UID,
        //product_id: String,
        name: String,
        unit_price: u128,
        product_id_generator: &mut ProductIdGenerator,
        //ctx: &mut TxContext,
    ): Product {
        let product_id = next_product_id(product_id_generator);
        new_product(
            id,
            product_id,
            name,
            unit_price,
        )
    }

    fun next_product_id(
        product_id_generator: &mut ProductIdGenerator,
    ): String {
        product_id_generator.sequence = product_id_generator.sequence + 1;
        string::utf8(hex::encode(bcs::to_bytes(&product_id_generator.sequence)))
    }

    public(friend) fun transfer_object(product: Product, recipient: address) {
        transfer::transfer(product, recipient);
    }

    public(friend) fun update_version_and_transfer_object(product: Product, recipient: address) {
        product.version = product.version + 1;
        transfer::transfer(product, recipient);
    }

    public(friend) fun share_object(product: Product) {
        transfer::share_object(product);
    }

    public(friend) fun freeze_object(product: Product) {
        transfer::freeze_object(product);
    }

    public(friend) fun emit_product_created(product_created: ProductCreated) {
        event::emit(product_created);
    }

    #[test_only]
    /// Wrapper of module initializer for testing
    public fun test_init(ctx: &mut TxContext) {
        init(ctx)
    }

    #[test_only]
    /// Wrapper of module initializer for testing
    public fun test_create_product(
        product_id: String,
        unit_price: u128,
        name: String,
        ctx: &mut TxContext,
    ) {
        let id = object::new(ctx);
        let product = new_product(
            id,
            product_id,
            name,
            unit_price,
        );
        transfer::share_object(product);
    }
}
