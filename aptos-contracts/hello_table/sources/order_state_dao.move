module hello_table::order_state_dao {
    use std::signer;

    use aptos_std::table::{Self, Table};

    use hello_table::order_state_po::OrderStatePO;

    struct Tables has key {
        order_state_table: Table<vector<u8>, OrderStatePO>,
    }

    public entry fun init_genesis(account: signer) {
        assert!(signer::address_of(&account) == @hello_table, 1);
        if (!exists<Tables>(@hello_table)) {
            move_to(
                &account,
                Tables {
                    order_state_table: table::new(),
                    //order_item_state_table: table::new(),
                },
            )
        };
    }
}
