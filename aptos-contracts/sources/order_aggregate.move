// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

module aptos_demo::order_aggregate {
    use aptos_demo::day::{Self, Day};
    use aptos_demo::month;
    use aptos_demo::order;
    use aptos_demo::order_add_order_ship_group_logic;
    use aptos_demo::order_cancel_order_ship_group_quantity_logic;
    use aptos_demo::order_create_logic;
    use aptos_demo::order_remove_item_logic;
    use aptos_demo::order_remove_order_ship_group_item_logic;
    use aptos_demo::order_remove_order_ship_group_logic;
    use aptos_demo::order_update_estimated_ship_date_logic;
    use aptos_demo::order_update_item_quantity_logic;
    use aptos_demo::year;
    use std::string::String;

    public entry fun create(
        account: &signer,
        order_id: String,
        product_id: String,
        quantity: u64,
    ) {
        let order_created = order_create_logic::verify(
            account,
            order_id,
            product_id,
            quantity,
        );
        let order = order_create_logic::mutate(
            account,
            &order_created,
        );
        order::add_order(order);
        order::emit_order_created(order_created);
    }

    public entry fun remove_item(
        account: &signer,
        order_id: String,
        product_id: String,
    ) {
        let order = order::remove_order(order_id);
        let order_item_removed = order_remove_item_logic::verify(
            account,
            product_id,
            &order,
        );
        let updated_order = order_remove_item_logic::mutate(
            account,
            &order_item_removed,
            order,
        );
        order::update_version_and_add(updated_order);
        order::emit_order_item_removed(order_item_removed);
    }

    public entry fun update_item_quantity(
        account: &signer,
        order_id: String,
        product_id: String,
        quantity: u64,
    ) {
        let order = order::remove_order(order_id);
        let order_item_quantity_updated = order_update_item_quantity_logic::verify(
            account,
            product_id,
            quantity,
            &order,
        );
        let updated_order = order_update_item_quantity_logic::mutate(
            account,
            &order_item_quantity_updated,
            order,
        );
        order::update_version_and_add(updated_order);
        order::emit_order_item_quantity_updated(order_item_quantity_updated);
    }

    public entry fun update_estimated_ship_date(
        account: &signer,
        order_id: String,
        estimated_ship_date_month_year_number: u16,
        estimated_ship_date_month_year_calendar: String,
        estimated_ship_date_month_number: u8,
        estimated_ship_date_month_is_leap: bool,
        estimated_ship_date_number: u8,
        estimated_ship_date_time_zone: String,
    ) {
        let estimated_ship_date: Day = day::new(
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
        );
        let order = order::remove_order(order_id);
        let order_estimated_ship_date_updated = order_update_estimated_ship_date_logic::verify(
            account,
            estimated_ship_date,
            &order,
        );
        let updated_order = order_update_estimated_ship_date_logic::mutate(
            account,
            &order_estimated_ship_date_updated,
            order,
        );
        order::update_version_and_add(updated_order);
        order::emit_order_estimated_ship_date_updated(order_estimated_ship_date_updated);
    }

    public entry fun add_order_ship_group(
        account: &signer,
        order_id: String,
        ship_group_seq_id: u8,
        shipment_method: String,
        product_id: String,
        quantity: u64,
    ) {
        let order = order::remove_order(order_id);
        let order_ship_group_added = order_add_order_ship_group_logic::verify(
            account,
            ship_group_seq_id,
            shipment_method,
            product_id,
            quantity,
            &order,
        );
        let updated_order = order_add_order_ship_group_logic::mutate(
            account,
            &order_ship_group_added,
            order,
        );
        order::update_version_and_add(updated_order);
        order::emit_order_ship_group_added(order_ship_group_added);
    }

    public entry fun cancel_order_ship_group_quantity(
        account: &signer,
        order_id: String,
        ship_group_seq_id: u8,
        product_id: String,
        cancel_quantity: u64,
    ) {
        let order = order::remove_order(order_id);
        let order_ship_group_quantity_canceled = order_cancel_order_ship_group_quantity_logic::verify(
            account,
            ship_group_seq_id,
            product_id,
            cancel_quantity,
            &order,
        );
        let updated_order = order_cancel_order_ship_group_quantity_logic::mutate(
            account,
            &order_ship_group_quantity_canceled,
            order,
        );
        order::update_version_and_add(updated_order);
        order::emit_order_ship_group_quantity_canceled(order_ship_group_quantity_canceled);
    }

    public entry fun remove_order_ship_group_item(
        account: &signer,
        order_id: String,
        ship_group_seq_id: u8,
        product_id: String,
    ) {
        let order = order::remove_order(order_id);
        let order_ship_group_item_removed = order_remove_order_ship_group_item_logic::verify(
            account,
            ship_group_seq_id,
            product_id,
            &order,
        );
        let updated_order = order_remove_order_ship_group_item_logic::mutate(
            account,
            &order_ship_group_item_removed,
            order,
        );
        order::update_version_and_add(updated_order);
        order::emit_order_ship_group_item_removed(order_ship_group_item_removed);
    }

    public entry fun remove_order_ship_group(
        account: &signer,
        order_id: String,
        ship_group_seq_id: u8,
    ) {
        let order = order::remove_order(order_id);
        let order_ship_group_removed = order_remove_order_ship_group_logic::verify(
            account,
            ship_group_seq_id,
            &order,
        );
        let updated_order = order_remove_order_ship_group_logic::mutate(
            account,
            &order_ship_group_removed,
            order,
        );
        order::update_version_and_add(updated_order);
        order::emit_order_ship_group_removed(order_ship_group_removed);
    }

}
