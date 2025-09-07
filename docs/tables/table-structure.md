# Table Structure and Relations

## Configuration Tables

**config_customer_order_header_status**

```sql
CREATE TABLE `config_customer_order_header_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `is_enabled` tinyint DEFAULT '1',
  PRIMARY KEY (`id`)
)
```

**config_customer_order_line_status**

```sql
CREATE TABLE `config_customer_order_line_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `is_enabled` tinyint DEFAULT '1',
  PRIMARY KEY (`id`)
)
```

**config_entity_sequences**

```sql
CREATE TABLE `config_entity_sequences` (
  `entity_type` int NOT NULL,
  `next_value` bigint NOT NULL,
  `is_enabled` tinyint DEFAULT '1',
  PRIMARY KEY (`entity_type`),
  CONSTRAINT `fk_entity_type` FOREIGN KEY (`entity_type`) REFERENCES `config_entity_types` (`id`)
)
```

**config_entity_types**

```sql
CREATE TABLE `config_entity_types` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `pad_length` int DEFAULT '6',
  `prefix_string` varchar(64) NOT NULL,
  `is_enabled` tinyint DEFAULT '1',
  PRIMARY KEY (`id`)
)
```

**config_payment_methods**

```sql
CREATE TABLE `config_payment_methods` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `is_enabled` tinyint DEFAULT '1',
  PRIMARY KEY (`id`)
)
```

**config_pos_transaction_types**

```sql
CREATE TABLE `config_pos_transaction_types` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `is_enabled` tinyint DEFAULT '1',
  PRIMARY KEY (`id`)
)
```

**config_retail_categories**

```sql
CREATE TABLE `config_retail_categories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `description` varchar(64) DEFAULT NULL,
  `is_enabled` tinyint DEFAULT '1',
  PRIMARY KEY (`id`)
)
```

**config_retail_locations**

```sql
CREATE TABLE `config_retail_locations` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `description` varchar(64) DEFAULT NULL,
  `is_enabled` tinyint DEFAULT '1',
  PRIMARY KEY (`id`)
)
```

**config_item_entry_types**

```sql
CREATE TABLE `config_item_entry_types` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `is_enabled` tinyint DEFAULT '1',
  PRIMARY KEY (`id`)
)
```

**config_role_actions**

```sql
CREATE TABLE `config_role_actions` (
  `role_id` int NOT NULL,
  `action_id` int NOT NULL,
  `is_enabled` tinyint DEFAULT '1',
  PRIMARY KEY (`role_id`,`action_id`),
  KEY `action_id` (`action_id`),
  CONSTRAINT `config_role_actions_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `config_user_roles` (`id`),
  CONSTRAINT `config_role_actions_ibfk_2` FOREIGN KEY (`action_id`) REFERENCES `config_user_actions` (`id`)
)
```

**config_tax_rates**

```sql
CREATE TABLE `config_tax_rates` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tax_region` varchar(64) NOT NULL,
  `name` varchar(64) DEFAULT NULL,
  `gst_rate` decimal(6,4) DEFAULT NULL,
  `pst_rate` decimal(6,4) DEFAULT NULL,
  `is_enabled` tinyint DEFAULT '1',
  PRIMARY KEY (`id`)
)
```

**config_user_actions**

```sql
CREATE TABLE `config_user_actions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `is_enabled` tinyint DEFAULT '1',
  PRIMARY KEY (`id`)
)
```

**config_user_roles**

