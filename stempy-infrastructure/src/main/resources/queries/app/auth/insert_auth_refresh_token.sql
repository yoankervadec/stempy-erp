INSERT INTO
  auth_refresh_token (
    user_id,
    token,
    expires_at,
    enabled
  )
VALUES (
  :RefreshToken.userId,
  :RefreshToken.token,
  :RefreshToken.expiresAt,
  :RefreshToken.enabled
);