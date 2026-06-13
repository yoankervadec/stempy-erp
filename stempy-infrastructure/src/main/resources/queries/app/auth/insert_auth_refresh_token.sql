INSERT INTO
  auth_refresh_token (
    user_id,
    token,
    expires_at,
    enabled
  )
VALUES (
  :AuthToken.userId,
  :AuthToken.token,
  :AuthToken.refreshTokenExpiresAt,
  :AuthToken.enabled
);