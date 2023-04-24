// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

module aptos_demo::order {
    use std::string::String;

    use aptos_std::table::{Self, Table};
    use aptos_std::table_with_length::{Self, TableWithLength};
    use aptos_framework::account;
    use aptos_framework::event;

    use aptos_demo::genesis_account;
    use aptos_demo::order_item::{Self, OrderItem};

    // friend aptos_demo::order_create_logic;
    // friend aptos_demo::order_remove_item_logic;
    // friend aptos_demo::order_update_item_quantity_logic;
    // friend aptos_demo::order_aggregate;

    const EID_ALREADY_EXISTS: u64 = 101;
    const EID_DATA_TOO_LONG: u64 = 102;


    struct Events has key {
        order_created_handle: event::EventHandle<OrderCreated>,
        order_item_removed_handle: event::EventHandle<OrderItemRemoved>,
        order_item_quantity_updated_handle: event::EventHandle<OrderItemQuantityUpdated>,
    }


    struct Tables has key {
        order_table: Table<String, Order>,
    }


    public(friend) fun initialize(account: &signer) {
        //acquires Events
        genesis_account::assert_genesis_account(account);

        let res_account = genesis_account::resource_account_signer();
        move_to(&res_account, Events {
            order_created_handle: account::new_event_handle<OrderCreated>(&res_account),
            order_item_removed_handle: account::new_event_handle<OrderItemRemoved>(&res_account),
            order_item_quantity_updated_handle: account::new_event_handle<OrderItemQuantityUpdated>(&res_account),
        });

        move_to(
            &res_account,
            Tables {
                order_table: table::new(),
            },
        );

        //todo emit event?
    }

    struct Order has store {
        order_id: String,
        version: u64,
        total_amount: u128,
        items: TableWithLength<String, OrderItem>,
    }

    public fun order_id(order: &Order): String {
        order.order_id
    }

    public fun version(order: &Order): u64 {
        order.version
    }

    public fun total_amount(order: &Order): u128 {
        order.total_amount
    }

    public(friend) fun set_total_amount(order: &mut Order, total_amount: u128) {
        order.total_amount = total_amount;
    }

    public(friend) fun add_item(order: &mut Order, item: OrderItem) {
        let key = order_item::product_id(&item);
        table_with_length::add(&mut order.items, key, item);
    }

    public(friend) fun remove_item(order: &mut Order, product_id: String) {
        let item = table_with_length::remove(&mut order.items, product_id);
        order_item::drop_order_item(item);
    }

    public(friend) fun borrow_mut_item(order: &mut Order, product_id: String): &mut OrderItem {
        table_with_length::borrow_mut(&mut order.items, product_id)
    }

    public fun borrow_item(order: &Order, product_id: String): &OrderItem {
        table_with_length::borrow(&order.items, product_id)
    }

    public fun items_contains(order: &Order, product_id: String): bool {
        table_with_length::contains(&order.items, product_id)
    }

    public fun items_length(order: &Order): u64 {
        table_with_length::length(&order.items)
    }

    //public(friend)
    fun new_order(
        order_id: String,
        total_amount: u128,
        //ctx: &mut TxContext,
    ): Order {
        Order {
            order_id, //id: object::new(ctx),
            version: 0,
            total_amount,
            items: table_with_length::new<String, OrderItem>(),
        }
    }

    struct OrderCreated has store, drop {
        order_id: String,
        //id: option::Option<object::ID>,
        product: String,
        quantity: u64,
        unit_price: u128,
        total_amount: u128,
        owner: address,
    }

    public fun order_created_order_id(order_created: &OrderCreated): String {
        order_created.order_id
    }

    // public(friend) fun set_order_created_id(order_created: &mut OrderCreated, order_id: String) {
    //     order_created.order_id = order_id;
    // }

    public fun order_created_product(order_created: &OrderCreated): String {
        order_created.product
    }

    public fun order_created_quantity(order_created: &OrderCreated): u64 {
        order_created.quantity
    }

    public fun order_created_unit_price(order_created: &OrderCreated): u128 {
        order_created.unit_price
    }

    public fun order_created_total_amount(order_created: &OrderCreated): u128 {
        order_created.total_amount
    }

    public fun order_created_owner(order_created: &OrderCreated): address {
        order_created.owner
    }

