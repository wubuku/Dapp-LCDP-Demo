# Aptos Demo Contracts

```shell
aptos config show-profiles

aptos account fund-with-faucet --account default --amount 50000000000
aptos move compile --named-addresses aptos_demo=default
aptos move publish --named-addresses aptos_demo=default --assume-yes

aptos move run --function-id 'default::aptos_demo_init::initialize' --assume-yes

aptos move run --function-id 'default::product_aggregate::create' --args 'string:test_product_1' 'u128:1000' --assume-yes

aptos move run --function-id 'default::order_aggregate::create' --args 'string:test_order_1' 'string:00000000000000000001' 'u64:1' --assume-yes

aptos move run --function-id 'default::order_aggregate::update_item_quantity' --args 'string:test_order_1' 'string:00000000000000000001' u64:3 --assume-yes
```
