SELECT
  ra.role_id,
  ra.action_id,
  ua.name AS action_name,
  ra.is_enabled
FROM
  config_role_actions AS ra
JOIN
  config_user_actions AS ua ON ra.action_id = ua.id
WHERE
  ra.is_enabled = TRUE;