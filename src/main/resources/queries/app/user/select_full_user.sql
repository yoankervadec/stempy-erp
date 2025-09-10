SELECT
  us.user_no,
  us.user_seq,
  us.username_short,
  us.username_long,
  us.user_role AS user_role_id,
  cur.name AS role_name,
  cur.is_enabled AS is_user_role_enabled,
  us.user_password,
  us.user_pin,
  us.user_permissions,
  us.user_tax_region AS user_tax_region_id,
  ctr.tax_region,
  ctr.name AS region_name,
  ctr.gst_rate,
  ctr.pst_rate,
  ctr.is_enabled AS is_tax_region_enabled,
  us.is_enabled,
  us.created_at,
  us.created_by
FROM
  users AS us
JOIN
  config_tax_rates AS ctr ON us.user_tax_region = ctr.id
JOIN
  config_user_roles AS cur ON us.user_role = cur.id
/* WHERE */