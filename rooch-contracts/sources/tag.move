// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

module rooch_demo::tag {
    use moveos_std::account_storage;
    use moveos_std::object::{Self, Object, ObjectID};
    use moveos_std::object_storage;
    use moveos_std::storage_context::{Self, StorageContext};
    use moveos_std::table::{Self, Table};
    use moveos_std::tx_context;
    use std::string::String;
    friend rooch_demo::tag_aggregate;

    const EID_ALREADY_EXISTS: u64 = 101;
    const EID_DATA_TOO_LONG: u64 = 102;

    struct Tables has key {
        tag_name_table: Table<String, ObjectID>,
    }

    public fun initialize(storage_ctx: &mut StorageContext, account: &signer) {
        let tx_ctx = storage_context::tx_context_mut(storage_ctx);

        account_storage::global_move_to(
            storage_ctx,
            account,
            Tables {
                tag_name_table: table::new(tx_ctx),
            },
        );
    }

    struct Tag has key {
        name: String,
        version: u64,
    }

    /// get object id
    public fun id(tag_obj: &Object<Tag>): ObjectID {
        object::id(tag_obj)
    }

    public fun name(tag_obj: &Object<Tag>): String {
        object::borrow(tag_obj).name
    }

    public fun version(tag_obj: &Object<Tag>): u64 {
        object::borrow(tag_obj).version
    }

    fun new_tag(
        name: String,
    ): Tag {
        assert!(std::string::length(&name) <= 50, EID_DATA_TOO_LONG);
        Tag {
            name,
            version: 0,
        }
    }


    public(friend) fun create_tag(
        storage_ctx: &mut StorageContext,
        name: String,
    ): Object<Tag> {
        let tx_ctx = storage_context::tx_context_mut(storage_ctx);
        let obj_owner = tx_context::sender(tx_ctx);
        let tag = new_tag(
            name,
        );
        let tag_obj = object::new(
            tx_ctx,
            obj_owner,
            tag,
        );
        asset_name_not_exists_then_add(storage_ctx, name, object::id(&tag_obj));
        tag_obj
    }

    public(friend) fun asset_name_not_exists(
        storage_ctx: &StorageContext,
        name: String,
    ) {
        let tables = account_storage::global_borrow<Tables>(storage_ctx, @rooch_demo);
        assert!(!table::contains(&tables.tag_name_table, name), EID_ALREADY_EXISTS);
    }

    fun asset_name_not_exists_then_add(
        storage_ctx: &mut StorageContext,
        name: String,
        id: ObjectID,
    ) {
        asset_name_not_exists(storage_ctx, name);
        let tables = account_storage::global_borrow_mut<Tables>(storage_ctx, @rooch_demo);
        table::add(&mut tables.tag_name_table, name, id);
    }

    fun remove_tag(storage_ctx: &mut StorageContext, obj_id: ObjectID): Object<Tag> {
        let obj_store = storage_context::object_storage_mut(storage_ctx);
        object_storage::remove<Tag>(obj_store, obj_id)
    }

    fun add_tag(storage_ctx: &mut StorageContext, tag_obj: Object<Tag>) {
        let obj_store = storage_context::object_storage_mut(storage_ctx);
        object_storage::add(obj_store, tag_obj);
    }

    public fun get_tag_by_name(storage_ctx: &mut StorageContext, name: String): Object<Tag> {
        let tables = account_storage::global_borrow_mut<Tables>(storage_ctx, @rooch_demo);
        let obj_id = table::borrow(&mut tables.tag_name_table, name);
        remove_tag(storage_ctx, *obj_id)
    }

    public fun get_tag(storage_ctx: &mut StorageContext, obj_id: ObjectID): Object<Tag> {
        remove_tag(storage_ctx, obj_id)
    }

    public fun return_tag(storage_ctx: &mut StorageContext, tag_obj: Object<Tag>) {
        add_tag(storage_ctx, tag_obj);
    }

}
