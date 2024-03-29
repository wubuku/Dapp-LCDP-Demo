// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

module rooch_demo::article {
    use moveos_std::event;
    use moveos_std::object::{Self, Object};
    use moveos_std::object_id::ObjectID;
    use moveos_std::object_storage;
    use moveos_std::storage_context::{Self, StorageContext};
    use moveos_std::table::{Self, Table};
    use moveos_std::tx_context;
    use rooch_demo::reference::{Self, Reference};
    use rooch_demo::reference_vo::ReferenceVO;
    use std::error;
    use std::option::{Self, Option};
    use std::signer;
    use std::string::String;
    friend rooch_demo::article_create_logic;
    friend rooch_demo::article_add_reference_logic;
    friend rooch_demo::article_update_reference_logic;
    friend rooch_demo::article_remove_reference_logic;
    friend rooch_demo::article_aggregate;

    const EIdAlreadyExists: u64 = 101;
    const EDataTooLong: u64 = 102;
    const EInappropriateVersion: u64 = 103;
    const ENotGenesisAccount: u64 = 105;
    const EIdNotFound: u64 = 106;

    struct ReferenceTableItemAdded has key {
        article_id: ObjectID,
        reference_number: u64,
    }

    public fun initialize(storage_ctx: &mut StorageContext, account: &signer) {
        assert!(signer::address_of(account) == @rooch_demo, error::invalid_argument(ENotGenesisAccount));
        let _ = storage_ctx;
        let _ = account;
    }

    struct Article has key {
        version: u64,
        title: String,
        author: address,
        content: String,
        tags: vector<ObjectID>,
        references: Table<u64, Reference>,
    }

    /// get object id
    public fun id(article_obj: &Object<Article>): ObjectID {
        object::id(article_obj)
    }

    public fun version(article_obj: &Object<Article>): u64 {
        object::borrow(article_obj).version
    }

    public fun title(article_obj: &Object<Article>): String {
        object::borrow(article_obj).title
    }

    public(friend) fun set_title(article_obj: &mut Object<Article>, title: String) {
        object::borrow_mut(article_obj).title = title;
    }

    public fun author(article_obj: &Object<Article>): address {
        object::borrow(article_obj).author
    }

    public(friend) fun set_author(article_obj: &mut Object<Article>, author: address) {
        object::borrow_mut(article_obj).author = author;
    }

    public fun content(article_obj: &Object<Article>): String {
        object::borrow(article_obj).content
    }

    public(friend) fun set_content(article_obj: &mut Object<Article>, content: String) {
        object::borrow_mut(article_obj).content = content;
    }

    public fun tags(article_obj: &Object<Article>): vector<ObjectID> {
        object::borrow(article_obj).tags
    }

    public(friend) fun set_tags(article_obj: &mut Object<Article>, tags: vector<ObjectID>) {
        object::borrow_mut(article_obj).tags = tags;
    }

    public(friend) fun add_reference(storage_ctx: &mut StorageContext, article_obj: &mut Object<Article>, reference: Reference) {
        let reference_number = reference::reference_number(&reference);
        assert!(!table::contains(&object::borrow_mut(article_obj).references, reference_number), EIdAlreadyExists);
        table::add(&mut object::borrow_mut(article_obj).references, reference_number, reference);
        event::emit(storage_ctx, ReferenceTableItemAdded {
            article_id: id(article_obj),
            reference_number,
        });
    }

    public(friend) fun remove_reference(article_obj: &mut Object<Article>, reference_number: u64) {
        assert!(table::contains(&object::borrow_mut(article_obj).references, reference_number), EIdNotFound);
        let reference = table::remove(&mut object::borrow_mut(article_obj).references, reference_number);
        reference::drop_reference(reference);
    }

    public(friend) fun borrow_mut_reference(article_obj: &mut Object<Article>, reference_number: u64): &mut Reference {
        table::borrow_mut(&mut object::borrow_mut(article_obj).references, reference_number)
    }

    public fun borrow_reference(article_obj: &Object<Article>, reference_number: u64): &Reference {
        table::borrow(&object::borrow(article_obj).references, reference_number)
    }

    public fun references_contains(article_obj: &Object<Article>, reference_number: u64): bool {
        table::contains(&object::borrow(article_obj).references, reference_number)
    }

    fun new_article(
        tx_ctx: &mut tx_context::TxContext,
        title: String,
        author: address,
        content: String,
        tags: vector<ObjectID>,
    ): Article {
        Article {
            version: 0,
            title,
            author,
            content,
            tags,
            references: table::new<u64, Reference>(tx_ctx),
        }
    }

    struct ArticleCreated has key {
        id: option::Option<ObjectID>,
        title: String,
        author: address,
        content: String,
        references: vector<ReferenceVO>,
        tags: vector<ObjectID>,
        owner: address,
    }

    public fun article_created_id(article_created: &ArticleCreated): option::Option<ObjectID> {
        article_created.id
    }

