// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

module rooch_demo::day_summary {
    use moveos_std::account_storage;
    use moveos_std::event;
    use moveos_std::object::{Self, Object};
    use moveos_std::object_id::ObjectID;
    use moveos_std::object_storage;
    use moveos_std::storage_context::{Self, StorageContext};
    use moveos_std::table::{Self, Table};
    use moveos_std::tx_context;
    use rooch_demo::day::Day;
    use std::error;
    use std::option::{Self, Option};
    use std::signer;
    use std::string::String;
    friend rooch_demo::day_summary_create_logic;
    friend rooch_demo::day_summary_delete_logic;
    friend rooch_demo::day_summary_aggregate;

    const EIdAlreadyExists: u64 = 101;
    const EDataTooLong: u64 = 102;
    const EInappropriateVersion: u64 = 103;
    const ENotGenesisAccount: u64 = 105;

    struct Tables has key {
        day_summary_id_table: Table<Day, ObjectID>,
    }

    public fun initialize(storage_ctx: &mut StorageContext, account: &signer) {
        assert!(signer::address_of(account) == @rooch_demo, error::invalid_argument(ENotGenesisAccount));
        let tx_ctx = storage_context::tx_context_mut(storage_ctx);

        account_storage::global_move_to(
            storage_ctx,
            account,
            Tables {
                day_summary_id_table: table::new(tx_ctx),
            },
        );
    }

    struct DaySummary has key {
        day: Day,
        version: u64,
        description: String,
        metadata: vector<u8>,
        array_data: vector<String>,
        optional_data: Option<String>,
        u16_array_data: vector<u16>,
        u32_array_data: vector<u32>,
        u64_array_data: vector<u64>,
        u128_array_data: vector<u128>,
        u256_array_data: vector<u256>,
    }

    /// get object id
    public fun id(day_summary_obj: &Object<DaySummary>): ObjectID {
        object::id(day_summary_obj)
    }

    public fun day(day_summary_obj: &Object<DaySummary>): Day {
        object::borrow(day_summary_obj).day
    }

    public fun version(day_summary_obj: &Object<DaySummary>): u64 {
        object::borrow(day_summary_obj).version
    }

    public fun description(day_summary_obj: &Object<DaySummary>): String {
        object::borrow(day_summary_obj).description
    }

    public(friend) fun set_description(day_summary_obj: &mut Object<DaySummary>, description: String) {
        object::borrow_mut(day_summary_obj).description = description;
    }

    public fun metadata(day_summary_obj: &Object<DaySummary>): vector<u8> {
        object::borrow(day_summary_obj).metadata
    }

    public(friend) fun set_metadata(day_summary_obj: &mut Object<DaySummary>, metadata: vector<u8>) {
        object::borrow_mut(day_summary_obj).metadata = metadata;
    }

    public fun array_data(day_summary_obj: &Object<DaySummary>): vector<String> {
        object::borrow(day_summary_obj).array_data
    }

    public(friend) fun set_array_data(day_summary_obj: &mut Object<DaySummary>, array_data: vector<String>) {
        object::borrow_mut(day_summary_obj).array_data = array_data;
    }

    public fun optional_data(day_summary_obj: &Object<DaySummary>): Option<String> {
        object::borrow(day_summary_obj).optional_data
    }

    public(friend) fun set_optional_data(day_summary_obj: &mut Object<DaySummary>, optional_data: Option<String>) {
        object::borrow_mut(day_summary_obj).optional_data = optional_data;
    }

    public fun u16_array_data(day_summary_obj: &Object<DaySummary>): vector<u16> {
        object::borrow(day_summary_obj).u16_array_data
    }

    public(friend) fun set_u16_array_data(day_summary_obj: &mut Object<DaySummary>, u16_array_data: vector<u16>) {
        object::borrow_mut(day_summary_obj).u16_array_data = u16_array_data;
    }

    public fun u32_array_data(day_summary_obj: &Object<DaySummary>): vector<u32> {
        object::borrow(day_summary_obj).u32_array_data
    }

    public(friend) fun set_u32_array_data(day_summary_obj: &mut Object<DaySummary>, u32_array_data: vector<u32>) {
        object::borrow_mut(day_summary_obj).u32_array_data = u32_array_data;
    }

    public fun u64_array_data(day_summary_obj: &Object<DaySummary>): vector<u64> {
        object::borrow(day_summary_obj).u64_array_data
    }

    public(friend) fun set_u64_array_data(day_summary_obj: &mut Object<DaySummary>, u64_array_data: vector<u64>) {
        object::borrow_mut(day_summary_obj).u64_array_data = u64_array_data;
    }

