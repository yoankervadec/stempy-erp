# Table Abbreviations

| Table Type          | Abbreviation | Full Table Name                           | Description                                                                        |
| ------------------- | :----------: | ----------------------------------------- | ---------------------------------------------------------------------------------- |
| Config              | `coheadstat` | `config_customer_order_header_status`     | Stores constant customer order **header** statuses                                 |
| Config              | `colinestat` | `config_customer_order_line_status`       | Stores constant customer order **line** statuses                                   |
| Config              |              | `config_entity_sequences`                 | Stores entity sequences (e.g., RetailProduct, Customer...)                         |
| Config              |              | `config_entity_types`                     | Stores entity types with _padLength_, _stringPrefix_...                            |
| Config              |    `cpym`    | `config_payment_methods`                  | Stores payment methods                                                             |
| Config              |    `ctrt`    | `config_pos_transaction_types`            | Stores POS transaction types                                                       |
| Config              |    `crtc`    | `config_retail_categories`                | Stores retail categories for RetailProduct                                         |
| Config              |    `crtl`    | `config_retail_locations`                 | Stores retail locations (warehouses)                                               |
| Config              |              | `config_user_actions`                     | Stores user actions                                                                |
| Config              |              | `config_user_roles`                       | Stores user roles                                                                  |
| Config              |              | `config_role_actions`                     | Stores allowed actions for a given role                                            |
| Config              |              | `config_tax_rates`                        | Stores tax rates for given region                                                  |
| Application         |     `rp`     | `retail_products`                         | Stores product master data                                                         |
| Application         |    `coh`     | `customer_order_headers`                  | Stores customer order headers                                                      |
| Application         |    `col`     | `customer_order_lines`                    | Stores customer lines                                                              |
| Application         |  `colattr`   | `customer_order_line_attributes`          | Stores customer line attributes                                                    |
| Application         |     `cx`     | `customers`                               | Stores customer master data                                                        |
| Application         |     `ie`     | `item_entries`                            | Stores item entries (used to calculate inventory)                                  |
| Application         |   `ieattr`   | `item_entry_attributes`                   | Stores item entrie attributes                                                      |
| Application         |     `pe`     | `payment_entries`                         | Stores payment entries                                                             |
| Application/Stating |              | `pos_transaction_headers_staging`         | Stores staged POS transaction headers                                              |
| Application/Stating |              | `pos_transaction_lines_staging`           | Stores staged POS transaction lines                                                |
| Application/Stating |              | `pos_transaction_line_attributes_staging` | Stores staged POS transaction line attributes                                      |
| Application         |     `pt`     | `pos_transactions`                        | Stores POS transactions                                                            |
| Extension           |              | `ref_customer_order_services`             | Stores services                                                                    |
| Extension           |              | `ref_product_attributes`                  | Stores RetailProduct attributes                                                    |
| Extension           |              | `ref_wood_species`                        | Stores wood species                                                                |
| Application         |     `re`     | `reservation_entries`                     | Stores reservation entries                                                         |
| Application         |   `seattr`   | `sale_entry_attributes`                   | Stores sales entries line attributes (similar to `customer_order_line_attributes`) |
| Application         |     `se`     | `sales_entries`                           | Stores sales entries (similar to `customer_order_headers`)                         |
| Application         |     `us`     | `users`                                   | Stores users                                                                       |
