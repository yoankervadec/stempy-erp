INSERT INTO auto_job_log (
  job_id,
  started_at,
  ended_at,
  execution_time_ms,
  error,
  message
) VALUES (
  :JobLog.jobId,
  :JobLog.startedAt,
  :JobLog.endedAt,
  :JobLog.executionTimeMs,
  :JobLog.error,
  :JobLog.message
 )