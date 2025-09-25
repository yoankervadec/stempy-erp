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
  private final Map<Integer, ScheduledJob> futures = new ConcurrentHashMap<>();

  public Scheduler(JobQueue queue) {
    this.queue = queue;
  }

  private static class ScheduledJob {
    final JobExecutable executable;
    final ScheduledFuture<?> future;

    ScheduledJob(JobExecutable executable, ScheduledFuture<?> future) {
      this.executable = executable;
      this.future = future;
    }
  }

  public void schedule(JobExecutable executable) {

    Job job = executable.meta();

    // Only schedule active jobs
    if (job.isActive()) {
      // Interval-based job
      if (job.isIntervalBasedJob()) {
        long intervalMs = job.getInterval().toMillis();
        if (intervalMs <= MIN_INTERVAL_MS) {
          throw new IllegalArgumentException(
              "Interval " + intervalMs + "ms must be greater than " + MIN_INTERVAL_MS + "ms for job "
                  + job.getJobName());
        }

        scheduleIntervalJob(executable, intervalMs, intervalMs, TimeUnit.MILLISECONDS);

        // Run-times job
      } else if (job.getRunTimes() != null && !job.getRunTimes().isEmpty()) {

        scheduleAtRunTimes(executable);
      }
    }
  }

  /**
   * Schedule an interval-based job
   */
  private void scheduleIntervalJob(JobExecutable executable, long initialDelay, long period, TimeUnit unit) {
    cancelByJobId(executable.meta().getJobId());

    ScheduledFuture<?> future = execScheduler.scheduleAtFixedRate(
        () -> queue.add(executable),
        initialDelay,
        period,
        unit);

    futures.put(executable.meta().getJobId(), new ScheduledJob(executable, future));
  }

  /**
   * Schedule a run-times job (self-schedule)
   */
  private void scheduleAtRunTimes(JobExecutable executable) {
    cancelByJobId(executable.meta().getJobId());

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

    futures.put(executable.meta().getJobId(), new ScheduledJob(executable, future));
  }

  public void cancelByJobId(int jobId) {
    ScheduledJob scheduledJob = futures.remove(jobId);
    if (scheduledJob != null && scheduledJob.future != null) {
      scheduledJob.future.cancel(false);
    }
  }

  public boolean isScheduled(int jobId) {
    return futures.containsKey(jobId);
  }

  public void queueNow(JobExecutable executable) {
    queue.add(executable);
  }

  public void shutdown() {
    execScheduler.shutdown();
    futures.clear();
  }

  // TODO return a Collection of scheduled jobs
  public void printFutures() {
    if (futures.isEmpty()) {
      System.out.println("No jobs scheduled.");
      return;
    }

    System.out.println("=== Scheduled Jobs ===");
    for (var entry : futures.values()) {
      Job job = entry.executable.meta();
      ScheduledFuture<?> future = entry.future;

      StringBuilder sb = new StringBuilder();
      sb.append("Job ID: ").append(job.getJobId())
          .append(" | Name: ").append(job.getJobName());

      if (job.isIntervalBasedJob()) {
        sb.append(" | Type: Interval")
            .append(" | Every: ").append(job.getInterval().toMillis()).append(" ms");
      } else {
        sb.append(" | Type: Run-times")
            .append(" | Next run: ").append(job.calculateNextRun());
      }

      sb.append(" | Status: ")
          .append(future.isCancelled() ? "Cancelled"
              : future.isDone() ? "Done"
                  : "Scheduled");

      System.out.println(sb.toString());
    }
    System.out.println("======================");
  }

}