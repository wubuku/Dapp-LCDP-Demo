module sui_contracts::day_summary {
    use std::option::Option;
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
    const EID_DATA_TOO_LONG: u64 = 102;

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
        metadata: vector<u8>,
        array_data: vector<String>,
        optional_data: Option<vector<u8>>,
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

    public fun metadata(day_summary: &DaySummary): vector<u8> {
        day_summary.metadata
    }

    public(friend) fun set_metadata(day_summary: &mut DaySummary, metadata: vector<u8>) {
        day_summary.metadata = metadata;
    }

    public fun array_data(day_summary: &DaySummary): vector<String> {
        day_summary.array_data
    }

    public(friend) fun set_array_data(day_summary: &mut DaySummary, array_data: vector<String>) {
        day_summary.array_data = array_data;
    }

    public fun optional_data(day_summary: &DaySummary): Option<vector<u8>> {
        day_summary.optional_data
    }

    public(friend) fun set_optional_data(day_summary: &mut DaySummary, optional_data: Option<vector<u8>>) {
        day_summary.optional_data = optional_data;
    }

    fun new_day_summary(
        id: UID,
        day: Day,
        description: String,
        metadata: vector<u8>,
        array_data: vector<String>,
        optional_data: Option<vector<u8>>,
    ): DaySummary {
        DaySummary {
            id,
            day,
            version: 0,
            description,
            metadata,
            array_data,
            optional_data,
        }
    }

    struct DaySummaryCreated has copy, drop {
        id: object::ID,
        day: Day,
        description: String,
        meta_data: vector<u8>,
        array_data: vector<String>,
        optional_data: Option<vector<u8>>,
    }

    public fun day_summary_created_id(day_summary_created: &DaySummaryCreated): object::ID {
        day_summary_created.id
    }

    public fun day_summary_created_day(day_summary_created: &DaySummaryCreated): Day {
        day_summary_created.day
    }

    public fun day_summary_created_description(day_summary_created: &DaySummaryCreated): String {
        day_summary_created.description
    }

    public fun day_summary_created_meta_data(day_summary_created: &DaySummaryCreated): vector<u8> {
        day_summary_created.meta_data
    }

    public fun day_summary_created_array_data(day_summary_created: &DaySummaryCreated): vector<String> {
        day_summary_created.array_data
    }

    public fun day_summary_created_optional_data(day_summary_created: &DaySummaryCreated): Option<vector<u8>> {
        day_summary_created.optional_data
    }

    public(friend) fun new_day_summary_created(
        id: &UID,
        day: Day,
        description: String,
        meta_data: vector<u8>,
        array_data: vector<String>,
        optional_data: Option<vector<u8>>,
    ): DaySummaryCreated {
        DaySummaryCreated {
            id: object::uid_to_inner(id),
            day,
            description,
            meta_data,
            array_data,
            optional_data,
        }
    }


    public(friend) fun create_day_summary(
        id: UID,
        day: Day,
        description: String,
        metadata: vector<u8>,
        array_data: vector<String>,
        optional_data: Option<vector<u8>>,
        day_summary_id_table: &mut DaySummaryIdTable,
    ): DaySummary {
        asset_day_not_exists_then_add(day, day_summary_id_table, object::uid_to_inner(&id));
        let day_summary = new_day_summary(
            id,
            day,
            description,
            metadata,
            array_data,
            optional_data,
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