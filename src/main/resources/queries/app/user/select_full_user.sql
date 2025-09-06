SELECT
  us.user_no,
  us.user_seq,
  us.username_short,
  us.username_long,
  us.user_role,
  us.user_password,
  us.user_pin,
  us.user_permissions,
  us.user_tax_region,
  us.is_enabled,
  us.created_at,
  us.created_by
FROM
  users AS us
/* WHERE */