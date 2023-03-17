module sui_contracts::month {
    use sui_contracts::year::Year;
    const EID_DATA_TOO_LONG: u64 = 102;

    struct Month has store, drop, copy {
        year: Year,
        number: u8,
        is_leap: bool,
    }

    public fun new(
        year: Year,
        number: u8,
        is_leap: bool,
    ): Month {
        Month {
            year,
            number,
            is_leap,
        }
    }

    public fun year(month: &Month): Year {
        month.year
    }

    public fun number(month: &Month): u8 {
        month.number
    }

    public fun is_leap(month: &Month): bool {
        month.is_leap
    }

}
