SELECT
  auth_role_permission.role_id,
  auth_role_permission.permission_id,
  auth_role_permission.allow,
  auth_permission.resource,
  auth_permission.action,
  auth_permission.enabled,
  auth_permission.created_at
FROM
  auth_role_permission
JOIN
  auth_permission ON auth_permission.id = auth_role_permission.permission_id