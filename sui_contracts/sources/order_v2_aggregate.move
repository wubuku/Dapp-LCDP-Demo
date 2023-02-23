module sui_contracts::order_v2_aggregate {
    use std::string::String;
    use sui::tx_context;
    use sui_contracts::order_v2;
    use sui_contracts::order_v2_create_logic;
    use sui_contracts::order_v2_remove_item_logic;
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

}
