// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

module sui_contracts::day {
    use std::string::String;
    use sui_contracts::month::Month;
    const EID_DATA_TOO_LONG: u64 = 102;

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
        let day = Day {
            month,
            number,
            time_zone,
        };
        validate(&day);
        day
    }

    fun validate(day: &Day) {
        assert!(std::string::length(&day.time_zone) <= 50, EID_DATA_TOO_LONG);
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
