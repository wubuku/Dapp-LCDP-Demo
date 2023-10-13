module sui_demo_contracts::order_v2_create_logic {
    use std::option;
    use std::string::String;

    use sui::tx_context::{Self, TxContext};
    use sui_demo_contracts::order_v2;
    use sui_demo_contracts::order_v2_created;
    use sui_demo_contracts::order_v2_item;
    use sui_demo_contracts::product::{Self, Product};

    friend sui_demo_contracts::order_v2_aggregate;

    public(friend) fun verify(
        order_id: String,
        product: &Product,
        quantity: u64,
        order_id_table: &order_v2::OrderIdTable,
        ctx: &mut TxContext,
    ): order_v2::OrderV2Created {
        order_v2::asset_order_id_not_exists(order_id, order_id_table);
        let e_quantity = quantity;
        let e_owner = tx_context::sender(ctx);
        let unit_price = product::unit_price(product);

        order_v2::new_order_v2_created(
            order_id,
            product::product_id(product),
            e_quantity,
            unit_price,
            unit_price * (e_quantity as u128),
            e_owner,
        )
    }

    public(friend) fun mutate(
        order_v2_created: &order_v2::OrderV2Created,
        order_id_table: &mut order_v2::OrderIdTable,
        ctx: &mut TxContext,
    ): order_v2::OrderV2 {
        let order_v2 = order_v2::create_order_v2(
            order_v2_created::order_id(order_v2_created),
            order_v2_created::total_amount(order_v2_created),
            option::none(),
            order_id_table,
            ctx,
        );

        let order_v2_item = order_v2_item::new_order_v2_item(
            order_v2_created::product(order_v2_created),
            order_v2_created::quantity(order_v2_created),
            order_v2_created::unit_price(order_v2_created) * (order_v2_created::quantity(
                order_v2_created
            ) as u128),
            //ctx,
        );
        order_v2::add_item(&mut order_v2, order_v2_item);
        order_v2
    }
}
