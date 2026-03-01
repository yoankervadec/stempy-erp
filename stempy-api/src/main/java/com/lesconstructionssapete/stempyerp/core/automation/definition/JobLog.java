package com.lesconstructionssapete.stempyerp.core.automation.definition;

import java.time.Duration;
import java.time.LocalDateTime;

import com.lesconstructionssapete.stempyerp.core.automation.handler.LogJobRuns;

public class JobLog {

  private final int jobId;
  private final LocalDateTime startedAt = LocalDateTime.now();
  private LocalDateTime endedAt;
  private boolean isError = false;
  private StringBuilder message = new StringBuilder();

  public JobLog(int jobId) {
    this.jobId = jobId;
  }

  public void save() {
    appendMessage("Execution time: " + getDurationMs() + "ms");
    LogJobRuns.enqueueLog(this);
  }

  // --- Message Helpers ---

  public String getMessage() {
    return message.toString();
  }

  public JobLog setMessage(String msg) {
    this.message = new StringBuilder(msg);
    return this;
  }

  public JobLog appendMessage(String msg) {
    if (message.length() > 0) {
      message.append(" | ");
    }
    message.append(msg);
    return this;
  }

  public JobLog markSuccess(String jobName) {
    this.isError = false;
    appendMessage("Success: " + jobName);
    return this;
  }

  public JobLog markFailure(String jobName, Exception e, int attempt, int maxAttempts) {
    this.isError = true;
    appendMessage(String.format("Failure [%s] attempt %d/%d: %s",
        jobName, attempt, maxAttempts, e.getMessage()));
    return this;
  }

  public JobLog markFinalFailure(String jobName, Exception e) {
    this.isError = true;
    appendMessage(String.format("Job %s permanently failed: %s",
        jobName, e.getMessage()));
    return this;
  }

  public int getDurationMs() {
    LocalDateTime end = (endedAt != null) ? endedAt : LocalDateTime.now();
    return (int) Duration.between(startedAt, end).toMillis();
  }

  // --- Getters & Setters ---

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

}
