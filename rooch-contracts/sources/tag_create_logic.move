module rooch_demo::tag_create_logic {
    use moveos_std::object::Object;
    use moveos_std::storage_context::StorageContext;
    use rooch_demo::tag_created;
    use rooch_demo::tag;
    use std::string::String;

    friend rooch_demo::tag_aggregate;

    public(friend) fun verify(
        storage_ctx: &mut StorageContext,
        account: &signer,
        name: String,
    ): tag::TagCreated {
        let _ = storage_ctx;
        let _ = account;
        tag::new_tag_created(
            name,
        )
    }

    public(friend) fun mutate(
        storage_ctx: &mut StorageContext,
        create_event: &tag::TagCreated,
    ): Object<tag::Tag> {
        let tag = tag::create_tag(
            storage_ctx,
            tag_created::name(create_event),
        );
        tag
    }

}
