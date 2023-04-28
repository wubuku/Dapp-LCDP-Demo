module aptos_demo::order_create_logic {
    use std::signer;
    use std::string::String;

    use aptos_demo::order;
    use aptos_demo::order_created;
    use aptos_demo::order_item;
    use aptos_demo::product;
    use aptos_demo::pass_object;
    use std::option;
    //use aptos_demo::product;

    friend aptos_demo::order_aggregate;

    public(friend) fun verify(
        account: &signer,
        order_id: String,
        product_id: String, //product: &Product,
        quantity: u64,
        //ctx: &mut TxContext,
    ): order::OrderCreated {
        //let id = object::new(ctx);
        let e_quantity = quantity;
        let e_owner = signer::address_of(account);//tx_context::sender(ctx);
        //let unit_price = product::get_unit_price_by_product_id(product_id);
        let product_pass_obj = product::get_product(product_id);
        let product = pass_object::borrow(&product_pass_obj);
        let unit_price = product::unit_price(product);
        product::return_product(product_pass_obj);
        //(
        order::new_order_created(
            order_id,
            product_id, //product::product_id(product),
            e_quantity,
            unit_price,
            unit_price * (e_quantity as u128),
            e_owner,
        )//,
        //id,
        //)
    }

    public(friend) fun mutate(
        order_created: &order::OrderCreated,
        //ctx: &mut TxContext,
    ): order::Order {
        let order = order::create_order(
            order_created::order_id(order_created),
            order_created::total_amount(order_created),
            option::none(),
        );
        let order_item = order_item::new_order_item(
            order_created::product_id(order_created),
            order_created::quantity(order_created),
            order_created::unit_price(order_created) * (order_created::quantity(order_created) as u128),
            //ctx,
        );
        order::add_item(&mut order, order_item);
        order
    }
}
