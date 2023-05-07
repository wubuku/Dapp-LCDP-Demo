module rooch_demo::article_aggregate {
    use std::string::String;
    use moveos_std::storage_context::StorageContext;
    use moveos_std::object::ObjectID;
    use rooch_demo::article_create_logic;
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
        let article = article_create_logic::mutate(storage_ctx, &article_created);
        article::set_article_created_id(&mut article_created, article::id(&article));
        article::return_article(storage_ctx, article);
        //todo article::transfer_object(article, article::article_created_owner(&article_created));
        //todo article::emit_article_created(article_created);
    }
}
