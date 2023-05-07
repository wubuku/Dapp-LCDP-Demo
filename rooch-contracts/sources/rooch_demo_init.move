module rooch_demo::rooch_demo_init {
    use moveos_std::storage_context::StorageContext;
    use rooch_demo::tag;
    use rooch_demo::article;

    public entry fun initialize(storage_ctx: &mut StorageContext, account: &signer) {
        tag::initialize(storage_ctx, account);
        article::initialize(storage_ctx, account);
    }
}
