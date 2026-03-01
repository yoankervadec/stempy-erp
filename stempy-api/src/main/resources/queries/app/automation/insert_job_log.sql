INSERT INTO jobs_log (
  job_id,
  started_at,
  ended_at,
  duration_ms,
  is_error,
  message
) VALUES (?, ?, ?, ?, ?, ?)