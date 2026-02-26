--
-- Naming abbreviations:
-- core_      system-wide configuration & constants
-- auth_      authentication & security
-- stage_     temporary working data
-- auto_      automation engine
-- dom_       domain business entities
-- ref_       reference tables (split from core)
-- org_       organisation level
-- acc_       accounting & finance
--
-- UTC timestamps:
-- MySQL: /etc/mysql/mysql.conf.d/mysqld.cnf → default-time-zone = '+00:00'
-- Java write: LocalDateTime.now(ZoneOffset.UTC)
-- Java read:  resultSet.getObject("created_at", LocalDateTime.class)
--
--
-- ====================================
-- CORE TABLES (core_)
-- ====================================
CREATE TABLE IF NOT EXISTS core_domain_entity_config (
  id BIGINT UNSIGNED AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  pad_length INT NOT NULL,
  prefix VARCHAR(10) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS core_domain_entity_sequence (
  domain_entity_id BIGINT UNSIGNED NOT NULL,
  next BIGINT NOT NULL,
  enabled BOOLEAN NOT NULL DEFAULT TRUE,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  PRIMARY KEY (domain_entity_id),
  FOREIGN KEY (domain_entity_id) REFERENCES core_domain_entity_config (id)
);

-- ====================================
-- REFERENCE TABLES (ref_)
-- ====================================
CREATE TABLE IF NOT EXISTS ref_tax_rate (
  id BIGINT UNSIGNED AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL, -- GST, QST, VAT20
  description VARCHAR(255),
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  rate DECIMAL(9, 6) NOT NULL,
  is_compound BOOLEAN DEFAULT FALSE NOT NULL,
  calculation_order INT DEFAULT 1 NOT NULL,
  valid_from DATE NOT NULL,
  valid_to DATE,
  PRIMARY KEY (id),
  UNIQUE (name, valid_from)
);

CREATE TABLE IF NOT EXISTS ref_tax_group (
  id BIGINT UNSIGNED AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL, -- QC_STANDARD, ON_STANDARD
  description VARCHAR(255),
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  PRIMARY KEY (id),
  UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS ref_tax_group_line (
  id BIGINT UNSIGNED AUTO_INCREMENT,
  tax_group_id BIGINT UNSIGNED NOT NULL,
  tax_rate_id BIGINT UNSIGNED NOT NULL,
  sort_order INT NOT NULL DEFAULT 1,
  PRIMARY KEY (id),
  FOREIGN KEY (tax_group_id) REFERENCES ref_tax_group (id),
  FOREIGN KEY (tax_rate_id) REFERENCES ref_tax_rate (id),
  UNIQUE (tax_group_id, tax_rate_id)
);

CREATE TABLE IF NOT EXISTS ref_retail_category (
  id BIGINT UNSIGNED AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  description VARCHAR(255),
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS ref_payment_method (
  id BIGINT UNSIGNED AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL, -- CASH, CREDIT_CARD, DEBIT_CARD, GIFT_CARD
  description VARCHAR(255),
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS ref_document_status (
  id BIGINT UNSIGNED AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL, -- OPEN, CLOSED, CANCELLED
  description VARCHAR(255),
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  PRIMARY KEY (id)
);

-- ====================================
-- ORGANISATION TABLES (org_)
-- ====================================
CREATE TABLE IF NOT EXISTS org_retail_entity (
  id BIGINT UNSIGNED AUTO_INCREMENT,
  retail_entity_no VARCHAR(25) NOT NULL,
  name VARCHAR(50) NOT NULL, -- Montreal Downtown Store / ECOM
  description VARCHAR(255),
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  created_by_user_id BIGINT UNSIGNED,
  updated_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  updated_by_user_id BIGINT UNSIGNED,
  PRIMARY KEY (id),
  FOREIGN KEY (created_by_user_id) REFERENCES auth_user (id),
  FOREIGN KEY (updated_by_user_id) REFERENCES auth_user (id),
  UNIQUE (retail_entity_no)
);

CREATE TABLE IF NOT EXISTS org_retail_entity_setup (
  retail_entity_id BIGINT UNSIGNED,
  PRIMARY KEY (retail_entity_id),
  FOREIGN KEY (retail_entity_id) REFERENCES org_retail_entity (id)
);

CREATE TABLE IF NOT EXISTS org_retail_location (
  id BIGINT UNSIGNED AUTO_INCREMENT,
  retail_entity_id BIGINT UNSIGNED NOT NULL,
  retail_location_no VARCHAR(25) NOT NULL,
  name VARCHAR(50) NOT NULL,
  description VARCHAR(255),
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  created_by_user_id BIGINT UNSIGNED,
  updated_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  updated_by_user_id BIGINT UNSIGNED,
  PRIMARY KEY (id),
  FOREIGN KEY (retail_entity_id) REFERENCES org_retail_entity (id),
  FOREIGN KEY (created_by_user_id) REFERENCES auth_user (id),
  FOREIGN KEY (updated_by_user_id) REFERENCES auth_user (id),
  UNIQUE (retail_location_no)
);

CREATE TABLE IF NOT EXISTS org_retail_location_setup (
  retail_location_id BIGINT UNSIGNED,
  in_transit_location_id BIGINT UNSIGNED,
  sale_location BOOLEAN NOT NULL,
  warehouse_location BOOLEAN NOT NULL,
  PRIMARY KEY (retail_location_id),
  FOREIGN KEY (retail_location_id) REFERENCES org_retail_location (id),
  FOREIGN KEY (in_transit_location_id) REFERENCES org_retail_location (id)
);

CREATE TABLE IF NOT EXISTS org_retail_pos_terminal (
  id BIGINT UNSIGNED AUTO_INCREMENT,
  retail_location_id BIGINT UNSIGNED NOT NULL,
  retail_pos_terminal_no VARCHAR(25) NOT NULL,
  name VARCHAR(50) NOT NULL,
  description VARCHAR(255),
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  created_by_user_id BIGINT UNSIGNED,
  updated_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  updated_by_user_id BIGINT UNSIGNED,
  PRIMARY KEY (id),
  FOREIGN KEY (retail_location_id) REFERENCES org_retail_location (id),
  FOREIGN KEY (created_by_user_id) REFERENCES auth_user (id),
  FOREIGN KEY (updated_by_user_id) REFERENCES auth_user (id),
  UNIQUE (retail_pos_terminal_no)
);

-- ====================================
-- DOMAIN TABLES (dom_)
-- ====================================
CREATE TABLE IF NOT EXISTS dom_customer_order_header (
  id BIGINT UNSIGNED AUTO_INCREMENT,
  customer_order_no VARCHAR(25) NOT NULL,
  created_at_retail_entity_id BIGINT UNSIGNED NOT NULL,
  sales_person_id BIGINT UNSIGNED,
  status_id BIGINT UNSIGNED NOT NULL,
  customer_id BIGINT UNSIGNED NOT NULL,
  name VARCHAR(50) NOT NULL, -- snapshot
  name2 VARCHAR(50), -- snapshot
  email VARCHAR(100), -- snapshot
  phone VARCHAR(20), -- snapshot
  phone2 VARCHAR(20), -- snapshot
  ship_to_address_line1 VARCHAR(255), -- snapshot
  ship_to_address_line2 VARCHAR(255), -- snapshot
  ship_to_city VARCHAR(50), -- snapshot
  ship_to_state VARCHAR(50), -- snapshot
  ship_to_postal_code VARCHAR(20), -- snapshot
  ship_to_country VARCHAR(50), -- snapshot
  bill_to_address_line1 VARCHAR(255), -- snapshot
  bill_to_address_line2 VARCHAR(255), -- snapshot
  bill_to_city VARCHAR(50), -- snapshot
  bill_to_state VARCHAR(50), -- snapshot
  bill_to_postal_code VARCHAR(20), -- snapshot
  bill_to_country VARCHAR(50), -- snapshot
  subtotal_amount DECIMAL(19, 4) NOT NULL,
  tax_amount DECIMAL(19, 4) NOT NULL,
  total_amount DECIMAL(19, 4) NOT NULL,
  ordered_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  created_by_user_id BIGINT UNSIGNED,
  PRIMARY KEY (id),
  FOREIGN KEY (created_at_retail_entity_id) REFERENCES org_retail_entity (id),
  FOREIGN KEY (sales_person_id) REFERENCES auth_user (id),
  FOREIGN KEY (status_id) REFERENCES ref_document_status (id),
  FOREIGN KEY (customer_id) REFERENCES dom_customer (id),
  FOREIGN KEY (created_by_user_id) REFERENCES auth_user (id),
  UNIQUE (customer_order_no)
);

CREATE TABLE IF NOT EXISTS dom_customer_order_line (
  id BIGINT UNSIGNED AUTO_INCREMENT,
  customer_order_line_no VARCHAR(25) NOT NULL,
  customer_order_id BIGINT UNSIGNED NOT NULL,
  status_id BIGINT UNSIGNED NOT NULL,
  created_at_retail_entity_id BIGINT UNSIGNED NOT NULL,
  sourced_from_retail_location_id BIGINT UNSIGNED NOT NULL,
  retail_product_id BIGINT UNSIGNED NOT NULL,
  retail_product_name VARCHAR(50) NOT NULL, -- snapshot
  quantity DECIMAL(19, 6) NOT NULL,
  tax_group_id BIGINT UNSIGNED NOT NULL, -- QC_STANDARD
  unit_price DECIMAL(19, 4) NOT NULL,
  discount_amount DECIMAL(19, 4) NOT NULL,
  subtotal_amount DECIMAL(19, 4) NOT NULL, -- before tax
  tax_amount DECIMAL(19, 4) NOT NULL, -- total tax for this line
  total_amount DECIMAL(19, 4) NOT NULL, -- subtotal + tax
  PRIMARY KEY (id),
  FOREIGN KEY (customer_order_id) REFERENCES dom_customer_order_header (id),
  FOREIGN KEY (status_id) REFERENCES ref_document_status (id),
  FOREIGN KEY (created_at_retail_entity_id) REFERENCES org_retail_entity (id),
  FOREIGN KEY (sourced_from_retail_location_id) REFERENCES org_retail_location (id),
  FOREIGN KEY (retail_product_id) REFERENCES dom_retail_product_variant (id),
  FOREIGN KEY (tax_group_id) REFERENCES ref_tax_group (id),
  UNIQUE (customer_order_line_no, customer_order_id)
);

CREATE TABLE IF NOT EXISTS dom_customer_order_line_tax (
  id BIGINT UNSIGNED AUTO_INCREMENT,
  customer_order_line_id BIGINT UNSIGNED NOT NULL,
  customer_order_id BIGINT UNSIGNED NOT NULL,
  tax_rate_name VARCHAR(50) NOT NULL, -- snapshot (GST, QST, VAT20)
  tax_rate_used DECIMAL(9, 6) NOT NULL, -- snapshot
  taxable_base DECIMAL(19, 4) NOT NULL,
  tax_amount DECIMAL(19, 4) NOT NULL,
  calculation_order INT NOT NULL DEFAULT 1,
  PRIMARY KEY (id),
  FOREIGN KEY (customer_order_line_id) REFERENCES dom_customer_order_line (id),
  FOREIGN KEY (customer_order_id) REFERENCES dom_customer_order_header (id)
);

CREATE TABLE IF NOT EXISTS dom_sales_invoice_header (
  id BIGINT UNSIGNED AUTO_INCREMENT,
  sales_invoice_no VARCHAR(25) NOT NULL,
  customer_order_id BIGINT UNSIGNED,
  status_id BIGINT UNSIGNED NOT NULL,
  created_at_retail_entity_id BIGINT UNSIGNED NOT NULL,
  created_at_retail_location_id BIGINT UNSIGNED NOT NULL,
  sales_person_id BIGINT UNSIGNED,
  customer_id BIGINT UNSIGNED NOT NULL,
  name VARCHAR(50) NOT NULL, -- snapshot
  name2 VARCHAR(50), -- snapshot
  email VARCHAR(100), -- snapshot
  phone VARCHAR(20), -- snapshot
  phone2 VARCHAR(20), -- snapshot
  ship_to_address_line1 VARCHAR(255), -- snapshot
  ship_to_address_line2 VARCHAR(255), -- snapshot
  ship_to_city VARCHAR(50), -- snapshot
  ship_to_state VARCHAR(50), -- snapshot
  ship_to_postal_code VARCHAR(20), -- snapshot
  ship_to_country VARCHAR(50), -- snapshot
  bill_to_address_line1 VARCHAR(255), -- snapshot
  bill_to_address_line2 VARCHAR(255), -- snapshot
  bill_to_city VARCHAR(50), -- snapshot
  bill_to_state VARCHAR(50), -- snapshot
  bill_to_postal_code VARCHAR(20), -- snapshot
  bill_to_country VARCHAR(50), -- snapshot
  subtotal_amount DECIMAL(19, 4) NOT NULL,
  tax_amount DECIMAL(19, 4) NOT NULL,
  total_amount DECIMAL(19, 4) NOT NULL,
  balance_due_amount DECIMAL(19, 4) NOT NULL,
  invoiced_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  due_at DATE NOT NULL,
  credit_note BOOLEAN NOT NULL DEFAULT FALSE,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  created_by_user_id BIGINT UNSIGNED,
  PRIMARY KEY (id),
  FOREIGN KEY (customer_order_id) REFERENCES dom_customer_order_header (id),
  FOREIGN KEY (status_id) REFERENCES ref_document_status (id),
  FOREIGN KEY (created_at_retail_entity_id) REFERENCES org_retail_entity (id),
  FOREIGN KEY (created_at_retail_location_id) REFERENCES org_retail_location (id),
  FOREIGN KEY (sales_person_id) REFERENCES auth_user (id),
  FOREIGN KEY (customer_id) REFERENCES dom_customer (id),
  FOREIGN KEY (created_by_user_id) REFERENCES auth_user (id),
  UNIQUE (sales_invoice_no)
);

CREATE TABLE IF NOT EXISTS dom_sales_invoice_line (
  id BIGINT UNSIGNED AUTO_INCREMENT,
  sales_invoice_line_no VARCHAR(25) NOT NULL,
  sales_invoice_id BIGINT UNSIGNED NOT NULL,
  created_at_retail_entity_id BIGINT UNSIGNED NOT NULL,
  sourced_from_retail_location_id BIGINT UNSIGNED NOT NULL,
  retail_product_id BIGINT UNSIGNED NOT NULL,
  retail_product_name VARCHAR(50) NOT NULL, -- snapshot
  quantity DECIMAL(19, 6) NOT NULL,
  unit_price DECIMAL(19, 4) NOT NULL,
  discount_amount DECIMAL(19, 4) NOT NULL,
  subtotal_amount DECIMAL(19, 4) NOT NULL, -- before tax
  tax_amount DECIMAL(19, 4) NOT NULL, -- total tax for this line
  total_amount DECIMAL(19, 4) NOT NULL, -- subtotal + tax
  cost_amount DECIMAL(19, 4) NOT NULL, -- for COGS and profitability analysis (snapshot)
  tax_group_id BIGINT UNSIGNED NOT NULL, -- QC_STANDARD
  PRIMARY KEY (id),
  FOREIGN KEY (sales_invoice_id) REFERENCES dom_sales_invoice_header (id),
  FOREIGN KEY (created_at_retail_entity_id) REFERENCES org_retail_entity (id),
  FOREIGN KEY (sourced_from_retail_location_id) REFERENCES org_retail_location (id),
  FOREIGN KEY (retail_product_id) REFERENCES dom_retail_product_variant (id),
  FOREIGN KEY (tax_group_id) REFERENCES ref_tax_group (id)
);

CREATE TABLE IF NOT EXISTS dom_sales_invoice_line_tax (
  id BIGINT UNSIGNED AUTO_INCREMENT,
  sales_invoice_line_id BIGINT UNSIGNED NOT NULL,
  sales_invoice_id BIGINT UNSIGNED NOT NULL,
  tax_rate_name VARCHAR(50) NOT NULL, -- snapshot (GST, QST, VAT20)
  tax_rate_used DECIMAL(9, 6) NOT NULL, -- snapshot
  taxable_base DECIMAL(19, 4) NOT NULL,
  tax_amount DECIMAL(19, 4) NOT NULL,
  calculation_order INT NOT NULL DEFAULT 1,
  PRIMARY KEY (id),
  FOREIGN KEY (sales_invoice_line_id) REFERENCES dom_sales_invoice_line (id),
  FOREIGN KEY (sales_invoice_id) REFERENCES dom_sales_invoice_header (id)
);

CREATE TABLE IF NOT EXISTS dom_payment_entry (
  id BIGINT UNSIGNED AUTO_INCREMENT,
  payment_entry_no VARCHAR(25) NOT NULL,
  status_id BIGINT UNSIGNED NOT NULL,
  retail_entity_id BIGINT UNSIGNED NOT NULL,
  sales_invoice_id BIGINT UNSIGNED NOT NULL,
  customer_id BIGINT UNSIGNED NULL,
  amount DECIMAL(19, 4) NOT NULL,
  payment_method_id BIGINT UNSIGNED NOT NULL,
  paid_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  created_by_user_id BIGINT UNSIGNED,
  PRIMARY KEY (id),
  FOREIGN KEY (status_id) REFERENCES ref_document_status (id),
  FOREIGN KEY (retail_entity_id) REFERENCES org_retail_entity (id),
  FOREIGN KEY (sales_invoice_id) REFERENCES dom_sales_invoice_header (id),
  FOREIGN KEY (customer_id) REFERENCES dom_customer (id),
  FOREIGN KEY (payment_method_id) REFERENCES ref_payment_method (id),
  FOREIGN KEY (created_by_user_id) REFERENCES auth_user (id),
  UNIQUE (payment_entry_no)
);

CREATE TABLE IF NOT EXISTS dom_sales_entry (
  id BIGINT UNSIGNED AUTO_INCREMENT,
  sales_entry_no VARCHAR(25) NOT NULL,
  retail_entity_id BIGINT UNSIGNED NOT NULL,
  retail_location_id BIGINT UNSIGNED NOT NULL,
  sales_invoice_id BIGINT UNSIGNED NOT NULL,
  sales_invoice_line_id BIGINT UNSIGNED NOT NULL,
  customer_id BIGINT UNSIGNED NULL,
  retail_product_id BIGINT UNSIGNED NOT NULL,
  quantity DECIMAL(19, 6) NOT NULL,
  unit_price DECIMAL(19, 4) NOT NULL,
  subtotal_amount DECIMAL(19, 4) NOT NULL,
  tax_amount DECIMAL(19, 4) NOT NULL,
  total_amount DECIMAL(19, 4) NOT NULL,
  cost_amount DECIMAL(19, 4) NOT NULL, -- for COGS and profitability analysis (snapshot)
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  created_by_user_id BIGINT UNSIGNED,
  PRIMARY KEY (id),
  FOREIGN KEY (retail_entity_id) REFERENCES org_retail_entity (id),
  FOREIGN KEY (retail_location_id) REFERENCES org_retail_location (id),
  FOREIGN KEY (sales_invoice_id) REFERENCES dom_sales_invoice_header (id),
  FOREIGN KEY (customer_id) REFERENCES dom_customer (id),
  FOREIGN KEY (retail_product_id) REFERENCES dom_retail_product_variant (id),
  FOREIGN KEY (sales_invoice_line_id) REFERENCES dom_sales_invoice_line (id),
  FOREIGN KEY (created_by_user_id) REFERENCES auth_user (id),
  UNIQUE (sales_entry_no)
);

CREATE TABLE IF NOT EXISTS dom_item_entry (
  id BIGINT UNSIGNED AUTO_INCREMENT,
  item_entry_no VARCHAR(25) NOT NULL,
  item_entry_type VARCHAR(50) NOT NULL, -- SALE, RETURN, TRANSFER_SHIPMENT, TRANSFER_RECEIPT
  source_document_reference_type VARCHAR(50) NOT NULL, -- SALES_INVOICE, PURCHASE_ORDER
  source_document_reference_no VARCHAR(25) NOT NULL,
  retail_entity_id BIGINT UNSIGNED NOT NULL,
  retail_location_id BIGINT UNSIGNED NOT NULL,
  retail_product_variant_id BIGINT UNSIGNED NOT NULL,
  retail_product_name VARCHAR(50) NOT NULL, -- snapshot
  quantity DECIMAL(19, 6) NOT NULL,
  unit_cost DECIMAL(19, 4) NOT NULL,
  total_cost DECIMAL(19, 4) NOT NULL,
  posting_date DATE NOT NULL,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  PRIMARY KEY (id),
  FOREIGN KEY (retail_entity_id) REFERENCES org_retail_entity (id),
  FOREIGN KEY (retail_location_id) REFERENCES org_retail_location (id),
  FOREIGN KEY (retail_product_variant_id) REFERENCES dom_retail_product_variant (id),
  UNIQUE (item_entry_no)
);

CREATE TABLE IF NOT EXISTS dom_pos_transaction (
  id BIGINT UNSIGNED AUTO_INCREMENT,
  pos_transaction_no VARCHAR(25) NOT NULL,
  status_id BIGINT UNSIGNED NOT NULL,
  retail_pos_terminal_id BIGINT UNSIGNED NOT NULL,
  retail_entity_id BIGINT UNSIGNED NOT NULL,
  retail_location_id BIGINT UNSIGNED NOT NULL,
  sales_person_id BIGINT UNSIGNED,
  subtotal_amount DECIMAL(19, 4) NOT NULL,
  discount_amount DECIMAL(19, 4) NOT NULL,
  tax_amount DECIMAL(19, 4) NOT NULL,
  total_amount DECIMAL(19, 4) NOT NULL,
  payment_amount DECIMAL(19, 4) NOT NULL,
  cost_amount DECIMAL(19, 4) NOT NULL,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  created_by_user_id BIGINT UNSIGNED,
  PRIMARY KEY (id),
  FOREIGN KEY (status_id) REFERENCES ref_document_status (id),
  FOREIGN KEY (retail_pos_terminal_id) REFERENCES org_retail_pos_terminal (id),
  FOREIGN KEY (retail_entity_id) REFERENCES org_retail_entity (id),
  FOREIGN KEY (retail_location_id) REFERENCES org_retail_location (id),
  FOREIGN KEY (sales_person_id) REFERENCES auth_user (id),
  FOREIGN KEY (created_by_user_id) REFERENCES auth_user (id),
  UNIQUE (pos_transaction_no)
);

CREATE TABLE IF NOT EXISTS dom_retail_product_master (
  id BIGINT UNSIGNED AUTO_INCREMENT,
  retail_product_master_no VARCHAR(25) NOT NULL,
  name VARCHAR(50) NOT NULL,
  description VARCHAR(255),
  enabled BOOLEAN NOT NULL DEFAULT TRUE,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  created_by_user_id BIGINT UNSIGNED,
  updated_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  updated_by_user_id BIGINT UNSIGNED,
  retail_category_id BIGINT UNSIGNED NOT NULL,
  price DECIMAL(19, 4) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (retail_category_id) REFERENCES ref_retail_category (id),
  FOREIGN KEY (created_by_user_id) REFERENCES auth_user (id),
  FOREIGN KEY (updated_by_user_id) REFERENCES auth_user (id),
  UNIQUE (retail_product_master_no)
);

CREATE TABLE IF NOT EXISTS dom_retail_product_master_policy (
  retail_product_master_id BIGINT UNSIGNED,
  discontinued BOOLEAN NOT NULL,
  track_inventory BOOLEAN NOT NULL,
  allow_negative_inventory BOOLEAN NOT NULL,
  apply_tax BOOLEAN NOT NULL,
  apply_promotion BOOLEAN NOT NULL,
  PRIMARY KEY (retail_product_master_id),
  FOREIGN KEY (retail_product_master_id) REFERENCES dom_retail_product_master (id)
);

CREATE TABLE IF NOT EXISTS dom_retail_product_variant (
  id BIGINT UNSIGNED AUTO_INCREMENT,
  retail_product_master_id BIGINT UNSIGNED NOT NULL,
  retail_product_no VARCHAR(25) NOT NULL, -- full product_no
  retail_product_variant_no VARCHAR(25),
  name VARCHAR(50) NOT NULL, -- optional override
  description VARCHAR(255), -- optional override
  enabled BOOLEAN NOT NULL DEFAULT TRUE,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  created_by_user_id BIGINT UNSIGNED,
  updated_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  updated_by_user_id BIGINT UNSIGNED,
  PRIMARY KEY (id),
  FOREIGN KEY (retail_product_master_id) REFERENCES dom_retail_product_master (id),
  FOREIGN KEY (created_by_user_id) REFERENCES auth_user (id),
  FOREIGN KEY (updated_by_user_id) REFERENCES auth_user (id),
  UNIQUE (retail_product_no)
);

CREATE TABLE IF NOT EXISTS dom_retail_product_barcode (
  id BIGINT UNSIGNED AUTO_INCREMENT,
  retail_product_master_id BIGINT UNSIGNED,
  retail_product_variant_id BIGINT UNSIGNED,
  enabled BOOLEAN NOT NULL DEFAULT TRUE,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  created_by_user_id BIGINT UNSIGNED,
  updated_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  updated_by_user_id BIGINT UNSIGNED,
  barcode_type VARCHAR(50) NOT NULL, -- EAN13, UPC, INTERNAL
  barcode_no VARCHAR(50) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (retail_product_variant_id) REFERENCES dom_retail_product_variant (id),
  FOREIGN KEY (retail_product_master_id) REFERENCES dom_retail_product_master (id),
  FOREIGN KEY (created_by_user_id) REFERENCES auth_user (id),
  FOREIGN KEY (updated_by_user_id) REFERENCES auth_user (id),
  UNIQUE (barcode_no)
);

CREATE TABLE IF NOT EXISTS dom_retail_product_price (
  id BIGINT UNSIGNED AUTO_INCREMENT,
  retail_product_variant_id BIGINT UNSIGNED NOT NULL,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  created_by_user_id BIGINT UNSIGNED,
  updated_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  updated_by_user_id BIGINT UNSIGNED,
  price DECIMAL(19, 4) NOT NULL,
  valid_from DATETIME(3) NOT NULL,
  valid_to DATETIME(3),
  PRIMARY KEY (id),
  FOREIGN KEY (retail_product_variant_id) REFERENCES dom_retail_product_variant (id),
  FOREIGN KEY (created_by_user_id) REFERENCES auth_user (id),
  FOREIGN KEY (updated_by_user_id) REFERENCES auth_user (id),
  UNIQUE (retail_product_variant_id, valid_from)
);

CREATE TABLE IF NOT EXISTS dom_retail_product_cost (
  id BIGINT UNSIGNED AUTO_INCREMENT,
  retail_product_variant_id BIGINT UNSIGNED NOT NULL,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  created_by_user_id BIGINT UNSIGNED,
  updated_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  updated_by_user_id BIGINT UNSIGNED,
  cost DECIMAL(19, 4) NOT NULL,
  valid_from DATETIME(3) NOT NULL,
  valid_to DATETIME(3),
  PRIMARY KEY (id),
  FOREIGN KEY (retail_product_variant_id) REFERENCES dom_retail_product_variant (id),
  FOREIGN KEY (created_by_user_id) REFERENCES auth_user (id),
  FOREIGN KEY (updated_by_user_id) REFERENCES auth_user (id),
  UNIQUE (retail_product_variant_id, valid_from)
);

CREATE TABLE IF NOT EXISTS dom_retail_promotion (
  id BIGINT UNSIGNED AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  description VARCHAR(255),
  enabled BOOLEAN NOT NULL DEFAULT TRUE,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  valid_from DATETIME(3) NOT NULL,
  valid_to DATETIME(3),
  fixed_value BOOLEAN NOT NULL, -- FIXED / PERCENT
  value DECIMAL(19, 6) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS dom_retail_promotion_target (
  id BIGINT UNSIGNED AUTO_INCREMENT,
  retail_promotion_id BIGINT UNSIGNED NOT NULL,
  enabled BOOLEAN NOT NULL DEFAULT TRUE,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  target_type VARCHAR(50) NOT NULL, -- VARIANT / PRODUCT_MASTER / CATEGORY
  target_id BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (retail_promotion_id) REFERENCES dom_retail_promotion (id)
);

CREATE TABLE IF NOT EXISTS dom_customer (
  id BIGINT UNSIGNED AUTO_INCREMENT,
  customer_no VARCHAR(25) NOT NULL,
  name VARCHAR(50) NOT NULL,
  name2 VARCHAR(50),
  email VARCHAR(100),
  phone VARCHAR(20),
  phone2 VARCHAR(20),
  ship_to_address_line1 VARCHAR(255),
  ship_to_address_line2 VARCHAR(255),
  ship_to_city VARCHAR(50),
  ship_to_state VARCHAR(50),
  ship_to_postal_code VARCHAR(20),
  ship_to_country VARCHAR(50),
  bill_to_address_line1 VARCHAR(255),
  bill_to_address_line2 VARCHAR(255),
  bill_to_city VARCHAR(50),
  bill_to_state VARCHAR(50),
  bill_to_postal_code VARCHAR(20),
  bill_to_country VARCHAR(50),
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  created_by_user_id BIGINT UNSIGNED,
  updated_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  updated_by_user_id BIGINT UNSIGNED,
  PRIMARY KEY (id),
  FOREIGN KEY (created_by_user_id) REFERENCES auth_user (id),
  FOREIGN KEY (updated_by_user_id) REFERENCES auth_user (id),
  UNIQUE (customer_no)
);

CREATE TABLE IF NOT EXISTS dom_purchase_order_header (
  id BIGINT UNSIGNED AUTO_INCREMENT,
  purchase_order_no VARCHAR(25) NOT NULL,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  PRIMARY KEY (id),
  UNIQUE (purchase_order_no)
);

CREATE TABLE IF NOT EXISTS dom_purchase_order_line (
  id BIGINT UNSIGNED AUTO_INCREMENT,
  purchase_order_id BIGINT UNSIGNED NOT NULL,
  retail_product_id BIGINT UNSIGNED NOT NULL,
  retail_product_name VARCHAR(50) NOT NULL, -- snapshot
  quantity DECIMAL(19, 6) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (purchase_order_id) REFERENCES dom_purchase_order_header (id),
  FOREIGN KEY (retail_product_id) REFERENCES dom_retail_product_variant (id)
);

CREATE TABLE IF NOT EXISTS dom_transfer_order_header (
  id BIGINT UNSIGNED AUTO_INCREMENT,
  transfer_order_no VARCHAR(25) NOT NULL,
  transfer_from_retail_location_id BIGINT UNSIGNED NOT NULL,
  transfer_to_retail_location_id BIGINT UNSIGNED NOT NULL,
  in_transit_retail_location_id BIGINT UNSIGNED NOT NULL,
  status_id BIGINT UNSIGNED NOT NULL,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  created_by_user_id BIGINT UNSIGNED,
  PRIMARY KEY (id),
  FOREIGN KEY (transfer_from_retail_location_id) REFERENCES org_retail_location (id),
  FOREIGN KEY (transfer_to_retail_location_id) REFERENCES org_retail_location (id),
  FOREIGN KEY (in_transit_retail_location_id) REFERENCES org_retail_location (id),
  FOREIGN KEY (status_id) REFERENCES ref_document_status (id),
  FOREIGN KEY (created_by_user_id) REFERENCES auth_user (id),
  UNIQUE (transfer_order_no)
);

CREATE TABLE IF NOT EXISTS dom_transfer_order_line (
  id BIGINT UNSIGNED AUTO_INCREMENT,
  transfer_order_line_no VARCHAR(25) NOT NULL,
  transfer_order_id BIGINT UNSIGNED NOT NULL,
  retail_product_id BIGINT UNSIGNED NOT NULL,
  retail_product_name VARCHAR(50) NOT NULL, -- snapshot
  quantity DECIMAL(19, 6) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (transfer_order_id) REFERENCES dom_transfer_order_header (id),
  FOREIGN KEY (retail_product_id) REFERENCES dom_retail_product_variant (id),
  UNIQUE (transfer_order_line_no, transfer_order_id)
);

CREATE TABLE IF NOT EXISTS dom_reservation_entry (
  id BIGINT UNSIGNED AUTO_INCREMENT,
  retail_product_variant_id BIGINT UNSIGNED NOT NULL,
  reserved_from_retail_location_id BIGINT UNSIGNED NULL,
  reserved_from_item_entry_id BIGINT UNSIGNED NULL,
  reserved_from_transfer_order_line_id BIGINT UNSIGNED NULL,
  reserved_from_purchase_order_line_id BIGINT UNSIGNED NULL,
  reserved_for_customer_order_line_id BIGINT UNSIGNED NULL,
  reserved_for_transfer_order_line_id BIGINT UNSIGNED NULL,
  quantity DECIMAL(19, 6) NOT NULL,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  PRIMARY KEY (id),
  FOREIGN KEY (retail_product_variant_id) REFERENCES dom_retail_product_variant (id),
  FOREIGN KEY (reserved_from_retail_location_id) REFERENCES org_retail_location (id),
  FOREIGN KEY (reserved_from_item_entry_id) REFERENCES dom_item_entry (id),
  FOREIGN KEY (reserved_from_transfer_order_line_id) REFERENCES dom_transfer_order_line (id),
  FOREIGN KEY (reserved_from_purchase_order_line_id) REFERENCES dom_purchase_order_line (id),
  FOREIGN KEY (reserved_for_customer_order_line_id) REFERENCES dom_customer_order_line (id),
  FOREIGN KEY (reserved_for_transfer_order_line_id) REFERENCES dom_transfer_order_line (id)
);

-- ====================================
-- AUTOMATION TABLES (auto_)
-- ====================================
CREATE TABLE IF NOT EXISTS auto_job (
  id BIGINT UNSIGNED AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  description VARCHAR(255),
  handler VARCHAR(50), -- maps to java class
  run_before_id BIGINT UNSIGNED,
  run_after_id BIGINT UNSIGNED,
  active BOOLEAN NOT NULL DEFAULT FALSE, -- short term
  deactivate_on_failure BOOLEAN NOT NULL DEFAULT TRUE,
  max_retries INT NOT NULL DEFAULT 0,
  interval_minutes DECIMAL(8, 2),
  run_times_utc json,
  run_days json,
  PRIMARY KEY (id),
  FOREIGN KEY (run_before_id) REFERENCES auto_job (id),
  FOREIGN KEY (run_after_id) REFERENCES auto_job (id)
);

CREATE TABLE IF NOT EXISTS auto_job_log (
  id BIGINT UNSIGNED AUTO_INCREMENT,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  job_id BIGINT UNSIGNED NOT NULL,
  started_at DATETIME(3),
  ended_at DATETIME(3),
  execution_time_ms INT NOT NULL DEFAULT 0,
  error BOOLEAN NOT NULL,
  message TEXT,
  PRIMARY KEY (id),
  FOREIGN KEY (job_id) REFERENCES auto_job (id)
);

-- ====================================
-- ACCOUNTING TABLES (acc_)
-- ====================================
CREATE TABLE IF NOT EXISTS acc_account (
  id BIGINT UNSIGNED AUTO_INCREMENT,
  account_no VARCHAR(25) NOT NULL,
  name VARCHAR(50) NOT NULL,
  enabled BOOLEAN NOT NULL,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  account_type VARCHAR(50) NOT NULL, -- ASSET, LIABILITY, EQUITY, REVENUE, EXPENSE
  parent_account_id BIGINT UNSIGNED,
  allow_posting BOOLEAN NOT NULL DEFAULT TRUE,
  PRIMARY KEY (id),
  FOREIGN KEY (parent_account_id) REFERENCES acc_account (id),
  UNIQUE (account_no)
);

CREATE TABLE IF NOT EXISTS acc_accounting_period (
  id BIGINT UNSIGNED AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL, -- 2024, 2024-Q1, 2024-01
  description VARCHAR(255),
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  start_date DATE NOT NULL,
  end_date DATE NOT NULL,
  status_id BIGINT UNSIGNED NOT NULL, -- OPEN, CLOSED
  PRIMARY KEY (id),
  FOREIGN KEY (status_id) REFERENCES ref_document_status (id),
  UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS acc_gl_entry_header (
  id BIGINT UNSIGNED AUTO_INCREMENT,
  gl_entry_no VARCHAR(25) NOT NULL,
  accounting_period_id BIGINT UNSIGNED NOT NULL,
  source_document_reference_type VARCHAR(50) NOT NULL, -- SALES_INVOICE, PURCHASE_ORDER
  source_document_reference_no VARCHAR(25) NOT NULL,
  description VARCHAR(255),
  status_id BIGINT UNSIGNED NOT NULL,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  PRIMARY KEY (id),
  FOREIGN KEY (accounting_period_id) REFERENCES acc_accounting_period (id),
  FOREIGN KEY (status_id) REFERENCES ref_document_status (id),
  UNIQUE (gl_entry_no)
);

CREATE TABLE IF NOT EXISTS acc_gl_entry_line (
  id BIGINT UNSIGNED AUTO_INCREMENT,
  gl_entry_header_id BIGINT UNSIGNED NOT NULL,
  account_id BIGINT UNSIGNED NOT NULL,
  debit_amount DECIMAL(19, 4) NOT NULL,
  credit_amount DECIMAL(19, 4) NOT NULL,
  retail_entity_id BIGINT UNSIGNED,
  retail_location_id BIGINT UNSIGNED,
  PRIMARY KEY (id),
  FOREIGN KEY (gl_entry_header_id) REFERENCES acc_gl_entry_header (id),
  FOREIGN KEY (account_id) REFERENCES acc_account (id),
  FOREIGN KEY (retail_entity_id) REFERENCES org_retail_entity (id),
  FOREIGN KEY (retail_location_id) REFERENCES org_retail_location (id)
);

-- ====================================
-- AUTH TABLES (auth_)
-- ====================================
CREATE TABLE IF NOT EXISTS auth_user (
  id BIGINT UNSIGNED AUTO_INCREMENT,
  user_no VARCHAR(25) NOT NULL,
  user_name VARCHAR(50) NOT NULL,
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  created_by_user_id BIGINT UNSIGNED,
  updated_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  updated_by_user_id BIGINT UNSIGNED,
  PRIMARY KEY (id),
  FOREIGN KEY (created_by_user_id) REFERENCES auth_user (id),
  FOREIGN KEY (updated_by_user_id) REFERENCES auth_user (id),
  UNIQUE (user_no)
);

CREATE TABLE IF NOT EXISTS auth_user_credential (
  id BIGINT UNSIGNED AUTO_INCREMENT,
  user_id BIGINT UNSIGNED NOT NULL,
  password VARCHAR(255) NOT NULL,
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES auth_user (id)
);

CREATE TABLE IF NOT EXISTS auth_role (
  id BIGINT UNSIGNED AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL, -- ADMIN, SALES_CLERK, INVENTORY_MANAGER
  description VARCHAR(255),
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  PRIMARY KEY (id),
  UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS auth_user_role (
  user_id BIGINT UNSIGNED NOT NULL,
  role_id BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (user_id, role_id),
  FOREIGN KEY (user_id) REFERENCES auth_user (id),
  FOREIGN KEY (role_id) REFERENCES auth_role (id)
);

CREATE TABLE IF NOT EXISTS auth_permission (
  id BIGINT UNSIGNED AUTO_INCREMENT,
  ressource VARCHAR(50) NOT NULL, -- CUSTOMER_ORDER, SALES_INVOICE, INVENTORY_ADJUSTMENT
  action VARCHAR(50) NOT NULL, -- CREATE, READ, UPDATE, DELETE, POST
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  PRIMARY KEY (id),
  UNIQUE (ressource, action)
);

CREATE TABLE IF NOT EXISTS auth_role_permission (
  role_id BIGINT UNSIGNED NOT NULL,
  permission_id BIGINT UNSIGNED NOT NULL,
  allow BOOLEAN NOT NULL DEFAULT TRUE, -- allow or deny permission, for future use when we have more complex permission evaluation logic
  PRIMARY KEY (role_id, permission_id),
  FOREIGN KEY (role_id) REFERENCES auth_role (id),
  FOREIGN KEY (permission_id) REFERENCES auth_permission (id)
);

CREATE TABLE IF NOT EXISTS auth_user_permission (
  user_id BIGINT UNSIGNED NOT NULL,
  permission_id BIGINT UNSIGNED NOT NULL,
  allow BOOLEAN NOT NULL DEFAULT TRUE, -- allow or deny permission, for future use when we have more complex permission evaluation logic
  PRIMARY KEY (user_id, permission_id),
  FOREIGN KEY (user_id) REFERENCES auth_user (id),
  FOREIGN KEY (permission_id) REFERENCES auth_permission (id)
);

CREATE TABLE IF NOT EXISTS auth_policy (
  id BIGINT UNSIGNED AUTO_INCREMENT,
  permission_id BIGINT UNSIGNED NOT NULL,
  condition_expression TEXT NOT NULL, -- e.g. "retail_entity_id IN (SELECT retail_entity_id FROM org_retail_entity_user WHERE user_id = ?)" for row level security,
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  PRIMARY KEY (id),
  FOREIGN KEY (permission_id) REFERENCES auth_permission (id)
);

CREATE TABLE IF NOT EXISTS auth_refresh_token (
  id BIGINT UNSIGNED AUTO_INCREMENT,
  user_id BIGINT UNSIGNED NOT NULL,
  token VARCHAR(255) NOT NULL,
  expires_at DATETIME(3) NOT NULL,
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES auth_user (id)
);

-- ====================================
-- STATING TABLES (stage_)
-- ====================================
CREATE TABLE IF NOT EXISTS stage_pos_transaction_header (
  pos_transaction_no VARCHAR(25) NOT NULL,
  retail_entity_id BIGINT UNSIGNED NOT NULL,
  retail_location_id BIGINT UNSIGNED NOT NULL,
  retail_pos_terminal_id BIGINT UNSIGNED NOT NULL,
  sales_person_id BIGINT UNSIGNED,
  customer_id BIGINT UNSIGNED NULL,
  name VARCHAR(50) NULL, -- snapshot
  name2 VARCHAR(50) NULL, -- snapshot
  email VARCHAR(100) NULL, -- snapshot
  phone VARCHAR(20) NULL, -- snapshot
  phone2 VARCHAR(20) NULL, -- snapshot
  ship_to_address_line1 VARCHAR(255) NULL, -- snapshot
  ship_to_address_line2 VARCHAR(255) NULL, -- snapshot
  ship_to_city VARCHAR(50) NULL, -- snapshot
  ship_to_state VARCHAR(50) NULL, -- snapshot
  ship_to_postal_code VARCHAR(20) NULL, -- snapshot
  ship_to_country VARCHAR(50) NULL, -- snapshot
  bill_to_address_line1 VARCHAR(255) NULL, -- snapshot
  bill_to_address_line2 VARCHAR(255) NULL, -- snapshot
  bill_to_city VARCHAR(50) NULL, -- snapshot
  bill_to_state VARCHAR(50) NULL, -- snapshot
  bill_to_postal_code VARCHAR(20) NULL, -- snapshot
  bill_to_country VARCHAR(50) NULL, -- snapshot
  tax_group_id BIGINT UNSIGNED NOT NULL, -- QC_STANDARD
  subtotal_amount DECIMAL(19, 4) NOT NULL,
  discount_amount DECIMAL(19, 4) NOT NULL,
  tax_amount DECIMAL(19, 4) NOT NULL,
  total_amount DECIMAL(19, 4) NOT NULL,
  payment_amount DECIMAL(19, 4) NOT NULL,
  balance_amount DECIMAL(19, 4) NOT NULL,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  created_by_user_id BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (pos_transaction_no),
  FOREIGN KEY (retail_pos_terminal_id) REFERENCES org_retail_pos_terminal (id),
  FOREIGN KEY (retail_entity_id) REFERENCES org_retail_entity (id),
  FOREIGN KEY (retail_location_id) REFERENCES org_retail_location (id),
  FOREIGN KEY (sales_person_id) REFERENCES auth_user (id),
  FOREIGN KEY (customer_id) REFERENCES dom_customer (id),
  FOREIGN KEY (tax_group_id) REFERENCES ref_tax_group (id),
  FOREIGN KEY (created_by_user_id) REFERENCES auth_user (id)
);

CREATE TABLE IF NOT EXISTS stage_pos_transaction_line (
  pos_transaction_line_no VARCHAR(25) NOT NULL,
  pos_transaction_no VARCHAR(25) NOT NULL,
  retail_product_id BIGINT UNSIGNED NOT NULL,
  retail_product_name VARCHAR(50) NOT NULL, -- snapshot
  sourced_from_retail_location_id BIGINT UNSIGNED NOT NULL,
  quantity DECIMAL(19, 6) NOT NULL,
  unit_price DECIMAL(19, 4) NOT NULL,
  discount_amount DECIMAL(19, 4) NOT NULL,
  subtotal_amount DECIMAL(19, 4) NOT NULL, -- before tax
  tax_amount DECIMAL(19, 4) NOT NULL, -- total tax for this line
  total_amount DECIMAL(19, 4) NOT NULL, -- subtotal + tax
  tax_group_id BIGINT UNSIGNED NOT NULL, -- QC_STANDARD
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  PRIMARY KEY (pos_transaction_line_no, pos_transaction_no),
  FOREIGN KEY (pos_transaction_no) REFERENCES stage_pos_transaction_header (pos_transaction_no),
  FOREIGN KEY (retail_product_id) REFERENCES dom_retail_product_variant (id),
  FOREIGN KEY (sourced_from_retail_location_id) REFERENCES org_retail_location (id),
  FOREIGN KEY (tax_group_id) REFERENCES ref_tax_group (id)
);