```sql
CREATE TABLE `config_user_roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `is_enabled` tinyint DEFAULT '1',
  PRIMARY KEY (`id`)
)
```

## Application Tables

**auth_refresh_tokens**

```sql
CREATE TABLE `auth_refresh_tokens` (
  `refresh_token_seq` bigint NOT NULL AUTO_INCREMENT,
  `user_seq` bigint NOT NULL,
  `token` varchar(500) NOT NULL,
  `expires_at` datetime NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`refresh_token_seq`),
  KEY `user_seq` (`user_seq`),
  CONSTRAINT `auth_refresh_tokens_ibfk_1` FOREIGN KEY (`user_seq`) REFERENCES `users` (`user_seq`)
)
```

**customer_order_headers**

```sql
CREATE TABLE `customer_order_headers` (
  `customer_order_no` varchar(64) NOT NULL,
  `customer_order_seq` bigint NOT NULL,
  `customer_seq` bigint NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `name2` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `phone_number2` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `billing_address` varchar(255) DEFAULT NULL,
  `billing_city` varchar(255) DEFAULT NULL,
  `billing_postal_code` varchar(255) DEFAULT NULL,
  `billing_province` varchar(255) DEFAULT NULL,
  `billing_country` varchar(255) DEFAULT NULL,
  `shipping_address` varchar(255) DEFAULT NULL,
  `shipping_city` varchar(255) DEFAULT NULL,
  `shipping_postal_code` varchar(255) DEFAULT NULL,
  `shipping_province` varchar(255) DEFAULT NULL,
  `shipping_country` varchar(255) DEFAULT NULL,
  `comments` text,
  `tax_region_id` int NOT NULL,
  `customer_order_status` int NOT NULL,
  `is_quote` tinyint DEFAULT '0',
  `is_posted` tinyint DEFAULT '0',
  `is_locked` tinyint DEFAULT '0',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `created_by` bigint NOT NULL,
  PRIMARY KEY (`customer_order_seq`),
  KEY `customer_seq` (`customer_seq`),
  KEY `tax_region_id` (`tax_region_id`),
  KEY `customer_order_status` (`customer_order_status`),
  KEY `created_by` (`created_by`),
  CONSTRAINT `customer_order_headers_ibfk_1` FOREIGN KEY (`customer_seq`) REFERENCES `customers` (`customer_seq`),
  CONSTRAINT `customer_order_headers_ibfk_2` FOREIGN KEY (`tax_region_id`) REFERENCES `config_tax_rates` (`id`),
  CONSTRAINT `customer_order_headers_ibfk_3` FOREIGN KEY (`customer_order_status`) REFERENCES `config_customer_order_header_status` (`id`),
  CONSTRAINT `customer_order_headers_ibfk_4` FOREIGN KEY (`created_by`) REFERENCES `users` (`user_seq`)
)
```

**customer_order_line_attributes**

```sql
CREATE TABLE `customer_order_line_attributes` (
  `customer_order_line_seq` bigint NOT NULL,
  `customer_order_seq` bigint NOT NULL,
  `product_attribute_id` int NOT NULL,
  PRIMARY KEY (`customer_order_line_seq`,`customer_order_seq`,`product_attribute_id`),
  KEY `product_attribute_id` (`product_attribute_id`),
  CONSTRAINT `customer_order_line_attributes_ibfk_1` FOREIGN KEY (`customer_order_line_seq`, `customer_order_seq`) REFERENCES `customer_order_lines` (`customer_order_line_seq`, `customer_order_seq`),
  CONSTRAINT `customer_order_line_attributes_ibfk_2` FOREIGN KEY (`product_attribute_id`) REFERENCES `ref_product_attributes` (`id`)
)
```

**customer_order_lines**

```sql
CREATE TABLE `customer_order_lines` (
  `customer_order_line_no` varchar(64) NOT NULL,
  `customer_order_line_seq` bigint NOT NULL,
  `customer_order_seq` bigint NOT NULL,
  `retail_location` int NOT NULL,
  `product_seq` bigint NOT NULL,
  `quantity_ordered` int NOT NULL DEFAULT '1',
  `retail_price` decimal(10,2) NOT NULL,
  `line_discount_amount` decimal(10,2) NOT NULL DEFAULT '0.00',
  `line_subtotal_amount` decimal(10,2) NOT NULL DEFAULT '0.00',
  `tax_rate` decimal(6,4) NOT NULL DEFAULT '0.0000',
  `line_tax_amount` decimal(10,2) NOT NULL DEFAULT '0.00',
  `line_total_amount` decimal(10,2) NOT NULL DEFAULT '0.00',
  `customer_order_line_status` int NOT NULL,
  `is_canceled` tinyint DEFAULT '0',
  `is_shipped` tinyint DEFAULT '0',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `created_by` bigint NOT NULL,
  PRIMARY KEY (`customer_order_line_seq`,`customer_order_seq`),
  KEY `customer_order_seq` (`customer_order_seq`),
  KEY `retail_location` (`retail_location`),
  KEY `product_seq` (`product_seq`),
  KEY `customer_order_line_status` (`customer_order_line_status`),
  KEY `created_by` (`created_by`),
  CONSTRAINT `customer_order_lines_ibfk_1` FOREIGN KEY (`customer_order_seq`) REFERENCES `customer_order_headers` (`customer_order_seq`),
  CONSTRAINT `customer_order_lines_ibfk_2` FOREIGN KEY (`retail_location`) REFERENCES `config_retail_locations` (`id`),
  CONSTRAINT `customer_order_lines_ibfk_3` FOREIGN KEY (`product_seq`) REFERENCES `retail_products` (`product_seq`),
  CONSTRAINT `customer_order_lines_ibfk_4` FOREIGN KEY (`customer_order_line_status`) REFERENCES `config_customer_order_line_status` (`id`),
  CONSTRAINT `customer_order_lines_ibfk_5` FOREIGN KEY (`created_by`) REFERENCES `users` (`user_seq`)
)
```

**customers**

```sql
CREATE TABLE `customers` (
  `customer_no` varchar(64) NOT NULL,
  `customer_seq` bigint NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `name2` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `phone_number2` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `billing_address` varchar(255) DEFAULT NULL,
  `billing_city` varchar(255) DEFAULT NULL,
  `billing_postal_code` varchar(255) DEFAULT NULL,
  `billing_province` varchar(255) DEFAULT NULL,
  `billing_country` varchar(255) DEFAULT NULL,
  `shipping_address` varchar(255) DEFAULT NULL,
  `shipping_city` varchar(255) DEFAULT NULL,
  `shipping_postal_code` varchar(255) DEFAULT NULL,
  `shipping_province` varchar(255) DEFAULT NULL,
  `shipping_country` varchar(255) DEFAULT NULL,
  `comments` text,
  `is_enabled` tinyint DEFAULT '1',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `created_by` bigint NOT NULL,
  PRIMARY KEY (`customer_seq`),
  KEY `created_by` (`created_by`),
  CONSTRAINT `customers_ibfk_1` FOREIGN KEY (`created_by`) REFERENCES `users` (`user_seq`)
)
```

**item_entries**

```sql
CREATE TABLE `item_entries` (
  `item_entry_no` varchar(64) NOT NULL,
  `item_entry_seq` bigint NOT NULL,
  `item_entry_type` int NOT NULL,
  `retail_location` int NOT NULL,
  `product_seq` bigint NOT NULL,
  `pos_transaction_seq` bigint DEFAULT NULL,
  `quantity` int NOT NULL DEFAULT '0',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`item_entry_seq`),
  KEY `item_entry_type` (`item_entry_type`),
  KEY `retail_location` (`retail_location`),
  KEY `product_seq` (`product_seq`),
  KEY `pos_transaction_seq` (`pos_transaction_seq`),
  CONSTRAINT `item_entries_ibfk_1` FOREIGN KEY (`item_entry_type`) REFERENCES `config_item_entry_types` (`id`),
  CONSTRAINT `item_entries_ibfk_2` FOREIGN KEY (`retail_location`) REFERENCES `config_retail_locations` (`id`),
  CONSTRAINT `item_entries_ibfk_3` FOREIGN KEY (`product_seq`) REFERENCES `retail_products` (`product_seq`),
  CONSTRAINT `item_entries_ibfk_4` FOREIGN KEY (`pos_transaction_seq`) REFERENCES `pos_transactions` (`pos_transaction_seq`)
)
```

**item_entry_attributes**

```sql
CREATE TABLE `item_entry_attributes` (
  `item_entry_seq` bigint NOT NULL,
  `product_attribute_id` int NOT NULL,
  PRIMARY KEY (`item_entry_seq`,`product_attribute_id`),
  KEY `product_attribute_id` (`product_attribute_id`),
  CONSTRAINT `item_entry_attributes_ibfk_1` FOREIGN KEY (`item_entry_seq`) REFERENCES `item_entries` (`item_entry_seq`),
  CONSTRAINT `item_entry_attributes_ibfk_2` FOREIGN KEY (`product_attribute_id`) REFERENCES `ref_product_attributes` (`id`)
)
```

**payment_entries**

```sql
CREATE TABLE `payment_entries` (
  `payment_entry_no` varchar(64) NOT NULL,
  `payment_entry_seq` bigint NOT NULL,
  `pos_transaction_seq` bigint NOT NULL,
  `customer_seq` bigint NOT NULL,
  `customer_order_seq` bigint DEFAULT NULL,
  `payment_method` int NOT NULL,
  `payment_amount` decimal(10,2) NOT NULL DEFAULT '0.00',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`payment_entry_seq`),
  KEY `pos_transaction_seq` (`pos_transaction_seq`),
  KEY `customer_seq` (`customer_seq`),
  KEY `customer_order_seq` (`customer_order_seq`),
  KEY `payment_method` (`payment_method`),
  CONSTRAINT `payment_entries_ibfk_1` FOREIGN KEY (`pos_transaction_seq`) REFERENCES `pos_transactions` (`pos_transaction_seq`),
  CONSTRAINT `payment_entries_ibfk_2` FOREIGN KEY (`customer_seq`) REFERENCES `customers` (`customer_seq`),
  CONSTRAINT `payment_entries_ibfk_3` FOREIGN KEY (`customer_order_seq`) REFERENCES `customer_order_headers` (`customer_order_seq`),
  CONSTRAINT `payment_entries_ibfk_4` FOREIGN KEY (`payment_method`) REFERENCES `config_payment_methods` (`id`)
)
```

**pos_transaction_headers_staging**

```sql
CREATE TABLE `pos_transaction_headers_staging` (
  `reserved_transaction_seq` bigint NOT NULL,
  `pos_transaction_type` int NOT NULL,
  `reserved_customer_order_seq` bigint DEFAULT NULL,
  `customer_seq` bigint DEFAULT NULL,
  `is_quote` tinyint DEFAULT '0',
  `tax_region_id` int NOT NULL,
  `required_date` date DEFAULT NULL,
  `comments` text,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `created_by` bigint NOT NULL,
  PRIMARY KEY (`reserved_transaction_seq`),
  KEY `pos_transaction_type` (`pos_transaction_type`),
  KEY `customer_seq` (`customer_seq`),
  KEY `tax_region_id` (`tax_region_id`),
  KEY `created_by` (`created_by`),
  CONSTRAINT `pos_transaction_headers_staging_ibfk_1` FOREIGN KEY (`pos_transaction_type`) REFERENCES `config_pos_transaction_types` (`id`),
  CONSTRAINT `pos_transaction_headers_staging_ibfk_2` FOREIGN KEY (`customer_seq`) REFERENCES `customers` (`customer_seq`),
  CONSTRAINT `pos_transaction_headers_staging_ibfk_3` FOREIGN KEY (`tax_region_id`) REFERENCES `config_tax_rates` (`id`),
  CONSTRAINT `pos_transaction_headers_staging_ibfk_4` FOREIGN KEY (`created_by`) REFERENCES `users` (`user_seq`)
)
```

**pos_transaction_line_attributes_staging**

```sql
CREATE TABLE `pos_transaction_line_attributes_staging` (
  `reserved_transaction_seq` bigint NOT NULL,
  `transaction_line_seq` bigint NOT NULL,
  `product_attribute_id` int NOT NULL,
  PRIMARY KEY (`reserved_transaction_seq`,`transaction_line_seq`),
  KEY `product_attribute_id` (`product_attribute_id`),
  CONSTRAINT `pos_transaction_line_attributes_staging_ibfk_1` FOREIGN KEY (`reserved_transaction_seq`, `transaction_line_seq`) REFERENCES `pos_transaction_lines_staging` (`reserved_transaction_seq`, `transaction_line_seq`),
  CONSTRAINT `pos_transaction_line_attributes_staging_ibfk_2` FOREIGN KEY (`product_attribute_id`) REFERENCES `ref_product_attributes` (`id`)
)
```

**pos_transaction_lines_staging**

```sql
CREATE TABLE `pos_transaction_lines_staging` (
  `reserved_transaction_seq` bigint NOT NULL,
  `transaction_line_seq` bigint NOT NULL,
  `retail_location` int NOT NULL,
  `product_seq` bigint DEFAULT NULL,
  `quantity` int NOT NULL DEFAULT '0',
  `retail_price` decimal(10,2) NOT NULL,
  `line_discount_amount` decimal(10,2) NOT NULL DEFAULT '0.00',
  `line_subtotal_amount` decimal(10,2) NOT NULL DEFAULT '0.00',
  `tax_rate` decimal(6,4) NOT NULL DEFAULT '0.0000',
  `line_tax_amount` decimal(10,2) NOT NULL DEFAULT '0.00',
  `line_total_amount` decimal(10,2) NOT NULL DEFAULT '0.00',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `created_by` bigint NOT NULL,
  PRIMARY KEY (`reserved_transaction_seq`,`transaction_line_seq`),
  KEY `retail_location` (`retail_location`),
  KEY `product_seq` (`product_seq`),
  KEY `created_by` (`created_by`),
  CONSTRAINT `pos_transaction_lines_staging_ibfk_1` FOREIGN KEY (`retail_location`) REFERENCES `config_retail_locations` (`id`),
  CONSTRAINT `pos_transaction_lines_staging_ibfk_2` FOREIGN KEY (`product_seq`) REFERENCES `retail_products` (`product_seq`),
  CONSTRAINT `pos_transaction_lines_staging_ibfk_3` FOREIGN KEY (`created_by`) REFERENCES `users` (`user_seq`)
)
```

**pos_transactions**

```sql
CREATE TABLE `pos_transactions` (
  `pos_transaction_no` varchar(64) NOT NULL,
  `pos_transaction_seq` bigint NOT NULL,
  `pos_transaction_type` int NOT NULL,
  `customer_seq` bigint NOT NULL,
  `customer_order_seq` bigint DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `created_by` bigint NOT NULL,
  PRIMARY KEY (`pos_transaction_seq`),
  KEY `pos_transaction_type` (`pos_transaction_type`),
  KEY `customer_seq` (`customer_seq`),
  KEY `customer_order_seq` (`customer_order_seq`),
  KEY `created_by` (`created_by`),
  CONSTRAINT `pos_transactions_ibfk_1` FOREIGN KEY (`pos_transaction_type`) REFERENCES `config_pos_transaction_types` (`id`),
  CONSTRAINT `pos_transactions_ibfk_2` FOREIGN KEY (`customer_seq`) REFERENCES `customers` (`customer_seq`),
  CONSTRAINT `pos_transactions_ibfk_3` FOREIGN KEY (`customer_order_seq`) REFERENCES `customer_order_headers` (`customer_order_seq`),
  CONSTRAINT `pos_transactions_ibfk_4` FOREIGN KEY (`created_by`) REFERENCES `users` (`user_seq`)
)
```

**reservation_entries**

```sql
CREATE TABLE `reservation_entries` (
  `reservation_entry_no` varchar(64) NOT NULL,
  `reservation_entry_seq` bigint NOT NULL,
  `customer_order_seq` bigint NOT NULL,
  `customer_order_line_seq` bigint NOT NULL,
  `quantity_reserved` int NOT NULL,
  `is_locked` tinyint DEFAULT '0',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`reservation_entry_seq`),
  KEY `customer_order_seq` (`customer_order_seq`,`customer_order_line_seq`),
  CONSTRAINT `reservation_entries_ibfk_1` FOREIGN KEY (`customer_order_seq`, `customer_order_line_seq`) REFERENCES `customer_order_lines` (`customer_order_seq`, `customer_order_line_seq`)
)
```

**retail_products**

```sql
CREATE TABLE `retail_products` (
  `product_no` varchar(64) NOT NULL,
  `product_seq` bigint NOT NULL,
  `retail_price` decimal(10,2) DEFAULT '0.00',
  `cost` decimal(10,2) DEFAULT '0.00',
  `description` varchar(64) DEFAULT NULL,
  `retail_category` int NOT NULL,
  `wood_specie` int DEFAULT NULL,
  `product_width` decimal(10,3) DEFAULT '0.000',
  `product_thickness` decimal(10,3) DEFAULT '0.000',
  `product_length` decimal(10,3) DEFAULT '0.000',
  `is_enabled` tinyint DEFAULT '1',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `created_by` bigint NOT NULL,
  PRIMARY KEY (`product_seq`),
  KEY `retail_category` (`retail_category`),
  KEY `wood_specie` (`wood_specie`),
  KEY `created_by` (`created_by`),
  CONSTRAINT `retail_products_ibfk_1` FOREIGN KEY (`retail_category`) REFERENCES `config_retail_categories` (`id`),
  CONSTRAINT `retail_products_ibfk_2` FOREIGN KEY (`wood_specie`) REFERENCES `ref_wood_species` (`id`),
  CONSTRAINT `retail_products_ibfk_3` FOREIGN KEY (`created_by`) REFERENCES `users` (`user_seq`)
)
```

**sale_entry_attributes**

```sql
CREATE TABLE `sale_entry_attributes` (
  `sale_entry_seq` bigint NOT NULL,
  `product_attribute_id` int NOT NULL,
  PRIMARY KEY (`sale_entry_seq`,`product_attribute_id`),
  KEY `product_attribute_id` (`product_attribute_id`),
  CONSTRAINT `sale_entry_attributes_ibfk_1` FOREIGN KEY (`sale_entry_seq`) REFERENCES `sales_entries` (`sale_entry_seq`),
  CONSTRAINT `sale_entry_attributes_ibfk_2` FOREIGN KEY (`product_attribute_id`) REFERENCES `ref_product_attributes` (`id`)
)
```

**sales_entries**

```sql
CREATE TABLE `sales_entries` (
  `sale_entry_no` varchar(64) NOT NULL,
  `sale_entry_seq` bigint NOT NULL,
  `pos_transaction_seq` bigint DEFAULT NULL,
  `product_seq` bigint NOT NULL,
  `quantity` int NOT NULL DEFAULT '0',
  `subtotal_amount` decimal(10,2) DEFAULT '0.00',
  `tax_rate` decimal(6,4) NOT NULL DEFAULT '0.0000',
  `tax_amount` decimal(10,2) NOT NULL DEFAULT '0.00',
  `total_amount` decimal(10,2) NOT NULL DEFAULT '0.00',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`sale_entry_seq`),
  KEY `pos_transaction_seq` (`pos_transaction_seq`),
  KEY `product_seq` (`product_seq`),
  CONSTRAINT `sales_entries_ibfk_1` FOREIGN KEY (`pos_transaction_seq`) REFERENCES `pos_transactions` (`pos_transaction_seq`),
  CONSTRAINT `sales_entries_ibfk_2` FOREIGN KEY (`product_seq`) REFERENCES `retail_products` (`product_seq`)
)
```

**users**

```sql
CREATE TABLE `users` (
  `user_no` varchar(64) NOT NULL,
  `user_seq` bigint NOT NULL,
  `username_short` varchar(64) NOT NULL,
  `username_long` varchar(64) NOT NULL,
  `user_role` int NOT NULL,
  `user_password` varchar(255) DEFAULT NULL,
  `user_pin` varchar(64) DEFAULT NULL,
  `user_permissions` json DEFAULT NULL,
  `user_tax_region` int NOT NULL,
  `is_enabled` tinyint DEFAULT '0',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `created_by` bigint NOT NULL,
  PRIMARY KEY (`user_seq`),
  KEY `user_role` (`user_role`),
  KEY `user_tax_region` (`user_tax_region`),
  KEY `users_ibfk_3` (`created_by`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`user_role`) REFERENCES `config_user_roles` (`id`),
  CONSTRAINT `users_ibfk_2` FOREIGN KEY (`user_tax_region`) REFERENCES `config_tax_rates` (`id`),
  CONSTRAINT `users_ibfk_3` FOREIGN KEY (`created_by`) REFERENCES `users` (`user_seq`)
)
```

## Reference (Extension) Tables

**ref_customer_order_services**

```sql
CREATE TABLE `ref_customer_order_services` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `factor` decimal(6,3) NOT NULL DEFAULT '1.000',
  `removes_attributes` json DEFAULT NULL,
  `add_attributes` json DEFAULT NULL,
  PRIMARY KEY (`id`)
)
```

**ref_product_attributes**

```sql
CREATE TABLE `ref_product_attributes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `is_enabled` tinyint DEFAULT '1',
  PRIMARY KEY (`id`)
)
```

**ref_wood_species**

```sql
CREATE TABLE `ref_wood_species` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `is_enabled` tinyint DEFAULT '1',
  PRIMARY KEY (`id`)
)
```
