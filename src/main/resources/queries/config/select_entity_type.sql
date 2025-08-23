SELECT
  id,
  name,
  pad_length,
  prefix_string,
  is_enabled
FROM
  config_entity_types
WHERE
  is_enabled = TRUE