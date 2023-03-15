module sui_contracts::order_item_ship_group_assoc_subitem {
    use std::string::String;
    friend sui_contracts::order_v2_create_logic;
    friend sui_contracts::order_v2_remove_item_logic;
    friend sui_contracts::order_v2_update_item_quantity_logic;
    friend sui_contracts::order_v2_update_estimated_ship_date_logic;
    friend sui_contracts::order_v2_add_order_ship_group_logic;
    friend sui_contracts::order_v2_cancel_order_ship_group_quantity_logic;
    friend sui_contracts::order_v2_remove_order_ship_group_item_logic;
    friend sui_contracts::order_v2_remove_order_ship_group_logic;
    friend sui_contracts::order_item_ship_group_association;

    const EID_ALREADY_EXISTS: u64 = 101;

    struct OrderItemShipGroupAssocSubitem has store, drop {
        order_item_ship_group_assoc_subitem_seq_id: u8,
        description: String,
    }

    public fun order_item_ship_group_assoc_subitem_seq_id(order_item_ship_group_assoc_subitem: &OrderItemShipGroupAssocSubitem): u8 {
        order_item_ship_group_assoc_subitem.order_item_ship_group_assoc_subitem_seq_id
    }

    public fun description(order_item_ship_group_assoc_subitem: &OrderItemShipGroupAssocSubitem): String {
        order_item_ship_group_assoc_subitem.description
    }

    public(friend) fun set_description(order_item_ship_group_assoc_subitem: &mut OrderItemShipGroupAssocSubitem, description: String) {
        order_item_ship_group_assoc_subitem.description = description;
    }

    public(friend) fun new_order_item_ship_group_assoc_subitem(
        order_item_ship_group_assoc_subitem_seq_id: u8,
        description: String,
    ): OrderItemShipGroupAssocSubitem {
        OrderItemShipGroupAssocSubitem {
            order_item_ship_group_assoc_subitem_seq_id,
            description,
        }
    }

    public(friend) fun drop_order_item_ship_group_assoc_subitem(order_item_ship_group_assoc_subitem: OrderItemShipGroupAssocSubitem) {
        let OrderItemShipGroupAssocSubitem {
            order_item_ship_group_assoc_subitem_seq_id: _,
            description: _,
        } = order_item_ship_group_assoc_subitem;
    }


}
