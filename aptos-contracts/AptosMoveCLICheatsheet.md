# Aptos Move CLI Cheatsheet

[ToC]

## Order aggregate

### Create method

```shell
aptos move run --function-id 'default::order_aggregate::create' \
--args 'string:order_id' 'string:product_id' u64:quantity \
--assume-yes
```

### RemoveItem method

```shell
aptos move run --function-id 'default::order_aggregate::remove_item' \
--args 'string:order_id' 'string:product_id' \
--assume-yes
```

### UpdateItemQuantity method

```shell
aptos move run --function-id 'default::order_aggregate::update_item_quantity' \
--args 'string:order_id' 'string:product_id' u64:quantity \
--assume-yes
```

### UpdateEstimatedShipDate method

```shell
aptos move run --function-id 'default::order_aggregate::update_estimated_ship_date' \
--args 'string:order_id' u16:estimated_ship_date_month_year_number 'string:estimated_ship_date_month_year_calendar' u8:estimated_ship_date_month_number bool:estimated_ship_date_month_is_leap u8:estimated_ship_date_number 'string:estimated_ship_date_time_zone' \
--assume-yes
```

### AddOrderShipGroup method

```shell
aptos move run --function-id 'default::order_aggregate::add_order_ship_group' \
--args 'string:order_id' u8:ship_group_seq_id 'string:shipment_method' 'string:product_id' u64:quantity \
--assume-yes
```

### CancelOrderShipGroupQuantity method

```shell
aptos move run --function-id 'default::order_aggregate::cancel_order_ship_group_quantity' \
--args 'string:order_id' u8:ship_group_seq_id 'string:product_id' u64:cancel_quantity \
--assume-yes
```

### RemoveOrderShipGroupItem method

```shell
aptos move run --function-id 'default::order_aggregate::remove_order_ship_group_item' \
--args 'string:order_id' u8:ship_group_seq_id 'string:product_id' \
--assume-yes
```

### RemoveOrderShipGroup method

```shell
aptos move run --function-id 'default::order_aggregate::remove_order_ship_group' \
--args 'string:order_id' u8:ship_group_seq_id \
--assume-yes
```

## Product aggregate

### Create method

```shell
aptos move run --function-id 'default::product_aggregate::create' \
--args 'string:name' u128:unit_price \
--assume-yes
```

### Update method

```shell
aptos move run --function-id 'default::product_aggregate::update' \
--args 'string:product_id' 'string:name' u128:unit_price \
--assume-yes
```

### Delete method

```shell
aptos move run --function-id 'default::product_aggregate::delete' \
--args 'string:product_id' \
--assume-yes
```

## DaySummary aggregate

### Create method

```shell
aptos move run --function-id 'default::day_summary_aggregate::create' \
--args u16:day_month_year_number 'string:day_month_year_calendar' u8:day_month_number bool:day_month_is_leap u8:day_number 'string:day_time_zone' 'string:description' 'vector<u8>:meta_data_item_1,meta_data_item_2' 'vector<string>:array_data_item_1,array_data_item_2' 'vector<string>:optional_data' 'vector<u16>:u16_array_data_item_1,u16_array_data_item_2' 'vector<u32>:u32_array_data_item_1,u32_array_data_item_2' 'vector<u64>:u64_array_data_item_1,u64_array_data_item_2' 'vector<u128>:u128_array_data_item_1,u128_array_data_item_2' 'vector<u256>:u256_array_data_item_1,u256_array_data_item_2' \
--assume-yes
```

