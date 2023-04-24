// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

module aptos_demo::year {
    use std::string::String;
    const EID_DATA_TOO_LONG: u64 = 102;

    struct Year has store, drop, copy {
        number: u16,
        calendar: String,
    }

    public fun new(
        number: u16,
        calendar: String,
    ): Year {
        assert!(std::string::length(&calendar) <= 50, EID_DATA_TOO_LONG);
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
