package com.lesconstructionssapete.stempyerp.core.shared.query;

/*
 * Define query paths.
 * Used by QueryCache exclusively.
 */

public enum Query {

  /**
   * ==========================
   * SELECT / VIEWS
   * ==========================
   */

  // Config
  SELECT_CONFIG_CUSTOMER_ORDER_HEADER_STATUS("config/select_customer_order_header_status.sql", false),
  SELECT_CONFIG_CUSTOMER_ORDER_LINE_STATUS("config/select_customer_order_line_status.sql", false),
  SELECT_CONFIG_ENTITY_TYPE("config/select_entity_type.sql", false),
  SELECT_CONFIG_TAX_REGION("config/select_config_tax_region.sql", false),
  SELECT_CONFIG_ITEM_ENTRY_TYPES("config/select_config_item_entry_types.sql", false),
  SELECT_CONFIG_PAYMENT_METHODS("config/select_config_payment_methods.sql", false),
  SELECT_CONFIG_POS_TRANSACTION_TYPES("config/select_config_pos_transaction_types.sql", false),
  SELECT_CONFIG_RETAIL_CATEGORIES("config/select_config_retail_categories.sql", false),
  SELECT_CONFIG_RETAIL_LOCATIONS("config/select_config_retail_locations.sql", false),
  SELECT_CONFIG_ROLE_ACTIONS("config/select_config_role_actions.sql", false),
  SELECT_CONFIG_USER_ACTION("config/select_config_user_actions.sql", false),
  SELECT_CONFIG_USER_ROLE("config/select_config_user_roles.sql", false),

  // Scheduler/Job
  SELECT_JOBS("app/scheduler/select_jobs.sql", false),

  // Entity Sequence
  SELECT_NEXT_SEQUENCE_NO("app/sequence/select_next_sequence_no.sql", true),

  // Retail Product
  SELECT_RETAIL_PRODUCT_BY_PRODUCT_NO("app/retail_product/select_by_product_no.sql", true),

  // Auth/Refresh Tokens
  SELECT_AUTH_REFRESH_TOKENS("app/auth/select_auth_refresh_tokens.sql", true),

  // Users
  SELECT_FULL_USER("app/user/select_full_user.sql", true),

  /**
   * ==========================
   * INSERT
   * ==========================
   */

  // Retail Product
  INSERT_RETAIL_PRODUCT("app/retail_product/insert_retail_product.sql", true),

  // Auth/Refresh Tokens
  INSERT_AUTH_REFRESH_TOKEN("app/auth/insert_auth_refresh_token.sql", true),

  // Automation
  INSERT_JOB_LOG("app/automation/insert_job_log.sql", true),

  /**
   * ==========================
   * UPDATE
   * ==========================
   */

  UPDATE_RETAIL_PRODUCT("app/retail_product/update_retail_product.sql", true),

  // Entity Sequence
  UPDATE_NEXT_SEQUENCE_NO("app/sequence/update_next_sequence_no.sql", true),

  /**
   * ==========================
   * DELETE
   * ==========================
   */

  ;

  private final String filePath;
  private final boolean cache;
  private static final String FOLDER = "/queries/";

  Query(String filePath, boolean cache) {
    this.filePath = FOLDER + filePath;
    this.cache = cache;
  }

  public String getFilePath() {
    return filePath;
  }

  public boolean isCacheEnabled() {
    return cache;
  }
}
