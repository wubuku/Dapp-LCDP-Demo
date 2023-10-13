module sui_demo_contracts::order_v2_update_estimated_ship_date_logic {
    use sui::tx_context::{TxContext};
    use sui_demo_contracts::day::Day;
    use sui_demo_contracts::order_v2;
    //use sui_demo_contracts::order_v2_item::{Self, OrderV2Item};
    use std::option;
    use sui_demo_contracts::order_v2_estimated_ship_date_updated;

    friend sui_demo_contracts::order_v2_aggregate;

    public(friend) fun verify(
        estimated_ship_date: Day,
        order_v2: &order_v2::OrderV2,
        ctx: &TxContext,
    ): order_v2::OrderV2EstimatedShipDateUpdated {
        let _ = ctx;
        order_v2::new_order_v2_estimated_ship_date_updated(
            order_v2,
            estimated_ship_date,
        )
    }

    public(friend) fun mutate(
        order_v2_estimated_ship_date_updated: &order_v2::OrderV2EstimatedShipDateUpdated,
        order_v2: order_v2::OrderV2,
        ctx: &TxContext,
    ): order_v2::OrderV2 {
        let _ = ctx;
        order_v2::set_estimated_ship_date(
            &mut order_v2,
            option::some(order_v2_estimated_ship_date_updated::estimated_ship_date(order_v2_estimated_ship_date_updated)),
        );
        order_v2
    }

}
