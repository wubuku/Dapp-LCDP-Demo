# Rooch Move CLI Cheatsheet

[ToC]

## Article aggregate

### Create method

```shell
rooch move run --sender-account _SENDER_ADDRESS_ --function '_CONTRACT_ADDRESS_::article_aggregate::create' \
--args 'string:title' address:author 'string:content' 'vector<u8>:references_item' 'vector<object_id>:tags_item'
```

### AddReference method

```shell
rooch move run --sender-account _SENDER_ADDRESS_ --function '_CONTRACT_ADDRESS_::article_aggregate::add_reference' \
--args 'object_id:id' u64:reference_number 'string:title' 'vector<string>:url_item'
```

### UpdateReference method

```shell
rooch move run --sender-account _SENDER_ADDRESS_ --function '_CONTRACT_ADDRESS_::article_aggregate::update_reference' \
--args 'object_id:id' u64:reference_number 'string:title' 'vector<string>:url_item' 'vector<string>:author_item'
```

### RemoveReference method

```shell
rooch move run --sender-account _SENDER_ADDRESS_ --function '_CONTRACT_ADDRESS_::article_aggregate::remove_reference' \
--args 'object_id:id' u64:reference_number
```

## Tag aggregate

### Create method

```shell
rooch move run --sender-account _SENDER_ADDRESS_ --function '_CONTRACT_ADDRESS_::tag_aggregate::create' \
--args 'string:name'
```

## Product aggregate

### Create method

```shell
rooch move run --sender-account _SENDER_ADDRESS_ --function '_CONTRACT_ADDRESS_::product_aggregate::create' \
--args 'string:name' u128:unit_price
```

## Order aggregate

### Create method

```shell
rooch move run --sender-account _SENDER_ADDRESS_ --function '_CONTRACT_ADDRESS_::order_aggregate::create' \
--args 'string:order_id' 'object_id:product_obj_id' u64:quantity
```

### RemoveItem method

```shell
rooch move run --sender-account _SENDER_ADDRESS_ --function '_CONTRACT_ADDRESS_::order_aggregate::remove_item' \
--args 'object_id:id' 'object_id:product_obj_id'
```

### UpdateItemQuantity method

```shell
rooch move run --sender-account _SENDER_ADDRESS_ --function '_CONTRACT_ADDRESS_::order_aggregate::update_item_quantity' \
--args 'object_id:id' 'object_id:product_obj_id' u64:quantity
```

### UpdateEstimatedShipDate method

```shell
rooch move run --sender-account _SENDER_ADDRESS_ --function '_CONTRACT_ADDRESS_::order_aggregate::update_estimated_ship_date' \
--args 'object_id:id' u16:estimated_ship_date_month_year_number 'string:estimated_ship_date_month_year_calendar' u8:estimated_ship_date_month_number bool:estimated_ship_date_month_is_leap u8:estimated_ship_date_number 'string:estimated_ship_date_time_zone'
```

### AddOrderShipGroup method

```shell
rooch move run --sender-account _SENDER_ADDRESS_ --function '_CONTRACT_ADDRESS_::order_aggregate::add_order_ship_group' \
--args 'object_id:id' u8:ship_group_seq_id 'string:shipment_method' 'object_id:product_obj_id' u64:quantity
```

### AddOrderItemShipGroupAssocSubitem method

```shell
rooch move run --sender-account _SENDER_ADDRESS_ --function '_CONTRACT_ADDRESS_::order_aggregate::add_order_item_ship_group_assoc_subitem' \
--args 'object_id:id' u8:ship_group_seq_id 'object_id:product_obj_id' u16:day_month_year_number 'string:day_month_year_calendar' u8:day_month_number bool:day_month_is_leap u8:day_number 'string:day_time_zone' 'string:description'
```

### CancelOrderShipGroupQuantity method

```shell
rooch move run --sender-account _SENDER_ADDRESS_ --function '_CONTRACT_ADDRESS_::order_aggregate::cancel_order_ship_group_quantity' \
--args 'object_id:id' u8:ship_group_seq_id 'object_id:product_obj_id' u64:cancel_quantity
```

### RemoveOrderShipGroupItem method

```shell
rooch move run --sender-account _SENDER_ADDRESS_ --function '_CONTRACT_ADDRESS_::order_aggregate::remove_order_ship_group_item' \
--args 'object_id:id' u8:ship_group_seq_id 'object_id:product_obj_id'
```

## DaySummary aggregate

### Create method

```shell
rooch move run --sender-account _SENDER_ADDRESS_ --function '_CONTRACT_ADDRESS_::day_summary_aggregate::create' \
--args u16:day_month_year_number 'string:day_month_year_calendar' u8:day_month_number bool:day_month_is_leap u8:day_number 'string:day_time_zone' 'string:description' 'vector<u8>:meta_data_item' 'vector<string>:array_data_item' 'vector<string>:optional_data_item' 'vector<u16>:u16_array_data_item' 'vector<u32>:u32_array_data_item' 'vector<u64>:u64_array_data_item' 'vector<u128>:u128_array_data_item' 'vector<u256>:u256_array_data_item'
```

