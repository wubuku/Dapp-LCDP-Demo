module rooch_demo::article_remove_reference_logic {
    use moveos_std::object::Object;
    use moveos_std::storage_context::StorageContext;
    use rooch_demo::article;
    use rooch_demo::reference_removed;

    friend rooch_demo::article_aggregate;

    public(friend) fun verify(
        _storage_ctx: &mut StorageContext,
        _account: &signer,
        reference_number: u64,
        article_obj: &Object<article::Article>,
    ): article::ReferenceRemoved {
        article::new_reference_removed(
            article_obj,
            reference_number,
        )
    }

    public(friend) fun mutate(
        _storage_ctx: &mut StorageContext,
        reference_removed: &article::ReferenceRemoved,
        article_obj: Object<article::Article>,
    ): Object<article::Article> {
        let ref_num = reference_removed::reference_number(reference_removed);
        article::remove_reference(&mut article_obj, ref_num);
        article_obj
    }
}
