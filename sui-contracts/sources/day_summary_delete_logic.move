module sui_demo_contracts::day_summary_delete_logic {
    use sui::tx_context::TxContext;
    use sui_demo_contracts::day_summary;

    friend sui_demo_contracts::day_summary_aggregate;

    public(friend) fun verify(
        day_summary: &day_summary::DaySummary,
        ctx: &TxContext,
    ): day_summary::DaySummaryDeleted {
        let _ = ctx;
        day_summary::new_day_summary_deleted(
            day_summary,
        )
    }

    public(friend) fun mutate(
        day_summary_deleted: &day_summary::DaySummaryDeleted,
        day_summary: day_summary::DaySummary,
        ctx: &TxContext, // modify the reference to mutable if needed
    ): day_summary::DaySummary {
        let day = day_summary::day(&day_summary);
        let _ = ctx;
        let _ = day;
        let _ = day_summary_deleted;
        day_summary
    }

}
