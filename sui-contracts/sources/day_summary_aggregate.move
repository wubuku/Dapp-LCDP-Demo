// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

module sui_demo_contracts::day_summary_aggregate {
    use std::option::Option;
    use std::string::String;
    use sui::tx_context;
    use sui_demo_contracts::day::{Self, Day};
    use sui_demo_contracts::day_summary;
    use sui_demo_contracts::day_summary_create_logic;
    use sui_demo_contracts::day_summary_delete_logic;
    use sui_demo_contracts::month;
    use sui_demo_contracts::year;

    #[allow(unused_mut_ref)]
    public entry fun create(
        day_month_year_number: u16,
        day_month_year_calendar: String,
        day_month_number: u8,
        day_month_is_leap: bool,
        day_number: u8,
        day_time_zone: String,
        description: String,
        meta_data: vector<u8>,
        array_data: vector<String>,
        optional_data: Option<vector<u8>>,
        day_summary_id_table: &mut day_summary::DaySummaryIdTable,
        ctx: &mut tx_context::TxContext,
    ) {
        let day: Day = day::new(
            month::new(
                year::new(
                    day_month_year_number,
                    day_month_year_calendar,
                ),
                day_month_number,
                day_month_is_leap,
            ),
            day_number,
            day_time_zone,
        );

        let day_summary_created = day_summary_create_logic::verify(
            day,
            description,
            meta_data,
            array_data,
            optional_data,
            day_summary_id_table,
            ctx,
        );
        let day_summary = day_summary_create_logic::mutate(
            &mut day_summary_created,
            day_summary_id_table,
            ctx,
        );
        day_summary::set_day_summary_created_id(&mut day_summary_created, day_summary::id(&day_summary));
        day_summary::transfer_object(day_summary, tx_context::sender(ctx));
        day_summary::emit_day_summary_created(day_summary_created);
    }

    public entry fun delete(
        day_summary: day_summary::DaySummary,
        ctx: &mut tx_context::TxContext,
    ) {
        let day_summary_deleted = day_summary_delete_logic::verify(
            &day_summary,
            ctx,
        );
        let updated_day_summary = day_summary_delete_logic::mutate(
            &day_summary_deleted,
            day_summary,
            ctx,
        );
        day_summary::drop_day_summary(updated_day_summary);
        day_summary::emit_day_summary_deleted(day_summary_deleted);
    }

}
