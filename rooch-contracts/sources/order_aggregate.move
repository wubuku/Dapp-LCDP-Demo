// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

module rooch_demo::order_aggregate {
    use moveos_std::object::ObjectID;
    use moveos_std::storage_context::StorageContext;
    use rooch_demo::order;
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

}
