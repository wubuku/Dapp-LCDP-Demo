// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

module rooch_demo::rooch_demo_init {
    use moveos_std::storage_context::StorageContext;
    use rooch_demo::article;
    use rooch_demo::product;
    use rooch_demo::tag;

    public entry fun initialize(storage_ctx: &mut StorageContext, account: &signer) {
        article::initialize(storage_ctx, account);
        tag::initialize(storage_ctx, account);
        product::initialize(storage_ctx, account);
    }

}
