// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

module rooch_demo::order {
    use moveos_std::account_storage;
    use moveos_std::events;
    use moveos_std::object::{Self, Object};
    use moveos_std::object_id::ObjectID;
    use moveos_std::object_storage;
    use moveos_std::storage_context::{Self, StorageContext};
    use moveos_std::table::{Self, Table};
    use moveos_std::tx_context;
    use rooch_demo::day::Day;
    use rooch_demo::order_item::{Self, OrderItem};
    use rooch_demo::order_ship_group::{Self, OrderShipGroup};
    use std::error;
    use std::option::{Self, Option};
    use std::signer;
    use std::string::String;
    friend rooch_demo::order_create_logic;
    friend rooch_demo::order_remove_item_logic;
    friend rooch_demo::order_update_item_quantity_logic;
    friend rooch_demo::order_update_estimated_ship_date_logic;
    friend rooch_demo::order_add_order_ship_group_logic;
    friend rooch_demo::order_cancel_order_ship_group_quantity_logic;
    friend rooch_demo::order_remove_order_ship_group_item_logic;
    friend rooch_demo::order_aggregate;

    const EID_ALREADY_EXISTS: u64 = 101;
    const EID_DATA_TOO_LONG: u64 = 102;
    const EINAPPROPRIATE_VERSION: u64 = 103;
    const ENOT_GENESIS_ACCOUNT: u64 = 105;

    struct Tables has key {
        order_id_table: Table<String, ObjectID>,
    }

    struct OrderItemTableItemAdded has key {
        order_id: String,
        product_object_id: ObjectID,
    }

    struct OrderShipGroupTableItemAdded has key {
        order_id: String,
        ship_group_seq_id: u8,
    }

    public fun initialize(storage_ctx: &mut StorageContext, account: &signer) {
        assert!(signer::address_of(account) == @rooch_demo, error::invalid_argument(ENOT_GENESIS_ACCOUNT));
        let tx_ctx = storage_context::tx_context_mut(storage_ctx);

        account_storage::global_move_to(
            storage_ctx,
            account,
            Tables {
                order_id_table: table::new(tx_ctx),
            },
        );
    }

    struct Order has key {
        order_id: String,
        version: u64,
        total_amount: u128,
        estimated_ship_date: Option<Day>,
        items: Table<ObjectID, OrderItem>,
        order_ship_groups: Table<u8, OrderShipGroup>,
    }

    /// get object id
    public fun id(order_obj: &Object<Order>): ObjectID {
        object::id(order_obj)
    }

    public fun order_id(order_obj: &Object<Order>): String {
        object::borrow(order_obj).order_id
    }

    public fun version(order_obj: &Object<Order>): u64 {
        object::borrow(order_obj).version
    }

    public fun total_amount(order_obj: &Object<Order>): u128 {
        object::borrow(order_obj).total_amount
    }

    public(friend) fun set_total_amount(order_obj: &mut Object<Order>, total_amount: u128) {
        object::borrow_mut(order_obj).total_amount = total_amount;
    }

    public fun estimated_ship_date(order_obj: &Object<Order>): Option<Day> {
        object::borrow(order_obj).estimated_ship_date
    }

    public(friend) fun set_estimated_ship_date(order_obj: &mut Object<Order>, estimated_ship_date: Option<Day>) {
        object::borrow_mut(order_obj).estimated_ship_date = estimated_ship_date;
    }

    public(friend) fun add_item(storage_ctx: &mut StorageContext, order_obj: &mut Object<Order>, item: OrderItem) {
        let product_object_id = order_item::product_object_id(&item);
        table::add(&mut object::borrow_mut(order_obj).items, product_object_id, item);
        events::emit_event(storage_ctx, OrderItemTableItemAdded {
            order_id: order_id(order_obj),
            product_object_id,
        });
    }

    public(friend) fun remove_item(order_obj: &mut Object<Order>, product_object_id: ObjectID) {
        let item = table::remove(&mut object::borrow_mut(order_obj).items, product_object_id);
        order_item::drop_order_item(item);
    }

    public(friend) fun borrow_mut_item(order_obj: &mut Object<Order>, product_object_id: ObjectID): &mut OrderItem {
        table::borrow_mut(&mut object::borrow_mut(order_obj).items, product_object_id)
    }

