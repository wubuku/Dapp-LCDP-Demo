#[test_only]
module sui_contracts::domain_name_aggregate_tests {
    use sui::test_scenario as ts;
    use sui_contracts::domain_name::{Self, DomainNameIdTable, DomainName};
    use sui_contracts::domain_name_aggregate;
    use std::string;


    #[test]
    //#[expected_failure(abort_code = sui_contracts::domain_name::EID_ALREADY_EXISTS)]
    fun test_register_and_renew() {
        let addr1 = @0xA;
        //let addr2 = @0xB;
        let scenario = ts::begin(addr1);
        {
            domain_name::test_init(ts::ctx(&mut scenario));
        };

        ts::next_tx(&mut scenario, addr1);
        {
            let domain_name_id_table = ts::take_shared<DomainNameIdTable>(&scenario);
            domain_name_aggregate::register(
                string::utf8( b"sui"),
                string::utf8(b"test"),
                1000000000,
                &mut domain_name_id_table,
                ts::ctx(&mut scenario),
            );
            ts::return_shared(domain_name_id_table);
        };

        ts::next_tx(&mut scenario, addr1);
        let domain_name_1 = ts::take_from_address<DomainName>(&scenario, addr1);
        assert!(domain_name::domain_name_id(&domain_name_1)
            == domain_name::new_domain_name_id(string::utf8(b"sui"), string::utf8(b"test")),
            0);
        domain_name_aggregate::renew(
            domain_name_1,
            1000000000,
            ts::ctx(&mut scenario),
        );

        ts::next_tx(&mut scenario, addr1);
        let domain_name_1 = ts::take_from_address<DomainName>(&scenario, addr1);
        assert!(domain_name::version(&domain_name_1) == 1, 0);
        ts::return_to_address(addr1, domain_name_1);
        // // ////////// register again //////////
        // ts::next_tx(&mut scenario, addr1);
        // {
        //     let domain_name_id_table = ts::take_shared<DomainNameIdTable>(&scenario);
        //     domain_name_aggregate::register(
        //         b"sui",
        //         b"test",
        //         1000000000,
        //         &mut domain_name_id_table,
        //         ts::ctx(&mut scenario),
        //     );
        //     ts::return_shared(domain_name_id_table);
        // };
        // // ///////////////////////////////////////

        ts::end(scenario);
    }
}