// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

module rooch_demo::order_aggregate {
    use moveos_std::object::ObjectID;
    use moveos_std::storage_context::StorageContext;
    use rooch_demo::order;
    use rooch_demo::order_add_order_ship_group_logic;
    use rooch_demo::order_cancel_order_ship_group_quantity_logic;
    use rooch_demo::order_create_logic;
    use std::string::String;

    public entry fun create(
        storage_ctx: &mut StorageContext,
        account: &signer,
        order_id: String,
        product_obj_id: ObjectID,
        quantity: u64,
    ) {
        let order_created = order_create_logic::verify(
            storage_ctx,
            account,
            order_id,
            product_obj_id,
            quantity,
        );
        let order_obj = order_create_logic::mutate(
            storage_ctx,
            &order_created,
        );
        order::set_order_created_id(&mut order_created, order::id(&order_obj));
        order::add_order(storage_ctx, order_obj);
    }


    public entry fun add_order_ship_group(
        storage_ctx: &mut StorageContext,
        account: &signer,
        id: ObjectID,
        ship_group_seq_id: u8,
        shipment_method: String,
        product_obj_id: ObjectID,
        quantity: u64,
    ) {
        let order_obj = order::remove_order(storage_ctx, id);
        let order_ship_group_added = order_add_order_ship_group_logic::verify(
            storage_ctx,
            account,
            ship_group_seq_id,
            shipment_method,
            product_obj_id,
            quantity,
            &order_obj,
        );
        let updated_order_obj = order_add_order_ship_group_logic::mutate(
            storage_ctx,
            &order_ship_group_added,
            order_obj,
        );
        order::update_version_and_add(storage_ctx, updated_order_obj);
    }


    public entry fun cancel_order_ship_group_quantity(
        storage_ctx: &mut StorageContext,
        account: &signer,
        id: ObjectID,
        ship_group_seq_id: u8,
        product_obj_id: ObjectID,
        cancel_quantity: u64,
    ) {
        let order_obj = order::remove_order(storage_ctx, id);
        let order_ship_group_quantity_canceled = order_cancel_order_ship_group_quantity_logic::verify(
            storage_ctx,
            account,
            ship_group_seq_id,
            product_obj_id,
            cancel_quantity,
            &order_obj,
        );
        let updated_order_obj = order_cancel_order_ship_group_quantity_logic::mutate(
            storage_ctx,
            &order_ship_group_quantity_canceled,
            order_obj,
        );
        order::update_version_and_add(storage_ctx, updated_order_obj);
    }

}