    public fun borrow_item(order_obj: &Object<Order>, product_object_id: ObjectID): &OrderItem {
        table::borrow(&object::borrow(order_obj).items, product_object_id)
    }

    public fun items_contains(order_obj: &Object<Order>, product_object_id: ObjectID): bool {
        table::contains(&object::borrow(order_obj).items, product_object_id)
    }

    public(friend) fun add_order_ship_group(storage_ctx: &mut StorageContext, order_obj: &mut Object<Order>, order_ship_group: OrderShipGroup) {
        let ship_group_seq_id = order_ship_group::ship_group_seq_id(&order_ship_group);
        table::add(&mut object::borrow_mut(order_obj).order_ship_groups, ship_group_seq_id, order_ship_group);
        events::emit_event(storage_ctx, OrderShipGroupTableItemAdded {
            order_id: order_id(order_obj),
            ship_group_seq_id,
        });
    }

    /*
    public(friend) fun remove_order_ship_group(order_obj: &mut Object<Order>, ship_group_seq_id: u8) {
        let order_ship_group = table::remove(&mut object::borrow_mut(order_obj).order_ship_groups, ship_group_seq_id);
        order_ship_group::drop_order_ship_group(order_ship_group);
    }
    */

    public(friend) fun borrow_mut_order_ship_group(order_obj: &mut Object<Order>, ship_group_seq_id: u8): &mut OrderShipGroup {
        table::borrow_mut(&mut object::borrow_mut(order_obj).order_ship_groups, ship_group_seq_id)
    }

    public fun borrow_order_ship_group(order_obj: &Object<Order>, ship_group_seq_id: u8): &OrderShipGroup {
        table::borrow(&object::borrow(order_obj).order_ship_groups, ship_group_seq_id)
    }

    public fun order_ship_groups_contains(order_obj: &Object<Order>, ship_group_seq_id: u8): bool {
        table::contains(&object::borrow(order_obj).order_ship_groups, ship_group_seq_id)
    }

    fun new_order(
        tx_ctx: &mut tx_context::TxContext,
        order_id: String,
        total_amount: u128,
        estimated_ship_date: Option<Day>,
    ): Order {
        assert!(std::string::length(&order_id) <= 50, EID_DATA_TOO_LONG);
        Order {
            order_id,
            version: 0,
            total_amount,
            estimated_ship_date,
            items: table::new<ObjectID, OrderItem>(tx_ctx),
            order_ship_groups: table::new<u8, OrderShipGroup>(tx_ctx),
        }
    }

    struct OrderCreated has key {
        id: option::Option<ObjectID>,
        order_id: String,
        product_obj_id: ObjectID,
        quantity: u64,
        unit_price: u128,
        total_amount: u128,
        owner: address,
    }

    public fun order_created_id(order_created: &OrderCreated): option::Option<ObjectID> {
        order_created.id
    }

    public(friend) fun set_order_created_id(order_created: &mut OrderCreated, id: ObjectID) {
        order_created.id = option::some(id);
    }

    public fun order_created_order_id(order_created: &OrderCreated): String {
        order_created.order_id
    }

    public fun order_created_product_obj_id(order_created: &OrderCreated): ObjectID {
        order_created.product_obj_id
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
        product_obj_id: ObjectID,
        quantity: u64,
        unit_price: u128,
        total_amount: u128,
        owner: address,
    ): OrderCreated {
        OrderCreated {
            id: option::none(),
            order_id,
            product_obj_id,
            quantity,
            unit_price,
            total_amount,
            owner,
        }
    }

    struct OrderItemRemoved has key {
        id: ObjectID,
        order_id: String,
        version: u64,
        product_obj_id: ObjectID,
    }

    public fun order_item_removed_id(order_item_removed: &OrderItemRemoved): ObjectID {
        order_item_removed.id
    }

    public fun order_item_removed_order_id(order_item_removed: &OrderItemRemoved): String {
        order_item_removed.order_id
    }

    public fun order_item_removed_product_obj_id(order_item_removed: &OrderItemRemoved): ObjectID {
        order_item_removed.product_obj_id
    }

