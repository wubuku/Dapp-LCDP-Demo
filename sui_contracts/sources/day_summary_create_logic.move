module sui_contracts::day_summary_create_logic {
    use std::string::String;
    use sui::object::{Self, UID};
    use sui::tx_context::{TxContext};
    use sui_contracts::day::Day;
    use sui_contracts::day_summary;
    use std::option::Option;

    friend sui_contracts::day_summary_aggregate;

    public(friend) fun verify(
        day: Day,
        description: String,
        meta_data: vector<u8>,
        array_data: vector<String>,
        optional_data: Option<vector<u8>>,
        day_summary_id_table: &day_summary::DaySummaryIdTable,
        ctx: &mut TxContext,
    ): (day_summary::DaySummaryCreated, UID) {
        let id = object::new(ctx);
        day_summary::asset_day_not_exists(day, day_summary_id_table);
        (
           day_summary::new_day_summary_created(
               &id,
               day,
               description,
               meta_data,
               array_data,
               optional_data,
           ),
           id,
        )
    }

    public(friend) fun mutate(
        day_summary_created: &day_summary::DaySummaryCreated,
        id: UID,
        day_summary_id_table: &mut day_summary::DaySummaryIdTable,
        ctx: &TxContext,
    ): day_summary::DaySummary {
        let _ = ctx;
        let day_summary = day_summary::create_day_summary(
            id,
            day_summary::day_summary_created_day(day_summary_created),
            day_summary::day_summary_created_description(day_summary_created),
            day_summary::day_summary_created_meta_data(day_summary_created),
            day_summary::day_summary_created_array_data(day_summary_created),
            day_summary::day_summary_created_optional_data(day_summary_created),
            day_summary_id_table,
        );
        day_summary
    }

}
