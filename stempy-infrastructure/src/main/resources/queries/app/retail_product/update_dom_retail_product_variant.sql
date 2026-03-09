
UPDATE
  retail_product_variant
SET
  retail_product_master_id = :retailProductMasterId,
  retail_product_no = :retailProductNo,
  retail_product_variant_no = :retailProductVariantNo,
  name = :name,
  description = :description,
  enabled = :enabled,
  updated_at = :updatedAt,
  updated_by_user_id = :updatedByUserId