    public(friend) fun new_order_item_removed(
        order_obj: &Object<Order>,
        product_obj_id: ObjectID,
    ): OrderItemRemoved {
        OrderItemRemoved {
            id: id(order_obj),
            order_id: order_id(order_obj),
            version: version(order_obj),
            product_obj_id,
        }
    }

    struct OrderItemQuantityUpdated has key {
        id: ObjectID,
        order_id: String,
        version: u64,
        product_obj_id: ObjectID,
        quantity: u64,
    }

    public fun order_item_quantity_updated_id(order_item_quantity_updated: &OrderItemQuantityUpdated): ObjectID {
        order_item_quantity_updated.id
    }

    public fun order_item_quantity_updated_order_id(order_item_quantity_updated: &OrderItemQuantityUpdated): String {
        order_item_quantity_updated.order_id
    }

    public fun order_item_quantity_updated_product_obj_id(order_item_quantity_updated: &OrderItemQuantityUpdated): ObjectID {
        order_item_quantity_updated.product_obj_id
    }

    public fun order_item_quantity_updated_quantity(order_item_quantity_updated: &OrderItemQuantityUpdated): u64 {
        order_item_quantity_updated.quantity
    }

    public(friend) fun new_order_item_quantity_updated(
        order_obj: &Object<Order>,
        product_obj_id: ObjectID,
        quantity: u64,
    ): OrderItemQuantityUpdated {
        OrderItemQuantityUpdated {
            id: id(order_obj),
            order_id: order_id(order_obj),
            version: version(order_obj),
            product_obj_id,
            quantity,
        }
    }

    struct OrderEstimatedShipDateUpdated has key {
        id: ObjectID,
        order_id: String,
        version: u64,
        estimated_ship_date: Day,
    }

    public fun order_estimated_ship_date_updated_id(order_estimated_ship_date_updated: &OrderEstimatedShipDateUpdated): ObjectID {
        order_estimated_ship_date_updated.id
    }

    public fun order_estimated_ship_date_updated_order_id(order_estimated_ship_date_updated: &OrderEstimatedShipDateUpdated): String {
        order_estimated_ship_date_updated.order_id
    }

    public fun order_estimated_ship_date_updated_estimated_ship_date(order_estimated_ship_date_updated: &OrderEstimatedShipDateUpdated): Day {
        order_estimated_ship_date_updated.estimated_ship_date
    }

    public(friend) fun new_order_estimated_ship_date_updated(
        order_obj: &Object<Order>,
        estimated_ship_date: Day,
    ): OrderEstimatedShipDateUpdated {
        OrderEstimatedShipDateUpdated {
            id: id(order_obj),
            order_id: order_id(order_obj),
            version: version(order_obj),
            estimated_ship_date,
        }
    }

    struct OrderShipGroupAdded has key {
        id: ObjectID,
        order_id: String,
        version: u64,
        ship_group_seq_id: u8,
        shipment_method: String,
        product_obj_id: ObjectID,
        quantity: u64,
    }

    public fun order_ship_group_added_id(order_ship_group_added: &OrderShipGroupAdded): ObjectID {
        order_ship_group_added.id
    }

    public fun order_ship_group_added_order_id(order_ship_group_added: &OrderShipGroupAdded): String {
        order_ship_group_added.order_id
    }

    public fun order_ship_group_added_ship_group_seq_id(order_ship_group_added: &OrderShipGroupAdded): u8 {
        order_ship_group_added.ship_group_seq_id
    }

    public fun order_ship_group_added_shipment_method(order_ship_group_added: &OrderShipGroupAdded): String {
        order_ship_group_added.shipment_method
    }

    public fun order_ship_group_added_product_obj_id(order_ship_group_added: &OrderShipGroupAdded): ObjectID {
        order_ship_group_added.product_obj_id
    }

    public fun order_ship_group_added_quantity(order_ship_group_added: &OrderShipGroupAdded): u64 {
        order_ship_group_added.quantity
    }

