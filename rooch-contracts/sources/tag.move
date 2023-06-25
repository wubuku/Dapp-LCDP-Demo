// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

module rooch_demo::tag {
    use moveos_std::account_storage;
    use moveos_std::events;
    use moveos_std::object::{Self, Object};
    use moveos_std::object_id::ObjectID;
    use moveos_std::object_storage;
    use moveos_std::storage_context::{Self, StorageContext};
    use moveos_std::table::{Self, Table};
    use moveos_std::tx_context;
    use std::error;
    use std::option;
    use std::signer;
    use std::string::String;
    friend rooch_demo::tag_create_logic;
    friend rooch_demo::tag_aggregate;

    const EID_ALREADY_EXISTS: u64 = 101;
    const EID_DATA_TOO_LONG: u64 = 102;
    const EINAPPROPRIATE_VERSION: u64 = 103;
    const ENOT_GENESIS_ACCOUNT: u64 = 105;

    struct Tables has key {
        tag_name_table: Table<String, ObjectID>,
    }

    public fun initialize(storage_ctx: &mut StorageContext, account: &signer) {
        assert!(signer::address_of(account) == @rooch_demo, error::invalid_argument(ENOT_GENESIS_ACCOUNT));
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

    struct TagCreated has key {
        id: option::Option<ObjectID>,
        name: String,
    }

    public fun tag_created_id(tag_created: &TagCreated): option::Option<ObjectID> {
        tag_created.id
    }

    public(friend) fun set_tag_created_id(tag_created: &mut TagCreated, id: ObjectID) {
        tag_created.id = option::some(id);
    }

    public fun tag_created_name(tag_created: &TagCreated): String {
        tag_created.name
    }

    public(friend) fun new_tag_created(
        name: String,
    ): TagCreated {
        TagCreated {
            id: option::none(),
            name,
        }
    }


    public(friend) fun create_tag(
        storage_ctx: &mut StorageContext,
        name: String,
    ): Object<Tag> {
        let tag = new_tag(
            name,
        );
        let tx_ctx = storage_context::tx_context_mut(storage_ctx);
        let obj_owner = tx_context::sender(tx_ctx);
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

    public(friend) fun update_version_and_add(storage_ctx: &mut StorageContext, tag_obj: Object<Tag>) {
        object::borrow_mut(&mut tag_obj).version = object::borrow( &mut tag_obj).version + 1;
        //assert!(object::borrow(&tag_obj).version != 0, EINAPPROPRIATE_VERSION);
        private_add_tag(storage_ctx, tag_obj);
    }

    public(friend) fun remove_tag(storage_ctx: &mut StorageContext, obj_id: ObjectID): Object<Tag> {
        let obj_store = storage_context::object_storage_mut(storage_ctx);
        object_storage::remove<Tag>(obj_store, obj_id)
    }

    public(friend) fun add_tag(storage_ctx: &mut StorageContext, tag_obj: Object<Tag>) {
        assert!(object::borrow(&tag_obj).version == 0, EINAPPROPRIATE_VERSION);
        private_add_tag(storage_ctx, tag_obj);
    }

    fun private_add_tag(storage_ctx: &mut StorageContext, tag_obj: Object<Tag>) {
        assert!(std::string::length(&object::borrow(&tag_obj).name) <= 50, EID_DATA_TOO_LONG);
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
        private_add_tag(storage_ctx, tag_obj);
    }

    public(friend) fun drop_tag(tag_obj: Object<Tag>) {
        let (_id, _owner, tag) =  object::unpack(tag_obj);
        let Tag {
            version: _version,
            name: _name,
        } = tag;
    }

    public(friend) fun emit_tag_created(storage_ctx: &mut StorageContext, tag_created: TagCreated) {
        events::emit_event(storage_ctx, tag_created);
    }

}
