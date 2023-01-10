#[test_only]
module sui_contracts::product_aggregate_tests {
    use std::string;

    use sui::test_scenario as ts;
    use sui_contracts::product::{Self, ProductIdGenerator, Product};
    use sui_contracts::product_aggregate;
    use std::debug;

    #[test]
    //#[expected_failure(abort_code = sui_contracts::domain_name::EID_ALREADY_EXISTS)]
    fun test_create_new_product() {
        let addr1 = @0xA;
        //let addr2 = @0xB;
        let scenario = ts::begin(addr1);
        {
            product::test_init(ts::ctx(&mut scenario));
        };

        ts::next_tx(&mut scenario, addr1);
        {
            let product_id_generator = ts::take_shared<ProductIdGenerator>(&scenario);
            product_aggregate::create(
                string::utf8(b"product1"),
                100,
                &mut product_id_generator,
                ts::ctx(&mut scenario),
            );
            ts::return_shared(product_id_generator);
        };

        ts::next_tx(&mut scenario, addr1);
        {
            let product = ts::take_immutable<Product>(&scenario);
            assert!(product::name(&product) == string::utf8(b"product1"), 1);
            debug::print(&product::product_id(&product));

            ts::return_immutable(product);
        };
        ts::end(scenario);
    }
}