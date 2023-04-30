// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

module aptos_demo::day_summary_aggregate {
    use aptos_demo::day::{Self, Day};
    use aptos_demo::day_summary;
    use aptos_demo::day_summary_create_logic;
    use aptos_demo::month;
    use aptos_demo::year;
    use std::option::Option;
    use std::string::String;

    public entry fun create(
        account: &signer,
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
            account,
            day,
            description,
            meta_data,
            array_data,
            optional_data,
        );
        let day_summary = day_summary_create_logic::mutate(
            &day_summary_created,
        );
        day_summary::add_day_summary(day_summary);
        day_summary::emit_day_summary_created(day_summary_created);
    }

}
