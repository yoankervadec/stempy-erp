package com.lesconstructionssapete.stempyerp.core.automation.job;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Job implements JobExecutable {

  private final int jobId;
  private final String jobName;
  private final String jobDescription;
  private final String handlerAString;
  private boolean isActive;
  private boolean deactivateOnFailure;
  private int retriesOnFailure;
  private Double intervalMinutes;
  private List<LocalTime> runTimes = new ArrayList<>();
  private LocalDateTime lastRun;
  private LocalDateTime nextRun;
  private int priority;
  private boolean isEnabled;

  public Job(
      int jobId,
      String jobName,
      String jobDescription,
      String handlerAString,
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
    this.handlerAString = handlerAString;
    this.isActive = isActive;
    this.deactivateOnFailure = deactivateOnFailure;
    this.retriesOnFailure = retriesOnFailure;
    this.intervalMinutes = intervalMinutes;
    this.runTimes = runTimes;
    this.lastRun = lastRun;
    this.nextRun = nextRun;
    this.priority = priority;
    this.isEnabled = isEnabled;
  }

  @Override
  public void execute() {
    throw new UnsupportedOperationException();
  }

  public boolean isFixedTimeJob() {
    return intervalMinutes == null;
  }

  public Duration getInterval() {
    if (!isFixedTimeJob()) {
      return Duration.ofMillis((long) (getIntervalMinutes() * 60_000));
    }
    return null;
  }

  // --- Getters & Setters ---
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
    return handlerAString;
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

  public void setDeactivateOnFailure(boolean stopOnFailure) {
    this.deactivateOnFailure = stopOnFailure;
  }

  public int getRetriesOnFailure() {
    return retriesOnFailure;
  }

  public void setRetriesOnFailure(int retriesOnFailure) {
    this.retriesOnFailure = retriesOnFailure;
  }

  public Double getIntervalMinutes() {
    return intervalMinutes;
  }

  public void setIntervalMinutes(Double intervalMinutes) {
    this.intervalMinutes = intervalMinutes;
  }

  public List<LocalTime> getRunTimes() {
    return runTimes;
  }

  public void setRunTimes(List<LocalTime> runTimes) {
    this.runTimes = runTimes;
  }

  public LocalDateTime getLastRun() {
    return lastRun;
  }

  public void setLastRun(LocalDateTime lastRun) {
    this.lastRun = lastRun;
  }

  public LocalDateTime getNextRun() {
    return nextRun;
  }

  public void setNextRun(LocalDateTime nextRun) {
    this.nextRun = nextRun;
  }

  public int getPriority() {
    return priority;
  }

  public void setPriority(int priority) {
    this.priority = priority;
  }

  public boolean isEnabled() {
    return isEnabled;
  }

  public void setEnabled(boolean isEnabled) {
    this.isEnabled = isEnabled;
  }
}
