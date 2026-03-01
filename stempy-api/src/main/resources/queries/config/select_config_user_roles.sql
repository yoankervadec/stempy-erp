SELECT
  id,
  name,
  is_enabled
FROM
  config_user_roles
WHERE
  is_enabled = TRUE;