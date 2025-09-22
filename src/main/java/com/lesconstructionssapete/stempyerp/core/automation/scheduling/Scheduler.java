package com.lesconstructionssapete.stempyerp.core.automation.scheduling;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.lesconstructionssapete.stempyerp.core.automation.definition.Job;
import com.lesconstructionssapete.stempyerp.core.automation.definition.JobExecutable;
import com.lesconstructionssapete.stempyerp.core.automation.execution.JobQueue;

public class Scheduler {

  private final static long MIN_INTERVAL_MS = 10;

  private final JobQueue queue;
  private final ScheduledExecutorService execScheduler = Executors
      .newScheduledThreadPool(Math.max(2, Runtime.getRuntime().availableProcessors() / 2));
  private final Map<Integer, ScheduledFuture<?>> futures = new ConcurrentHashMap<>();

  public Scheduler(JobQueue queue) {
    this.queue = queue;
  }

  public void schedule(JobExecutable executable) {

    Job job = executable.meta();

    if (job.isIntervalBasedJob()) {

      long intervalMs = job.getInterval().toMillis();
      if (intervalMs <= MIN_INTERVAL_MS) {
        throw new IllegalArgumentException(
            "Interval " + intervalMs + "ms must be greater than " + MIN_INTERVAL_MS + "ms for job " + job.getJobName());
      }

      scheduleIntervalJob(executable, intervalMs, intervalMs, TimeUnit.MILLISECONDS);
    } else if (job.getRunTimes() != null && !job.getRunTimes().isEmpty()) {

      scheduleAtRunTimes(executable);
    }

  }

  /**
   * Schedule an interval-based job
   */
  private void scheduleIntervalJob(JobExecutable executable, long initialDelay, long period, TimeUnit unit) {
    cancel(executable); // Ensures no duplicates

    ScheduledFuture<?> future = execScheduler.scheduleAtFixedRate(
        () -> queue.add(executable),
        initialDelay,
        period,
        unit);

    futures.put(executable.meta().getJobId(), future);
  }

  /**
   * Schedule a run-times job (self-schedule)
   */
  private void scheduleAtRunTimes(JobExecutable executable) {
    cancel(executable);

    LocalDateTime next = executable.meta().calculateNextRun();
    if (next == null)
      return;

    long delay = Duration.between(LocalDateTime.now(), next).toMillis();
    if (delay < 0)
      delay = 0;

    ScheduledFuture<?> future = execScheduler.schedule(
        () -> {
          executable.meta().markRunCompleted(); // Advance schedule
          queue.add(executable);
          scheduleAtRunTimes(executable); // Reschedule next
        },
        delay,
        TimeUnit.MILLISECONDS);

    futures.put(executable.meta().getJobId(), future);
  }

  /**
   * Cancel an existing job.
   */
  public void cancel(JobExecutable executable) {
    ScheduledFuture<?> future = futures.remove(executable.meta().getJobId());
    if (future != null) {
      future.cancel(false);
    }
  }

  // manual add
  public void queueNow(JobExecutable executable) {
    queue.add(executable);
  }

  public void shutdown() {
    execScheduler.shutdown();
    futures.clear();
  }

}