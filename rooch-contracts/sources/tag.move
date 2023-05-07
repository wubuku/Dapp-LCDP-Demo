module rooch_demo::tag {
    use std::string::String;
    use moveos_std::table::Table;
    use moveos_std::object::{ObjectID, Object};
    use moveos_std::table;
    use moveos_std::account_storage;
    use moveos_std::storage_context::StorageContext;
    use moveos_std::storage_context;
    use moveos_std::tx_context;
    use moveos_std::object;
    use moveos_std::object_storage;

    const EID_ALREADY_EXISTS: u64 = 101;

    struct Tag has key {
        // domain ID(key)
        name: String,
    }

    /// get 'name' from object
    public fun name(tag_obj: &Object<Tag>): String {
        object::borrow(tag_obj).name
    }

    struct Tables has key {
        tag_name_table: Table<String, ObjectID>,
    }

    public fun initialize(storage_ctx: &mut StorageContext, account: &signer) {
        // let res_account = genesis_account::resource_account_signer();
        // move_to(&res_account, Events {
        // });

        let tx_ctx = storage_context::tx_context_mut(storage_ctx);

        account_storage::global_move_to(
            storage_ctx,
            account,
            Tables {
                tag_name_table: table::new(tx_ctx),
            },
        );
    }

    public(friend) fun create_tag(
        storage_ctx: &mut StorageContext,
        name: String,
    ): Object<Tag> {
        let tx_ctx = storage_context::tx_context_mut(storage_ctx);
        let owner = tx_context::sender(tx_ctx);
        let tag_obj = object::new(
            tx_ctx,
            owner,
            new_tag(
                name
            ),
        );
        asset_name_not_exists_then_add(storage_ctx, name, object::id(&tag_obj));
        tag_obj
    }

    fun new_tag(
        name: String
    ): Tag {
        Tag {
            name,
        }
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


    fun add_tag(
        storage_ctx: &mut StorageContext,
        tag_obj: Object<Tag>
    ) {
        let obj_store = storage_context::object_storage_mut(storage_ctx);
        object_storage::add(obj_store, tag_obj);
    }

    fun remove_tag(
        storage_ctx: &mut StorageContext,
        obj_id: ObjectID
    ): Object<Tag> {
        let obj_store = storage_context::object_storage_mut(storage_ctx);
        object_storage::remove<Tag>(obj_store, obj_id)
    }

    public fun get_tag(
        storage_ctx: &mut StorageContext,
        obj_id: ObjectID
    ): Object<Tag> {
        remove_tag(storage_ctx, obj_id)
    }

    public fun get_tag_by_name(
        storage_ctx: &mut StorageContext,
        name: String
    ): Object<Tag> {
        let tables = account_storage::global_borrow_mut<Tables>(storage_ctx, @rooch_demo);
        let obj_id = table::borrow(&mut tables.tag_name_table, name);
        let tag = remove_tag(storage_ctx, *obj_id);
        tag
    }

    public fun return_tag(
        storage_ctx: &mut StorageContext,
        tag_obj: Object<Tag>
    ) {
        //let name = name(&tag_obj);
        add_tag(storage_ctx, tag_obj);
    }
}
