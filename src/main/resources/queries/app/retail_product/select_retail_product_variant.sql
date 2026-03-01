
SELECT
  retail_product_variant.id,
  retail_product_variant.retail_product_master_id,
  retail_product_variant.retail_product_no,
  retail_product_variant.retail_product_variant_no,
  retail_product_variant.name,
  retail_product_variant.description,
  retail_product_variant.enabled,
  retail_product_variant.created_at,
  retail_product_variant.created_by_user_id,
  retail_product_variant.updated_at,
  retail_product_variant.updated_by_user_id
FROM
  retail_product_variant