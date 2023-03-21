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
            TABLE_NAME        = 'order_item' AND
            CONSTRAINT_TYPE   = 'FOREIGN KEY');
set @sqlVar = if(@fkConstraintName is not null, 
			concat('ALTER TABLE order_item drop foreign key ', @fkConstraintName),
            'select 1');
prepare stmt from @sqlVar;
execute stmt;
deallocate prepare stmt;
set @fkConstraintName = (SELECT CONSTRAINT_NAME FROM information_schema.TABLE_CONSTRAINTS WHERE
            CONSTRAINT_SCHEMA = DATABASE() AND
            TABLE_NAME        = 'order_item_ship_group_assoc_subitem' AND
            CONSTRAINT_TYPE   = 'FOREIGN KEY');
set @sqlVar = if(@fkConstraintName is not null, 
			concat('ALTER TABLE order_item_ship_group_assoc_subitem drop foreign key ', @fkConstraintName),
            'select 1');
prepare stmt from @sqlVar;
execute stmt;
deallocate prepare stmt;
set @fkConstraintName = (SELECT CONSTRAINT_NAME FROM information_schema.TABLE_CONSTRAINTS WHERE
            CONSTRAINT_SCHEMA = DATABASE() AND
            TABLE_NAME        = 'order_item_ship_group_association' AND
            CONSTRAINT_TYPE   = 'FOREIGN KEY');
set @sqlVar = if(@fkConstraintName is not null, 
			concat('ALTER TABLE order_item_ship_group_association drop foreign key ', @fkConstraintName),
            'select 1');
prepare stmt from @sqlVar;
execute stmt;
deallocate prepare stmt;
set @fkConstraintName = (SELECT CONSTRAINT_NAME FROM information_schema.TABLE_CONSTRAINTS WHERE
            CONSTRAINT_SCHEMA = DATABASE() AND
            TABLE_NAME        = 'order_ship_group' AND
            CONSTRAINT_TYPE   = 'FOREIGN KEY');
set @sqlVar = if(@fkConstraintName is not null, 
			concat('ALTER TABLE order_ship_group drop foreign key ', @fkConstraintName),
            'select 1');
prepare stmt from @sqlVar;
execute stmt;
deallocate prepare stmt;
set @fkConstraintName = (SELECT CONSTRAINT_NAME FROM information_schema.TABLE_CONSTRAINTS WHERE
            CONSTRAINT_SCHEMA = DATABASE() AND
            TABLE_NAME        = 'order_v2_item' AND
            CONSTRAINT_TYPE   = 'FOREIGN KEY');
set @sqlVar = if(@fkConstraintName is not null, 
			concat('ALTER TABLE order_v2_item drop foreign key ', @fkConstraintName),
            'select 1');
prepare stmt from @sqlVar;
execute stmt;
deallocate prepare stmt;
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
            TABLE_NAME        = 'order_item' AND
            CONSTRAINT_TYPE   = 'FOREIGN KEY');
set @sqlVar = if(@fkConstraintName is not null, 
			concat('ALTER TABLE order_item drop foreign key ', @fkConstraintName),
            'select 1');
prepare stmt from @sqlVar;
execute stmt;
deallocate prepare stmt;
set @fkConstraintName = (SELECT CONSTRAINT_NAME FROM information_schema.TABLE_CONSTRAINTS WHERE
            CONSTRAINT_SCHEMA = DATABASE() AND
            TABLE_NAME        = 'order_item_ship_group_assoc_subitem' AND
            CONSTRAINT_TYPE   = 'FOREIGN KEY');
set @sqlVar = if(@fkConstraintName is not null, 
			concat('ALTER TABLE order_item_ship_group_assoc_subitem drop foreign key ', @fkConstraintName),
            'select 1');
prepare stmt from @sqlVar;
execute stmt;
deallocate prepare stmt;
set @fkConstraintName = (SELECT CONSTRAINT_NAME FROM information_schema.TABLE_CONSTRAINTS WHERE
            CONSTRAINT_SCHEMA = DATABASE() AND
            TABLE_NAME        = 'order_item_ship_group_association' AND
            CONSTRAINT_TYPE   = 'FOREIGN KEY');
set @sqlVar = if(@fkConstraintName is not null, 
			concat('ALTER TABLE order_item_ship_group_association drop foreign key ', @fkConstraintName),
            'select 1');
