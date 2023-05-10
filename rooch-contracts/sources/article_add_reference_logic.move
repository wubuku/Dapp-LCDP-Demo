module rooch_demo::article_add_reference_logic {
    use rooch_demo::article;
    use moveos_std::storage_context::StorageContext;
    use moveos_std::object::{Object};
    // use std::signer;
    // use std::string::String;
    // use std::string;
    // use std::vector;
    // use rooch_demo::tag;
//
//     friend rooch_demo::article_aggregate;
//
//     public(friend) fun verify(
//         storage_ctx: &mut StorageContext,
//         account: &signer,
//
//     ): article::ReferenceAdded {
//         let _ = storage_ctx;
//         let owner = signer::address_of(account);
//
//
//     }
//
    public(friend) fun mutate(
        _storage_ctx: &mut StorageContext,
        //reference_added: &article::ReferenceAdded,
        article: Object<article::Article>,
    ): Object<article::Article> {
        // let article = article::create_article(
        //     storage_ctx,
        //     article::reference_added_title(reference_added),
        //     article::reference_added_content(reference_added),
        //     article::reference_added_author(reference_added),
        //     article::reference_added_tags(reference_added),
        // );
        article
    }
}