    public(friend) fun new_order_ship_group_added(
        order_obj: &Object<Order>,
        ship_group_seq_id: u8,
        shipment_method: String,
        product_obj_id: ObjectID,
        quantity: u64,
    ): OrderShipGroupAdded {
        OrderShipGroupAdded {
            id: id(order_obj),
            order_id: order_id(order_obj),
            version: version(order_obj),
            ship_group_seq_id,
            shipment_method,
            product_obj_id,
            quantity,
        }
    }

    struct OrderShipGroupQuantityCanceled has key {
        id: ObjectID,
        order_id: String,
        version: u64,
        ship_group_seq_id: u8,
        product_obj_id: ObjectID,
        cancel_quantity: u64,
    }

    public fun order_ship_group_quantity_canceled_id(order_ship_group_quantity_canceled: &OrderShipGroupQuantityCanceled): ObjectID {
        order_ship_group_quantity_canceled.id
    }

    public fun order_ship_group_quantity_canceled_order_id(order_ship_group_quantity_canceled: &OrderShipGroupQuantityCanceled): String {
        order_ship_group_quantity_canceled.order_id
    }

    public fun order_ship_group_quantity_canceled_ship_group_seq_id(order_ship_group_quantity_canceled: &OrderShipGroupQuantityCanceled): u8 {
        order_ship_group_quantity_canceled.ship_group_seq_id
    }

    public fun order_ship_group_quantity_canceled_product_obj_id(order_ship_group_quantity_canceled: &OrderShipGroupQuantityCanceled): ObjectID {
        order_ship_group_quantity_canceled.product_obj_id
    }

    public fun order_ship_group_quantity_canceled_cancel_quantity(order_ship_group_quantity_canceled: &OrderShipGroupQuantityCanceled): u64 {
        order_ship_group_quantity_canceled.cancel_quantity
    }

    public(friend) fun new_order_ship_group_quantity_canceled(
        order_obj: &Object<Order>,
        ship_group_seq_id: u8,
        product_obj_id: ObjectID,
        cancel_quantity: u64,
    ): OrderShipGroupQuantityCanceled {
        OrderShipGroupQuantityCanceled {
            id: id(order_obj),
            order_id: order_id(order_obj),
            version: version(order_obj),
            ship_group_seq_id,
            product_obj_id,
            cancel_quantity,
        }
    }

    struct OrderShipGroupItemRemoved has key {
        id: ObjectID,
        order_id: String,
        version: u64,
        ship_group_seq_id: u8,
        product_obj_id: ObjectID,
    }

    public fun order_ship_group_item_removed_id(order_ship_group_item_removed: &OrderShipGroupItemRemoved): ObjectID {
        order_ship_group_item_removed.id
    }

    public fun order_ship_group_item_removed_order_id(order_ship_group_item_removed: &OrderShipGroupItemRemoved): String {
        order_ship_group_item_removed.order_id
    }

    public fun order_ship_group_item_removed_ship_group_seq_id(order_ship_group_item_removed: &OrderShipGroupItemRemoved): u8 {
        order_ship_group_item_removed.ship_group_seq_id
    }

    public fun order_ship_group_item_removed_product_obj_id(order_ship_group_item_removed: &OrderShipGroupItemRemoved): ObjectID {
        order_ship_group_item_removed.product_obj_id
    }

    public(friend) fun new_order_ship_group_item_removed(
        order_obj: &Object<Order>,
        ship_group_seq_id: u8,
        product_obj_id: ObjectID,
    ): OrderShipGroupItemRemoved {
        OrderShipGroupItemRemoved {
            id: id(order_obj),
            order_id: order_id(order_obj),
            version: version(order_obj),
            ship_group_seq_id,
            product_obj_id,
        }
    }


    public(friend) fun create_order(
        storage_ctx: &mut StorageContext,
        order_id: String,
        total_amount: u128,
        estimated_ship_date: Option<Day>,
    ): Object<Order> {
        let tx_ctx = storage_context::tx_context_mut(storage_ctx);
        let order = new_order(
            tx_ctx,
            order_id,
            total_amount,
            estimated_ship_date,
        );
        let obj_owner = tx_context::sender(tx_ctx);
        let order_obj = object::new(
            tx_ctx,
            obj_owner,
            order,
        );
        asset_order_id_not_exists_then_add(storage_ctx, order_id, object::id(&order_obj));
        order_obj
    }

