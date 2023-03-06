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
drop table if exists day_summary;
drop table if exists day_summary_array_data;
drop table if exists day_summary_event;
drop table if exists domain_name;
drop table if exists domain_name_event;
drop table if exists HIBERNATE_HELLO;
drop table if exists hibernate_sequences;
drop table if exists order_event;
drop table if exists order_item;
drop table if exists order_item_event;
drop table if exists order_t;
drop table if exists order_v2;
drop table if exists order_v2_event;
drop table if exists order_v2_item;
drop table if exists order_v2_item_event;
drop table if exists product;
drop table if exists sui_id_generator_data_object;
create table day_summary (day_month_year_number integer not null, day_month_year_calendar varchar(20) not null, day_month_number integer not null, day_month_is_leap bit not null, day_number integer not null, day_time_zone varchar(20) not null, off_chain_version bigint not null, id VARCHAR(42), description varchar(255), metadata tinyblob, optional_data tinyblob, version DECIMAL(20,0), created_by varchar(255), updated_by varchar(255), active bit, deleted bit, created_at datetime, updated_at datetime, primary key (day_month_year_number, day_month_year_calendar, day_month_number, day_month_is_leap, day_number, day_time_zone));
create table day_summary_array_data (day_month_year_number integer not null, day_month_year_calendar varchar(20) not null, day_month_number integer not null, day_month_is_leap bit not null, day_number integer not null, day_time_zone varchar(20) not null, array_data_item varchar(255) not null, primary key (day_month_year_number, day_month_year_calendar, day_month_number, day_month_is_leap, day_number, day_time_zone, array_data_item));
create table day_summary_event (day_month_year_number integer not null, day_month_year_calendar varchar(20) not null, day_month_number integer not null, day_month_is_leap bit not null, day_number integer not null, day_time_zone varchar(20) not null, off_chain_version bigint not null, event_type varchar(255) not null, id VARCHAR(42), created_by varchar(255), created_at datetime, command_id varchar(255), version DECIMAL(20,0), command_type varchar(50), lob_text VARCHAR(2000), primary key (day_month_year_number, day_month_year_calendar, day_month_number, day_month_is_leap, day_number, day_time_zone, off_chain_version));
create table domain_name (domain_name_id_top_level_domain varchar(255) not null, domain_name_id_second_level_domain varchar(255) not null, off_chain_version bigint not null, id VARCHAR(42), expiration_date DECIMAL(20,0), version DECIMAL(20,0), created_by varchar(255), updated_by varchar(255), active bit, deleted bit, created_at datetime, updated_at datetime, primary key (domain_name_id_top_level_domain, domain_name_id_second_level_domain));
create table domain_name_event (domain_name_id_top_level_domain varchar(255) not null, domain_name_id_second_level_domain varchar(255) not null, off_chain_version bigint not null, event_type varchar(255) not null, id VARCHAR(42), created_by varchar(255), created_at datetime, command_id varchar(255), version DECIMAL(20,0), command_type varchar(50), lob_text VARCHAR(2000), primary key (domain_name_id_top_level_domain, domain_name_id_second_level_domain, off_chain_version));
create table HIBERNATE_HELLO (id bigint not null, message varchar(255), primary key (id));
create table hibernate_sequences (sequence_name varchar(255) not null, next_val bigint, primary key (sequence_name));
insert into hibernate_sequences(sequence_name, next_val) values ('default',9999);
create table order_event (id VARCHAR(42) not null, off_chain_version bigint not null, event_type varchar(255) not null, created_by varchar(255), created_at datetime, command_id varchar(255), version DECIMAL(20,0), command_type varchar(50), lob_text VARCHAR(2000), primary key (id, off_chain_version));
create table order_item (order_item_id_order_id VARCHAR(42) not null, order_item_id_product_id varchar(50) not null, off_chain_version bigint not null, quantity DECIMAL(20,0), item_amount DECIMAL(20,0), created_by varchar(255), updated_by varchar(255), active bit, deleted bit, created_at datetime, updated_at datetime, primary key (order_item_id_order_id, order_item_id_product_id));
create table order_item_event (order_item_id_order_id VARCHAR(42) not null, order_item_id_product_id varchar(50) not null, order_off_chain_version bigint not null, event_type varchar(255) not null, created_by varchar(255), created_at datetime, command_id varchar(255), primary key (order_item_id_order_id, order_item_id_product_id, order_off_chain_version));
create table order_t (id varchar(42) not null, off_chain_version bigint not null, total_amount DECIMAL(20,0), version DECIMAL(20,0), created_by varchar(255), updated_by varchar(255), active bit, deleted bit, created_at datetime, updated_at datetime, primary key (id));
create table order_v2 (order_id varchar(50) not null, off_chain_version bigint not null, id VARCHAR(42), total_amount DECIMAL(20,0), estimated_ship_date_month_year_number integer, estimated_ship_date_month_year_calendar varchar(20), estimated_ship_date_month_number integer, estimated_ship_date_month_is_leap bit, estimated_ship_date_number integer, estimated_ship_date_time_zone varchar(20), version DECIMAL(20,0), created_by varchar(255), updated_by varchar(255), active bit, deleted bit, created_at datetime, updated_at datetime, primary key (order_id));
create table order_v2_event (order_id varchar(50) not null, off_chain_version bigint not null, event_type varchar(255) not null, id VARCHAR(42), created_by varchar(255), created_at datetime, command_id varchar(255), version DECIMAL(20,0), command_type varchar(50), lob_text VARCHAR(2000), primary key (order_id, off_chain_version));
create table order_v2_item (order_v2_item_id_order_v2_order_id varchar(50) not null, order_v2_item_id_product_id varchar(50) not null, off_chain_version bigint not null, quantity DECIMAL(20,0), item_amount DECIMAL(20,0), created_by varchar(255), updated_by varchar(255), active bit, deleted bit, created_at datetime, updated_at datetime, primary key (order_v2_item_id_order_v2_order_id, order_v2_item_id_product_id));
create table order_v2_item_event (order_v2_item_id_order_v2_order_id varchar(50) not null, order_v2_item_id_product_id varchar(50) not null, order_v2_off_chain_version bigint not null, event_type varchar(255) not null, created_by varchar(255), created_at datetime, command_id varchar(255), primary key (order_v2_item_id_order_v2_order_id, order_v2_item_id_product_id, order_v2_off_chain_version));
create table product (product_id varchar(20) not null, off_chain_version bigint not null, id VARCHAR(42), name varchar(255), unit_price DECIMAL(20,0), version DECIMAL(20,0), created_by varchar(255), updated_by varchar(255), active bit, created_at datetime, updated_at datetime, command_id varchar(255), primary key (product_id));
create table sui_id_generator_data_object (object_name varchar(200) not null, sui_object_id varchar(42), sui_object_type varchar(200), created_by varchar(255), updated_by varchar(255), created_at datetime, updated_at datetime, primary key (object_name));
alter table day_summary add constraint unique_daysummary_sid unique (id);
alter table domain_name add constraint unique_domainname_sid unique (id);
alter table order_v2 add constraint unique_orderv2_sid unique (id);
alter table product add constraint unique_product_sid unique (id);
alter table day_summary_array_data add constraint FK2o1jthqfqexit9icrx6iwvpl8 foreign key (day_month_year_number, day_month_year_calendar, day_month_number, day_month_is_leap, day_number, day_time_zone) references day_summary (day_month_year_number, day_month_year_calendar, day_month_number, day_month_is_leap, day_number, day_time_zone);
