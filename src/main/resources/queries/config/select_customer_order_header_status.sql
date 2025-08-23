SELECT
  id,
  name,
  is_enabled
FROM
  config_customer_order_header_status
WHERE
  is_enabled = TRUE;