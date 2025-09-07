# Table Structure and Relations

---

## Configuration Tables

---

## Application Tables

#### Users (`users`)

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
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`user_role`) REFERENCES `config_user_roles` (`id`),
  CONSTRAINT `users_ibfk_2` FOREIGN KEY (`user_tax_region`) REFERENCES `config_tax_rates` (`id`),
  CONSTRAINT `users_ibfk_3` FOREIGN KEY (`created_by`) REFERENCES `users` (`user_seq`)
)
```

#### Sales Entries (`sales_entries`)

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
  CONSTRAINT `sales_entries_ibfk_1` FOREIGN KEY (`pos_transaction_seq`) REFERENCES `pos_transactions` (`pos_transaction_seq`),
  CONSTRAINT `sales_entries_ibfk_2` FOREIGN KEY (`product_seq`) REFERENCES `retail_products` (`product_seq`)
)
```

#### Sales Entries Attributes (`sale_entry_attributes`)

```sql
CREATE TABLE `sale_entry_attributes` (
  `sale_entry_seq` bigint NOT NULL,
  `product_attribute_id` int NOT NULL,
  PRIMARY KEY (`sale_entry_seq`,`product_attribute_id`),
  CONSTRAINT `sale_entry_attributes_ibfk_1` FOREIGN KEY (`sale_entry_seq`) REFERENCES `sales_entries` (`sale_entry_seq`),
  CONSTRAINT `sale_entry_attributes_ibfk_2` FOREIGN KEY (`product_attribute_id`) REFERENCES `ref_product_attributes` (`id`)
)
```

#### Retail Products (`retail_products`)

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

---

## Reference (Extension) Tables
