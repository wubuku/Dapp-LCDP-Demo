// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

module aptos_demo::product {
    use aptos_demo::genesis_account;
    use aptos_demo::pass_object;
    use aptos_framework::account;
    use aptos_framework::event;
    use aptos_std::table::{Self, Table};
    use std::string::{Self, String};
    use std::vector;
    friend aptos_demo::product_create_logic;
    friend aptos_demo::product_aggregate;

    const EDATA_TOO_LONG: u64 = 102;
    const EINAPPROPRIATE_VERSION: u64 = 103;
    const ENOT_INITIALIZED: u64 = 110;
    const PRODUCT_ID_LENGTH: u64 = 20;


    struct Events has key {
        // product_id_generator_created_handle: event::EventHandle<ProductIdGeneratorCreated>,
        product_created_handle: event::EventHandle<ProductCreated>,
    }

    struct Tables has key {
        product_table: Table<String, Product>,
    }

    struct ProductIdGenerator has key {
        sequence: u128,
    }


    public fun initialize(account: &signer) {
        genesis_account::assert_genesis_account(account);

        let res_account = genesis_account::resource_account_signer();
        move_to(&res_account, Events {
            // product_id_generator_created_handle: account::new_event_handle<ProductIdGeneratorCreated>(&res_account),
            product_created_handle: account::new_event_handle<ProductCreated>(&res_account),
        });

        let product_id_generator = ProductIdGenerator {
            sequence: 0,
        };
        move_to(&res_account, product_id_generator);
        // let events = borrow_global_mut<Events>(genesis_account::resouce_account_address());
        // event::emit_event(&mut events.product_id_generator_created_handle, ProductIdGeneratorCreated {
        // });

        move_to(
            &res_account,
            Tables {
                product_table: table::new(),
            },
        );

    }

    struct Product has store {
        product_id: String,
        version: u64,
        name: String,
        unit_price: u128,
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
        product_id: String,
        name: String,
        unit_price: u128,
    ): Product {
        assert!(std::string::length(&product_id) <= 20, EDATA_TOO_LONG);
        Product {
            product_id,
            version: 0,
            name,
            unit_price,
        }
    }

    struct ProductCreated has store, drop {
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
        name: String,
        unit_price: u128,
    ): ProductCreated acquires ProductIdGenerator {
        assert!(exists<ProductIdGenerator>(genesis_account::resouce_account_address()), ENOT_INITIALIZED);
        let product_id_generator = borrow_global_mut<ProductIdGenerator>(genesis_account::resouce_account_address());
        let product_id = next_product_id(product_id_generator);
        ProductCreated {
            product_id,
            name,
            unit_price,
        }
    }


    public(friend) fun create_product(
        name: String,
        unit_price: u128,
    ): Product acquires ProductIdGenerator {
        assert!(exists<ProductIdGenerator>(genesis_account::resouce_account_address()), ENOT_INITIALIZED);
        let product_id_generator = borrow_global<ProductIdGenerator>(genesis_account::resouce_account_address());
        let product_id = current_product_id(product_id_generator);
        let product = new_product(
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

    public(friend) fun update_version_and_add(product: Product) acquires Tables {
        product.version = product.version + 1;
        //assert!(product.version != 0, EINAPPROPRIATE_VERSION);
        private_add_product(product);
    }

    public(friend) fun add_product(product: Product) acquires Tables {
        assert!(product.version == 0, EINAPPROPRIATE_VERSION);
        private_add_product(product);
    }

    public(friend) fun remove_product(product_id: String): Product acquires Tables {
        assert!(exists<Tables>(genesis_account::resouce_account_address()), ENOT_INITIALIZED);
        let tables = borrow_global_mut<Tables>(genesis_account::resouce_account_address());
        table::remove(&mut tables.product_table, product_id)
    }

    fun private_add_product(product: Product) acquires Tables {
        assert!(exists<Tables>(genesis_account::resouce_account_address()), ENOT_INITIALIZED);
        let tables = borrow_global_mut<Tables>(genesis_account::resouce_account_address());
        table::add(&mut tables.product_table, product_id(&product), product);
    }

    public fun get_product(product_id: String): pass_object::PassObject<Product> acquires Tables {
        let product = remove_product(product_id);
        pass_object::new(product)
    }

    public fun return_product(product_pass_obj: pass_object::PassObject<Product>) acquires Tables {
        let product = pass_object::extract(product_pass_obj);
        private_add_product(product);
    }

    public(friend) fun drop_product(product: Product) {
        let Product {
            version: _version,
            product_id: _product_id,
            name: _name,
            unit_price: _unit_price,
        } = product;
    }

    public(friend) fun emit_product_created(product_created: ProductCreated) acquires Events {
        assert!(exists<Events>(genesis_account::resouce_account_address()), ENOT_INITIALIZED);
        let events = borrow_global_mut<Events>(genesis_account::resouce_account_address());
        event::emit_event(&mut events.product_created_handle, product_created);
    }

}