    public(friend) fun new_order_created(
        order_id: String,
        product: String,
        quantity: u64,
        unit_price: u128,
        total_amount: u128,
        owner: address,
    ): OrderCreated {
        OrderCreated {
            order_id, //id: option::none(),
            product,
            quantity,
            unit_price,
            total_amount,
            owner,
        }
    }

    struct OrderItemRemoved has store, drop {
        order_id: String,
        //id: object::ID,
        version: u64,
        product_id: String,
    }

    public fun order_item_removed_order_id(order_item_removed: &OrderItemRemoved): String {
        order_item_removed.order_id
    }

    public fun order_item_removed_product_id(order_item_removed: &OrderItemRemoved): String {
        order_item_removed.product_id
    }

    public(friend) fun new_order_item_removed(
        order: &Order,
        product_id: String,
    ): OrderItemRemoved {
        OrderItemRemoved {
            order_id: order_id(order),
            version: version(order),
            product_id,
        }
    }

    struct OrderItemQuantityUpdated has store, drop {
        order_id: String,
        //object::ID,
        version: u64,
        product_id: String,
        quantity: u64,
    }

    public fun order_item_quantity_updated_order_id(order_item_quantity_updated: &OrderItemQuantityUpdated): String {
        order_item_quantity_updated.order_id
    }

    public fun order_item_quantity_updated_product_id(order_item_quantity_updated: &OrderItemQuantityUpdated): String {
        order_item_quantity_updated.product_id
    }

    public fun order_item_quantity_updated_quantity(order_item_quantity_updated: &OrderItemQuantityUpdated): u64 {
        order_item_quantity_updated.quantity
    }

    public(friend) fun new_order_item_quantity_updated(
        order: &Order,
        product_id: String,
        quantity: u64,
    ): OrderItemQuantityUpdated {
        OrderItemQuantityUpdated {
            order_id: order_id(order),
            version: version(order),
            product_id,
            quantity,
        }
    }


    public(friend) fun create_order(
        order_id: String,
        total_amount: u128,
        //estimated_ship_date: Option<Day>,
        //order_id_table: &mut OrderIdTable,
        //ctx: &mut TxContext,
    ) acquires Tables {
        let order = new_order(
            order_id,
            total_amount,
            //estimated_ship_date,
            //ctx,
        );

        let tables = borrow_global_mut<Tables>(genesis_account::resouce_account_address());
        assert!(!table::contains(&tables.order_table, order_id), EID_ALREADY_EXISTS);
        table::add(&mut tables.order_table, order_id, order);
    }

    public(friend) fun asset_order_not_exists(
        order_id: String,
    ) acquires Tables {
        let tables = borrow_global_mut<Tables>(genesis_account::resouce_account_address());
        assert!(!table::contains(&tables.order_table, order_id), EID_ALREADY_EXISTS);
    }

    // public(friend) fun transfer_object(order: Order, recipient: address) {
    //     transfer::transfer(order, recipient);
    // }
    //
    // public(friend) fun update_version_and_transfer_object(order: Order, recipient: address) {
    //     order.version = order.version + 1;
    //     transfer::transfer(order, recipient);
    // }
    //
    // public(friend) fun share_object(order: Order) {
    //     transfer::share_object(order);
    // }
    //
    // public(friend) fun freeze_object(order: Order) {
    //     transfer::freeze_object(order);
    // }


    public(friend) fun emit_order_created(order_created: OrderCreated) acquires Events {
        let events = borrow_global_mut<Events>(genesis_account::resouce_account_address());
        event::emit_event(&mut events.order_created_handle, order_created);
    }

    public(friend) fun emit_order_item_removed(order_item_removed: OrderItemRemoved) acquires Events {
        let events = borrow_global_mut<Events>(genesis_account::resouce_account_address());
        event::emit_event(&mut events.order_item_removed_handle, order_item_removed);
    }

    public(friend) fun emit_order_item_quantity_updated(
        order_item_quantity_updated: OrderItemQuantityUpdated
    ) acquires Events {
        let events = borrow_global_mut<Events>(genesis_account::resouce_account_address());
        event::emit_event(&mut events.order_item_quantity_updated_handle, order_item_quantity_updated);
    }
}
