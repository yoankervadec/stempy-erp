SELECT
  auth_user_role.user_id,
  auth_user_role.role_id,
  auth_role.name,
  auth_role.description,
  auth_role.enabled,
  auth_role.created_at
FROM
  auth_user_role
JOIN
  auth_role ON auth_user_role.role_id = auth_role.id