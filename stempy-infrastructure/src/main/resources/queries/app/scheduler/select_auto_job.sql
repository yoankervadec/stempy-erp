
SELECT
  auto_job.id,
  auto_job.name,
  auto_job.enabled,
  auto_job.created_at,
  auto_job.description,
  auto_job.handler,
  auto_job.run_before_id,
  auto_job.run_after_id,
  auto_job.active,
  auto_job.deactivate_on_failure,
  auto_job.max_retries,
  auto_job.interval_minutes,
  auto_job.run_times_utc,
  auto_job.run_days
FROM
  auto_job