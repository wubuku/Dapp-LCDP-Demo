// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

module rooch_demo::order_item_ship_group_association {
    use moveos_std::event;
    use moveos_std::object_id::ObjectID;
    use moveos_std::storage_context::StorageContext;
    use moveos_std::table::{Self, Table};
    use moveos_std::tx_context;
    use rooch_demo::day::Day;
    use rooch_demo::order_item_ship_group_assoc_subitem::{Self, OrderItemShipGroupAssocSubitem};
    use std::string::String;
    friend rooch_demo::order_create_logic;
    friend rooch_demo::order_remove_item_logic;
    friend rooch_demo::order_update_item_quantity_logic;
    friend rooch_demo::order_update_estimated_ship_date_logic;
    friend rooch_demo::order_add_order_ship_group_logic;
    friend rooch_demo::order_add_order_item_ship_group_assoc_subitem_logic;
    friend rooch_demo::order_cancel_order_ship_group_quantity_logic;
    friend rooch_demo::order_remove_order_ship_group_item_logic;
    friend rooch_demo::order_ship_group;

    const EID_ALREADY_EXISTS: u64 = 101;
    const EDATA_TOO_LONG: u64 = 102;
    const EID_NOT_FOUND: u64 = 106;

    struct OrderItemShipGroupAssocSubitemTableItemAdded has key {
        order_id: String,
        order_ship_group_ship_group_seq_id: u8,
        order_item_ship_group_association_product_obj_id: ObjectID,
        order_item_ship_group_assoc_subitem_day: Day,
    }

    struct OrderItemShipGroupAssociation has store {
        product_obj_id: ObjectID,
        quantity: u64,
        cancel_quantity: u64,
        subitems: Table<Day, OrderItemShipGroupAssocSubitem>,
    }

    public fun product_obj_id(order_item_ship_group_association: &OrderItemShipGroupAssociation): ObjectID {
        order_item_ship_group_association.product_obj_id
    }

    public fun quantity(order_item_ship_group_association: &OrderItemShipGroupAssociation): u64 {
        order_item_ship_group_association.quantity
    }

    public(friend) fun set_quantity(order_item_ship_group_association: &mut OrderItemShipGroupAssociation, quantity: u64) {
        order_item_ship_group_association.quantity = quantity;
    }

    public fun cancel_quantity(order_item_ship_group_association: &OrderItemShipGroupAssociation): u64 {
        order_item_ship_group_association.cancel_quantity
    }

    public(friend) fun set_cancel_quantity(order_item_ship_group_association: &mut OrderItemShipGroupAssociation, cancel_quantity: u64) {
        order_item_ship_group_association.cancel_quantity = cancel_quantity;
    }

    public(friend) fun add_subitem(storage_ctx: &mut StorageContext, order_id: String, order_ship_group_ship_group_seq_id: u8, order_item_ship_group_association: &mut OrderItemShipGroupAssociation, subitem: OrderItemShipGroupAssocSubitem) {
        let order_item_ship_group_assoc_subitem_day = order_item_ship_group_assoc_subitem::order_item_ship_group_assoc_subitem_day(&subitem);
        assert!(!table::contains(&order_item_ship_group_association.subitems, order_item_ship_group_assoc_subitem_day), EID_ALREADY_EXISTS);
        table::add(&mut order_item_ship_group_association.subitems, order_item_ship_group_assoc_subitem_day, subitem);
        event::emit_event(storage_ctx, OrderItemShipGroupAssocSubitemTableItemAdded {
            order_id,
            order_ship_group_ship_group_seq_id,
            order_item_ship_group_association_product_obj_id: product_obj_id(order_item_ship_group_association),
            order_item_ship_group_assoc_subitem_day,
        });
    }

    public(friend) fun remove_subitem(order_item_ship_group_association: &mut OrderItemShipGroupAssociation, order_item_ship_group_assoc_subitem_day: Day) {
        assert!(table::contains(&order_item_ship_group_association.subitems, order_item_ship_group_assoc_subitem_day), EID_NOT_FOUND);
        let subitem = table::remove(&mut order_item_ship_group_association.subitems, order_item_ship_group_assoc_subitem_day);
        order_item_ship_group_assoc_subitem::drop_order_item_ship_group_assoc_subitem(subitem);
    }

    public(friend) fun borrow_mut_subitem(order_item_ship_group_association: &mut OrderItemShipGroupAssociation, order_item_ship_group_assoc_subitem_day: Day): &mut OrderItemShipGroupAssocSubitem {
        table::borrow_mut(&mut order_item_ship_group_association.subitems, order_item_ship_group_assoc_subitem_day)
    }

    public fun borrow_subitem(order_item_ship_group_association: &OrderItemShipGroupAssociation, order_item_ship_group_assoc_subitem_day: Day): &OrderItemShipGroupAssocSubitem {
        table::borrow(&order_item_ship_group_association.subitems, order_item_ship_group_assoc_subitem_day)
    }

    public fun subitems_contains(order_item_ship_group_association: &OrderItemShipGroupAssociation, order_item_ship_group_assoc_subitem_day: Day): bool {
        table::contains(&order_item_ship_group_association.subitems, order_item_ship_group_assoc_subitem_day)
    }

    public(friend) fun new_order_item_ship_group_association(
        tx_ctx: &mut tx_context::TxContext,
        product_obj_id: ObjectID,
        quantity: u64,
        cancel_quantity: u64,
    ): OrderItemShipGroupAssociation {
        OrderItemShipGroupAssociation {
            product_obj_id,
            quantity,
            cancel_quantity,
            subitems: table::new<Day, OrderItemShipGroupAssocSubitem>(tx_ctx),
        }
    }

    // Please note that when the hierarchical structure of entities within an aggregate exceeds three levels,
    // currently the 'drop_{entity_name}' function for entities from the second to the third-to-last level cannot be generated.
    /*
    public(friend) fun drop_order_item_ship_group_association(order_item_ship_group_association: OrderItemShipGroupAssociation) {
        let OrderItemShipGroupAssociation {
            product_obj_id: _,
            quantity: _,
            cancel_quantity: _,
            subitems,
        } = order_item_ship_group_association;
    }

    */

}
