// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

module aptos_demo::aptos_demo_init {
    use aptos_demo::day_summary;
    use aptos_demo::genesis_account;
    use aptos_demo::order;
    use aptos_demo::product;

    public entry fun initialize(account: &signer) {
        genesis_account::initialize(account);
        order::initialize(account);
        product::initialize(account);
        day_summary::initialize(account);
    }

}
