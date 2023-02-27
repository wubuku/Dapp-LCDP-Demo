module sui_contracts::order_v2_aggregate {
    use std::string::String;
    use sui::tx_context;
    use sui_contracts::day;
    use sui_contracts::month;
    use sui_contracts::year;
    use sui_contracts::order_v2;
    use sui_contracts::order_v2_create_logic;
    use sui_contracts::order_v2_remove_item_logic;
    use sui_contracts::order_v2_update_estimated_ship_date_logic;
    use sui_contracts::order_v2_update_item_quantity_logic;
    use sui_contracts::product::Product;

    public entry fun create(
        order_id: String,
        product: &Product,
        quantity: u64,
        order_id_table: &mut order_v2::OrderIdTable,
        ctx: &mut tx_context::TxContext,
    ) {
        let (order_v2_created, id) = order_v2_create_logic::verify(
            order_id,
            product,
            quantity,
            order_id_table,
            ctx,
        );
        let order_v2 = order_v2_create_logic::mutate(
            &order_v2_created,
            id,
            order_id_table,
            ctx,
        );
        order_v2::transfer_object(order_v2, order_v2::order_v2_created_owner(&order_v2_created));
        order_v2::emit_order_v2_created(order_v2_created);
    }


    public entry fun remove_item(
        order_v2: order_v2::OrderV2,
        product_id: String,
        ctx: &mut tx_context::TxContext,
    ) {
        let order_v2_item_removed = order_v2_remove_item_logic::verify(
            product_id,
            &order_v2,
            ctx,
        );
        let updated_order_v2 = order_v2_remove_item_logic::mutate(
            &order_v2_item_removed,
            order_v2,
            ctx,
        );
        order_v2::update_version_and_transfer_object(updated_order_v2, tx_context::sender(ctx));
        order_v2::emit_order_v2_item_removed(order_v2_item_removed);
    }


    public entry fun update_item_quantity(
        order_v2: order_v2::OrderV2,
        product_id: String,
        quantity: u64,
        ctx: &mut tx_context::TxContext,
    ) {
        let order_v2_item_quantity_updated = order_v2_update_item_quantity_logic::verify(
            product_id,
            quantity,
            &order_v2,
            ctx,
        );
        let updated_order_v2 = order_v2_update_item_quantity_logic::mutate(
            &order_v2_item_quantity_updated,
            order_v2,
            ctx,
        );
        order_v2::update_version_and_transfer_object(updated_order_v2, tx_context::sender(ctx));
        order_v2::emit_order_v2_item_quantity_updated(order_v2_item_quantity_updated);
    }


    public entry fun update_estimated_ship_date(
        order_v2: order_v2::OrderV2,
        estimated_ship_date_month_year_number: u16,
        estimated_ship_date_month_year_calendar: String,
        estimated_ship_date_month_number: u8,
        estimated_ship_date_month_is_leap: bool,
        estimated_ship_date_number: u8,
        estimated_ship_date_time_zone: String,
        ctx: &mut tx_context::TxContext,
    ) {
        let order_v2_estimated_ship_date_updated = order_v2_update_estimated_ship_date_logic::verify(
            day::new(
                month::new(
                    year::new(
                        estimated_ship_date_month_year_number,
                        estimated_ship_date_month_year_calendar,
                    ),
                    estimated_ship_date_month_number,
                    estimated_ship_date_month_is_leap,
                ),
                estimated_ship_date_number,
                estimated_ship_date_time_zone,
            ),
            &order_v2,
            ctx,
        );
        let updated_order_v2 = order_v2_update_estimated_ship_date_logic::mutate(
            &order_v2_estimated_ship_date_updated,
            order_v2,
            ctx,
        );
        order_v2::update_version_and_transfer_object(updated_order_v2, tx_context::sender(ctx));
        order_v2::emit_order_v2_estimated_ship_date_updated(order_v2_estimated_ship_date_updated);
    }

}
