UPDATE
  dom_retail_product_master
SET
  retail_product_master_no = :RetailProductMaster.retailProductMasterNo,
  name = :RetailProductMaster.name,
  description = :RetailProductMaster.description,
  enabled = :RetailProductMaster.enabled,
  updated_at = :RetailProductMaster.updatedAt,
  updated_by_user_id = :RetailProductMaster.updatedByUserId,
  retail_category_id = :RetailProductMaster.retailCategoryId,
  price = :RetailProductMaster.defaultPrice