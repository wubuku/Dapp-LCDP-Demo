module rooch_demo::article_aggregate {
    use std::string::String;
    use moveos_std::storage_context::StorageContext;
    use moveos_std::object::ObjectID;
    use rooch_demo::article_create_logic;
    use rooch_demo::article_add_reference_logic;
    use rooch_demo::article;

    public entry fun create(
        storage_ctx: &mut StorageContext,
        account: &signer,
        title: String,
        author: address,
        content: String,
        tags: vector<ObjectID>,
    ) {
        let article_created = article_create_logic::verify(storage_ctx, account, title, author, content, tags);
        let article_obj = article_create_logic::mutate(storage_ctx, &article_created);
        article::set_article_created_id(&mut article_created, article::id(&article_obj));
        article::return_article(storage_ctx, article_obj);

        //todo article::emit_article_created(article_created);
    }

    public entry fun add_reference(
        storage_ctx: &mut StorageContext,
        account: &signer,
        article_id: ObjectID,
        reference_number: u64,
        title: String,
        url: String,
    ) {
        let article_obj = article::get_article(storage_ctx, article_id);
        let reference_added = article_add_reference_logic::verify(storage_ctx, account, &article_obj, reference_number, title, url);
        let update_article_obj = article_add_reference_logic::mutate(storage_ctx, &reference_added, article_obj);
        article::return_article(storage_ctx, update_article_obj);
        //todo
    }
}
