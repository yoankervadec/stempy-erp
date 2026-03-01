SELECT
  next_value
FROM
  config_entity_sequences
WHERE
  entity_type = ?
  AND is_enabled = TRUE
FOR UPDATE;