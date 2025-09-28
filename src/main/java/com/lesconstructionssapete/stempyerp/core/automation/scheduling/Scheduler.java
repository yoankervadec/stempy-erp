package com.lesconstructionssapete.stempyerp.core.automation.scheduling;

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

  private final static long MIN_DELAY = 10000; // 10 seconds

  private final JobQueue queue;
  private final ScheduledExecutorService execScheduler = Executors
      .newScheduledThreadPool(Math.max(2, Runtime.getRuntime().availableProcessors() / 2));
  private final Map<Integer, ScheduledJob> futures = new ConcurrentHashMap<>();

  public Scheduler(JobQueue queue) {
    this.queue = queue;
  }

  /**
   * Holds a scheduled job and its future for management.
   */
  private static class ScheduledJob {
    final JobExecutable executable;
    final ScheduledFuture<?> future;

    ScheduledJob(JobExecutable executable, ScheduledFuture<?> future) {
      this.executable = executable;
      this.future = future;
    }
  }

  /**
   * Schedules a job for queuing based on its defined schedule.
   * If the job is inactive, it will not be scheduled.
   * 
   * @param executable
   */
  public void schedule(JobExecutable executable) {
    Job job = executable.meta();

    // Only schedule active jobs
    if (job.isActive()) {
      scheduleSelf(executable);
    }
  }

  /**
   * Schedules the given job executable to run at its next scheduled time.
   * If the job is already scheduled, it will be canceled and rescheduled.
   * 
   * @param executable
   */
  private void scheduleSelf(JobExecutable executable) {
    Job job = executable.meta();

    cancelByJobId(job.getJobId()); // Avoid double-scheduling

    if (job.timeSinceLastRun() != null && job.timeSinceLastRun().toMillis() < MIN_DELAY) {
      throw new IllegalArgumentException("Job ID " + job.getJobId() + " (" + job.getJobName()
          + ") was run less than " + (MIN_DELAY / 1000) + " seconds ago. Cannot reschedule so soon.");
    }

    LocalDateTime next = job.calculateNextRun();
    if (next == null)
      return;

    long delay = job.timeUntilNextRun().toMillis();
    if (delay < 0)
      delay = 0;

    ScheduledFuture<?> future = execScheduler.schedule(
        () -> {
          // Don't run if job was deactivated in the meantime
          if (!job.isActive())
            return;

          queue.add(executable); // Queue for execution

          job.markRunCompleted(); // Advance schedule

          scheduleSelf(executable); // Self-reschedule for next cycle
        },
        delay,
        TimeUnit.MILLISECONDS);

    futures.put(job.getJobId(), new ScheduledJob(executable, future));
  }

  /**
   * Cancels the scheduled queuing of a job by its ID.
   * Does not clear the job from the queue if already queued.
   * <p>
   * If the job is not scheduled, this method does nothing.
   * 
   * @param jobId
   */
  public void cancelByJobId(int jobId) {
    ScheduledJob scheduledJob = futures.remove(jobId);
    if (scheduledJob != null && scheduledJob.future != null) {
      scheduledJob.future.cancel(false);
    }
  }

  /**
   * Checks if a job with the given ID is currently scheduled.
   * 
   * @param jobId
   * @return true if scheduled, false otherwise
   */
  public boolean isScheduled(int jobId) {
    return futures.containsKey(jobId);
  }

  /**
   * Queues the given job executable for immediate execution.
   * 
   * @param executable
   */
  public void queueNow(JobExecutable executable) {
    queue.add(executable);
  }

  /**
   * Shuts down the scheduler, cancelling all scheduled jobs, without clearing the
   * queue.
   */
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
            .append(" | Every: ").append(job.getInterval().toSeconds()).append(" s");
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