    public(friend) fun asset_order_id_not_exists(
        storage_ctx: &StorageContext,
        order_id: String,
    ) {
        let tables = account_storage::global_borrow<Tables>(storage_ctx, @rooch_demo);
        assert!(!table::contains(&tables.order_id_table, order_id), EID_ALREADY_EXISTS);
    }

    fun asset_order_id_not_exists_then_add(
        storage_ctx: &mut StorageContext,
        order_id: String,
        id: ObjectID,
    ) {
        asset_order_id_not_exists(storage_ctx, order_id);
        let tables = account_storage::global_borrow_mut<Tables>(storage_ctx, @rooch_demo);
        table::add(&mut tables.order_id_table, order_id, id);
    }

    public(friend) fun update_version_and_add(storage_ctx: &mut StorageContext, order_obj: Object<Order>) {
        object::borrow_mut(&mut order_obj).version = object::borrow( &mut order_obj).version + 1;
        assert!(object::borrow(&order_obj).version != 0, EINAPPROPRIATE_VERSION);
        private_add_order(storage_ctx, order_obj);
    }

    public(friend) fun remove_order(storage_ctx: &mut StorageContext, obj_id: ObjectID): Object<Order> {
        let obj_store = storage_context::object_storage_mut(storage_ctx);
        object_storage::remove<Order>(obj_store, obj_id)
    }

    public(friend) fun add_order(storage_ctx: &mut StorageContext, order_obj: Object<Order>) {
        assert!(object::borrow(&order_obj).version == 0, EINAPPROPRIATE_VERSION);
        private_add_order(storage_ctx, order_obj);
    }

    fun private_add_order(storage_ctx: &mut StorageContext, order_obj: Object<Order>) {
        let obj_store = storage_context::object_storage_mut(storage_ctx);
        object_storage::add(obj_store, order_obj);
    }

    public fun get_order_by_order_id(storage_ctx: &mut StorageContext, order_id: String): Object<Order> {
        let tables = account_storage::global_borrow_mut<Tables>(storage_ctx, @rooch_demo);
        let obj_id = table::borrow(&mut tables.order_id_table, order_id);
        remove_order(storage_ctx, *obj_id)
    }

    public fun get_order(storage_ctx: &mut StorageContext, obj_id: ObjectID): Object<Order> {
        remove_order(storage_ctx, obj_id)
    }

    public fun return_order(storage_ctx: &mut StorageContext, order_obj: Object<Order>) {
        private_add_order(storage_ctx, order_obj);
    }

    public(friend) fun emit_order_created(storage_ctx: &mut StorageContext, order_created: OrderCreated) {
        events::emit_event(storage_ctx, order_created);
    }

    public(friend) fun emit_order_item_removed(storage_ctx: &mut StorageContext, order_item_removed: OrderItemRemoved) {
        events::emit_event(storage_ctx, order_item_removed);
    }

    public(friend) fun emit_order_item_quantity_updated(storage_ctx: &mut StorageContext, order_item_quantity_updated: OrderItemQuantityUpdated) {
        events::emit_event(storage_ctx, order_item_quantity_updated);
    }

    public(friend) fun emit_order_estimated_ship_date_updated(storage_ctx: &mut StorageContext, order_estimated_ship_date_updated: OrderEstimatedShipDateUpdated) {
        events::emit_event(storage_ctx, order_estimated_ship_date_updated);
    }

    public(friend) fun emit_order_ship_group_added(storage_ctx: &mut StorageContext, order_ship_group_added: OrderShipGroupAdded) {
        events::emit_event(storage_ctx, order_ship_group_added);
    }

    public(friend) fun emit_order_ship_group_quantity_canceled(storage_ctx: &mut StorageContext, order_ship_group_quantity_canceled: OrderShipGroupQuantityCanceled) {
        events::emit_event(storage_ctx, order_ship_group_quantity_canceled);
    }

    public(friend) fun emit_order_ship_group_item_removed(storage_ctx: &mut StorageContext, order_ship_group_item_removed: OrderShipGroupItemRemoved) {
        events::emit_event(storage_ctx, order_ship_group_item_removed);
    }

}
