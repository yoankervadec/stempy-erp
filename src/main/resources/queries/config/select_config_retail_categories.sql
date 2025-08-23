SELECT
  id,
  name,
  description,
  is_enabled
FROM
  config_retail_categories
WHERE
  is_enabled = TRUE;