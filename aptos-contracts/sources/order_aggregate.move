// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

module aptos_demo::order_aggregate {
    use std::string::String;

    use aptos_demo::order;
    use aptos_demo::order_create_logic;
    use aptos_demo::order_remove_item_logic;
    use aptos_demo::order_update_item_quantity_logic;

    //use sui::tx_context;
    public entry fun create(
        account: &signer,
        order_id: String,
        product_id: String, //&Product,
        quantity: u64,
        //ctx: &mut tx_context::TxContext,
    ) {
        let order_created = order_create_logic::verify(
            account,
            order_id,
            product_id,
            quantity,
            //ctx,
        );
        let order = order_create_logic::mutate(
            &order_created,
            //ctx,
        );
        order::add_order(order);
        //order::set_order_created_id(&mut order_created, order::id(&order));
        //order::transfer_object(order, order::order_created_owner(&order_created));
        order::emit_order_created(order_created);
    }


    public entry fun remove_item(
        account: &signer,
        order_id: String,
        product_id: String,
        //ctx: &mut tx_context::TxContext,
    ) {
        let order = order::remove_order(order_id);
        let order_item_removed = order_remove_item_logic::verify(
            account,
            product_id,
            &order,
            //ctx,
        );
        let updated_order = order_remove_item_logic::mutate(
            &order_item_removed,
            order,
            //ctx,
        );
        order::update_version_and_save(updated_order);
        order::emit_order_item_removed(order_item_removed);
    }


    public entry fun update_item_quantity(
        account: &signer,
        order_id: String,
        product_id: String,
        quantity: u64,
        //ctx: &mut tx_context::TxContext,
    ) {
        let order = order::remove_order(order_id);
        let order_item_quantity_updated = order_update_item_quantity_logic::verify(
            account,
            product_id,
            quantity,
            &order,
            //ctx,
        );
        let updated_order = order_update_item_quantity_logic::mutate(
            &order_item_quantity_updated,
            order,
            //ctx,
        );
        order::update_version_and_save(updated_order);
        order::emit_order_item_quantity_updated(order_item_quantity_updated);
    }
}
