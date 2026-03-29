INSERT INTO
  dom_retail_product_master (
    retail_product_master_no,
    name,
    description,
    enabled,
    created_at,
    created_by_user_id,
    updated_at,
    updated_by_user_id,
    retail_category_id,
    price
  )
VALUES
  (
    :RetailProductMaster.retailProductMasterNo,
    :RetailProductMaster.name,
    :RetailProductMaster.description,
    :RetailProductMaster.enabled,
    :RetailProductMaster.createdAt,
    :RetailProductMaster.createdByUserId,
    :RetailProductMaster.updatedAt,
    :RetailProductMaster.updatedByUserId,
    :RetailProductMaster.retailCategoryId,
    :RetailProductMaster.defaultPrice
  )