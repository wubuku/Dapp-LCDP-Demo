module rooch_demo::article_create_logic {
    use std::signer;
    use std::string::String;
    use rooch_demo::article;
    use moveos_std::storage_context::StorageContext;
    use moveos_std::object::{Object, ObjectID};
    use std::string;
    use std::vector;
    use rooch_demo::tag;
    use rooch_demo::reference_vo::ReferenceVO;

    friend rooch_demo::article_aggregate;

    public(friend) fun verify(
        storage_ctx: &mut StorageContext,
        account: &signer,
        title: String,
        author: address,
        content: String,
        references: vector<ReferenceVO>,
        tags: vector<ObjectID>,
    ): article::ArticleCreated {
        let _ = storage_ctx;
        let owner = signer::address_of(account);

        let tail = string::utf8(b"\n");
        let i = 0;
        while (i < vector::length(&tags)) {
            let tag_id = vector::borrow(&tags, i);
            let tag = tag::get_tag(storage_ctx, *tag_id);
            string::append(&mut tail, string::utf8(b"#"));
            string::append(&mut tail, tag::name(&tag));
            string::append(&mut tail, string::utf8(b" "));
            tag::return_tag(storage_ctx, tag);
            i = i + 1;
        };

        string::append(&mut content, tail);

        article::new_article_created(
            title,
            author,
            content,
            references,
            tags,
            owner
        )
    }

    public(friend) fun mutate(
        storage_ctx: &mut StorageContext,
        article_created: &article::ArticleCreated,
    ): Object<article::Article> {
        let article = article::create_article(
            storage_ctx,
            article::article_created_title(article_created),
            article::article_created_author(article_created),
            article::article_created_content(article_created),
            article::article_created_tags(article_created),
        );
        //todo add references
        article
    }
}