prepare stmt from @sqlVar;
execute stmt;
deallocate prepare stmt;
set @fkConstraintName = (SELECT CONSTRAINT_NAME FROM information_schema.TABLE_CONSTRAINTS WHERE
            CONSTRAINT_SCHEMA = DATABASE() AND
            TABLE_NAME        = 'order_ship_group' AND
            CONSTRAINT_TYPE   = 'FOREIGN KEY');
set @sqlVar = if(@fkConstraintName is not null, 
			concat('ALTER TABLE order_ship_group drop foreign key ', @fkConstraintName),
            'select 1');
prepare stmt from @sqlVar;
execute stmt;
deallocate prepare stmt;
set @fkConstraintName = (SELECT CONSTRAINT_NAME FROM information_schema.TABLE_CONSTRAINTS WHERE
            CONSTRAINT_SCHEMA = DATABASE() AND
            TABLE_NAME        = 'order_v2_item' AND
            CONSTRAINT_TYPE   = 'FOREIGN KEY');
set @sqlVar = if(@fkConstraintName is not null, 
			concat('ALTER TABLE order_v2_item drop foreign key ', @fkConstraintName),
            'select 1');
prepare stmt from @sqlVar;
execute stmt;
deallocate prepare stmt;
drop table if exists day_summary;
drop table if exists day_summary_array_data;
drop table if exists day_summary_event;
drop table if exists domain_name;
drop table if exists domain_name_event;
drop table if exists hibernate_hello;
drop table if exists hibernate_sequences;
drop table if exists move_object_id_generator_object;
drop table if exists order_event;
drop table if exists order_item;
drop table if exists order_item_event;
drop table if exists order_item_ship_group_assoc_subitem;
drop table if exists order_item_ship_group_assoc_subitem_event;
drop table if exists order_item_ship_group_association;
drop table if exists order_item_ship_group_association_event;
drop table if exists order_ship_group;
drop table if exists order_ship_group_event;
drop table if exists order_t;
drop table if exists order_v2;
drop table if exists order_v2_event;
drop table if exists order_v2_item;
drop table if exists order_v2_item_event;
drop table if exists product;
drop table if exists product_event;
drop table if exists sui_package;
create table day_summary (day_month_year_number integer not null, day_month_year_calendar varchar(50) not null, day_month_number integer not null, day_month_is_leap bit not null, day_number integer not null, day_time_zone varchar(50) not null, off_chain_version bigint not null, id VARCHAR(42), description varchar(255), metadata tinyblob, optional_data tinyblob, version DECIMAL(20,0), created_by varchar(255), updated_by varchar(255), active bit, deleted bit, created_at datetime, updated_at datetime, primary key (day_month_year_number, day_month_year_calendar, day_month_number, day_month_is_leap, day_number, day_time_zone)) engine=InnoDB;
create table day_summary_array_data (day_month_year_number integer not null, day_month_year_calendar varchar(50) not null, day_month_number integer not null, day_month_is_leap bit not null, day_number integer not null, day_time_zone varchar(50) not null, array_data_item varchar(255) not null, primary key (day_month_year_number, day_month_year_calendar, day_month_number, day_month_is_leap, day_number, day_time_zone, array_data_item)) engine=InnoDB;
create table day_summary_event (day_month_year_number integer not null, day_month_year_calendar varchar(50) not null, day_month_number integer not null, day_month_is_leap bit not null, day_number integer not null, day_time_zone varchar(50) not null, version DECIMAL(20,0) not null, event_type varchar(255) not null, id VARCHAR(42), created_by varchar(255), created_at datetime, command_id varchar(255), sui_timestamp bigint, sui_tx_digest varchar(50), sui_event_seq bigint, sui_package_id VARCHAR(42), sui_transaction_module varchar(100), sui_sender VARCHAR(42), sui_type varchar(200), status CHAR(1), command_type varchar(50), dynamic_properties_lob VARCHAR(2000), primary key (day_month_year_number, day_month_year_calendar, day_month_number, day_month_is_leap, day_number, day_time_zone, version)) engine=InnoDB;
create table domain_name (domain_name_id_top_level_domain varchar(255) not null, domain_name_id_second_level_domain varchar(255) not null, off_chain_version bigint not null, id VARCHAR(42), expiration_date DECIMAL(20,0), version DECIMAL(20,0), created_by varchar(255), updated_by varchar(255), active bit, deleted bit, created_at datetime, updated_at datetime, primary key (domain_name_id_top_level_domain, domain_name_id_second_level_domain)) engine=InnoDB;
create table domain_name_event (domain_name_id_top_level_domain varchar(255) not null, domain_name_id_second_level_domain varchar(255) not null, version DECIMAL(20,0) not null, event_type varchar(255) not null, id VARCHAR(42), created_by varchar(255), created_at datetime, command_id varchar(255), sui_timestamp bigint, sui_tx_digest varchar(50), sui_event_seq bigint, sui_package_id VARCHAR(42), sui_transaction_module varchar(100), sui_sender VARCHAR(42), sui_type varchar(200), status CHAR(1), command_type varchar(50), dynamic_properties_lob VARCHAR(2000), primary key (domain_name_id_top_level_domain, domain_name_id_second_level_domain, version)) engine=InnoDB;
create table hibernate_hello (id bigint not null, message varchar(255), primary key (id)) engine=InnoDB;
create table hibernate_sequences (sequence_name varchar(255) not null, next_val bigint, primary key (sequence_name)) engine=InnoDB;
insert into hibernate_sequences(sequence_name, next_val) values ('default',9999);
create table move_object_id_generator_object (name varchar(255) not null, created_at datetime, created_by varchar(255), object_id varchar(255), object_type varchar(255), updated_at datetime, updated_by varchar(255), primary key (name)) engine=InnoDB;
create table order_event (id VARCHAR(42) not null, version DECIMAL(20,0) not null, event_type varchar(255) not null, created_by varchar(255), created_at datetime, command_id varchar(255), sui_timestamp bigint, sui_tx_digest varchar(50), sui_event_seq bigint, sui_package_id VARCHAR(42), sui_transaction_module varchar(100), sui_sender VARCHAR(42), sui_type varchar(200), status CHAR(1), command_type varchar(50), dynamic_properties_lob VARCHAR(2000), primary key (id, version)) engine=InnoDB;
create table order_item (order_item_id_order_id VARCHAR(42) not null, order_item_id_product_id varchar(50) not null, off_chain_version bigint not null, quantity DECIMAL(20,0), item_amount DECIMAL(20,0), created_by varchar(255), updated_by varchar(255), active bit, deleted bit, created_at datetime, updated_at datetime, primary key (order_item_id_order_id, order_item_id_product_id)) engine=InnoDB;
create table order_item_event (order_item_id_order_id VARCHAR(42) not null, order_item_id_product_id varchar(50) not null, version DECIMAL(20,0) not null, event_type varchar(255) not null, created_by varchar(255), created_at datetime, command_id varchar(255), primary key (order_item_id_order_id, order_item_id_product_id, version)) engine=InnoDB;
create table order_item_ship_group_assoc_subitem (order_v2_order_id varchar(50) not null, order_ship_group_ship_group_seq_id integer not null, order_item_ship_group_association_product_id varchar(100) not null, order_item_ship_group_assoc_subitem_day_month_year_number integer not null, order_item_ship_group_assoc_subitem_day_month_year_calendar varchar(50) not null, order_item_ship_group_assoc_subitem_day_month_number integer not null, order_item_ship_group_assoc_subitem_day_month_is_leap bit not null, order_item_ship_group_assoc_subitem_day_number integer not null, order_item_ship_group_assoc_subitem_day_time_zone varchar(50) not null, off_chain_version bigint not null, description varchar(100), created_by varchar(255), updated_by varchar(255), active bit, deleted bit, created_at datetime, updated_at datetime, primary key (order_v2_order_id, order_ship_group_ship_group_seq_id, order_item_ship_group_association_product_id, order_item_ship_group_assoc_subitem_day_month_year_number, order_item_ship_group_assoc_subitem_day_month_year_calendar, order_item_ship_group_assoc_subitem_day_month_number, order_item_ship_group_assoc_subitem_day_month_is_leap, order_item_ship_group_assoc_subitem_day_number, order_item_ship_group_assoc_subitem_day_time_zone)) engine=InnoDB;
create table order_item_ship_group_assoc_subitem_event (order_v2_order_id varchar(50) not null, order_ship_group_ship_group_seq_id integer not null, order_item_ship_group_association_product_id varchar(100) not null, order_item_ship_group_assoc_subitem_day_month_year_number integer not null, order_item_ship_group_assoc_subitem_day_month_year_calendar varchar(50) not null, order_item_ship_group_assoc_subitem_day_month_number integer not null, order_item_ship_group_assoc_subitem_day_month_is_leap bit not null, order_item_ship_group_assoc_subitem_day_number integer not null, order_item_ship_group_assoc_subitem_day_time_zone varchar(50) not null, version DECIMAL(20,0) not null, event_type varchar(255) not null, created_by varchar(255), created_at datetime, command_id varchar(255), primary key (order_v2_order_id, order_ship_group_ship_group_seq_id, order_item_ship_group_association_product_id, order_item_ship_group_assoc_subitem_day_month_year_number, order_item_ship_group_assoc_subitem_day_month_year_calendar, order_item_ship_group_assoc_subitem_day_month_number, order_item_ship_group_assoc_subitem_day_month_is_leap, order_item_ship_group_assoc_subitem_day_number, order_item_ship_group_assoc_subitem_day_time_zone, version)) engine=InnoDB;
create table order_item_ship_group_association (order_v2_order_item_ship_group_association_id_order_v2_order_id varchar(50) not null, order_v2_ship_group_seq_id integer not null, order_v2_item_ship_group_assoc_product_id varchar(100) not null, off_chain_version bigint not null, quantity DECIMAL(20,0), cancel_quantity DECIMAL(20,0), created_by varchar(255), updated_by varchar(255), active bit, deleted bit, created_at datetime, updated_at datetime, primary key (order_v2_order_item_ship_group_association_id_order_v2_order_id, order_v2_ship_group_seq_id, order_v2_item_ship_group_assoc_product_id)) engine=InnoDB;
create table order_item_ship_group_association_event (order_v2_order_item_ship_group_association_id_order_v2_order_id varchar(50) not null, order_v2_ship_group_seq_id integer not null, order_v2_item_ship_group_assoc_product_id varchar(100) not null, version DECIMAL(20,0) not null, event_type varchar(255) not null, created_by varchar(255), created_at datetime, command_id varchar(255), primary key (order_v2_order_item_ship_group_association_id_order_v2_order_id, order_v2_ship_group_seq_id, order_v2_item_ship_group_assoc_product_id, version)) engine=InnoDB;
create table order_ship_group (order_v2_order_ship_group_id_order_v2_order_id varchar(50) not null, order_v2_ship_group_seq_id integer not null, off_chain_version bigint not null, shipment_method varchar(255), created_by varchar(255), updated_by varchar(255), active bit, deleted bit, created_at datetime, updated_at datetime, primary key (order_v2_order_ship_group_id_order_v2_order_id, order_v2_ship_group_seq_id)) engine=InnoDB;
create table order_ship_group_event (order_v2_order_ship_group_id_order_v2_order_id varchar(50) not null, order_v2_ship_group_seq_id integer not null, version DECIMAL(20,0) not null, event_type varchar(255) not null, created_by varchar(255), created_at datetime, command_id varchar(255), primary key (order_v2_order_ship_group_id_order_v2_order_id, order_v2_ship_group_seq_id, version)) engine=InnoDB;
create table order_t (id varchar(42) not null, off_chain_version bigint not null, total_amount DECIMAL(20,0), version DECIMAL(20,0), created_by varchar(255), updated_by varchar(255), active bit, deleted bit, created_at datetime, updated_at datetime, primary key (id)) engine=InnoDB;
create table order_v2 (order_id varchar(50) not null, off_chain_version bigint not null, id VARCHAR(42), total_amount DECIMAL(20,0), estimated_ship_date_month_year_number integer, estimated_ship_date_month_year_calendar varchar(50), estimated_ship_date_month_number integer, estimated_ship_date_month_is_leap bit, estimated_ship_date_number integer, estimated_ship_date_time_zone varchar(50), version DECIMAL(20,0), created_by varchar(255), updated_by varchar(255), active bit, deleted bit, created_at datetime, updated_at datetime, primary key (order_id)) engine=InnoDB;
create table order_v2_event (order_id varchar(50) not null, version DECIMAL(20,0) not null, event_type varchar(255) not null, id VARCHAR(42), created_by varchar(255), created_at datetime, command_id varchar(255), sui_timestamp bigint, sui_tx_digest varchar(50), sui_event_seq bigint, sui_package_id VARCHAR(42), sui_transaction_module varchar(100), sui_sender VARCHAR(42), sui_type varchar(200), status CHAR(1), command_type varchar(50), dynamic_properties_lob VARCHAR(2000), primary key (order_id, version)) engine=InnoDB;
create table order_v2_item (order_v2_item_id_order_v2_order_id varchar(50) not null, order_v2_item_id_product_id varchar(100) not null, off_chain_version bigint not null, quantity DECIMAL(20,0), item_amount DECIMAL(20,0), created_by varchar(255), updated_by varchar(255), active bit, deleted bit, created_at datetime, updated_at datetime, primary key (order_v2_item_id_order_v2_order_id, order_v2_item_id_product_id)) engine=InnoDB;
create table order_v2_item_event (order_v2_item_id_order_v2_order_id varchar(50) not null, order_v2_item_id_product_id varchar(100) not null, version DECIMAL(20,0) not null, event_type varchar(255) not null, created_by varchar(255), created_at datetime, command_id varchar(255), primary key (order_v2_item_id_order_v2_order_id, order_v2_item_id_product_id, version)) engine=InnoDB;
create table product (product_id varchar(20) not null, off_chain_version bigint not null, id VARCHAR(42), name varchar(255), unit_price DECIMAL(20,0), version DECIMAL(20,0), created_by varchar(255), updated_by varchar(255), active bit, deleted bit, created_at datetime, updated_at datetime, primary key (product_id)) engine=InnoDB;
create table product_event (product_id varchar(20) not null, version DECIMAL(20,0) not null, event_type varchar(255) not null, id VARCHAR(42), created_by varchar(255), created_at datetime, command_id varchar(255), sui_timestamp bigint, sui_tx_digest varchar(50), sui_event_seq bigint, sui_package_id VARCHAR(42), sui_transaction_module varchar(100), sui_sender VARCHAR(42), sui_type varchar(200), status CHAR(1), command_type varchar(50), dynamic_properties_lob VARCHAR(2000), primary key (product_id, version)) engine=InnoDB;
create table sui_package (name varchar(255) not null, created_at datetime, created_by varchar(255), object_id varchar(255), publisher varchar(255), updated_at datetime, updated_by varchar(255), version bigint, primary key (name)) engine=InnoDB;
alter table day_summary add constraint unique_daysummary_sid unique (id);
alter table domain_name add constraint unique_domainname_sid unique (id);
alter table order_v2 add constraint unique_orderv2_sid unique (id);
alter table product add constraint unique_product_sid unique (id);
alter table day_summary_array_data add constraint FK2o1jthqfqexit9icrx6iwvpl8 foreign key (day_month_year_number, day_month_year_calendar, day_month_number, day_month_is_leap, day_number, day_time_zone) references day_summary (day_month_year_number, day_month_year_calendar, day_month_number, day_month_is_leap, day_number, day_time_zone);
alter table order_item add constraint FK613ccnvx1pxajvm27cvxbnwaf foreign key (order_item_id_order_id) references order_t (id);
alter table order_item_ship_group_assoc_subitem add constraint FKam6xdy24ytnicp96rqrct9h3f foreign key (order_v2_order_id, order_ship_group_ship_group_seq_id, order_item_ship_group_association_product_id) references order_item_ship_group_association (order_v2_order_item_ship_group_association_id_order_v2_order_id, order_v2_ship_group_seq_id, order_v2_item_ship_group_assoc_product_id);
alter table order_item_ship_group_association add constraint FK8uwvt4yjfs1hs0wux1i0h75hb foreign key (order_v2_order_item_ship_group_association_id_order_v2_order_id, order_v2_ship_group_seq_id) references order_ship_group (order_v2_order_ship_group_id_order_v2_order_id, order_v2_ship_group_seq_id);
alter table order_ship_group add constraint FK3emc0xwvt61h8y2gp13ybaagy foreign key (order_v2_order_ship_group_id_order_v2_order_id) references order_v2 (order_id);
alter table order_v2_item add constraint FKe7jpjhab3atameit16rnw4vr3 foreign key (order_v2_item_id_order_v2_order_id) references order_v2 (order_id);
