package com.lesconstructionssapete.stempyerp.core.automation.definition;

import java.time.Duration;
import java.time.LocalDateTime;

import com.lesconstructionssapete.stempyerp.core.automation.handler.LogJobRuns;

public class JobLog {

  private final int jobId;
  private final LocalDateTime startedAt = LocalDateTime.now();
  private LocalDateTime endedAt;
  private boolean isError = false;
  private String message;

  public JobLog(int jobId) {
    this.jobId = jobId;
  }

  public void save() {
    LogJobRuns.enqueueLog(this);
  }

  public int getDurationMs() {
    LocalDateTime end = (endedAt != null) ? endedAt : LocalDateTime.now();
    return (int) Duration.between(startedAt, end).toMillis();
  }

  public int getJobId() {
    return jobId;
  }

  public LocalDateTime getStartedAt() {
    return startedAt;
  }

  public LocalDateTime getEndedAt() {
    return endedAt;
  }

  public void setEndedAt(LocalDateTime endedAt) {
    this.endedAt = endedAt;
  }

  public boolean isError() {
    return isError;
  }

  public void setError(boolean isError) {
    this.isError = isError;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

}
