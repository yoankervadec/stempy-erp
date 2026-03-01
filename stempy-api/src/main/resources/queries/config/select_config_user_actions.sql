SELECT
  id,
  name,
  is_enabled
FROM
  config_user_actions
WHERE
  is_enabled = TRUE;