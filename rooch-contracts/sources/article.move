module rooch_demo::article {
    use std::string::String;
    use moveos_std::object::{ObjectID, Object};
    use moveos_std::storage_context::StorageContext;
    use moveos_std::storage_context;
    use moveos_std::object_storage;
    use moveos_std::object;
    use moveos_std::tx_context;
    use std::option;

    friend rooch_demo::article_create_logic;
    friend rooch_demo::article_aggregate;

    struct Article has key {
        title: String,
        author: address,
        content: String,
        tags: vector<ObjectID>,
    }

    /// get object id
    public fun id(obj: &Object<Article>): ObjectID {
        object::id(obj)
    }

    struct ArticleCreated has drop {
        // todo this a event object
        id: option::Option<ObjectID>,
        title: String,
        author: address,
        content: String,
        tags: vector<ObjectID>,
        owner: address,
    }

    public fun article_created_id(article_created: &ArticleCreated): option::Option<ObjectID> {
        article_created.id
    }

    public(friend) fun set_article_created_id(order_created: &mut ArticleCreated, id: ObjectID) {
        order_created.id = option::some(id);
    }

    public fun article_created_title(article_created: &ArticleCreated): String {
        article_created.title
    }

    public fun article_created_author(article_created: &ArticleCreated): address {
        article_created.author
    }

    public fun article_created_content(article_created: &ArticleCreated): String {
        article_created.content
    }

    public fun article_created_tags(article_created: &ArticleCreated): vector<ObjectID> {
        article_created.tags
    }

    public fun article_created_owner(article_created: &ArticleCreated): address {
        article_created.owner
    }

    public(friend) fun new_article_created(
        title: String,
        author: address,
        content: String,
        tags: vector<ObjectID>,
        owner: address,
    ): ArticleCreated {
        ArticleCreated {
            id: option::none(),
            title,
            author,
            content,
            tags,
            owner,
        }
    }

    public fun initialize(storage_ctx: &mut StorageContext, account: &signer) {
        let _ = storage_ctx;
        let _ = account;
    }

    public(friend) fun create_article(
        storage_ctx: &mut StorageContext,
        title: String,
        content: String,
        author: address,
        tags: vector<ObjectID>,
    ): Object<Article> {
        let tx_ctx = storage_context::tx_context_mut(storage_ctx);
        let owner = tx_context::sender(tx_ctx);

        let obj = object::new(
            tx_ctx,
            owner,
            new_article(
                title,
                content,
                author,
                tags,
            ),
        );
        obj
    }

    fun new_article(
        title: String,
        content: String,
        author: address,
        tags: vector<ObjectID>,
    ): Article {
        Article {
            title,
            content,
            author,
            tags,
        }
    }

    fun add_article(
        storage_ctx: &mut StorageContext,
        article_obj: Object<Article>
    ) {
        let obj_store = storage_context::object_storage_mut(storage_ctx);
        object_storage::add(obj_store, article_obj);
    }

    fun remove_article(
        storage_ctx: &mut StorageContext,
        obj_id: ObjectID
    ): Object<Article> {
        let obj_store = storage_context::object_storage_mut(storage_ctx);
        object_storage::remove<Article>(obj_store, obj_id)
    }

    public fun get_article(
        storage_ctx: &mut StorageContext,
        obj_id: ObjectID
    ): Object<Article> {
        remove_article(storage_ctx, obj_id)
    }

    public fun return_article(
        storage_ctx: &mut StorageContext,
        article_obj: Object<Article>
    ) {
        add_article(storage_ctx, article_obj);
    }
}
