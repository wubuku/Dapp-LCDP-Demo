module rooch_demo::article_add_reference_logic {
    use std::option;
    use std::string::{Self, String};

    use moveos_std::object::Object;
    use moveos_std::storage_context::StorageContext;
    use rooch_demo::article::{Self, Article};
    use rooch_demo::reference;

    friend rooch_demo::article_aggregate;

    public(friend) fun verify(
        storage_ctx: &mut StorageContext,
        account: &signer,
        article_obj: &Object<Article>,
        reference_number: u64,
        title: String,
        url: String,
    ): article::ReferenceAdded {
        let _ = storage_ctx;
        let _ = account;
        //let owner = signer::address_of(account);
        article::new_reference_added(
            article_obj,
            reference_number,
            title,
            option::some(url),
        )
    }

    public(friend) fun mutate(
        _storage_ctx: &mut StorageContext,
        reference_added: &article::ReferenceAdded,
        article_obj: Object<article::Article>,
    ): Object<article::Article> {
        let _ = _storage_ctx;
        let ref = reference::new_reference(
            article::reference_added_reference_number(reference_added),
            article::reference_added_title(reference_added),
            string::utf8(b"Unknown"),
            option::none(),
            option::none(),
            option::none(),
            article::reference_added_url(reference_added),
            option::none(),
        );
        article::add_reference(&mut article_obj, ref);
        article_obj
    }
}
