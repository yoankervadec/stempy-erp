
UPDATE
  retail_products AS rp
SET
  rp.product_no = ?,
  rp.product_seq = ?,
  rp.retail_price = ?,
  rp.cost = ?,
  rp.description = ?,
  rp.retail_category AS retail_category_id = ?,
  rp.wood_specie AS wood_specy_id = ?,
  rp.product_width = ?,
  rp.product_thickness = ?,
  rp.product_length = ?,
  rp.is_enabled = ?
/*WHERE*/