SELECT
  auth_permission.id,
  auth_permission.resource,
  auth_permission.action,
  auth_permission.enabled,
  auth_permission.created_at
FROM
  auth_permission