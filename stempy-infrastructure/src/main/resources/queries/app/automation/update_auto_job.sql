UPDATE
  auto_job
SET
  enabled = :enabled,
  description = :description,
  handler = :handlerAsString,
  run_before_id = :runBeforeJobId,
  run_after_id = :runAfterJobId,
  active = :active,
  deactivate_on_failure = :deactivateOnFailure,
  max_retries = :maxRetries,
  interval_minutes = :intervalMinutes,
  run_times_utc = :runTimesUTC,
  run_days = :runDaysOfWeek
