set @fkConstraintName = (SELECT CONSTRAINT_NAME FROM information_schema.TABLE_CONSTRAINTS WHERE
            CONSTRAINT_SCHEMA = DATABASE() AND
            TABLE_NAME        = 'day_summary_array_data' AND
            CONSTRAINT_TYPE   = 'FOREIGN KEY');
set @sqlVar = if(@fkConstraintName is not null, 
			concat('ALTER TABLE day_summary_array_data drop foreign key ', @fkConstraintName),
            'select 1');
prepare stmt from @sqlVar;
execute stmt;
deallocate prepare stmt;
set @fkConstraintName = (SELECT CONSTRAINT_NAME FROM information_schema.TABLE_CONSTRAINTS WHERE
            CONSTRAINT_SCHEMA = DATABASE() AND
            TABLE_NAME        = 'day_summary_state_event_array_data' AND
            CONSTRAINT_TYPE   = 'FOREIGN KEY');
set @sqlVar = if(@fkConstraintName is not null, 
			concat('ALTER TABLE day_summary_state_event_array_data drop foreign key ', @fkConstraintName),
            'select 1');
prepare stmt from @sqlVar;
execute stmt;
deallocate prepare stmt;
drop table if exists day_summary;
drop table if exists day_summary_array_data;
drop table if exists day_summary_event;
drop table if exists day_summary_state_event_array_data;
drop table if exists domain_name;
drop table if exists domain_name_event;
drop table if exists HIBERNATE_HELLO;
drop table if exists hibernate_sequences;
drop table if exists order_event;
drop table if exists order_item;
drop table if exists order_item_event;
drop table if exists order_T;
drop table if exists order_v2;
drop table if exists order_v2_event;
drop table if exists order_v2_item;
drop table if exists order_v2_item_event;
drop table if exists product;
create table day_summary (day_month_year_number integer not null, day_month_year_calendar varchar(20) not null, day_month_number integer not null, day_month_is_leap bit not null, day_number integer not null, day_time_zone varchar(20) not null, version bigint not null, description varchar(255), metadata tinyblob, optional_data tinyblob, created_by varchar(255), updated_by varchar(255), active bit, deleted bit, created_at datetime, updated_at datetime, primary key (day_month_year_number, day_month_year_calendar, day_month_number, day_month_is_leap, day_number, day_time_zone));
create table day_summary_array_data (day_month_year_number integer not null, day_month_year_calendar varchar(20) not null, day_month_number integer not null, day_month_is_leap bit not null, day_number integer not null, day_time_zone varchar(20) not null, ArrayDataItem varchar(255) not null, primary key (day_month_year_number, day_month_year_calendar, day_month_number, day_month_is_leap, day_number, day_time_zone, ArrayDataItem));
create table day_summary_event (day_month_year_number integer not null, day_month_year_calendar varchar(20) not null, day_month_number integer not null, day_month_is_leap bit not null, day_number integer not null, day_time_zone varchar(20) not null, version bigint not null, event_type varchar(255) not null, created_by varchar(255), created_at datetime, command_id varchar(255), description varchar(255), metadata tinyblob, optional_data tinyblob, active bit, is_property_description_removed bit, is_property_metadata_removed bit, is_property_optional_data_removed bit, is_property_active_removed bit, primary key (day_month_year_number, day_month_year_calendar, day_month_number, day_month_is_leap, day_number, day_time_zone, version));
create table day_summary_state_event_array_data (day_month_year_number integer not null, day_month_year_calendar varchar(20) not null, day_month_number integer not null, day_month_is_leap bit not null, day_number integer not null, day_time_zone varchar(20) not null, version bigint not null, ArrayDataItem varchar(255) not null, primary key (day_month_year_number, day_month_year_calendar, day_month_number, day_month_is_leap, day_number, day_time_zone, version, ArrayDataItem));
create table domain_name (domain_name_id_top_level_domain varchar(255) not null, domain_name_id_second_level_domain varchar(255) not null, version bigint not null, expiration_date decimal(19,2), created_by varchar(255), updated_by varchar(255), active bit, deleted bit, created_at datetime, updated_at datetime, primary key (domain_name_id_top_level_domain, domain_name_id_second_level_domain));
create table domain_name_event (domain_name_id_top_level_domain varchar(255) not null, domain_name_id_second_level_domain varchar(255) not null, version bigint not null, event_type varchar(255) not null, created_by varchar(255), created_at datetime, command_id varchar(255), expiration_date decimal(19,2), active bit, is_property_expiration_date_removed bit, is_property_active_removed bit, primary key (domain_name_id_top_level_domain, domain_name_id_second_level_domain, version));
create table HIBERNATE_HELLO (id bigint not null, message varchar(255), primary key (id));
create table hibernate_sequences (sequence_name varchar(255) not null, next_val bigint, primary key (sequence_name));
create table order_event (id VARCHAR(42) not null, version bigint not null, event_type varchar(255) not null, created_by varchar(255), created_at datetime, command_id varchar(255), total_amount decimal(19,2), active bit, is_property_total_amount_removed bit, is_property_active_removed bit, primary key (id, version));
create table order_item (order_item_id_order_id VARCHAR(42) not null, order_item_id_product_id varchar(50) not null, version bigint not null, quantity decimal(19,2), item_amount decimal(19,2), created_by varchar(255), updated_by varchar(255), active bit, deleted bit, created_at datetime, updated_at datetime, primary key (order_item_id_order_id, order_item_id_product_id));
create table order_item_event (order_item_id_order_id VARCHAR(42) not null, order_item_id_product_id varchar(50) not null, order_version bigint not null, event_type varchar(255) not null, created_by varchar(255), created_at datetime, command_id varchar(255), quantity decimal(19,2), item_amount decimal(19,2), active bit, version bigint not null, is_property_quantity_removed bit, is_property_item_amount_removed bit, is_property_active_removed bit, primary key (order_item_id_order_id, order_item_id_product_id, order_version));
create table order_T (id varchar(42) not null, version bigint not null, total_amount decimal(19,2), created_by varchar(255), updated_by varchar(255), active bit, deleted bit, created_at datetime, updated_at datetime, primary key (id));
create table order_v2 (order_id varchar(50) not null, version bigint not null, total_amount decimal(19,2), estimated_ship_date_month_year_number integer, estimated_ship_date_month_year_calendar varchar(20), estimated_ship_date_month_number integer, estimated_ship_date_month_is_leap bit, estimated_ship_date_number integer, estimated_ship_date_time_zone varchar(20), created_by varchar(255), updated_by varchar(255), active bit, deleted bit, created_at datetime, updated_at datetime, primary key (order_id));
create table order_v2_event (order_id varchar(50) not null, version bigint not null, event_type varchar(255) not null, created_by varchar(255), created_at datetime, command_id varchar(255), total_amount decimal(19,2), estimated_ship_date_month_year_number integer, estimated_ship_date_month_year_calendar varchar(20), estimated_ship_date_month_number integer, estimated_ship_date_month_is_leap bit, estimated_ship_date_number integer, estimated_ship_date_time_zone varchar(20), active bit, is_property_total_amount_removed bit, is_property_estimated_ship_date_removed bit, is_property_active_removed bit, primary key (order_id, version));
create table order_v2_item (order_v2_item_id_order_v2_order_id varchar(50) not null, order_v2_item_id_product_id varchar(50) not null, version bigint not null, quantity decimal(19,2), item_amount decimal(19,2), created_by varchar(255), updated_by varchar(255), active bit, deleted bit, created_at datetime, updated_at datetime, primary key (order_v2_item_id_order_v2_order_id, order_v2_item_id_product_id));
create table order_v2_item_event (order_v2_item_id_order_v2_order_id varchar(50) not null, order_v2_item_id_product_id varchar(50) not null, order_v2_version bigint not null, event_type varchar(255) not null, created_by varchar(255), created_at datetime, command_id varchar(255), quantity decimal(19,2), item_amount decimal(19,2), active bit, version bigint not null, is_property_quantity_removed bit, is_property_item_amount_removed bit, is_property_active_removed bit, primary key (order_v2_item_id_order_v2_order_id, order_v2_item_id_product_id, order_v2_version));
create table product (product_id varchar(20) not null, version bigint not null, name varchar(255), unit_price decimal(19,2), created_by varchar(255), updated_by varchar(255), active bit, created_at datetime, updated_at datetime, command_id varchar(255), primary key (product_id));
alter table day_summary_array_data add constraint FK2o1jthqfqexit9icrx6iwvpl8 foreign key (day_month_year_number, day_month_year_calendar, day_month_number, day_month_is_leap, day_number, day_time_zone) references day_summary (day_month_year_number, day_month_year_calendar, day_month_number, day_month_is_leap, day_number, day_time_zone);
alter table day_summary_state_event_array_data add constraint FKrtgvbi9h7rbgf5nc1xsal07vh foreign key (day_month_year_number, day_month_year_calendar, day_month_number, day_month_is_leap, day_number, day_time_zone, version) references day_summary_event (day_month_year_number, day_month_year_calendar, day_month_number, day_month_is_leap, day_number, day_time_zone, version);
