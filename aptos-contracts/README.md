# Aptos Demo Contracts

```shell
aptos config show-profiles

aptos init
aptos account fund-with-faucet --account default --amount 50000000000

aptos move compile --named-addresses aptos_demo=default
aptos move publish --named-addresses aptos_demo=default --assume-yes

aptos move run --function-id 'default::aptos_demo_init::initialize' --assume-yes

aptos move run --function-id 'default::product_aggregate::create' --args 'string:test_product_1' 'u128:1000' --assume-yes

aptos move run --function-id 'default::order_aggregate::create' --args 'string:test_order_1' 'string:00000000000000000001' 'u64:1' --assume-yes

aptos move run --function-id 'default::order_aggregate::update_item_quantity' --args 'string:test_order_1' 'string:00000000000000000001' u64:3 --assume-yes

aptos move run --function-id 'default::day_summary_aggregate::create' \
--args u16:2022 'string:ChineseLunar' u8:4 bool:false u8:25 'string:Beijing' 'string:description' 'vector<u8>:1,2,3' 'vector<string>:str1,str2' 'vector<String>:optional_data_item' 'vector<u16>:1,2,3,4,5' 'vector<u32>:1,2,3,4' 'vector<u64>:1,3,5,7' 'vector<u128>:128,129,139' 'vector<u256>:256,257,258' \
--assume-yes
```
