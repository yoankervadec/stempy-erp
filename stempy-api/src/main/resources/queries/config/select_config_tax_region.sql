SELECT
  id,
  tax_region,
  name,
  gst_rate,
  pst_rate,
  is_enabled
FROM
  config_tax_rates
WHERE
  is_enabled = TRUE