INSERT INTO
  auth_refresh_tokens (
    user_seq,
    token,
    expires_at
  )
VALUES (?, ?, ?);