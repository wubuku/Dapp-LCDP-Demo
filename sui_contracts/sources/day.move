module sui_contracts::day {
    use sui_contracts::month::Month;
    use std::string::String;

    struct Day has store, drop, copy {
        month: Month,
        number: u8,
        time_zone: String,
    }

    public fun new(
        month: Month,
        number: u8,
        time_zone: String,
    ): Day {
        Day {
            month,
            number,
            time_zone,
        }
    }

    public fun month(day: &Day): Month {
        day.month
    }

    public fun number(day: &Day): u8 {
        day.number
    }

    public fun time_zone(day: &Day): String {
        day.time_zone
    }
}
