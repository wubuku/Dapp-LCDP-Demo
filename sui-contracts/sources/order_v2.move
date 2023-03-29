module sui_contracts::order_v2 {
    use std::option::Option;
    use std::string::String;
    use sui::event;
    use sui::object::{Self, UID};
    use sui::table;
    use sui::transfer;
    use sui::tx_context::TxContext;
    use sui_contracts::day::Day;
    use sui_contracts::order_ship_group::{Self, OrderShipGroup};
    use sui_contracts::order_v2_item::{Self, OrderV2Item};
    friend sui_contracts::order_v2_create_logic;
    friend sui_contracts::order_v2_remove_item_logic;
    friend sui_contracts::order_v2_update_item_quantity_logic;
    friend sui_contracts::order_v2_update_estimated_ship_date_logic;
    friend sui_contracts::order_v2_add_order_ship_group_logic;
    friend sui_contracts::order_v2_cancel_order_ship_group_quantity_logic;
    friend sui_contracts::order_v2_remove_order_ship_group_item_logic;
    friend sui_contracts::order_v2_remove_order_ship_group_logic;
    friend sui_contracts::order_v2_aggregate;

    const EID_ALREADY_EXISTS: u64 = 101;
    const EID_DATA_TOO_LONG: u64 = 102;

    struct OrderIdTable has key {
        id: UID,
        table: table::Table<String, object::ID>,
    }

    struct OrderIdTableCreated has copy, drop {
        id: object::ID,
    }

    fun init(ctx: &mut TxContext) {
        let id_generator_table = OrderIdTable {
            id: object::new(ctx),
            table: table::new(ctx),
        };
        let id_generator_table_id = object::uid_to_inner(&id_generator_table.id);
        transfer::share_object(id_generator_table);
        event::emit(OrderIdTableCreated {
            id: id_generator_table_id,
        });
    }

    struct OrderV2 has key {
        id: UID,
        order_id: String,
        version: u64,
        total_amount: u128,
        estimated_ship_date: Option<Day>,
        items: table::Table<String, OrderV2Item>,
        order_ship_groups: table::Table<u8, OrderShipGroup>,
    }

    public fun id(order_v2: &OrderV2): object::ID {
        object::uid_to_inner(&order_v2.id)
    }

    public fun order_id(order_v2: &OrderV2): String {
        order_v2.order_id
    }

    public fun version(order_v2: &OrderV2): u64 {
        order_v2.version
    }

    public fun total_amount(order_v2: &OrderV2): u128 {
        order_v2.total_amount
    }

    public(friend) fun set_total_amount(order_v2: &mut OrderV2, total_amount: u128) {
        order_v2.total_amount = total_amount;
    }

    public fun estimated_ship_date(order_v2: &OrderV2): Option<Day> {
        order_v2.estimated_ship_date
    }

    public(friend) fun set_estimated_ship_date(order_v2: &mut OrderV2, estimated_ship_date: Option<Day>) {
        order_v2.estimated_ship_date = estimated_ship_date;
    }

    public(friend) fun add_item(order_v2: &mut OrderV2, item: OrderV2Item) {
        let key = order_v2_item::product_id(&item);
        table::add(&mut order_v2.items, key, item);
    }

    public(friend) fun remove_item(order_v2: &mut OrderV2, product_id: String) {
        let item = table::remove(&mut order_v2.items, product_id);
        order_v2_item::drop_order_v2_item(item);
    }

    public(friend) fun borrow_mut_item(order_v2: &mut OrderV2, product_id: String): &mut OrderV2Item {
        table::borrow_mut(&mut order_v2.items, product_id)
    }

    public fun borrow_item(order_v2: &OrderV2, product_id: String): &OrderV2Item {
        table::borrow(&order_v2.items, product_id)
    }

    public fun items_contains(order_v2: &OrderV2, product_id: String): bool {
        table::contains(&order_v2.items, product_id)
    }

    public fun items_length(order_v2: &OrderV2): u64 {
        table::length(&order_v2.items)
    }

    public(friend) fun add_order_ship_group(order_v2: &mut OrderV2, order_ship_group: OrderShipGroup) {
        let key = order_ship_group::ship_group_seq_id(&order_ship_group);
        table::add(&mut order_v2.order_ship_groups, key, order_ship_group);
    }

    /*
    public(friend) fun remove_order_ship_group(order_v2: &mut OrderV2, ship_group_seq_id: u8) {
        let order_ship_group = table::remove(&mut order_v2.order_ship_groups, ship_group_seq_id);
        order_ship_group::drop_order_ship_group(order_ship_group);
    }
    */

    public(friend) fun borrow_mut_order_ship_group(order_v2: &mut OrderV2, ship_group_seq_id: u8): &mut OrderShipGroup {
        table::borrow_mut(&mut order_v2.order_ship_groups, ship_group_seq_id)
    }

    public fun borrow_order_ship_group(order_v2: &OrderV2, ship_group_seq_id: u8): &OrderShipGroup {
        table::borrow(&order_v2.order_ship_groups, ship_group_seq_id)
    }

    public fun order_ship_groups_contains(order_v2: &OrderV2, ship_group_seq_id: u8): bool {
        table::contains(&order_v2.order_ship_groups, ship_group_seq_id)
    }

    public fun order_ship_groups_length(order_v2: &OrderV2): u64 {
        table::length(&order_v2.order_ship_groups)
    }

    fun new_order_v2(
        id: UID,
        order_id: String,
        total_amount: u128,
        estimated_ship_date: Option<Day>,
        ctx: &mut TxContext,
    ): OrderV2 {
        assert!(std::string::length(&order_id) <= 50, EID_DATA_TOO_LONG);
        OrderV2 {
            id,
            order_id,
            version: 0,
            total_amount,
            estimated_ship_date,
            items: table::new<String, OrderV2Item>(ctx),
            order_ship_groups: table::new<u8, OrderShipGroup>(ctx),
        }
    }

    struct OrderV2Created has copy, drop {
        id: object::ID,
        order_id: String,
        product: String,
        quantity: u64,
        unit_price: u128,
        total_amount: u128,
        owner: address,
    }

    public fun order_v2_created_id(order_v2_created: &OrderV2Created): object::ID {
        order_v2_created.id
    }

    public fun order_v2_created_order_id(order_v2_created: &OrderV2Created): String {
        order_v2_created.order_id
    }

    public fun order_v2_created_product(order_v2_created: &OrderV2Created): String {
        order_v2_created.product
    }

    public fun order_v2_created_quantity(order_v2_created: &OrderV2Created): u64 {
        order_v2_created.quantity
    }

    public fun order_v2_created_unit_price(order_v2_created: &OrderV2Created): u128 {
        order_v2_created.unit_price
    }

    public fun order_v2_created_total_amount(order_v2_created: &OrderV2Created): u128 {
        order_v2_created.total_amount
    }

    public fun order_v2_created_owner(order_v2_created: &OrderV2Created): address {
        order_v2_created.owner
    }

    public(friend) fun new_order_v2_created(
        id: &UID,
        order_id: String,
        product: String,
        quantity: u64,
        unit_price: u128,
        total_amount: u128,
        owner: address,
    ): OrderV2Created {
        OrderV2Created {
            id: object::uid_to_inner(id),
            order_id,
            product,
            quantity,
            unit_price,
            total_amount,
            owner,
        }
    }

    struct OrderV2ItemRemoved has copy, drop {
        id: object::ID,
        order_id: String,
        version: u64,
        product_id: String,
    }

    public fun order_v2_item_removed_id(order_v2_item_removed: &OrderV2ItemRemoved): object::ID {
        order_v2_item_removed.id
    }

    public fun order_v2_item_removed_order_id(order_v2_item_removed: &OrderV2ItemRemoved): String {
        order_v2_item_removed.order_id
    }

    public fun order_v2_item_removed_product_id(order_v2_item_removed: &OrderV2ItemRemoved): String {
        order_v2_item_removed.product_id
    }

    public(friend) fun new_order_v2_item_removed(
        order_v2: &OrderV2,
        product_id: String,
    ): OrderV2ItemRemoved {
        OrderV2ItemRemoved {
            id: id(order_v2),
            order_id: order_id(order_v2),
            version: version(order_v2),
            product_id,
        }
    }

    struct OrderV2ItemQuantityUpdated has copy, drop {
        id: object::ID,
        order_id: String,
        version: u64,
        product_id: String,
        quantity: u64,
    }

    public fun order_v2_item_quantity_updated_id(order_v2_item_quantity_updated: &OrderV2ItemQuantityUpdated): object::ID {
        order_v2_item_quantity_updated.id
    }

    public fun order_v2_item_quantity_updated_order_id(order_v2_item_quantity_updated: &OrderV2ItemQuantityUpdated): String {
        order_v2_item_quantity_updated.order_id
    }

    public fun order_v2_item_quantity_updated_product_id(order_v2_item_quantity_updated: &OrderV2ItemQuantityUpdated): String {
        order_v2_item_quantity_updated.product_id
    }

    public fun order_v2_item_quantity_updated_quantity(order_v2_item_quantity_updated: &OrderV2ItemQuantityUpdated): u64 {
        order_v2_item_quantity_updated.quantity
    }

    public(friend) fun new_order_v2_item_quantity_updated(
        order_v2: &OrderV2,
        product_id: String,
        quantity: u64,
    ): OrderV2ItemQuantityUpdated {
        OrderV2ItemQuantityUpdated {
            id: id(order_v2),
            order_id: order_id(order_v2),
            version: version(order_v2),
            product_id,
            quantity,
        }
    }

    struct OrderV2EstimatedShipDateUpdated has copy, drop {
        id: object::ID,
        order_id: String,
        version: u64,
        estimated_ship_date: Day,
    }

    public fun order_v2_estimated_ship_date_updated_id(order_v2_estimated_ship_date_updated: &OrderV2EstimatedShipDateUpdated): object::ID {
        order_v2_estimated_ship_date_updated.id
    }

    public fun order_v2_estimated_ship_date_updated_order_id(order_v2_estimated_ship_date_updated: &OrderV2EstimatedShipDateUpdated): String {
        order_v2_estimated_ship_date_updated.order_id
    }

    public fun order_v2_estimated_ship_date_updated_estimated_ship_date(order_v2_estimated_ship_date_updated: &OrderV2EstimatedShipDateUpdated): Day {
        order_v2_estimated_ship_date_updated.estimated_ship_date
    }

    public(friend) fun new_order_v2_estimated_ship_date_updated(
        order_v2: &OrderV2,
        estimated_ship_date: Day,
    ): OrderV2EstimatedShipDateUpdated {
        OrderV2EstimatedShipDateUpdated {
            id: id(order_v2),
            order_id: order_id(order_v2),
            version: version(order_v2),
            estimated_ship_date,
        }
    }

    struct OrderShipGroupAdded has copy, drop {
        id: object::ID,
        order_id: String,
        version: u64,
        ship_group_seq_id: u8,
        shipment_method: String,
        product_id: String,
        quantity: u64,
    }

    public fun order_ship_group_added_id(order_ship_group_added: &OrderShipGroupAdded): object::ID {
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

    public fun order_ship_group_added_product_id(order_ship_group_added: &OrderShipGroupAdded): String {
        order_ship_group_added.product_id
    }

    public fun order_ship_group_added_quantity(order_ship_group_added: &OrderShipGroupAdded): u64 {
        order_ship_group_added.quantity
    }

    public(friend) fun new_order_ship_group_added(
        order_v2: &OrderV2,
        ship_group_seq_id: u8,
        shipment_method: String,
        product_id: String,
        quantity: u64,
    ): OrderShipGroupAdded {
        OrderShipGroupAdded {
            id: id(order_v2),
            order_id: order_id(order_v2),
            version: version(order_v2),
            ship_group_seq_id,
            shipment_method,
            product_id,
            quantity,
        }
    }

    struct OrderShipGroupQuantityCanceled has copy, drop {
        id: object::ID,
        order_id: String,
        version: u64,
        ship_group_seq_id: u8,
        product_id: String,
        cancel_quantity: u64,
    }

    public fun order_ship_group_quantity_canceled_id(order_ship_group_quantity_canceled: &OrderShipGroupQuantityCanceled): object::ID {
        order_ship_group_quantity_canceled.id
    }

    public fun order_ship_group_quantity_canceled_order_id(order_ship_group_quantity_canceled: &OrderShipGroupQuantityCanceled): String {
        order_ship_group_quantity_canceled.order_id
    }

    public fun order_ship_group_quantity_canceled_ship_group_seq_id(order_ship_group_quantity_canceled: &OrderShipGroupQuantityCanceled): u8 {
        order_ship_group_quantity_canceled.ship_group_seq_id
    }

    public fun order_ship_group_quantity_canceled_product_id(order_ship_group_quantity_canceled: &OrderShipGroupQuantityCanceled): String {
        order_ship_group_quantity_canceled.product_id
    }

    public fun order_ship_group_quantity_canceled_cancel_quantity(order_ship_group_quantity_canceled: &OrderShipGroupQuantityCanceled): u64 {
        order_ship_group_quantity_canceled.cancel_quantity
    }

    public(friend) fun new_order_ship_group_quantity_canceled(
        order_v2: &OrderV2,
        ship_group_seq_id: u8,
        product_id: String,
        cancel_quantity: u64,
    ): OrderShipGroupQuantityCanceled {
        OrderShipGroupQuantityCanceled {
            id: id(order_v2),
            order_id: order_id(order_v2),
            version: version(order_v2),
            ship_group_seq_id,
            product_id,
            cancel_quantity,
        }
    }

    struct OrderShipGroupItemRemoved has copy, drop {
        id: object::ID,
        order_id: String,
        version: u64,
        ship_group_seq_id: u8,
        product_id: String,
    }

    public fun order_ship_group_item_removed_id(order_ship_group_item_removed: &OrderShipGroupItemRemoved): object::ID {
        order_ship_group_item_removed.id
    }

    public fun order_ship_group_item_removed_order_id(order_ship_group_item_removed: &OrderShipGroupItemRemoved): String {
        order_ship_group_item_removed.order_id
    }

    public fun order_ship_group_item_removed_ship_group_seq_id(order_ship_group_item_removed: &OrderShipGroupItemRemoved): u8 {
        order_ship_group_item_removed.ship_group_seq_id
    }

    public fun order_ship_group_item_removed_product_id(order_ship_group_item_removed: &OrderShipGroupItemRemoved): String {
        order_ship_group_item_removed.product_id
    }

    public(friend) fun new_order_ship_group_item_removed(
        order_v2: &OrderV2,
        ship_group_seq_id: u8,
        product_id: String,
    ): OrderShipGroupItemRemoved {
        OrderShipGroupItemRemoved {
            id: id(order_v2),
            order_id: order_id(order_v2),
            version: version(order_v2),
            ship_group_seq_id,
            product_id,
        }
    }

    struct OrderShipGroupRemoved has copy, drop {
        id: object::ID,
        order_id: String,
        version: u64,
        ship_group_seq_id: u8,
    }

    public fun order_ship_group_removed_id(order_ship_group_removed: &OrderShipGroupRemoved): object::ID {
        order_ship_group_removed.id
    }

    public fun order_ship_group_removed_order_id(order_ship_group_removed: &OrderShipGroupRemoved): String {
        order_ship_group_removed.order_id
    }

    public fun order_ship_group_removed_ship_group_seq_id(order_ship_group_removed: &OrderShipGroupRemoved): u8 {
        order_ship_group_removed.ship_group_seq_id
    }

    public(friend) fun new_order_ship_group_removed(
        order_v2: &OrderV2,
        ship_group_seq_id: u8,
    ): OrderShipGroupRemoved {
        OrderShipGroupRemoved {
            id: id(order_v2),
            order_id: order_id(order_v2),
            version: version(order_v2),
            ship_group_seq_id,
        }
    }


    public(friend) fun create_order_v2(
        id: UID,
        order_id: String,
        total_amount: u128,
        estimated_ship_date: Option<Day>,
        order_id_table: &mut OrderIdTable,
        ctx: &mut TxContext,
    ): OrderV2 {
        asset_order_id_not_exists_then_add(order_id, order_id_table, object::uid_to_inner(&id));
        let order_v2 = new_order_v2(
            id,
            order_id,
            total_amount,
            estimated_ship_date,
            ctx,
        );
        order_v2
    }

    public(friend) fun asset_order_id_not_exists(
        order_id: String,
        order_id_table: &OrderIdTable,
    ) {
        assert!(!table::contains(&order_id_table.table, order_id), EID_ALREADY_EXISTS);
    }

    fun asset_order_id_not_exists_then_add(
        order_id: String,
        order_id_table: &mut OrderIdTable,
        id: object::ID,
    ) {
        asset_order_id_not_exists(order_id, order_id_table);
        table::add(&mut order_id_table.table, order_id, id);
    }

    public(friend) fun transfer_object(order_v2: OrderV2, recipient: address) {
        transfer::transfer(order_v2, recipient);
    }

    public(friend) fun update_version_and_transfer_object(order_v2: OrderV2, recipient: address) {
        order_v2.version = order_v2.version + 1;
        transfer::transfer(order_v2, recipient);
    }

    public(friend) fun share_object(order_v2: OrderV2) {
        transfer::share_object(order_v2);
    }

    public(friend) fun freeze_object(order_v2: OrderV2) {
        transfer::freeze_object(order_v2);
    }

    public(friend) fun emit_order_v2_created(order_v2_created: OrderV2Created) {
        event::emit(order_v2_created);
    }

    public(friend) fun emit_order_v2_item_removed(order_v2_item_removed: OrderV2ItemRemoved) {
        event::emit(order_v2_item_removed);
    }

    public(friend) fun emit_order_v2_item_quantity_updated(order_v2_item_quantity_updated: OrderV2ItemQuantityUpdated) {
        event::emit(order_v2_item_quantity_updated);
    }

    public(friend) fun emit_order_v2_estimated_ship_date_updated(order_v2_estimated_ship_date_updated: OrderV2EstimatedShipDateUpdated) {
        event::emit(order_v2_estimated_ship_date_updated);
    }

    public(friend) fun emit_order_ship_group_added(order_ship_group_added: OrderShipGroupAdded) {
        event::emit(order_ship_group_added);
    }

    public(friend) fun emit_order_ship_group_quantity_canceled(order_ship_group_quantity_canceled: OrderShipGroupQuantityCanceled) {
        event::emit(order_ship_group_quantity_canceled);
    }

    public(friend) fun emit_order_ship_group_item_removed(order_ship_group_item_removed: OrderShipGroupItemRemoved) {
        event::emit(order_ship_group_item_removed);
    }

    public(friend) fun emit_order_ship_group_removed(order_ship_group_removed: OrderShipGroupRemoved) {
        event::emit(order_ship_group_removed);
    }

    #[test_only]
    /// Wrapper of module initializer for testing
    public fun test_init(ctx: &mut TxContext) {
        init(ctx)
    }

}