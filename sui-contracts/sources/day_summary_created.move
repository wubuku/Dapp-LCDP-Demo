module sui_contracts::day_summary_created {

    use std::option::Option;
    use std::string::String;
    use sui::object;
    use sui_contracts::day::Day;
    use sui_contracts::day_summary::{Self, DaySummaryCreated};

    public fun id(day_summary_created: &DaySummaryCreated): object::ID {
        day_summary::day_summary_created_id(day_summary_created)
    }

    public fun day(day_summary_created: &DaySummaryCreated): Day {
        day_summary::day_summary_created_day(day_summary_created)
    }

    public fun description(day_summary_created: &DaySummaryCreated): String {
        day_summary::day_summary_created_description(day_summary_created)
    }

    public fun meta_data(day_summary_created: &DaySummaryCreated): vector<u8> {
        day_summary::day_summary_created_meta_data(day_summary_created)
    }

    public fun array_data(day_summary_created: &DaySummaryCreated): vector<String> {
        day_summary::day_summary_created_array_data(day_summary_created)
    }

    public fun optional_data(day_summary_created: &DaySummaryCreated): Option<vector<u8>> {
        day_summary::day_summary_created_optional_data(day_summary_created)
    }

}
