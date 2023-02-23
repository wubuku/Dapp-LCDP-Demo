module sui_contracts::day_summary {
    use std::string::String;
    use sui::event;
    use sui::object::{Self, UID};
    use sui::table;
    use sui::transfer;
    use sui::tx_context::TxContext;
    use sui_contracts::day::Day;
    friend sui_contracts::day_summary_create_logic;
    
    friend sui_contracts::day_summary_aggregate;

    const EID_ALREADY_EXISTS: u64 = 101;

    struct DaySummaryIdTable has key {
        id: UID,
        table: table::Table<Day, object::ID>,
    }

    struct DaySummaryIdTableCreated has copy, drop {
        id: object::ID,
    }

    fun init(ctx: &mut TxContext) {
        let id_generator_table = DaySummaryIdTable {
            id: object::new(ctx),
            table: table::new(ctx),
        };
        let id_generator_table_id = object::uid_to_inner(&id_generator_table.id);
        transfer::share_object(id_generator_table);
        event::emit(DaySummaryIdTableCreated {
            id: id_generator_table_id,
        });
    }

    struct DaySummary has key {
        id: UID,
        day: Day,
        version: u64,
        description: String,
    }

    public fun id(day_summary: &DaySummary): object::ID {
        object::uid_to_inner(&day_summary.id)
    }

    public fun day(day_summary: &DaySummary): Day {
        day_summary.day
    }

    public fun version(day_summary: &DaySummary): u64 {
        day_summary.version
    }

    public fun description(day_summary: &DaySummary): String {
        day_summary.description
    }

    public(friend) fun set_description(day_summary: &mut DaySummary, description: String) {
        day_summary.description = description;
    }

    fun new_day_summary(
        id: UID,
        day: Day,
        description: String,
    ): DaySummary {
        DaySummary {
            id,
            day,
            version: 0,
            description,
        }
    }

    struct DaySummaryCreated has copy, drop {
        id: object::ID,
        day: Day,
        description: String,
    }

    public fun day_summary_created_day(day_summary_created: &DaySummaryCreated): Day {
        day_summary_created.day
    }

    public fun day_summary_created_description(day_summary_created: &DaySummaryCreated): String {
        day_summary_created.description
    }

    public(friend) fun new_day_summary_created(
        id: &UID,
        day: Day,
        description: String,
    ): DaySummaryCreated {
        DaySummaryCreated {
            id: object::uid_to_inner(id),
            day,
            description,
        }
    }


    public(friend) fun create_day_summary(
        id: UID,
        day: Day,
        description: String,
        day_summary_id_table: &mut DaySummaryIdTable,
    ): DaySummary {
        asset_day_not_exists_then_add(day, day_summary_id_table, object::uid_to_inner(&id));
        let day_summary = new_day_summary(
            id,
            day,
            description,
        );
        day_summary
    }

    public(friend) fun asset_day_not_exists(
        day: Day,
        day_summary_id_table: &DaySummaryIdTable,
    ) {
        assert!(!table::contains(&day_summary_id_table.table, day), EID_ALREADY_EXISTS);
    }

    fun asset_day_not_exists_then_add(
        day: Day,
        day_summary_id_table: &mut DaySummaryIdTable,
        id: object::ID,
    ) {
        asset_day_not_exists(day, day_summary_id_table);
        table::add(&mut day_summary_id_table.table, day, id);
    }

    public(friend) fun transfer_object(day_summary: DaySummary, recipient: address) {
        transfer::transfer(day_summary, recipient);
    }

    public(friend) fun update_version_and_transfer_object(day_summary: DaySummary, recipient: address) {
        day_summary.version = day_summary.version + 1;
        transfer::transfer(day_summary, recipient);
    }

    public(friend) fun share_object(day_summary: DaySummary) {
        transfer::share_object(day_summary);
    }

    public(friend) fun freeze_object(day_summary: DaySummary) {
        transfer::freeze_object(day_summary);
    }

    public(friend) fun emit_day_summary_created(day_summary_created: DaySummaryCreated) {
        event::emit(day_summary_created);
    }

    #[test_only]
    /// Wrapper of module initializer for testing
    public fun test_init(ctx: &mut TxContext) {
        init(ctx)
    }

}