    public fun u128_array_data(day_summary_obj: &Object<DaySummary>): vector<u128> {
        object::borrow(day_summary_obj).u128_array_data
    }

    public(friend) fun set_u128_array_data(day_summary_obj: &mut Object<DaySummary>, u128_array_data: vector<u128>) {
        object::borrow_mut(day_summary_obj).u128_array_data = u128_array_data;
    }

    public fun u256_array_data(day_summary_obj: &Object<DaySummary>): vector<u256> {
        object::borrow(day_summary_obj).u256_array_data
    }

    public(friend) fun set_u256_array_data(day_summary_obj: &mut Object<DaySummary>, u256_array_data: vector<u256>) {
        object::borrow_mut(day_summary_obj).u256_array_data = u256_array_data;
    }

    fun new_day_summary(
        day: Day,
        description: String,
        metadata: vector<u8>,
        array_data: vector<String>,
        optional_data: Option<String>,
        u16_array_data: vector<u16>,
        u32_array_data: vector<u32>,
        u64_array_data: vector<u64>,
        u128_array_data: vector<u128>,
        u256_array_data: vector<u256>,
    ): DaySummary {
        DaySummary {
            day,
            version: 0,
            description,
            metadata,
            array_data,
            optional_data,
            u16_array_data,
            u32_array_data,
            u64_array_data,
            u128_array_data,
            u256_array_data,
        }
    }

    struct DaySummaryCreated has key {
        id: option::Option<ObjectID>,
        day: Day,
        description: String,
        meta_data: vector<u8>,
        array_data: vector<String>,
        optional_data: Option<String>,
        u16_array_data: vector<u16>,
        u32_array_data: vector<u32>,
        u64_array_data: vector<u64>,
        u128_array_data: vector<u128>,
        u256_array_data: vector<u256>,
    }

    public fun day_summary_created_id(day_summary_created: &DaySummaryCreated): option::Option<ObjectID> {
        day_summary_created.id
    }

