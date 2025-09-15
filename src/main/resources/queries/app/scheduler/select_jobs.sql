
SELECT
  id,
  name,
  description,
  handler,
  is_active,
  deactivate_on_failure,
  retries_on_failure,
  interval_minutes,
  run_times,
  last_run,
  next_run,
  priority,
  is_enabled
FROM
  jobs