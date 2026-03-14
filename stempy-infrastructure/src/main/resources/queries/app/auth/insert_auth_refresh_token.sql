INSERT INTO
  auth_refresh_token (
    user_id,
    token,
    expires_at,
    enabled
  )
VALUES (
  :userId,
  :token,
  :expiresAt,
  :enabled
);