
UPDATE
  retail_product_variant
SET
  retail_product_master_id = :RetailProduct.retailProductMasterId,
  retail_product_no = :RetailProduct.retailProductNo,
  retail_product_variant_no = :RetailProduct.retailProductVariantNo,
  name = :RetailProduct.name,
  description = :RetailProduct.description,
  enabled = :RetailProduct.enabled,
  updated_at = :RetailProduct.updatedAt,
  updated_by_user_id = :RetailProduct.updatedByUserId