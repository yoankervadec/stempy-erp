UPDATE
  config_entity_sequences
SET
  next_value = next_value + 1
WHERE
  entity_type = ?
  AND is_enabled = TRUE;