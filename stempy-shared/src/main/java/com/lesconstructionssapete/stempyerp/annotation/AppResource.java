package com.lesconstructionssapete.stempyerp.annotation;

public enum AppResource {

  // =======================================
  // Authentication and Authorization Resources
  // =======================================

  /**
   * Required permission for permission management, including assignment to
   * roles and users.
   */
  AUTH_PERMISSION,

  AUTH_ROLE,

  AUTH_USER,

  AUTH_USER_CREDENTIAL,

  AUTH_REFRESH_TOKEN,

  // =======================================
  // Automation Resources
  // =======================================

  AUTO_JOB,

  AUTO_JOB_LOG,

  // =======================================
  // Domain Resources
  // =======================================

  RETAIL_PRODUCT_MASTER,

  RETAIL_PRODUCT_MASTER_POLICY,

  RETAIL_PRODUCT_VARIANT,

  RETAIL_PRODUCT_COST,

  RETAIL_PRODUCT_PRICE,

  CUSTOMER,

  CUSTOMER_ORDER_HEADER,

  CUSTOMER_ORDER_LINE,

  CUSTOMER_ORDER_LINE_TAX,

  SALES_INVOICE_HEADER,

  SALES_INVOICE_LINE,

  SALES_INVOICE_LINE_TAX,

  TRANSFER_ORDER_HEADER,

  TRANSFER_ORDER_LINE,

  ITEM_ENTRY,

  PAYMENT_ENTRY,

  SALES_ENTRY,

  POS_TRANSACTION,

  PURCHASE_ORDER_HEADER,

  PURCHASE_ORDER_LINE,

  RESERVATION_ENTRY,

  RETAIL_PROMOTION,

  RETAIL_PROMOTION_TARGET,

  // =======================================
  // Organisation Resources
  // =======================================

  ORG_RETAIL_ENTITY,

  ORG_RETAIL_ENTITY_SETUP,

  ORG_RETAIL_LOCATION,

  ORG_RETAIL_LOCATION_SETUP,

  ORG_RETAIL_POS_TERMINAL,

  // =======================================
  // System/Reference Resources
  // =======================================

  REF_DOCUMENT_STATUS,

  REF_PAYMENT_METHOD,

  REF_RETAIL_CATEGORY,

  REF_TAX_GROUP,

  REF_TAX_GROUP_LINE,

  REF_TAX_RATE,

  // =======================================
  // Staging Resources
  // =======================================

  STAGE_POS_TRANSACTION_HEADER,

  STAGE_POS_TRANSACTION_LINE,

}
