# Sui Client CLI Cheatsheet

[ToC]

## DomainName aggregate

### Register method

```shell
sui client call --package _PACKAGE_ID_ --module domain_name_aggregate --function register \
--args '"string_domain_name_id_top_level_domain"' '"string_domain_name_id_second_level_domain"' \"u64_registration_period\" \"_DOMAIN_NAME_DOMAIN_NAME_ID_TABLE_OBJECT_ID_\" \
--gas-budget 100000
```

### Renew method

```shell
sui client call --package _PACKAGE_ID_ --module domain_name_aggregate --function renew \
--args domain_name_Object_ID \"u64_renew_period\" \
--gas-budget 100000
```

## Order aggregate

### Create method

```shell
sui client call --package _PACKAGE_ID_ --module order_aggregate --function create \
--args \"_PRODUCT_OBJECT_ID_\" \"u64_quantity\" \
--gas-budget 100000
```

### RemoveItem method

```shell
sui client call --package _PACKAGE_ID_ --module order_aggregate --function remove_item \
--args order_Object_ID '"string_product_id"' \
--gas-budget 100000
```

### UpdateItemQuantity method

```shell
sui client call --package _PACKAGE_ID_ --module order_aggregate --function update_item_quantity \
--args order_Object_ID '"string_product_id"' \"u64_quantity\" \
--gas-budget 100000
```

### Delete method

```shell
sui client call --package _PACKAGE_ID_ --module order_aggregate --function delete \
--args order_Object_ID \
--gas-budget 100000
```

## Product aggregate

### Create method

```shell
sui client call --package _PACKAGE_ID_ --module product_aggregate --function create \
--args '"string_name"' \"u128_unit_price\" \"_PRODUCT_PRODUCT_ID_GENERATOR_OBJECT_ID_\" \
--gas-budget 100000
```

### Update method

```shell
sui client call --package _PACKAGE_ID_ --module product_aggregate --function update \
--args product_Object_ID '"string_name"' \"u128_unit_price\" \
--gas-budget 100000
```

### Delete method

```shell
sui client call --package _PACKAGE_ID_ --module product_aggregate --function delete \
--args product_Object_ID \
--gas-budget 100000
```

## OrderV2 aggregate

### Create method

```shell
sui client call --package _PACKAGE_ID_ --module order_v2_aggregate --function create \
--args '"string_order_id"' \"_PRODUCT_OBJECT_ID_\" \"u64_quantity\" \"_ORDER_V2_ORDER_ID_TABLE_OBJECT_ID_\" \
--gas-budget 100000
```

### RemoveItem method

```shell
sui client call --package _PACKAGE_ID_ --module order_v2_aggregate --function remove_item \
--args order_v2_Object_ID '"string_product_id"' \
--gas-budget 100000
```

### UpdateItemQuantity method

```shell
sui client call --package _PACKAGE_ID_ --module order_v2_aggregate --function update_item_quantity \
--args order_v2_Object_ID '"string_product_id"' \"u64_quantity\" \
--gas-budget 100000
```

### UpdateEstimatedShipDate method

```shell
sui client call --package _PACKAGE_ID_ --module order_v2_aggregate --function update_estimated_ship_date \
--args order_v2_Object_ID u16_estimated_ship_date_month_year_number '"string_estimated_ship_date_month_year_calendar"' u8_estimated_ship_date_month_number bool_estimated_ship_date_month_is_leap u8_estimated_ship_date_number '"string_estimated_ship_date_time_zone"' \
--gas-budget 100000
```

### AddOrderShipGroup method

```shell
sui client call --package _PACKAGE_ID_ --module order_v2_aggregate --function add_order_ship_group \
--args order_v2_Object_ID u8_ship_group_seq_id '"string_shipment_method"' '"string_product_id"' \"u64_quantity\" \
--gas-budget 100000
```

### CancelOrderShipGroupQuantity method

```shell
sui client call --package _PACKAGE_ID_ --module order_v2_aggregate --function cancel_order_ship_group_quantity \
--args order_v2_Object_ID u8_ship_group_seq_id '"string_product_id"' \"u64_cancel_quantity\" \
--gas-budget 100000
```

### RemoveOrderShipGroupItem method

```shell
sui client call --package _PACKAGE_ID_ --module order_v2_aggregate --function remove_order_ship_group_item \
--args order_v2_Object_ID u8_ship_group_seq_id '"string_product_id"' \
--gas-budget 100000
```

### RemoveOrderShipGroup method

```shell
sui client call --package _PACKAGE_ID_ --module order_v2_aggregate --function remove_order_ship_group \
--args order_v2_Object_ID u8_ship_group_seq_id \
--gas-budget 100000
```

## DaySummary aggregate

### Create method

```shell
sui client call --package _PACKAGE_ID_ --module day_summary_aggregate --function create \
--args u16_day_month_year_number '"string_day_month_year_calendar"' u8_day_month_number bool_day_month_is_leap u8_day_number '"string_day_time_zone"' '"string_description"' \"vector_u8_meta_data\" '["string_array_data_item"]' '["vector_u8_optional_data"]' \"_DAY_SUMMARY_DAY_SUMMARY_ID_TABLE_OBJECT_ID_\" \
--gas-budget 100000
```


---

## Tips

You can escape single quotes in string arguments by using the following method when enclosing them within single quotes in a shell:

1. Close the current single quote.
2. Use a backslash `\` to escape the single quote.
3. Open a new set of single quotes to continue the string.

Here is an example of how to escape a single quote within a string enclosed by single quotes in a shell:

```shell
echo 'It'\''s a beautiful day'
```

