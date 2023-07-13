module rooch_demo::article_update_reference_logic {
    use std::option::{Self, Option};
    use std::string::String;

    use moveos_std::object::Object;
    use moveos_std::storage_context::StorageContext;
    use rooch_demo::article;
    use rooch_demo::reference;
    use rooch_demo::reference_updated;

    friend rooch_demo::article_aggregate;

    public(friend) fun verify(
        _storage_ctx: &mut StorageContext,
        _account: &signer,
        reference_number: u64,
        title: String,
        url: Option<String>,
        author: Option<String>,
        article_obj: &Object<article::Article>,
    ): article::ReferenceUpdated {
        article::new_reference_updated(
            article_obj,
            reference_number,
            title,
            url,
            author,
        )
    }

    public(friend) fun mutate(
        _storage_ctx: &mut StorageContext,
        _account: &signer,
        reference_updated: &article::ReferenceUpdated,
        article_obj: Object<article::Article>,
    ): Object<article::Article> {
        let ref_num = reference_updated::reference_number(reference_updated);
        let ref = article::borrow_mut_reference(&mut article_obj, ref_num);
        reference::set_title(ref, reference_updated::title(reference_updated));
        reference::set_url(ref, reference_updated::url(reference_updated));
        reference::set_author(ref, option::destroy_some(reference_updated::author(reference_updated)));

        article_obj
    }
}
