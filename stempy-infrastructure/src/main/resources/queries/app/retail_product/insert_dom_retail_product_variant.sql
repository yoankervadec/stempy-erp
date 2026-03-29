INSERT INTO
  retail_product_variant (
    retail_product_master_id,
    retail_product_no,
    retail_product_variant_no,
    name,
    description,
    enabled,
    created_at,
    created_by_user_id,
    updated_at,
    updated_by_user_id
  )
VALUES (
  :RetailProduct.retailProductMasterId,
  :RetailProduct.retailProductNo,
  :RetailProduct.retailProductVariantNo,
  :RetailProduct.name,
  :RetailProduct.description,
  :RetailProduct.enabled,
  :RetailProduct.createdAt,
  :RetailProduct.createdByUserId,
  :RetailProduct.updatedAt,
  :RetailProduct.updatedByUserId
);