SELECT
  id,
  name,
  is_enabled
FROM
  config_item_entry_types
WHERE
  is_enabled = TRUE