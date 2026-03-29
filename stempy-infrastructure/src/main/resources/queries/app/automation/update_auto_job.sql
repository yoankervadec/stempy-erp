UPDATE
  auto_job
SET
  enabled = :Job.enabled,
  description = :Job.description,
  handler = :Job.handlerAsString,
  run_before_id = :Job.runBeforeJobId,
  run_after_id = :Job.runAfterJobId,
  active = :Job.active,
  deactivate_on_failure = :Job.deactivateOnFailure,
  max_retries = :Job.maxRetries,
  interval_minutes = :Job.intervalMinutes,
  run_times_utc = :Job.runTimesUTC,
  run_days = :Job.runDaysOfWeek
