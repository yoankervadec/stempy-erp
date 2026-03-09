UPDATE
  dom_retail_product_master
SET
  retail_product_master_no = :retailProductMasterNo,
  name = :name,
  description = :description,
  enabled = :enabled,
  updated_at = :updatedAt
  updated_by_user_id = :updatedByUserId
  retail_category_id = :retailCategoryId
  price = :defaultPrice