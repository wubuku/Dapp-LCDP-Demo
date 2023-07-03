module rooch_demo::day_summary_delete_logic {
    use moveos_std::object::Object;
    use moveos_std::storage_context::StorageContext;
    use rooch_demo::day_summary;

    friend rooch_demo::day_summary_aggregate;

    public(friend) fun verify(
        storage_ctx: &mut StorageContext,
        account: &signer,
        day_summary_obj: &Object<day_summary::DaySummary>,
    ): day_summary::DaySummaryDeleted {
        let _ = storage_ctx;
        let _ = account;
        day_summary::new_day_summary_deleted(
            day_summary_obj,
        )
    }

    public(friend) fun mutate(
        storage_ctx: &mut StorageContext,
        day_summary_deleted: &day_summary::DaySummaryDeleted,
        day_summary_obj: Object<day_summary::DaySummary>,
    ): Object<day_summary::DaySummary> {
        let day = day_summary::day(&day_summary_obj);
        let _ = storage_ctx;
        let _ = day;
        let _ = day_summary_deleted;
        day_summary_obj
    }

}
