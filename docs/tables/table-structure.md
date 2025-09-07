# Table Structure and Relations

---

## Configuration Tables

---

## Application Tables

#### Users (`users`)

| Name             | Type         | Nullable | Default Value     | Primary | Foreign | References           |
| ---------------- | ------------ | -------- | ----------------- | ------- | ------- | -------------------- |
| user_no          | VARCHAR(64)  | false    |                   |         |         |                      |
| user_seq         | BIGINT       | false    |                   | true    |         |                      |
| username_short   | VARCHAR(64)  | false    |                   |         |         |                      |
| username_long    | VARCHAR(64)  | false    |                   |         |         |                      |
| user_role        | INT          | false    |                   |         | true    | config_user_roles.id |
| user_password    | VARCHAR(255) | true     |                   |         |         |                      |
| user_pin         | VARCHAR(64)  | true     |                   |         |         |                      |
| user_permissions | JSON         | true     |                   |         |         |                      |
| user_tax_region  | INT          | false    |                   |         | true    | config_tax_rates.id  |
| is_enabled       | TINYINT      | true     | 0                 |         |         |                      |
| created_at       | DATETIME     | true     | CURRENT_TIMESTAMP |         |         |                      |
| created_by       | BIGINT       | false    |                   |         | true    | users.user_seq       |

#### Sales Entries (`sales_entries`)

| Name                | Type          | Nullable | Default Value     | Primary | Foreign | References                           |
| ------------------- | ------------- | -------- | ----------------- | ------- | ------- | ------------------------------------ |
| sale_entry_no       | VARCHAR(64)   | false    |                   |         |         |                                      |
| sale_entry_seq      | BIGINT        | false    |                   | true    |         |                                      |
| pos_transaction_seq | BIGINT        | true     |                   |         | true    | pos_transactions.pos_transaction_seq |
| product_seq         | BIGINT        | false    |                   |         | true    | retail_products.product_seq          |
| quantity            | INT           | false    | 0                 |         |         |                                      |
| subtotal_amount     | DECIMAL(10,2) | true     | 0.00              |         |         |                                      |
| tax_rate            | DECIMAL(6,4)  | true     | 0.0000            |         |         |                                      |
| tax_amount          | DECIMAL(10,2) | true     | 0.00              |         |         |                                      |
| total_amount        | DECIMAL(10,2) | true     | 0.00              |         |         |                                      |
| created_at          | DATETIME      | true     | CURRENT_TIMESTAMP |         |         |                                      |

#### Sales Entries Attributes (`sale_entry_attributes`)

| Name                 | Type   | Nullable | Default Value | Primary | Foreign | References                   |
| -------------------- | ------ | -------- | ------------- | ------- | ------- | ---------------------------- |
| sale_entry_seq       | BIGINT | false    |               | true    | true    | sales_entries.sale_entry_seq |
| product_attribute_id | INT    | false    |               | true    | true    | ref_product_attributes.id    |

#### Retail Products (`retail_products`)

| Name              | Type          | Nullable | Default Value     | Primary | Foreign | References                  |
| ----------------- | ------------- | -------- | ----------------- | ------- | ------- | --------------------------- |
| product_no        | VARCHAR(64)   | true     |                   |         |         |                             |
| product_seq       | BIGINT        | true     |                   | true    |         |                             |
| retail_price      | DECIMAL(10,2) | false    | 0.00              |         |         |                             |
| cost              | DECIMAL(10,2) | false    | 0.00              |         |         |                             |
| description       | VARCHAR(64)   | false    |                   |         |         |                             |
| retail_category   | INT           | true     |                   |         | true    | config_retail_categories.id |
| wood_specie       | INT           | false    |                   |         | true    | ref_wood_species.id         |
| product_width     | DECIMAL(10,3) | false    | 0.000             |         |         |                             |
| product_thickness | DECIMAL(10,3) | false    | 0.000             |         |         |                             |
| product_length    | DECIMAL(10,3) | false    | 0.000             |         |         |                             |
| is_enabled        | TINYINT       | true     | 0                 |         |         |                             |
| created_at        | DATETIME      | true     | CURRENT_TIMESTAMP |         |         |                             |
| created_by        | BIGINT        | false    |                   |         | true    | users.user_seq              |

---

## Reference (Extension) Tables
