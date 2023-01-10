module sui_contracts::order_aggregate {
    use std::string::String;

    use sui::tx_context::{Self, TxContext};
    use sui_contracts::order;
    use sui_contracts::order_create_logic;
    use sui_contracts::order_remove_item_logic;
    use sui_contracts::product::Product;
    use sui_contracts::order_update_item_quantity_logic;

    public entry fun create(
        product: &mut Product,
        quantity: u64,
        ctx: &mut TxContext,
    ) {
        let (order_created, id) = order_create_logic::verify(
            product,
            quantity,
            ctx,
        );
        let order_ = order_create_logic::mutate(
            &order_created,
            id,
            ctx,
        );
        order::transfer_object(
            order_,
            order::order_created_owner(&order_created)
        );
        order::emit_order_created(order_created);
    }

    public entry fun remove_item(
        order: order::Order,
        product_id: String,
        ctx: &mut TxContext,
    ) {
        let order_item_removed = order_remove_item_logic::verify(
            product_id,
            &order,
            ctx,
        );
        let updated_order = order_remove_item_logic::mutate(
            &order_item_removed,
            order,
            ctx,
        );
        order::update_version_and_transfer_object(
            updated_order,
            tx_context::sender(ctx), //the owner of order is NOT indicated in event
        );
        order::emit_order_item_removed(order_item_removed);
    }

    public entry fun update_item_quantity(
        order: order::Order,
        product_id: String,
        quantity: u64,
        ctx: &mut TxContext,
    ) {
        let order_item_quantity_updated = order_update_item_quantity_logic::verify(
            product_id,
            quantity,
            &order,
            ctx,
        );
        let updated_order = order_update_item_quantity_logic::mutate(
            &order_item_quantity_updated,
            order,
            ctx,
        );
        order::update_version_and_transfer_object(
            updated_order,
            tx_context::sender(ctx), //the owner of order is NOT indicated in event
        );
        order::emit_order_item_quantity_updated(order_item_quantity_updated);
    }
}
