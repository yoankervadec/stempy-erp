# Table Abbreviations and Definitions

Actual SQL query files are stored in "src/main/resources/queries", however the `WHERE` clauses will be exluded when possible/necessary. The `WHERE` clause may occasionally be written in the repository level (example below) so the aliases must therefore remain consistents between the repositories and the .sql files.

```java
RetailProduct findByproductNo(Connection con, String productNo) throws SQLException {
    RetailProduct retailProduct;

    // Load base query (without WHERE clause) from "src/main/resources/queries"
    String sqlBase = QueryCache.get(
        Query.SELECT_RETAIL_PRODUCT_BY_PRODUCT_NO);


    SqlBuilder builder = new SqlBuilder(sqlBase)
        .where("rp.product_no = ?", productNo); // Alias used "rp.product_no"

    String sqlString = builder.build();
    List<Object> params = builder.getParams();

    try (var stmt = con.prepareStatement(sqlString)) {

      for (int i = 0; i < params.size(); i++) {
        stmt.setObject(i + 1, params.get(i));
      }

      ...
    }

    return retailProduct;
  }
```

| Table Type          | Abbreviation | Table Name                                | Description                                                                                |
| ------------------- | :----------: | ----------------------------------------- | ------------------------------------------------------------------------------------------ |
| Config              |    `cohs`    | `config_customer_order_header_status`     | Lookup of allowed statuses for customer order **headers** (e.g., Ready, Payment Pending)   |
| Config              |    `cols`    | `config_customer_order_line_status`       | Lookup of allowed statuses for customer order **lines** (e.g., Waiting, Shipped)           |
| Config              |    `ces`     | `config_entity_sequences`                 | Tracks next sequence numbers for entities (e.g., Product, Customer, Order)                 |
| Config              |    `cet`     | `config_entity_types`                     | Defines entity types with sequence settings (prefix, padding length, etc.)                 |
| Config              |    `cpm`     | `config_payment_methods`                  | Lookup of accepted payment methods (e.g., Cash, Cheque)                                    |
| Config              |    `ctt`     | `config_pos_transaction_types`            | Lookup of POS transaction types (e.g., Sale, Refund, Log) for `pos_transactions`           |
| Config              |    `crc`     | `config_retail_categories`                | Product categories for `retail_products`                                                   |
| Config              |    `crl`     | `config_retail_locations`                 | Retail locations / warehouses                                                              |
| Config              |    `cua`     | `config_user_actions`                     | Lookup of user actions (e.g., CREATE_ORDER, VOID_TXN)                                      |
| Config              |    `cur`     | `config_user_roles`                       | Defines user roles (e.g., Admin, Cashier, Manager)                                         |
| Config              |    `cra`     | `config_role_actions`                     | Maps roles to allowed actions                                                              |
| Config              |    `ctr`     | `config_tax_rates`                        | Tax rates by region                                                                        |
| Application         |     `rp`     | `retail_products`                         | Product master data (e.g., productNo, description, price, category)                        |
| Application         |     `cx`     | `customers`                               | Customer master data                                                                       |
| Application         |    `coh`     | `customer_order_headers`                  | Customer orders (header-level info: customer, date, status)                                |
| Application         |    `col`     | `customer_order_lines`                    | Customer order lines (product, qty, price)                                                 |
| Application         |    `cola`    | `customer_order_line_attributes`          | Extra attributes for customer order lines                                                  |
| Application         |     `pt`     | `pos_transactions`                        | POS transactions (finalized, persisted)                                                    |
| Application         |     `ie`     | `item_entries`                            | Item entries (inventory movements, e.g., in/out/adjustments)                               |
| Application         |    `iea`     | `item_entry_attributes`                   | Extra attributes for item entries                                                          |
| Application         |     `pe`     | `payment_entries`                         | Payments linked to transactions/orders                                                     |
| Application         |     `se`     | `sales_entries`                           | Sales entries (line-level sales data, like order lines but for audit purposes)             |
| Application         |    `sea`     | `sale_entry_attributes`                   | Extra attributes for `sales_entries`                                                       |
| Application         |     `re`     | `reservation_entries`                     | Reservations against stock (e.g., allocated inventory)                                     |
| Application         |     `us`     | `users`                                   | User accounts for authentication and authorization                                         |
| Application/Staging |    `ptsh`    | `pos_transaction_headers_staging`         | Temporary staging for POS transaction headers (pre-validation, similar to shipping cart)   |
| Application/Staging |    `ptsl`    | `pos_transaction_lines_staging`           | Temporary staging for POS transaction lines (pre-validation)                               |
| Application/Staging |   `ptsla`    | `pos_transaction_line_attributes_staging` | Temporary staging for POS transaction line attributes                                      |
| Extension           |    `rcos`    | `ref_customer_order_services`             | Services offered for customer orders (e.g., delivery, assembly), may remove/add attributes |
| Extension           |    `rpa`     | `ref_product_attributes`                  | Attribute definitions for `retail_products`                                                |
| Extension           |    `rws`     | `ref_wood_species`                        | Reference table for wood species                                                           |
