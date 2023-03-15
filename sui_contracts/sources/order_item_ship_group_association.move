module sui_contracts::order_item_ship_group_association {
    use std::string::String;
    use sui::table;
    use sui::tx_context::TxContext;
    use sui_contracts::order_item_ship_group_assoc_subitem::{Self, OrderItemShipGroupAssocSubitem};
    friend sui_contracts::order_v2_create_logic;
    friend sui_contracts::order_v2_remove_item_logic;
    friend sui_contracts::order_v2_update_item_quantity_logic;
    friend sui_contracts::order_v2_update_estimated_ship_date_logic;
    friend sui_contracts::order_v2_add_order_ship_group_logic;
    friend sui_contracts::order_v2_cancel_order_ship_group_quantity_logic;
    friend sui_contracts::order_v2_remove_order_ship_group_item_logic;
    friend sui_contracts::order_v2_remove_order_ship_group_logic;
    friend sui_contracts::order_ship_group;

    const EID_ALREADY_EXISTS: u64 = 101;

    struct OrderItemShipGroupAssociation has store {
        product_id: String,
        quantity: u64,
        cancel_quantity: u64,
        subitems: table::Table<u8, OrderItemShipGroupAssocSubitem>,
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
        let key = order_item_ship_group_assoc_subitem::order_item_ship_group_assoc_subitem_seq_id(&subitem);
        table::add(&mut order_item_ship_group_association.subitems, key, subitem);
    }

    public(friend) fun remove_subitem(order_item_ship_group_association: &mut OrderItemShipGroupAssociation, order_item_ship_group_assoc_subitem_seq_id: u8) {
        let subitem = table::remove(&mut order_item_ship_group_association.subitems, order_item_ship_group_assoc_subitem_seq_id);
        order_item_ship_group_assoc_subitem::drop_order_item_ship_group_assoc_subitem(subitem);
    }

    public(friend) fun borrow_mut_subitem(order_item_ship_group_association: &mut OrderItemShipGroupAssociation, order_item_ship_group_assoc_subitem_seq_id: u8): &mut OrderItemShipGroupAssocSubitem {
        table::borrow_mut(&mut order_item_ship_group_association.subitems, order_item_ship_group_assoc_subitem_seq_id)
    }

    public fun borrow_subitem(order_item_ship_group_association: &OrderItemShipGroupAssociation, order_item_ship_group_assoc_subitem_seq_id: u8): &OrderItemShipGroupAssocSubitem {
        table::borrow(&order_item_ship_group_association.subitems, order_item_ship_group_assoc_subitem_seq_id)
    }

    public fun subitems_contains(order_item_ship_group_association: &OrderItemShipGroupAssociation, order_item_ship_group_assoc_subitem_seq_id: u8): bool {
        table::contains(&order_item_ship_group_association.subitems, order_item_ship_group_assoc_subitem_seq_id)
    }

    public fun subitems_length(order_item_ship_group_association: &OrderItemShipGroupAssociation): u64 {
        table::length(&order_item_ship_group_association.subitems)
    }

    public(friend) fun new_order_item_ship_group_association(
        product_id: String,
        quantity: u64,
        cancel_quantity: u64,
        ctx: &mut TxContext,
    ): OrderItemShipGroupAssociation {
        OrderItemShipGroupAssociation {
            product_id,
            quantity,
            cancel_quantity,
            subitems: table::new<u8, OrderItemShipGroupAssocSubitem>(ctx),
        }
    }

    public(friend) fun drop_order_item_ship_group_association(order_item_ship_group_association: OrderItemShipGroupAssociation) {
        let OrderItemShipGroupAssociation {
            product_id: _,
            quantity: _,
            cancel_quantity: _,
            subitems,
        } = order_item_ship_group_association;
        table::drop<u8, OrderItemShipGroupAssocSubitem>(subitems);
    }


}
