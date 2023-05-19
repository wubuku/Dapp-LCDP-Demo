module rooch_demo::day_summary_create_logic {
    use std::option::Option;
    use std::string::String;

    use moveos_std::object::Object;
    use moveos_std::storage_context::StorageContext;
    use rooch_demo::day::Day;
    use rooch_demo::day_summary;
    use rooch_demo::day_summary_created;

    friend rooch_demo::day_summary_aggregate;

    public(friend) fun verify(
        _storage_ctx: &mut StorageContext,
        _account: &signer,
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
        storage_ctx: &mut StorageContext,
        day_summary_created: &day_summary::DaySummaryCreated,
    ): Object<day_summary::DaySummary> {
        let day_summary = day_summary::create_day_summary(
            storage_ctx,
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
