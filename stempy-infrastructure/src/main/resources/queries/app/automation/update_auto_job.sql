UPDATE
  auto_job
SET
  enabled = :enabled,
  created_at = :created_at,
  description = :description,
  handler = :handler,
  run_before_id = :run_before_id,
  run_after_id = :run_after_id,
  active = :active,
  deactivate_on_failure = :deactivate_on_failure,
  max_retries = :max_retries,
  interval_minutes = :interval_minutes,
  run_times_utc = :run_times_utc,
  run_days = :run_days
