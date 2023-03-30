module sui_contracts::order {
    use std::string::String;
    use sui::event;
    use sui::object::{Self, UID};
    use sui::table;
    use sui::transfer;
    use sui::tx_context::TxContext;
    use sui_contracts::order_item::{Self, OrderItem};
    use std::option;
    friend sui_contracts::order_create_logic;
    friend sui_contracts::order_remove_item_logic;
    friend sui_contracts::order_update_item_quantity_logic;
    friend sui_contracts::order_aggregate;

    const EID_DATA_TOO_LONG: u64 = 102;

    struct Order has key {
        id: UID,
        version: u64,
        total_amount: u128,
        items: table::Table<String, OrderItem>,
    }

    public fun id(order: &Order): object::ID {
        object::uid_to_inner(&order.id)
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
        table::add(&mut order.items, key, item);
    }

    public(friend) fun remove_item(order: &mut Order, product_id: String) {
        let item = table::remove(&mut order.items, product_id);
        order_item::drop_order_item(item);
    }

    public(friend) fun borrow_mut_item(order: &mut Order, product_id: String): &mut OrderItem {
        table::borrow_mut(&mut order.items, product_id)
    }

    public fun borrow_item(order: &Order, product_id: String): &OrderItem {
        table::borrow(&order.items, product_id)
    }

    public fun items_contains(order: &Order, product_id: String): bool {
        table::contains(&order.items, product_id)
    }

    public fun items_length(order: &Order): u64 {
        table::length(&order.items)
    }

    public(friend) fun new_order(
        id: UID,
        total_amount: u128,
        ctx: &mut TxContext,
    ): Order {
        Order {
            id,
            version: 0,
            total_amount,
            items: table::new<String, OrderItem>(ctx),
        }
    }

    struct OrderCreated has copy, drop {
        id: option::Option<object::ID>,
        product: String,
        quantity: u64,
        unit_price: u128,
        total_amount: u128,
        owner: address,
    }

    public fun order_created_id(order_created: &OrderCreated): option::Option<object::ID> {
        order_created.id
    }

    public(friend) fun set_order_created_id(order_created: &mut OrderCreated, id: object::ID) {
        order_created.id = option::some(id);
    }

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
        //id: &UID,
        product: String,
        quantity: u64,
        unit_price: u128,
        total_amount: u128,
        owner: address,
    ): OrderCreated {
        OrderCreated {
            id: option::none(),
            product,
            quantity,
            unit_price,
            total_amount,
            owner,
        }
    }

    struct OrderItemRemoved has copy, drop {
        id: object::ID,
        version: u64,
        product_id: String,
    }

    public fun order_item_removed_id(order_item_removed: &OrderItemRemoved): object::ID {
        order_item_removed.id
    }

    public fun order_item_removed_product_id(order_item_removed: &OrderItemRemoved): String {
        order_item_removed.product_id
    }

    public(friend) fun new_order_item_removed(
        order: &Order,
        product_id: String,
    ): OrderItemRemoved {
        OrderItemRemoved {
            id: id(order),
            version: version(order),
            product_id,
        }
    }

    struct OrderItemQuantityUpdated has copy, drop {
        id: object::ID,
        version: u64,
        product_id: String,
        quantity: u64,
    }

    public fun order_item_quantity_updated_id(order_item_quantity_updated: &OrderItemQuantityUpdated): object::ID {
        order_item_quantity_updated.id
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
            id: id(order),
            version: version(order),
            product_id,
            quantity,
        }
    }


    public(friend) fun transfer_object(order: Order, recipient: address) {
        transfer::transfer(order, recipient);
    }

    public(friend) fun update_version_and_transfer_object(order: Order, recipient: address) {
        order.version = order.version + 1;
        transfer::transfer(order, recipient);
    }

    public(friend) fun share_object(order: Order) {
        transfer::share_object(order);
    }

    public(friend) fun freeze_object(order: Order) {
        transfer::freeze_object(order);
    }

    public(friend) fun emit_order_created(order_created: OrderCreated) {
        event::emit(order_created);
    }

    public(friend) fun emit_order_item_removed(order_item_removed: OrderItemRemoved) {
        event::emit(order_item_removed);
    }

    public(friend) fun emit_order_item_quantity_updated(order_item_quantity_updated: OrderItemQuantityUpdated) {
        event::emit(order_item_quantity_updated);
    }
}
