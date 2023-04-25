module aptos_demo::aptos_demo_init {

    use aptos_demo::genesis_account;
    use aptos_demo::order;
    use aptos_demo::product;

    public entry fun initialize(account: &signer) {
        genesis_account::initialize(account);
        product::initialize(account);
        order::initialize(account);
    }
}
