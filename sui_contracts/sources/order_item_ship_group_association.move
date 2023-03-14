module sui_contracts::order_item_ship_group_association {
    use std::string::String;
    friend sui_contracts::order_v2_create_logic;
    friend sui_contracts::order_v2_remove_item_logic;
    friend sui_contracts::order_v2_update_item_quantity_logic;
    friend sui_contracts::order_v2_update_estimated_ship_date_logic;
    friend sui_contracts::order_v2_add_order_ship_group_logic;
    friend sui_contracts::order_ship_group;

    const EID_ALREADY_EXISTS: u64 = 101;

    struct OrderItemShipGroupAssociation has store, drop {
        product_id: String,
        quantity: u64,
        cancel_quantity: u64,
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

    public(friend) fun new_order_item_ship_group_association(
        product_id: String,
        quantity: u64,
        cancel_quantity: u64,
    ): OrderItemShipGroupAssociation {
        OrderItemShipGroupAssociation {
            product_id,
            quantity,
            cancel_quantity,
        }
    }

    public(friend) fun drop_order_item_ship_group_association(order_item_ship_group_association: OrderItemShipGroupAssociation) {
        let OrderItemShipGroupAssociation {
            product_id: _,
            quantity: _,
            cancel_quantity: _,
        } = order_item_ship_group_association;
    }


}
