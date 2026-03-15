INSERT INTO jobs_log (
  job_id,
  started_at,
  ended_at,
  execution_time_ms,
  error,
  message
) VALUES (
  :jobId,
  :startedAt,
  :endedAt,
  :executionTimeMs,
  :error,
  :message
 )