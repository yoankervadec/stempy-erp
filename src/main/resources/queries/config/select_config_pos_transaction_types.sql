SELECT
  id,
  name,
  is_enabled
FROM
  config_pos_transaction_types
WHERE
  is_enabled = TRUE