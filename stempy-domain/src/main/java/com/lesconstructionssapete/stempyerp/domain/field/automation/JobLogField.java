package com.lesconstructionssapete.stempyerp.domain.field.automation;

import com.lesconstructionssapete.stempyerp.domain.field.DomainField;
import com.lesconstructionssapete.stempyerp.domain.field.DomainFieldProvider;

public enum JobLogField implements DomainFieldProvider {

  ID(new DomainField("JobLog", "id", Long.class, false, true, true, "id", "auto_job_log", java.sql.Types.BIGINT)),
  JOB_ID(new DomainField("JobLog", "jobId", Long.class, false, false, false, "job_id", "auto_job_log",
      java.sql.Types.BIGINT)),
  CREATED_AT(new DomainField("JobLog", "createdAt", java.sql.Timestamp.class, false, false, false, "created_at",
      "auto_job_log", java.sql.Types.TIMESTAMP)),
  STARTED_AT(new DomainField("JobLog", "startedAt", java.sql.Timestamp.class, false, false, false, "started_at",
      "auto_job_log", java.sql.Types.TIMESTAMP)),
  ENDED_AT(new DomainField("JobLog", "endedAt", java.sql.Timestamp.class, false, false, false, "ended_at",
      "auto_job_log", java.sql.Types.TIMESTAMP)),
  EXECUTION_TIME_MS(new DomainField("JobLog", "executionTimeMs", Integer.class, false, false, false,
      "execution_time_ms", "auto_job_log", java.sql.Types.INTEGER)),
  ERROR(new DomainField("JobLog", "error", Boolean.class, false, false, false, "error", "auto_job_log",
      java.sql.Types.BOOLEAN)),
  MESSAGE(new DomainField("JobLog", "message", String.class, false, false, false, "message", "auto_job_log",
      java.sql.Types.VARCHAR));

  private final DomainField attribute;

  JobLogField(DomainField attribute) {
    this.attribute = attribute;
  }

  @Override
  public DomainField attribute() {
    return attribute;
  }

}
