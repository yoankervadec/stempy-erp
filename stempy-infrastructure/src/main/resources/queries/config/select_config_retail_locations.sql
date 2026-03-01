SELECT
  id,
  name,
  description,
  is_enabled
FROM
  config_retail_locations
WHERE
  is_enabled = TRUE;