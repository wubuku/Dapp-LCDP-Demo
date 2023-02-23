module sui_contracts::year {
    use std::string::String;

    struct Year has store, drop, copy {
        number: u16,
        calendar: String,
    }

    public fun new(
        number: u16,
        calendar: String,
    ): Year {
        Year {
            number,
            calendar,
        }
    }

    public fun number(year: &Year): u16 {
        year.number
    }

    public fun calendar(year: &Year): String {
        year.calendar
    }

}
