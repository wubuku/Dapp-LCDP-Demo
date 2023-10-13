#[test_only]
module sui_demo_contracts::order_aggregate_tests {
    use std::string;

    use sui::test_scenario as ts;
    use sui_demo_contracts::order;
    use sui_demo_contracts::order_aggregate;
    use sui_demo_contracts::order_item;
    use sui_demo_contracts::product::{Self, ProductIdGenerator, Product};
    use sui_demo_contracts::product_aggregate;

    #[test]
    //#[expected_failure(abort_code = sui_demo_contracts::domain_name::EID_ALREADY_EXISTS)]
    fun test_create_new_order_update_item_remove_item() {
        let addr1 = @0xA;
        //let addr2 = @0xB;
        let scenario = ts::begin(addr1);
        {
            product::test_init(ts::ctx(&mut scenario));
        };

        let product_name = string::utf8(b"product1");

        ts::next_tx(&mut scenario, addr1);
        {
            let product_id_generator = ts::take_shared<ProductIdGenerator>(&scenario);
            product_aggregate::create(
                product_name,
                100,
                &mut product_id_generator,
                ts::ctx(&mut scenario),
            );
            ts::return_shared(product_id_generator);
        };

        ts::next_tx(&mut scenario, addr1);
        {
            let product = ts::take_immutable<Product>(&scenario);
            assert!(product::name(&product) == product_name, 1);
            //debug::print(&product::product_id(&product));
            order_aggregate::create(&mut product, 1, ts::ctx(&mut scenario));
            ts::return_immutable(product);
        };
        // -----------------

        ts::next_tx(&mut scenario, addr1);
        {
            let product = ts::take_immutable<Product>(&scenario);
            let product_id = product::product_id(&product);
            ts::return_immutable(product);

            let order = ts::take_from_sender(&scenario);
            let order_item = order::borrow_item(&order, product_id);
            assert!(order_item::quantity(order_item) == 1, 2);
            ts::return_to_sender(&scenario, order);
        };

        ts::next_tx(&mut scenario, addr1);
        {
            let product = ts::take_immutable<Product>(&scenario);
            let product_id = product::product_id(&product);
            ts::return_immutable(product);

            let order = ts::take_from_sender(&scenario);
            order_aggregate::update_item_quantity(
                order,
                product_id,
                2,
                ts::ctx(&mut scenario),
            );
        };

        ts::next_tx(&mut scenario, addr1);
        {
            let product = ts::take_immutable<Product>(&scenario);
            let product_id = product::product_id(&product);
            ts::return_immutable(product);

            let order = ts::take_from_sender(&scenario);
            let order_item = order::borrow_item(
                &order,
                product_id,
            );
            assert!(order_item::quantity(order_item) == 2, 2);
            ts::return_to_sender(&scenario, order);
        };

        ts::next_tx(&mut scenario, addr1);
        {
            let product = ts::take_immutable<Product>(&scenario);
            let product_id = product::product_id(&product);
            ts::return_immutable(product);

            let order = ts::take_from_sender(&scenario);
            order_aggregate::remove_item(
                order,
                product_id,
                ts::ctx(&mut scenario),
            );
        };

        ts::next_tx(&mut scenario, addr1);
        {
            let product = ts::take_immutable<Product>(&scenario);
            let product_id = product::product_id(&product);
            ts::return_immutable(product);

            let order = ts::take_from_sender(&scenario);
            let total_amount = order::total_amount(&order);
            assert!(total_amount == 0, 3);

            assert!(order::items_contains(&order, product_id) == false, 4);
            assert!(order::items_length(&order) == 0, 5);
            ts::return_to_sender(&scenario, order);
        };

        // -----------------
        ts::end(scenario);
    }
}
