// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

module sui_demo_contracts::day_summary_deleted {

    use sui::object;
    use sui_demo_contracts::day::Day;
    use sui_demo_contracts::day_summary::{Self, DaySummaryDeleted};

    public fun id(day_summary_deleted: &DaySummaryDeleted): object::ID {
        day_summary::day_summary_deleted_id(day_summary_deleted)
    }

    public fun day(day_summary_deleted: &DaySummaryDeleted): Day {
        day_summary::day_summary_deleted_day(day_summary_deleted)
    }

}
