package com.lesconstructionssapete.stempyerp.query;

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
  SELECT_CORE_DOMAIN_ENTITY_CONFIG("config/select_core_domain_entity_config.sql", false),
  SELECT_REF_PAYMENT_METHOD("config/select_ref_payment_method.sql", false),
  SELECT_REF_RETAIL_CATEGORY("config/select_ref_retail_category.sql", false),
  SELECT_CONFIG_RETAIL_LOCATION("config/select_config_retail_locations.sql", false),
  SELECT_CONFIG_ROLE_ACTION("config/select_config_role_actions.sql", false),
  SELECT_CONFIG_USER_ACTION("config/select_config_user_actions.sql", false),
  SELECT_CONFIG_USER_ROLE("config/select_config_user_roles.sql", false),
  SELECT_REF_TAX_GROUP("config/select_ref_tax_group.sql", false),
  SELECT_REF_TAX_GROUP_LINE("config/select_ref_tax_group_line.sql", false),
  SELECT_REF_TAX_RATE("config/select_ref_tax_rate.sql", false),

  // Scheduler/Job
  SELECT_JOBS("app/scheduler/select_jobs.sql", false),

  // Entity Sequence
  SELECT_NEXT_SEQUENCE_NO("app/sequence/select_next_sequence_no.sql", true),

  // Retail Product
  SELECT_RETAIL_PRODUCT_VARIANT("app/retail_product/select_retail_product_variant.sql", true),

  // Auth/Refresh Tokens
  SELECT_AUTH_REFRESH_TOKENS("app/auth/select_auth_refresh_tokens.sql", true),

  // Auth / Users
  SELECT_USER("app/auth/select_user.sql", true),
  SELECT_USER_CREDENTIAL("app/auth/select_user_credential.sql", true),

  /**
   * ==========================
   * INSERT
   * ==========================
   */

  // Retail Product
  INSERT_RETAIL_PRODUCT_VARIANT("app/retail_product/insert_retail_product_variant.sql", true),

  // Auth/Refresh Tokens
  INSERT_AUTH_REFRESH_TOKEN("app/auth/insert_auth_refresh_token.sql", true),

  // Automation
  INSERT_JOB_LOG("app/automation/insert_job_log.sql", true),

  /**
   * ==========================
   * UPDATE
   * ==========================
   */

  // Retail Product
  UPDATE_RETAIL_PRODUCT_VARIANT("app/retail_product/update_retail_product_variant.sql", true),

  // Entity Sequence
  UPDATE_NEXT_SEQUENCE_NO("app/sequence/update_next_sequence_no.sql", true),

  // Automation
  UPDATE_JOB("app/automation/update_job.sql", true);

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
