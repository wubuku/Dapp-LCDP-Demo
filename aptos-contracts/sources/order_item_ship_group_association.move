// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

module aptos_demo::order_item_ship_group_association {
    use aptos_demo::day::Day;
    use aptos_demo::order_item_ship_group_assoc_subitem::{Self, OrderItemShipGroupAssocSubitem};
    use aptos_std::table_with_length::{Self, TableWithLength};
    use std::string::String;
    friend aptos_demo::order_create_logic;
    friend aptos_demo::order_remove_item_logic;
    friend aptos_demo::order_update_item_quantity_logic;
    friend aptos_demo::order_update_estimated_ship_date_logic;
    friend aptos_demo::order_add_order_ship_group_logic;
    friend aptos_demo::order_cancel_order_ship_group_quantity_logic;
    friend aptos_demo::order_remove_order_ship_group_item_logic;
    friend aptos_demo::order_remove_order_ship_group_logic;
    friend aptos_demo::order_ship_group;

    const EID_ALREADY_EXISTS: u64 = 101;
    const EDATA_TOO_LONG: u64 = 102;
    const EID_NOT_FOUND: u64 = 106;

    struct OrderItemShipGroupAssociation has store {
        product_id: String,
        quantity: u64,
        cancel_quantity: u64,
        subitems: TableWithLength<Day, OrderItemShipGroupAssocSubitem>,
    }

    public fun product_id(order_item_ship_group_association: &OrderItemShipGroupAssociation): String {
        order_item_ship_group_association.product_id
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

    public(friend) fun add_subitem(order_item_ship_group_association: &mut OrderItemShipGroupAssociation, subitem: OrderItemShipGroupAssocSubitem) {
        let key = order_item_ship_group_assoc_subitem::order_item_ship_group_assoc_subitem_day(&subitem);
        assert!(!table_with_length::contains(&order_item_ship_group_association.subitems, key), EID_ALREADY_EXISTS);
        table_with_length::add(&mut order_item_ship_group_association.subitems, key, subitem);
    }

    public(friend) fun remove_subitem(order_item_ship_group_association: &mut OrderItemShipGroupAssociation, order_item_ship_group_assoc_subitem_day: Day) {
        assert!(table_with_length::contains(&order_item_ship_group_association.subitems, order_item_ship_group_assoc_subitem_day), EID_NOT_FOUND);
        let subitem = table_with_length::remove(&mut order_item_ship_group_association.subitems, order_item_ship_group_assoc_subitem_day);
        order_item_ship_group_assoc_subitem::drop_order_item_ship_group_assoc_subitem(subitem);
    }

    public(friend) fun borrow_mut_subitem(order_item_ship_group_association: &mut OrderItemShipGroupAssociation, order_item_ship_group_assoc_subitem_day: Day): &mut OrderItemShipGroupAssocSubitem {
        table_with_length::borrow_mut(&mut order_item_ship_group_association.subitems, order_item_ship_group_assoc_subitem_day)
    }

    public fun borrow_subitem(order_item_ship_group_association: &OrderItemShipGroupAssociation, order_item_ship_group_assoc_subitem_day: Day): &OrderItemShipGroupAssocSubitem {
        table_with_length::borrow(&order_item_ship_group_association.subitems, order_item_ship_group_assoc_subitem_day)
    }

    public fun subitems_contains(order_item_ship_group_association: &OrderItemShipGroupAssociation, order_item_ship_group_assoc_subitem_day: Day): bool {
        table_with_length::contains(&order_item_ship_group_association.subitems, order_item_ship_group_assoc_subitem_day)
    }

    public fun subitems_length(order_item_ship_group_association: &OrderItemShipGroupAssociation): u64 {
        table_with_length::length(&order_item_ship_group_association.subitems)
    }

    public(friend) fun new_order_item_ship_group_association(
        product_id: String,
        quantity: u64,
        cancel_quantity: u64,
    ): OrderItemShipGroupAssociation {
        assert!(std::string::length(&product_id) <= 100, EDATA_TOO_LONG);
        OrderItemShipGroupAssociation {
            product_id,
            quantity,
            cancel_quantity,
            subitems: table_with_length::new<Day, OrderItemShipGroupAssocSubitem>(),
        }
    }

    public(friend) fun drop_order_item_ship_group_association(order_item_ship_group_association: OrderItemShipGroupAssociation) {
        let OrderItemShipGroupAssociation {
            product_id: _,
            quantity: _,
            cancel_quantity: _,
            subitems,
        } = order_item_ship_group_association;
        table_with_length::destroy_empty<Day, OrderItemShipGroupAssocSubitem>(subitems);
    }


}
