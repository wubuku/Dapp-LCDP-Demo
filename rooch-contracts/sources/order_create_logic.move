module rooch_demo::order_create_logic {
    use std::option;
    use std::signer;
    use std::string::String;

    use moveos_std::object::{Object};
    use moveos_std::object_id::ObjectID;
    use moveos_std::storage_context::StorageContext;
    use rooch_demo::order;
    use rooch_demo::order_created;
    use rooch_demo::order_item;
    use rooch_demo::product;
    use std::vector;

    friend rooch_demo::order_aggregate;

    public(friend) fun verify(
        storage_ctx: &mut StorageContext,
        account: &signer,
        order_id: String,
        product_obj_id: ObjectID,
        quantity: u64,
    ): order::OrderCreated {
        let e_quantity = quantity;
        let e_owner = signer::address_of(account);
        let product_pass_obj = product::get_product(storage_ctx, product_obj_id);
        let unit_price = product::unit_price(&product_pass_obj);
        product::return_product(storage_ctx, product_pass_obj);
        order::new_order_created(
            order_id,
            product_obj_id, //product::product_id(product),
            e_quantity,
            unit_price,
            unit_price * (e_quantity as u128),
            e_owner,
        )
    }

    public(friend) fun mutate(
        storage_ctx: &mut StorageContext,
        _account: &signer,
        order_created: &order::OrderCreated,
    ): Object<order::Order> {
        let order = order::create_order(
            storage_ctx,
            order_created::order_id(order_created),
            order_created::total_amount(order_created),
            option::none(),
            vector::empty(),
            option::none(),
        );
        let order_item = order_item::new_order_item(
            order_created::product_obj_id(order_created),
            order_created::quantity(order_created),
            order_created::unit_price(order_created) * (order_created::quantity(order_created) as u128),
        );
        order::add_item(storage_ctx,&mut order, order_item);
        order
    }
}
