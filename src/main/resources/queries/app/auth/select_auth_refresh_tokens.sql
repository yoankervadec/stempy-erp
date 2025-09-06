SELECT
  ort.refresh_token_seq,
  us.user_no,
  ort.user_seq,
  ort.token,
  ort.expires_at,
  ort.created_at
FROM
  auth_refresh_tokens AS ort
JOIN
  users AS us ON ort.user_seq = us.user_seq
/* WHERE */
