package com.lesconstructionssapete.stempyerp.core.automation.definition;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a scheduled job definition.
 * 
 * A job can either:
 * - Run at fixed intervals (e.g., every 10 minutes), OR
 * - Run at fixed times of day (e.g., 02:00 and 14:00).
 *
 * Jobs carry metadata such as description, retry rules,
 * priority, and scheduling details to help schedulers
 * and workers manage execution.
 */
public class Job {

  // --- Core job metadata ---
  private final int jobId;
  private final String jobName;
  private final String jobDescription;
  private final String handlerAsString;

  // --- Scheduling and execution ---
  private boolean isActive;
  private final boolean deactivateOnFailure;
  private final int retriesOnFailure;
  private final Double intervalMinutes;
  private final List<LocalTime> runTimes;
  private LocalDateTime lastRun;
  private LocalDateTime nextRun;
  private final int priority;
  private final boolean isEnabled;

  /**
   * Creates a new Job definition.
   */
  public Job(
      int jobId,
      String jobName,
      String jobDescription,
      String handlerAsString,
      boolean isActive,
      boolean deactivateOnFailure,
      int retriesOnFailure,
      Double intervalMinutes,
      List<LocalTime> runTimes,
      LocalDateTime lastRun,
      LocalDateTime nextRun,
      int priority,
      boolean isEnabled) {
    this.jobId = jobId;
    this.jobName = jobName;
    this.jobDescription = jobDescription;
    this.handlerAsString = handlerAsString;
    this.isActive = isActive;
    this.deactivateOnFailure = deactivateOnFailure;
    this.retriesOnFailure = retriesOnFailure;
    this.intervalMinutes = intervalMinutes;
    this.runTimes = runTimes != null ? runTimes : new ArrayList<>();
    this.lastRun = lastRun;
    this.nextRun = nextRun;
    this.priority = priority;
    this.isEnabled = isEnabled;
  }

  public Job(Job job) {
    this.jobId = job.getJobId();
    this.jobName = job.getJobName();
    this.jobDescription = job.getJobDescription();
    this.handlerAsString = job.getHandlerAsString();
    this.isActive = job.isActive();
    this.deactivateOnFailure = job.isDeactivateOnFailure();
    this.retriesOnFailure = job.getRetriesOnFailure();
    this.intervalMinutes = job.getIntervalMinutes();
    this.runTimes = job.getRunTimes();
    this.lastRun = job.getLastRun();
    this.nextRun = job.calculateNextRun();
    this.priority = job.getPriority();
    this.isEnabled = job.isEnabled();
  }

  // =====================================================
  // Scheduling helpers
  // =====================================================

  /**
   * Returns true if this job runs at specific times (fixed-time),
   * false if it runs at regular intervals.
   */
  public boolean isIntervalBasedJob() {
    return intervalMinutes > 0 && intervalMinutes != null;
  }

  /**
   * Gets the configured interval as a Duration (for interval jobs).
   * 
   * @return duration, or null if this is a fixed-time job
   */
  public Duration getInterval() {
    if (isIntervalBasedJob()) {
      return Duration.ofMillis((long) (intervalMinutes * 60_000));
    }
    return null;
  }

  /**
   * Calculates the next run time based on the scheduling type:
   * - Interval jobs: lastRun + interval
   * - Fixed-time jobs: first runTime after now, otherwise earliest tomorrow
   * 
   * @return LocalDateTime of next run, or null if no schedule
   */
  public LocalDateTime calculateNextRun() {
    var now = LocalDateTime.now();

    // Interval-based scheduling
    if (isIntervalBasedJob()) {
      if (lastRun == null) {
        return now.plus(getInterval());
      }
      return lastRun.plus(getInterval());
    }

    // Fixed-time scheduling
    if (!isIntervalBasedJob() && !runTimes.isEmpty()) {
      var todayCandidate = runTimes.stream()
          .map(t -> LocalDateTime.of(now.toLocalDate(), t))
          .filter(dt -> dt.isAfter(now))
          .findFirst()
          .orElse(null);

      if (todayCandidate != null) {
        return todayCandidate;
      }

      // Otherwise roll to the next day
      return LocalDateTime.of(now.toLocalDate().plusDays(1), runTimes.get(0));
    }

    return null; // No scheduling data
  }

  /**
   * Updates the nextRun field by recalculating it.
   */
  public void updateNextRun() {
    this.nextRun = calculateNextRun();
  }

  /**
   * Checks if this job is due to run right now.
   * 
   * @return true if enabled, active, and nextRun <= now
   */
  public boolean isDueToRun() {
    return isEnabled && isActive && nextRun != null && !nextRun.isAfter(LocalDateTime.now());
  }

  /**
   * Marks this job as having completed execution.
   * Updates lastRun and recalculates nextRun.
   */
  public void markRunCompleted() {
    this.lastRun = LocalDateTime.now();
    updateNextRun();
  }

  /**
   * Time remaining until the next run.
   * 
   * @return Duration until next run, or null if no schedule
   */
  public Duration timeUntilNextRun() {
    return nextRun == null ? null : Duration.between(LocalDateTime.now(), nextRun);
  }

  // =====================================================
  // Getters & Setters
  // =====================================================

  public int getJobId() {
    return jobId;
  }

  public String getJobName() {
    return jobName;
  }

  public String getJobDescription() {
    return jobDescription;
  }

  public String getHandlerAsString() {
    return handlerAsString;
  }

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean isActive) {
    this.isActive = isActive;
  }

  public boolean isDeactivateOnFailure() {
    return deactivateOnFailure;
  }

  public int getRetriesOnFailure() {
    return retriesOnFailure;
  }

  protected Double getIntervalMinutes() {
    return intervalMinutes;
  }

  public List<LocalTime> getRunTimes() {
    return runTimes;
  }

  public LocalDateTime getLastRun() {
    return lastRun;
  }

  public LocalDateTime getNextRun() {
    return nextRun;
  }

  public int getPriority() {
    return priority;
  }

  public boolean isEnabled() {
    return isEnabled;
  }
}