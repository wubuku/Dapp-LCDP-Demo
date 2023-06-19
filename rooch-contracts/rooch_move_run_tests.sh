#!/bin/bash

contract_address=0xf8e38d63a5208d499725e7ac4851c4a0836e45e2230041b7e3cf43e4738c47b4

rooch move publish --named-addresses rooch_demo=$contract_address

rooch move run --function $contract_address::rooch_demo_init::initialize --sender-account $contract_address

rooch move run --function $contract_address::day_summary_aggregate::create --sender-account $contract_address --args u16:2023 string:"ChineseLunar" u8:6 bool:false u8:18 string:"Beijing" string:"bar" string:"foo" vector\<string\>:"item1","item2" vector\<string\>:"optional_1" vector\<u16\>:1,6 vector\<u32\>:3,2 vector\<u64\>:6,4 vector\<u128\>:1,2,8 vector\<u256\>:2,5,6

rooch move run --function $contract_address::product_aggregate::create --sender-account $contract_address --args String:"product_1" u128:10000

product_object_id=$(curl --location --request POST 'http://localhost:50051' \
--header 'Content-Type: application/json' \
--data-raw '{
 "id":101,
 "jsonrpc":"2.0",
 "method":"rooch_getEventsByEventHandle",
 "params":["'$contract_address'::product::ProductCreated"]
}' | jq '.result.data[0].parsed_event_data.value.id.value.vec[0]')

echo "$product_object_id"
product_object_id=$(echo "$product_object_id" | sed 's/"//g')
echo "$product_object_id"

rooch move run --function $contract_address::order_aggregate::create --sender-account $contract_address --args string:"ord_002" object_id:"$product_object_id" u64:1

order_object_id=$(curl --location --request POST 'http://localhost:50051' \
--header 'Content-Type: application/json' \
--data-raw '{
 "id":101,
 "jsonrpc":"2.0",
 "method":"rooch_getEventsByEventHandle",
 "params":["'$contract_address'::order::OrderCreated"]
}' | jq '.result.data[0].parsed_event_data.value.id.value.vec[0]')

echo "$order_object_id"
order_object_id=$(echo "$order_object_id" | sed 's/"//g')
echo "$order_object_id"

rooch move run --function $contract_address::order_aggregate::add_order_ship_group --sender-account $contract_address --args object_id:"$order_object_id" u8:1 string:"ship_method_1" object_id:"$product_object_id" u64:1

rooch move run --function $contract_address::order_aggregate::add_order_item_ship_group_assoc_subitem --sender-account $contract_address --args object_id:"$order_object_id" u8:1 object_id:"$product_object_id" u16:2023 string:"ChineseLunar" u8:6 bool:false u8:18 string:"Beijing" string:"desc"



