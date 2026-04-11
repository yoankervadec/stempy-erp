SELECT
  auth_user_permission.user_id,
  auth_user_permission.permission_id,
  auth_user_permission.allow
FROM
  auth_user_permission

  SELECT
  auth_user_permission.user_id,
  auth_user_permission.permission_id,
  auth_user_permission.allow,
  auth_permission.resource,
  auth_permission.action,
  auth_permission.enabled,
  auth_permission.created_at
FROM
  auth_user_permission
JOIN
  auth_permission ON auth_permission.id = auth_user_permission.permission_id