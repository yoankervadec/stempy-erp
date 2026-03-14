SELECT
  auth_refresh_token.id,
  auth_refresh_token.user_id,
  auth_user.user_no,
  auth_refresh_token.token,
  auth_refresh_token.expires_at,
  auth_refresh_token.enabled,
  auth_refresh_token.created_at
FROM
  auth_refresh_token
JOIN
  auth_user ON auth_refresh_token.user_id = auth_user.id
/* WHERE */
