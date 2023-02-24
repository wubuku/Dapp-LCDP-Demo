module sui_contracts::day_summary_aggregate {
    use std::string::String;
    use sui::tx_context;
    use sui_contracts::day::{Self, Day};
    use sui_contracts::day_summary;
    use sui_contracts::day_summary_create_logic;
    use sui_contracts::month;
    use sui_contracts::year;

    public entry fun create(
        day_month_year_number: u16,
        day_month_year_calendar: String,
        day_month_number: u8,
        day_month_is_leap: bool,
        day_number: u8,
        day_time_zone: String,
        description: String,
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

        let (day_summary_created, id) = day_summary_create_logic::verify(
            day,
            description,
            day_summary_id_table,
            ctx,
        );
        let day_summary = day_summary_create_logic::mutate(
            &day_summary_created,
            id,
            day_summary_id_table,
            ctx,
        );
        day_summary::transfer_object(day_summary, tx_context::sender(ctx));
        day_summary::emit_day_summary_created(day_summary_created);
    }

}
