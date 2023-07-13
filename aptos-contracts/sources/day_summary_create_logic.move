module aptos_demo::day_summary_create_logic {
    use std::option::Option;
    use std::string::String;

    use aptos_demo::day::Day;
    use aptos_demo::day_summary;
    use aptos_demo::day_summary_created;

    friend aptos_demo::day_summary_aggregate;

    public(friend) fun verify(
        account: &signer,
        day: Day,
        description: String,
        meta_data: vector<u8>,
        array_data: vector<String>,
        optional_data: Option<String>,
        u16_array_data: vector<u16>,
        u32_array_data: vector<u32>,
        u64_array_data: vector<u64>,
        u128_array_data: vector<u128>,
        u256_array_data: vector<u256>,
    ): day_summary::DaySummaryCreated {
        let _ = account;
        day_summary::asset_day_summary_not_exists(day);
        day_summary::new_day_summary_created(
            day,
            description,
            meta_data,
            array_data,
            optional_data,
            u16_array_data,
            u32_array_data,
            u64_array_data,
            u128_array_data,
            u256_array_data,
        )
    }

    public(friend) fun mutate(
        _account: &signer,
        day_summary_created: &day_summary::DaySummaryCreated,
    ): day_summary::DaySummary {
        let day_summary = day_summary::create_day_summary(
            day_summary_created::day(day_summary_created),
            day_summary_created::description(day_summary_created),
            day_summary_created::meta_data(day_summary_created),
            day_summary_created::array_data(day_summary_created),
            day_summary_created::optional_data(day_summary_created),
            day_summary_created::u16_array_data(day_summary_created),
            day_summary_created::u32_array_data(day_summary_created),
            day_summary_created::u64_array_data(day_summary_created),
            day_summary_created::u128_array_data(day_summary_created),
            day_summary_created::u256_array_data(day_summary_created),
            //day_summary_id_table,
            //ctx,
        );
        day_summary
    }
}
