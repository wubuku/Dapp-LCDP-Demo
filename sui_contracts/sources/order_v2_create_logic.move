module sui_contracts::order_v2_create_logic {
    use std::string::String;
    use sui::object::{Self, UID};
    use sui::tx_context::{Self, TxContext};
    use sui_contracts::order_v2;
    use sui_contracts::order_v2_item;
    use sui_contracts::product::{Self, Product};

    friend sui_contracts::order_v2_aggregate;

    public(friend) fun verify(
        order_id: String,
        product: &Product,
        quantity: u64,
        order_id_table: &order_v2::OrderIdTable,
        ctx: &mut TxContext,
    ): (order_v2::OrderV2Created, UID) {
        let id = object::new(ctx);
        order_v2::asset_order_id_not_exists(order_id, order_id_table);
        let e_quantity = quantity;
        let e_owner = tx_context::sender(ctx);
        let unit_price = product::unit_price(product);
        (
            order_v2::new_order_v2_created(
                &id,
                order_id,
                product::product_id(product),
                e_quantity,
                unit_price,
                unit_price * (e_quantity as u128),
                e_owner,
            ),
            id,
        )
    }

    public(friend) fun mutate(
        order_v2_created: &order_v2::OrderV2Created,
        id: UID,
        order_id_table: &mut order_v2::OrderIdTable,
        ctx: &mut TxContext,
    ): order_v2::OrderV2 {
        let _ = ctx;
        let order_v2 = order_v2::create_order_v2(
            id,
            order_v2::order_v2_created_order_id(order_v2_created),
            order_v2::order_v2_created_total_amount(order_v2_created),
            order_id_table,
            ctx,
        );

        let order_v2_item = order_v2_item::new_order_v2_item(
            order_v2::order_v2_created_product(order_v2_created),
            order_v2::order_v2_created_quantity(order_v2_created),
            order_v2::order_v2_created_unit_price(order_v2_created) * (order_v2::order_v2_created_quantity(
                order_v2_created
            ) as u128),
            //ctx,
        );
        order_v2::add_item(&mut order_v2, order_v2_item);
        order_v2
    }
}
