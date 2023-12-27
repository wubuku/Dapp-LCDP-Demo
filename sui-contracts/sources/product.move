// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

module sui_demo_contracts::product {
    use std::option::{Self, Option};
    use std::string::{Self, String};
    use std::vector;
    use sui::event;
    use sui::object::{Self, ID, UID};
    use sui::transfer;
    use sui::tx_context::TxContext;
    friend sui_demo_contracts::product_create_logic;
    friend sui_demo_contracts::product_update_logic;
    friend sui_demo_contracts::product_delete_logic;
    friend sui_demo_contracts::product_aggregate;

    #[allow(unused_const)]
    const EDataTooLong: u64 = 102;
    const EInappropriateVersion: u64 = 103;
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
        owner: address,
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

    public fun owner(product: &Product): address {
        product.owner
    }

    public(friend) fun set_owner(product: &mut Product, owner: address) {
        product.owner = owner;
    }

    fun new_product(
        product_id: String,
        name: String,
        unit_price: u128,
        owner: address,
        ctx: &mut TxContext,
    ): Product {
        assert!(std::string::length(&product_id) <= 20, EDataTooLong);
        Product {
            id: object::new(ctx),
            product_id,
            version: 0,
            name,
            unit_price,
            owner,
        }
    }

    struct ProductCrudEvent has copy, drop {
        crud_type: u8,
        id: Option<ID>,
        product_id: String,
        name: String,
        version: u64,
        unit_price: u128,
        owner: address,
    }

    public fun product_crud_event_crud_type(product_crud_event: &ProductCrudEvent): u8 {
        product_crud_event.crud_type
    }

    public fun product_crud_event_id(product_crud_event: &ProductCrudEvent): Option<ID> {
        product_crud_event.id
    }

    public(friend) fun set_product_crud_event_id(product_crud_event: &mut ProductCrudEvent, id: ID) {
        product_crud_event.id = option::some(id);
    }

    public fun product_crud_event_product_id(product_crud_event: &ProductCrudEvent): String {
        product_crud_event.product_id
    }

    public fun product_crud_event_name(product_crud_event: &ProductCrudEvent): String {
        product_crud_event.name
    }

    public fun product_crud_event_unit_price(product_crud_event: &ProductCrudEvent): u128 {
        product_crud_event.unit_price
    }

    public fun product_crud_event_owner(product_crud_event: &ProductCrudEvent): address {
        product_crud_event.owner
    }

    public(friend) fun new_product_created(
        name: String,
        unit_price: u128,
        owner: address,
        product_id_generator: &mut ProductIdGenerator,
    ): ProductCrudEvent {
        let product_id = next_product_id(product_id_generator);
        ProductCrudEvent {
            crud_type: 0,
            id: option::none(),
            product_id,
            name,
            version: 18446744073709551615, // max u64 for null
            unit_price,
            owner,
        }
    }

    public(friend) fun new_product_updated(
        product: &Product,
        name: String,
        unit_price: u128,
        owner: address,
    ): ProductCrudEvent {
        ProductCrudEvent {
            crud_type: 1,
            id: std::option::some(id(product)),
            product_id: product_id(product),
            name,
            version: version(product),
            unit_price,
            owner,
        }
    }

    public(friend) fun new_product_deleted(
        product: &Product,
    ): ProductCrudEvent {
        ProductCrudEvent {
            crud_type: 2,
            id: std::option::some(id(product)),
            product_id: product_id(product),
            name: name(product),
            version: version(product),
            unit_price: unit_price(product),
            owner: owner(product),
        }
    }


    public(friend) fun create_product(
        name: String,
        unit_price: u128,
        owner: address,
        product_id_generator: &ProductIdGenerator,
        ctx: &mut TxContext,
    ): Product {
        let product_id = current_product_id(product_id_generator);
        let product = new_product(
            product_id,
            name,
            unit_price,
            owner,
            ctx,
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
        assert!(product.version == 0, EInappropriateVersion);
        transfer::transfer(product, recipient);
    }

    public(friend) fun update_version_and_transfer_object(product: Product, recipient: address) {
        update_object_version(&mut product);
        transfer::transfer(product, recipient);
    }

    #[lint_allow(share_owned)]
    public(friend) fun share_object(product: Product) {
        assert!(product.version == 0, EInappropriateVersion);
        transfer::share_object(product);
    }

    public(friend) fun freeze_object(product: Product) {
        assert!(product.version == 0, EInappropriateVersion);
        transfer::freeze_object(product);
    }

    public(friend) fun update_version_and_freeze_object(product: Product) {
        update_object_version(&mut product);
        transfer::freeze_object(product);
    }

    public(friend) fun update_object_version(product: &mut Product) {
        product.version = product.version + 1;
        //assert!(product.version != 0, EInappropriateVersion);
    }

    public(friend) fun drop_product(product: Product) {
        let Product {
            id,
            product_id: _product_id,
            version: _version,
            name: _name,
            unit_price: _unit_price,
            owner: _owner,
        } = product;
        object::delete(id);
    }

    public(friend) fun emit_product_created(product_created: ProductCrudEvent) {
        event::emit(product_created);
    }

    public(friend) fun emit_product_updated(product_updated: ProductCrudEvent) {
        event::emit(product_updated);
    }

    public(friend) fun emit_product_deleted(product_deleted: ProductCrudEvent) {
        event::emit(product_deleted);
    }

    #[test_only]
    /// Wrapper of module initializer for testing
    public fun test_init(ctx: &mut TxContext) {
        init(ctx)
    }

}