    public(friend) fun set_day_summary_created_id(day_summary_created: &mut DaySummaryCreated, id: ObjectID) {
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

    public fun day_summary_created_optional_data(day_summary_created: &DaySummaryCreated): Option<String> {
        day_summary_created.optional_data
    }

    public fun day_summary_created_u16_array_data(day_summary_created: &DaySummaryCreated): vector<u16> {
        day_summary_created.u16_array_data
    }

    public fun day_summary_created_u32_array_data(day_summary_created: &DaySummaryCreated): vector<u32> {
        day_summary_created.u32_array_data
    }

    public fun day_summary_created_u64_array_data(day_summary_created: &DaySummaryCreated): vector<u64> {
        day_summary_created.u64_array_data
    }

    public fun day_summary_created_u128_array_data(day_summary_created: &DaySummaryCreated): vector<u128> {
        day_summary_created.u128_array_data
    }

    public fun day_summary_created_u256_array_data(day_summary_created: &DaySummaryCreated): vector<u256> {
        day_summary_created.u256_array_data
    }

    public(friend) fun new_day_summary_created(
        day: Day,
        description: String,
        meta_data: vector<u8>,
        array_data: vector<String>,
        optional_data: Option<String>,
        u16_array_data: vector<u16>,
        u32_array_data: vector<u32>,
        u64_array_data: vector<u64>,
        u128_array_data: vector<u128>,
        u256_array_data: vector<u256>,
    ): DaySummaryCreated {
        DaySummaryCreated {
            id: option::none(),
            day,
            description,
            meta_data,
            array_data,
            optional_data,
            u16_array_data,
            u32_array_data,
            u64_array_data,
            u128_array_data,
            u256_array_data,
        }
    }

    struct DaySummaryDeleted has key {
        id: ObjectID,
        day: Day,
        version: u64,
    }

    public fun day_summary_deleted_id(day_summary_deleted: &DaySummaryDeleted): ObjectID {
        day_summary_deleted.id
    }

    public fun day_summary_deleted_day(day_summary_deleted: &DaySummaryDeleted): Day {
        day_summary_deleted.day
    }

    public(friend) fun new_day_summary_deleted(
        day_summary_obj: &Object<DaySummary>,
    ): DaySummaryDeleted {
        DaySummaryDeleted {
            id: id(day_summary_obj),
            day: day(day_summary_obj),
            version: version(day_summary_obj),
        }
    }


    public(friend) fun create_day_summary(
        storage_ctx: &mut StorageContext,
        day: Day,
        description: String,
        metadata: vector<u8>,
        array_data: vector<String>,
        optional_data: Option<String>,
        u16_array_data: vector<u16>,
        u32_array_data: vector<u32>,
        u64_array_data: vector<u64>,
        u128_array_data: vector<u128>,
        u256_array_data: vector<u256>,
    ): Object<DaySummary> {
        let day_summary = new_day_summary(
            day,
            description,
            metadata,
            array_data,
            optional_data,
            u16_array_data,
            u32_array_data,
            u64_array_data,
            u128_array_data,
            u256_array_data,
        );
        let tx_ctx = storage_context::tx_context_mut(storage_ctx);
        let obj_owner = tx_context::sender(tx_ctx);
        let day_summary_obj = object::new(
            tx_ctx,
            obj_owner,
            day_summary,
        );
        asset_day_not_exists_then_add(storage_ctx, day, object::id(&day_summary_obj));
        day_summary_obj
    }

    public(friend) fun asset_day_not_exists(
        storage_ctx: &StorageContext,
        day: Day,
    ) {
        let tables = account_storage::global_borrow<Tables>(storage_ctx, @rooch_demo);
        assert!(!table::contains(&tables.day_summary_id_table, day), EIdAlreadyExists);
    }

    fun asset_day_not_exists_then_add(
        storage_ctx: &mut StorageContext,
        day: Day,
        id: ObjectID,
    ) {
        asset_day_not_exists(storage_ctx, day);
        let tables = account_storage::global_borrow_mut<Tables>(storage_ctx, @rooch_demo);
        table::add(&mut tables.day_summary_id_table, day, id);
    }

    public(friend) fun update_version_and_add(storage_ctx: &mut StorageContext, day_summary_obj: Object<DaySummary>) {
        object::borrow_mut(&mut day_summary_obj).version = object::borrow( &mut day_summary_obj).version + 1;
        //assert!(object::borrow(&day_summary_obj).version != 0, EInappropriateVersion);
        private_add_day_summary(storage_ctx, day_summary_obj);
    }

    public(friend) fun remove_day_summary(storage_ctx: &mut StorageContext, obj_id: ObjectID): Object<DaySummary> {
        let obj_store = storage_context::object_storage_mut(storage_ctx);
        object_storage::remove<DaySummary>(obj_store, obj_id)
    }

    public(friend) fun add_day_summary(storage_ctx: &mut StorageContext, day_summary_obj: Object<DaySummary>) {
        assert!(object::borrow(&day_summary_obj).version == 0, EInappropriateVersion);
        private_add_day_summary(storage_ctx, day_summary_obj);
    }

    fun private_add_day_summary(storage_ctx: &mut StorageContext, day_summary_obj: Object<DaySummary>) {
        let obj_store = storage_context::object_storage_mut(storage_ctx);
        object_storage::add(obj_store, day_summary_obj);
    }

    public fun get_day_summary_by_day(storage_ctx: &mut StorageContext, day: Day): Object<DaySummary> {
        let tables = account_storage::global_borrow_mut<Tables>(storage_ctx, @rooch_demo);
        let obj_id = table::borrow(&mut tables.day_summary_id_table, day);
        remove_day_summary(storage_ctx, *obj_id)
    }

    public fun get_day_summary(storage_ctx: &mut StorageContext, obj_id: ObjectID): Object<DaySummary> {
        remove_day_summary(storage_ctx, obj_id)
    }

    public fun return_day_summary(storage_ctx: &mut StorageContext, day_summary_obj: Object<DaySummary>) {
        private_add_day_summary(storage_ctx, day_summary_obj);
    }

    public(friend) fun drop_day_summary(day_summary_obj: Object<DaySummary>) {
        let (_id, _owner, day_summary) =  object::unpack(day_summary_obj);
        let DaySummary {
            version: _version,
            day: _day,
            description: _description,
            metadata: _metadata,
            array_data: _array_data,
            optional_data: _optional_data,
            u16_array_data: _u16_array_data,
            u32_array_data: _u32_array_data,
            u64_array_data: _u64_array_data,
            u128_array_data: _u128_array_data,
            u256_array_data: _u256_array_data,
        } = day_summary;
    }

    public(friend) fun emit_day_summary_created(storage_ctx: &mut StorageContext, day_summary_created: DaySummaryCreated) {
        event::emit(storage_ctx, day_summary_created);
    }

    public(friend) fun emit_day_summary_deleted(storage_ctx: &mut StorageContext, day_summary_deleted: DaySummaryDeleted) {
        event::emit(storage_ctx, day_summary_deleted);
    }

}
