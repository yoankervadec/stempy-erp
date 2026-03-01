SELECT
  id,
  name,
  is_enabled
FROM
  config_payment_methods
WHERE
  is_enabled = TRUE