    public(friend) fun set_article_created_id(article_created: &mut ArticleCreated, id: ObjectID) {
        article_created.id = option::some(id);
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

    public fun article_created_references(article_created: &ArticleCreated): vector<ReferenceVO> {
        article_created.references
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
        references: vector<ReferenceVO>,
        tags: vector<ObjectID>,
        owner: address,
    ): ArticleCreated {
        ArticleCreated {
            id: option::none(),
            title,
            author,
            content,
            references,
            tags,
            owner,
        }
    }

    struct ReferenceAdded has key {
        id: ObjectID,
        version: u64,
        reference_number: u64,
        title: String,
        url: Option<String>,
    }

    public fun reference_added_id(reference_added: &ReferenceAdded): ObjectID {
        reference_added.id
    }

    public fun reference_added_reference_number(reference_added: &ReferenceAdded): u64 {
        reference_added.reference_number
    }

    public fun reference_added_title(reference_added: &ReferenceAdded): String {
        reference_added.title
    }

    public fun reference_added_url(reference_added: &ReferenceAdded): Option<String> {
        reference_added.url
    }

    public(friend) fun new_reference_added(
        article_obj: &Object<Article>,
        reference_number: u64,
        title: String,
        url: Option<String>,
    ): ReferenceAdded {
        ReferenceAdded {
            id: id(article_obj),
            version: version(article_obj),
            reference_number,
            title,
            url,
        }
    }

    struct ReferenceUpdated has key {
        id: ObjectID,
        version: u64,
        reference_number: u64,
        title: String,
        url: Option<String>,
        author: Option<String>,
    }

    public fun reference_updated_id(reference_updated: &ReferenceUpdated): ObjectID {
        reference_updated.id
    }

    public fun reference_updated_reference_number(reference_updated: &ReferenceUpdated): u64 {
        reference_updated.reference_number
    }

    public fun reference_updated_title(reference_updated: &ReferenceUpdated): String {
        reference_updated.title
    }

    public fun reference_updated_url(reference_updated: &ReferenceUpdated): Option<String> {
        reference_updated.url
    }

    public fun reference_updated_author(reference_updated: &ReferenceUpdated): Option<String> {
        reference_updated.author
    }

    public(friend) fun new_reference_updated(
        article_obj: &Object<Article>,
        reference_number: u64,
        title: String,
        url: Option<String>,
        author: Option<String>,
    ): ReferenceUpdated {
        ReferenceUpdated {
            id: id(article_obj),
            version: version(article_obj),
            reference_number,
            title,
            url,
            author,
        }
    }

    struct ReferenceRemoved has key {
        id: ObjectID,
        version: u64,
        reference_number: u64,
    }

    public fun reference_removed_id(reference_removed: &ReferenceRemoved): ObjectID {
        reference_removed.id
    }

    public fun reference_removed_reference_number(reference_removed: &ReferenceRemoved): u64 {
        reference_removed.reference_number
    }

    public(friend) fun new_reference_removed(
        article_obj: &Object<Article>,
        reference_number: u64,
    ): ReferenceRemoved {
        ReferenceRemoved {
            id: id(article_obj),
            version: version(article_obj),
            reference_number,
        }
    }


    public(friend) fun create_article(
        storage_ctx: &mut StorageContext,
        title: String,
        author: address,
        content: String,
        tags: vector<ObjectID>,
    ): Object<Article> {
        let tx_ctx = storage_context::tx_context_mut(storage_ctx);
        let article = new_article(
            tx_ctx,
            title,
            author,
            content,
            tags,
        );
        let obj_owner = tx_context::sender(tx_ctx);
        let article_obj = object::new(
            tx_ctx,
            obj_owner,
            article,
        );
        article_obj
    }

    public(friend) fun update_version_and_add(storage_ctx: &mut StorageContext, article_obj: Object<Article>) {
        object::borrow_mut(&mut article_obj).version = object::borrow( &mut article_obj).version + 1;
        //assert!(object::borrow(&article_obj).version != 0, EInappropriateVersion);
        private_add_article(storage_ctx, article_obj);
    }

    public(friend) fun remove_article(storage_ctx: &mut StorageContext, obj_id: ObjectID): Object<Article> {
        let obj_store = storage_context::object_storage_mut(storage_ctx);
        object_storage::remove<Article>(obj_store, obj_id)
    }

    public(friend) fun add_article(storage_ctx: &mut StorageContext, article_obj: Object<Article>) {
        assert!(object::borrow(&article_obj).version == 0, EInappropriateVersion);
        private_add_article(storage_ctx, article_obj);
    }

    fun private_add_article(storage_ctx: &mut StorageContext, article_obj: Object<Article>) {
        let obj_store = storage_context::object_storage_mut(storage_ctx);
        object_storage::add(obj_store, article_obj);
    }

    public fun get_article(storage_ctx: &mut StorageContext, obj_id: ObjectID): Object<Article> {
        remove_article(storage_ctx, obj_id)
    }

    public fun return_article(storage_ctx: &mut StorageContext, article_obj: Object<Article>) {
        private_add_article(storage_ctx, article_obj);
    }

    public(friend) fun drop_article(article_obj: Object<Article>) {
        let (_id, _owner, article) =  object::unpack(article_obj);
        let Article {
            version: _version,
            title: _title,
            author: _author,
            content: _content,
            tags: _tags,
            references,
        } = article;
        table::destroy_empty(references);
    }

    public(friend) fun emit_article_created(storage_ctx: &mut StorageContext, article_created: ArticleCreated) {
        event::emit(storage_ctx, article_created);
    }

    public(friend) fun emit_reference_added(storage_ctx: &mut StorageContext, reference_added: ReferenceAdded) {
        event::emit(storage_ctx, reference_added);
    }

    public(friend) fun emit_reference_updated(storage_ctx: &mut StorageContext, reference_updated: ReferenceUpdated) {
        event::emit(storage_ctx, reference_updated);
    }

    public(friend) fun emit_reference_removed(storage_ctx: &mut StorageContext, reference_removed: ReferenceRemoved) {
        event::emit(storage_ctx, reference_removed);
    }

}
