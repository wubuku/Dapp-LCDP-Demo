// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

module rooch_demo::article_aggregate {
    use moveos_std::object::ObjectID;
    use moveos_std::storage_context::StorageContext;
    use rooch_demo::article;
    use rooch_demo::article_add_reference_logic;
    use rooch_demo::article_create_logic;
    use rooch_demo::reference_vo;
    use std::option::{Self, Option};
    use std::string::String;
    use std::vector;

    public entry fun create(
        storage_ctx: &mut StorageContext,
        account: &signer,
        title: String,
        author: address,
        content: String,
        references: vector<u8>,
        tags: vector<ObjectID>,
    ) {
        let article_created = article_create_logic::verify(
            storage_ctx,
            account,
            title,
            author,
            content,
            reference_vo::vector_from_bytes(references),
            tags,
        );
        let article_obj = article_create_logic::mutate(
            storage_ctx,
            &article_created,
        );
        article::set_article_created_id(&mut article_created, article::id(&article_obj));
        article::add_article(storage_ctx, article_obj);
    }


    public entry fun add_reference(
        storage_ctx: &mut StorageContext,
        account: &signer,
        id: ObjectID,
        reference_number: u64,
        title: String,
        url: vector<String>,
    ) {
        let article_obj = article::remove_article(storage_ctx, id);
        let reference_added = article_add_reference_logic::verify(
            storage_ctx,
            account,
            reference_number,
            title,
            vector_to_option(url),
            &article_obj,
        );
        let updated_article_obj = article_add_reference_logic::mutate(
            storage_ctx,
            &reference_added,
            article_obj,
        );
        article::update_version_and_add(storage_ctx, updated_article_obj);
    }

    fun vector_to_option<V : drop>(v: vector<V>): Option<V> {
        if (vector::length(&v) == 0) { option::none() } else {
            option::some(
                vector::pop_back(&mut v)
            )
        }
    }

}
