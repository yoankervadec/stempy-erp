# Automation Module

The **Automation Module** provides a lightweight job scheduling and execution framework inside **StempyERP**.  
It is designed to run recurring jobs (interval-based or at fixed run times), handle their execution in a background worker, and allow refreshing/updating the schedule dynamically.

---

## Package Structure

```bash
│
├── definition/           # Core interfaces and job metadata
│   ├── Job               # Immutable job metadata (id, name, schedule, enabled, etc.)
│   ├── JobExecutable     # Interface for executable jobs (ties code to Job)
│   └── JobFactory        # Creates JobExecutable instances with dependencies
│
├── execution/            # Core orchestration and execution
│   ├── AutomationManager # Singleton entrypoint, manages queue + scheduler
│   ├── JobQueue          # Thread-safe queue of pending jobs
│   └── WorkerThread      # Dedicated worker thread that pulls from JobQueue
│
├── scheduling/           # Scheduling engine
│   └── Scheduler         # Uses ScheduledExecutorService to plan jobs
│
└── handler/              # Concrete jobs
    ├── RefreshSchedule   # Job that reloads jobs from DB and refreshes scheduler
    └── UpdateInventory   # E.g., Refreshes and caches inventory
```

---

## Core Workflow

1. **AutomationManager (Singleton)**

   - Central entrypoint for automation.
   - Owns:
     - `JobQueue`
     - `WorkerThread` (runs jobs in background)
     - `Scheduler` (plans jobs in the future)
   - Responsible for:
     - Starting/stopping the automation system
     - Scheduling jobs at startup
     - Refreshing schedules logic
     - Running jobs immediately (`runNow`)

2. **Scheduler**

   - Wraps a `ScheduledExecutorService`.
   - Handles two types of jobs:
     - **Interval-based**: run every `n` milliseconds.
     - **Run-times based**: run at specific `LocalDateTime` values every day.
   - Tracks jobs in a `Map<JobId, ScheduledFuture<?>>` for cancellation and inspection.

3. **JobQueue**

   - A simple thread-safe queue of `JobExecutable` instances.
   - Decouples scheduling from execution (schedulers only enqueue, workers only dequeue).

4. **WorkerThread**

   - Continuously consumes `JobExecutable` from the `JobQueue`.
   - Executes the job’s `execute(JobLog log)` method.
   - Appends execution results to a `JobLog`.

5. **Job + JobExecutable**

   - `Job`: plain metadata (id, name, active, schedule). Immutable snapshot from DB.
   - `JobExecutable`: actual runnable unit of work, tied to a `Job`.
   - Pattern: `Job` describes _what_ and _when_, `JobExecutable` describes _how_.

6. **JobFactory**
   - Responsible for converting `Job` metadata into a `JobExecutable` based on the job's `handlerAsString`.
   - Supports dependency injection: e.g., repository implementations, manager references.

---

## Example Lifecycle

1. **Startup**

   - `AutomationManager.create(jobs)` is called with jobs loaded from DB.
   - `manager.start()`:
     - Worker thread begins listening to queue.
     - Scheduler registers all active jobs.

2. **Job Trigger**

   - Scheduler enqueues a `JobExecutable` into the `JobQueue`.
   - Worker thread dequeues it, executes it, logs the result.

3. **Refresh**

   - `RefreshJobSchedule` runs (interval or manual trigger).
   - It reloads jobs from DB and calls `AutomationManager.refresh(newJobs)`.
   - Scheduler cancels outdated jobs and re-schedules updated ones.

4. **Shutdown**
   - `manager.stop()`:
     - Shuts down scheduler and worker thread.
     - Cancels all scheduled jobs.

---

## Features

- **Interval scheduling** with millisecond precision.
- **Run-times scheduling** with self-rescheduling logic.
- **Thread-safe job queue** decouples scheduling from execution.
- **Singleton AutomationManager** ensures only one automation system runs.
- **Dynamic refresh**: jobs can be reloaded and re-scheduled without restarting app.
- **Dependency injection** into jobs via `JobFactory`.

---

## SQL Table: jobs

| Column Name           | Type         | Description                                                                        |
| --------------------- | ------------ | ---------------------------------------------------------------------------------- |
| id                    | INT          | Unique job identifier.                                                             |
| name                  | VARCHAR(64)  | Name of the job.                                                                   |
| description           | TEXT         | Detailed description of the job’s purpose.                                         |
| handler               | VARCHAR(64)  | Fully qualified class name in `automation.handler` that implements the job.        |
| is_active             | TINYINT      | Temporary deactivation flag (e.g., after a failure).                               |
| deactivate_on_failure | TINYINT      | Determines whether the job should be deactivated after a failure or retried later. |
| retries_on_failure    | INT          | Number of retries allowed if the job fails.                                        |
| interval_minutes      | DECIMAL(6,2) | Interval for interval-based jobs (e.g., 0.5 = 30 seconds).                         |
| run_times             | JSON         | Specific run times (e.g., `["08:00:00", "13:00:00"]`) for scheduled execution.     |
| last_run              | DATETIME     | Timestamp of the last execution.                                                   |
| next_run              | DATETIME     | Scheduled timestamp for the next execution.                                        |
| priority              | INT          | Execution priority (higher value = runs earlier; e.g., 10 = first, 0 = last).      |
| is_enabled            | TINYINT      | Long-term activation flag (true = enabled, false = permanently disabled).          |
