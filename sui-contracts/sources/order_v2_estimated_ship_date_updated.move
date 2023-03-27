module sui_contracts::order_v2_estimated_ship_date_updated {

    use std::string::String;
    use sui::object;
    use sui_contracts::day::Day;
    use sui_contracts::order_v2::{Self, OrderV2EstimatedShipDateUpdated};

    public fun id(order_v2_estimated_ship_date_updated: &OrderV2EstimatedShipDateUpdated): object::ID {
        order_v2::order_v2_estimated_ship_date_updated_id(order_v2_estimated_ship_date_updated)
    }

    public fun order_id(order_v2_estimated_ship_date_updated: &OrderV2EstimatedShipDateUpdated): String {
        order_v2::order_v2_estimated_ship_date_updated_order_id(order_v2_estimated_ship_date_updated)
    }

    public fun estimated_ship_date(order_v2_estimated_ship_date_updated: &OrderV2EstimatedShipDateUpdated): Day {
        order_v2::order_v2_estimated_ship_date_updated_estimated_ship_date(order_v2_estimated_ship_date_updated)
    }

}
