module sui_contracts::product {
    use std::string::{Self, String};
    use std::vector;
    use sui::event;
    use sui::object::{Self, UID};
    use sui::transfer;
    use sui::tx_context::TxContext;
    friend sui_contracts::product_create_logic;
    friend sui_contracts::product_aggregate;

    const PRODUCT_ID_LENGTH: u64 = 20;

    struct ProductIdGenerator has key {
        id: UID,
        sequence: u128,
    }

    struct ProductIdGeneratorCreated has copy, drop {
        id: object::ID,
    }

    fun init(ctx: &mut TxContext) {
        let product_id_generator = ProductIdGenerator {
            id: object::new(ctx),
            sequence: 0,
        };
        let product_id_generator_id = object::uid_to_inner(&product_id_generator.id);
        transfer::share_object(product_id_generator);
        event::emit(ProductIdGeneratorCreated {
            id: product_id_generator_id
        });
    }

    struct Product has key {
        id: UID,
        product_id: String,
        version: u64,
        name: String,
        unit_price: u128,
    }

    public fun id(product: &Product): object::ID {
        object::uid_to_inner(&product.id)
    }

    public fun product_id(product: &Product): String {
        product.product_id
    }

    public fun version(product: &Product): u64 {
        product.version
    }

    public fun name(product: &Product): String {
        product.name
    }

    public(friend) fun set_name(product: &mut Product, name: String) {
        product.name = name;
    }

    public fun unit_price(product: &Product): u128 {
        product.unit_price
    }

    public(friend) fun set_unit_price(product: &mut Product, unit_price: u128) {
        product.unit_price = unit_price;
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

    public fun product_created_unit_price(product_created: &ProductCreated): u128 {
        product_created.unit_price
    }

    public(friend) fun new_product_created(
        id: &UID,
        name: String,
        unit_price: u128,
        product_id_generator: &mut ProductIdGenerator,
    ): ProductCreated {
        let product_id = next_product_id(product_id_generator);
        ProductCreated {
            id: object::uid_to_inner(id),
            product_id,
            name,
            unit_price,
        }
    }


    public(friend) fun create_product(
        id: UID,
        name: String,
        unit_price: u128,
        product_id_generator: &ProductIdGenerator,
    ): Product {
        let product_id = current_product_id(product_id_generator);
        let product = new_product(
            id,
            product_id,
            name,
            unit_price,
        );
        product
    }

    fun current_product_id(
        product_id_generator: &ProductIdGenerator,
    ): String {
        string::utf8(u128_to_fixed_length_string(product_id_generator.sequence, PRODUCT_ID_LENGTH))
    }

    fun u128_to_fixed_length_string(n: u128, length: u64): vector<u8> {
        let s = vector::empty<u8>();
        let m = n;
        while (m > 0) {
            let digit = ((m % 10) as u8);
            vector::push_back(&mut s, digit + 48);//b'0'
            m = m / 10;
        };
        while (vector::length(&s) < length) {
            vector::push_back(&mut s, 48);//b'0'
        };
        vector::reverse(&mut s);
        s
    }

    fun next_product_id(
        product_id_generator: &mut ProductIdGenerator,
    ): String {
        product_id_generator.sequence = product_id_generator.sequence + 1;
        current_product_id(product_id_generator)
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

}
