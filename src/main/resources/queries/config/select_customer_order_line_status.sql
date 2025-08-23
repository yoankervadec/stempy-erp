SELECT
  id,
  name,
  is_enabled
FROM
  config_customer_order_line_status
WHERE
  is_enabled = TRUE;