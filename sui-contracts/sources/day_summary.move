// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

module sui_demo_contracts::day_summary {
    use std::option::{Self, Option};
    use std::string::String;
    use sui::event;
    use sui::object::{Self, UID};
    use sui::table;
    use sui::transfer;
    use sui::tx_context::TxContext;
    use sui_demo_contracts::day::Day;
    friend sui_demo_contracts::day_summary_create_logic;
    friend sui_demo_contracts::day_summary_delete_logic;
    friend sui_demo_contracts::day_summary_aggregate;

    const EID_ALREADY_EXISTS: u64 = 101;
    const EDATA_TOO_LONG: u64 = 102;
    const EINAPPROPRIATE_VERSION: u64 = 103;

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
        day: Day,
        description: String,
        metadata: vector<u8>,
        array_data: vector<String>,
        optional_data: Option<vector<u8>>,
        ctx: &mut TxContext,
    ): DaySummary {
        DaySummary {
            id: object::new(ctx),
            day,
            version: 0,
            description,
            metadata,
            array_data,
            optional_data,
        }
    }

    struct DaySummaryCreated has copy, drop {
        id: option::Option<object::ID>,
        day: Day,
        description: String,
        meta_data: vector<u8>,
        array_data: vector<String>,
        optional_data: Option<vector<u8>>,
    }

    public fun day_summary_created_id(day_summary_created: &DaySummaryCreated): option::Option<object::ID> {
        day_summary_created.id
    }

    public(friend) fun set_day_summary_created_id(day_summary_created: &mut DaySummaryCreated, id: object::ID) {
        day_summary_created.id = option::some(id);
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
        day: Day,
        description: String,
        meta_data: vector<u8>,
        array_data: vector<String>,
        optional_data: Option<vector<u8>>,
    ): DaySummaryCreated {
        DaySummaryCreated {
            id: option::none(),
            day,
            description,
            meta_data,
            array_data,
            optional_data,
        }
    }

    struct DaySummaryDeleted has copy, drop {
        id: object::ID,
        day: Day,
        version: u64,
    }

    public fun day_summary_deleted_id(day_summary_deleted: &DaySummaryDeleted): object::ID {
        day_summary_deleted.id
    }

    public fun day_summary_deleted_day(day_summary_deleted: &DaySummaryDeleted): Day {
        day_summary_deleted.day
    }

    public(friend) fun new_day_summary_deleted(
        day_summary: &DaySummary,
    ): DaySummaryDeleted {
        DaySummaryDeleted {
            id: id(day_summary),
            day: day(day_summary),
            version: version(day_summary),
        }
    }


    public(friend) fun create_day_summary(
        day: Day,
        description: String,
        metadata: vector<u8>,
        array_data: vector<String>,
        optional_data: Option<vector<u8>>,
        day_summary_id_table: &mut DaySummaryIdTable,
        ctx: &mut TxContext,
    ): DaySummary {
        let day_summary = new_day_summary(
            day,
            description,
            metadata,
            array_data,
            optional_data,
            ctx,
        );
        asset_day_not_exists_then_add(day, day_summary_id_table, object::uid_to_inner(&day_summary.id));
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
        assert!(day_summary.version == 0, EINAPPROPRIATE_VERSION);
        transfer::transfer(day_summary, recipient);
    }

    public(friend) fun update_version_and_transfer_object(day_summary: DaySummary, recipient: address) {
        update_object_version(&mut day_summary);
        transfer::transfer(day_summary, recipient);
    }

    public(friend) fun share_object(day_summary: DaySummary) {
        assert!(day_summary.version == 0, EINAPPROPRIATE_VERSION);
        transfer::share_object(day_summary);
    }

    public(friend) fun update_version_and_share_object(day_summary: DaySummary) {
        update_object_version(&mut day_summary);
        transfer::share_object(day_summary);
    }

    public(friend) fun freeze_object(day_summary: DaySummary) {
        assert!(day_summary.version == 0, EINAPPROPRIATE_VERSION);
        transfer::freeze_object(day_summary);
    }

    public(friend) fun update_version_and_freeze_object(day_summary: DaySummary) {
        update_object_version(&mut day_summary);
        transfer::freeze_object(day_summary);
    }

    fun update_object_version(day_summary: &mut DaySummary) {
        day_summary.version = day_summary.version + 1;
        //assert!(day_summary.version != 0, EINAPPROPRIATE_VERSION);
    }

    public(friend) fun drop_day_summary(day_summary: DaySummary) {
        let DaySummary {
            id,
            version: _version,
            day: _day,
            description: _description,
            metadata: _metadata,
            array_data: _array_data,
            optional_data: _optional_data,
        } = day_summary;
        object::delete(id);
    }

    public(friend) fun emit_day_summary_created(day_summary_created: DaySummaryCreated) {
        event::emit(day_summary_created);
    }

    public(friend) fun emit_day_summary_deleted(day_summary_deleted: DaySummaryDeleted) {
        event::emit(day_summary_deleted);
    }

    #[test_only]
    /// Wrapper of module initializer for testing
    public fun test_init(ctx: &mut TxContext) {
        init(ctx)
    }

}
