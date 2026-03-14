package com.lesconstructionssapete.stempyerp.domain.automation;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
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
  private final long id;
  private final String name;
  private final boolean enabled;
  private final Instant createdAt;
  private final String description;
  private final String handlerAsString;

  // --- Scheduling and execution ---
  private final Long runBeforeJobId; // Optional: for job chaining
  private final Long runAfterJobId; // Optional: for job chaining
  private boolean active;
  private final boolean deactivateOnFailure;
  private final int maxRetries;
  private final Double intervalMinutes; // If null or 0, this is a fixed-time job; otherwise interval-based
  private final List<LocalTime> runTimesUTC; // For fixed-time jobs, the times of day to run (in UTC)
  private final List<DayOfWeek> runDaysOfWeek; // Optional: for fixed-time jobs, which days to run on
  private Instant lastRun;
  private Instant nextRun;

  /**
   * Creates a new Job definition.
   */
  public Job(
      long id,
      String name,
      boolean enabled,
      Instant createdAt,
      String description,
      String handlerAsString,
      Long runBeforeJobId,
      Long runAfterJobId,
      boolean active,
      boolean deactivateOnFailure,
      int maxRetries,
      Double intervalMinutes,
      List<LocalTime> runTimesUTC,
      List<DayOfWeek> runDaysOfWeek) {
    this.id = id;
    this.name = name;
    this.enabled = enabled;
    this.createdAt = createdAt;
    this.description = description;
    this.handlerAsString = handlerAsString;
    this.runBeforeJobId = runBeforeJobId;
    this.runAfterJobId = runAfterJobId;
    this.active = active;
    this.deactivateOnFailure = deactivateOnFailure;
    this.maxRetries = maxRetries;
    this.intervalMinutes = intervalMinutes;
    this.runTimesUTC = runTimesUTC != null ? runTimesUTC : new ArrayList<>();
    this.runDaysOfWeek = runDaysOfWeek != null ? runDaysOfWeek : new ArrayList<>();
  }

  public Job(Job job) {
    this.id = job.getId();
    this.name = job.getName();
    this.enabled = job.isEnabled();
    this.createdAt = job.getCreatedAt();
    this.description = job.getDescription();
    this.handlerAsString = job.getHandlerAsString();
    this.runBeforeJobId = job.getRunBeforeJobId();
    this.runAfterJobId = job.getRunAfterJobId();
    this.active = job.isActive();
    this.deactivateOnFailure = job.isDeactivateOnFailure();
    this.maxRetries = job.getMaxRetries();
    this.intervalMinutes = job.getIntervalMinutes();
    this.runTimesUTC = job.getRunTimesUTC();
    this.runDaysOfWeek = job.getRunDaysOfWeek();
    this.lastRun = job.getLastRun();
    this.nextRun = job.calculateNextRun();
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
   * @return Instant of next run, or null if no schedule
   */
  public Instant calculateNextRun() {
    var now = Instant.now();

    // Interval-based scheduling
    if (isIntervalBasedJob()) {
      if (lastRun == null) {
        return now.plus(getInterval());
      }
      return lastRun.plus(getInterval());
    }

    // Fixed-time scheduling
    if (!isIntervalBasedJob() && !runTimesUTC.isEmpty()) {
      var startOfTodayUTC = now.truncatedTo(ChronoUnit.DAYS);

      var todayCandidate = runTimesUTC.stream()
          .map(t -> startOfTodayUTC.plusSeconds(t.toSecondOfDay()))
          .filter(candidate -> candidate.isAfter(now))
          .sorted()
          .findFirst()
          .orElse(null);

      if (todayCandidate != null) {
        return todayCandidate;
      }

      // Otherwise roll to the earliest configured time on the next day.
      var startOfTomorrowUTC = startOfTodayUTC.plus(1, ChronoUnit.DAYS);
      return runTimesUTC.stream()
          .map(t -> startOfTomorrowUTC.plusSeconds(t.toSecondOfDay()))
          .sorted()
          .findFirst()
          .orElse(null);
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
    return enabled && active && nextRun != null && !nextRun.isAfter(Instant.now());
  }

  /**
   * Marks this job as having completed execution.
   * Updates lastRun and recalculates nextRun.
   */
  public void markRunCompleted() {
    this.lastRun = Instant.now();
    updateNextRun();
  }

  /**
   * Time remaining until the next run.
   * 
   * @return Duration until next run, or null if no schedule
   */
  public Duration timeUntilNextRun() {
    return nextRun == null ? null : Duration.between(Instant.now(), nextRun);
  }

  /**
   * Time elapsed since the last run.
   * 
   * @return Duration since last run, or null if never run
   */
  public Duration timeSinceLastRun() {
    return lastRun == null ? null : Duration.between(lastRun, Instant.now());
  }

  // =====================================================
  // Getters & Setters
  // =====================================================

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public String getDescription() {
    return description;
  }

  public String getHandlerAsString() {
    return handlerAsString;
  }

  public Long getRunBeforeJobId() {
    return runBeforeJobId;
  }

  public Long getRunAfterJobId() {
    return runAfterJobId;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean isActive) {
    this.active = isActive;
  }

  public boolean isDeactivateOnFailure() {
    return deactivateOnFailure;
  }

  public int getMaxRetries() {
    return maxRetries;
  }

  public Double getIntervalMinutes() {
    return intervalMinutes;
  }

  public List<LocalTime> getRunTimesUTC() {
    return runTimesUTC;
  }

  public List<DayOfWeek> getRunDaysOfWeek() {
    return runDaysOfWeek;
  }

  public Instant getLastRun() {
    return lastRun;
  }

  public Instant getNextRun() {
    return nextRun;
  }

}