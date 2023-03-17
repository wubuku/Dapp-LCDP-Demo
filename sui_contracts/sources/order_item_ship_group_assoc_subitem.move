module sui_contracts::order_item_ship_group_assoc_subitem {
    use std::string::String;
    use sui_contracts::day::Day;
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
    const EID_DATA_TOO_LONG: u64 = 102;

    struct OrderItemShipGroupAssocSubitem has store, drop {
        order_item_ship_group_assoc_subitem_day: Day,
        description: String,
    }

    public fun order_item_ship_group_assoc_subitem_day(order_item_ship_group_assoc_subitem: &OrderItemShipGroupAssocSubitem): Day {
        order_item_ship_group_assoc_subitem.order_item_ship_group_assoc_subitem_day
    }

    public fun description(order_item_ship_group_assoc_subitem: &OrderItemShipGroupAssocSubitem): String {
        order_item_ship_group_assoc_subitem.description
    }

    public(friend) fun set_description(order_item_ship_group_assoc_subitem: &mut OrderItemShipGroupAssocSubitem, description: String) {
        order_item_ship_group_assoc_subitem.description = description;
    }

    public(friend) fun new_order_item_ship_group_assoc_subitem(
        order_item_ship_group_assoc_subitem_day: Day,
        description: String,
    ): OrderItemShipGroupAssocSubitem {
        assert!(std::string::length(&description) <= 100, EID_DATA_TOO_LONG);
        OrderItemShipGroupAssocSubitem {
            order_item_ship_group_assoc_subitem_day,
            description,
        }
    }

    public(friend) fun drop_order_item_ship_group_assoc_subitem(order_item_ship_group_assoc_subitem: OrderItemShipGroupAssocSubitem) {
        let OrderItemShipGroupAssocSubitem {
            order_item_ship_group_assoc_subitem_day: _,
            description: _,
        } = order_item_ship_group_assoc_subitem;
    }


}
