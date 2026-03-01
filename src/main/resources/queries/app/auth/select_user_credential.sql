SELECT
  auth_user_credential.id,
  auth_user_credential.user_id,
  auth_user_credential.password,
  auth_user_credential.enabled,
  auth_user_credential.created_at
FROM
  auth_user_credential