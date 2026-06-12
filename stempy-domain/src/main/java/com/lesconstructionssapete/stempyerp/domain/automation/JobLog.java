package com.lesconstructionssapete.stempyerp.domain.automation;

import java.time.Duration;
import java.time.Instant;

import com.lesconstructionssapete.stempyerp.domain.field.EntityField;
import com.lesconstructionssapete.stempyerp.domain.field.FieldMeta;

public class JobLog {

  public enum Fields implements EntityField {

    ID(FieldMeta.builder("JobLog", "auto_job_log")
        .field("id", "id")
        .type(Long.class, java.sql.Types.BIGINT)
        .notNullable()
        .build()),

    JOB_ID(FieldMeta.builder("JobLog", "auto_job_log")
        .field("jobId", "job_id")
        .type(Long.class, java.sql.Types.BIGINT)
        .notNullable()
        .build()),

    CREATED_AT(FieldMeta.builder("JobLog", "auto_job_log")
        .field("createdAt", "created_at")
        .type(Instant.class, java.sql.Types.TIMESTAMP)
        .notNullable()
        .build()),

    STARTED_AT(FieldMeta.builder("JobLog", "auto_job_log")
        .field("startedAt", "started_at")
        .type(Instant.class, java.sql.Types.TIMESTAMP)
        .notNullable()
        .build()),

    ENDED_AT(FieldMeta.builder("JobLog", "auto_job_log")
        .field("endedAt", "ended_at")
        .type(Instant.class, java.sql.Types.TIMESTAMP)
        .notNullable()
        .build()),

    EXECUTION_TIME_MS(FieldMeta.builder("JobLog", "auto_job_log")
        .field("executionTimeMs", "execution_time_ms")
        .type(Integer.class, java.sql.Types.INTEGER)
        .notNullable()
        .build()),

    ERROR(FieldMeta.builder("JobLog", "auto_job_log")
        .field("error", "error")
        .type(Boolean.class, java.sql.Types.BOOLEAN)
        .notNullable()
        .build()),

    MESSAGE(FieldMeta.builder("JobLog", "auto_job_log")
        .field("message", "message")
        .type(String.class, java.sql.Types.VARCHAR)
        .notNullable()
        .build());

    private final FieldMeta meta;

    Fields(FieldMeta meta) {
      this.meta = meta;
    }

    @Override
    public FieldMeta meta() {
      return meta;
    }
  }

  private final long jobId;
  private final Instant startedAt = Instant.now();
  private Instant endedAt;
  private boolean error = false;
  private StringBuilder message = new StringBuilder();

  public JobLog(long jobId) {
    this.jobId = jobId;
  }

  public JobLog(
      long jobId,
      Instant createdAt,
      Instant startedAt,
      Instant endedAt,
      boolean error,
      String message) {
    this.jobId = jobId;
    this.endedAt = endedAt;
    this.error = error;
    this.message = new StringBuilder(message);
  }

  public void save() {
    appendMessage("Execution time: " + getDurationMs() + "ms");
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
    this.error = false;
    appendMessage("Success: " + jobName);
    return this;
  }

  public JobLog markFailure(String jobName, Exception e, int attempt, int maxAttempts) {
    this.error = true;
    appendMessage(String.format("Failure [%s] attempt %d/%d: %s",
        jobName, attempt, maxAttempts, e.getMessage()));
    return this;
  }

  public JobLog markFinalFailure(String jobName, Exception e) {
    this.error = true;
    appendMessage(String.format("Job %s permanently failed: %s",
        jobName, e.getMessage()));
    return this;
  }

  public int getDurationMs() {
    Instant end = (endedAt != null) ? endedAt : Instant.now();
    return (int) Duration.between(startedAt, end).toMillis();
  }

  // --- Getters & Setters ---

  public long getJobId() {
    return jobId;
  }

  public Instant getStartedAt() {
    return startedAt;
  }

  public Instant getEndedAt() {
    return endedAt;
  }

  public void setEndedAt(Instant endedAt) {
    this.endedAt = endedAt;
  }

  public boolean isError() {
    return error;
  }

  public void setError(boolean error) {
    this.error = error;
  }

}
