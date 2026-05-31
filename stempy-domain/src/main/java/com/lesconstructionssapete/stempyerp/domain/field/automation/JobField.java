package com.lesconstructionssapete.stempyerp.domain.field.automation;

import com.lesconstructionssapete.stempyerp.domain.field.DomainField;
import com.lesconstructionssapete.stempyerp.domain.field.DomainFieldProvider;

public enum JobField implements DomainFieldProvider {

  ID(new DomainField("Job", "id", Long.class, false, true, true, "id", "auto_job", java.sql.Types.BIGINT)),
  NAME(new DomainField("Job", "name", String.class, false, false, false, "name", "auto_job", java.sql.Types.VARCHAR)),
  ENABLED(new DomainField("Job", "enabled", Boolean.class, false, false, false, "enabled", "auto_job",
      java.sql.Types.BOOLEAN)),
  CREATED_AT(
      new DomainField("Job", "createdAt", java.sql.Timestamp.class, false, false, false, "created_at", "auto_job",
          java.sql.Types.TIMESTAMP)),
  DESCRIPTION(new DomainField("Job", "description", String.class, false, false, false, "description", "auto_job",
      java.sql.Types.VARCHAR)),
  HANDLER_AS_STRING(new DomainField("Job", "handlerAsString", String.class, false, false, false, "handler", "auto_job",
      java.sql.Types.VARCHAR)),
  RUN_BEFORE_JOB_ID(new DomainField("Job", "runBeforeJobId", Long.class, false, false, false, "run_before_id",
      "auto_job", java.sql.Types.BIGINT)),
  RUN_AFTER_JOB_ID(new DomainField("Job", "runAfterJobId", Long.class, false, false, false, "run_after_id", "auto_job",
      java.sql.Types.BIGINT)),
  ACTIVE(new DomainField("Job", "active", Boolean.class, false, false, false, "active", "auto_job",
      java.sql.Types.BOOLEAN)),
  DEACTIVATE_ON_FAILURE(
      new DomainField("Job", "deactivateOnFailure", Boolean.class, false, false, false, "deactivate_on_failure",
          "auto_job", java.sql.Types.BOOLEAN)),
  MAX_RETRIES(new DomainField("Job", "maxRetries", Integer.class, false, false, false, "max_retries",
      "auto_job", java.sql.Types.INTEGER)),
  INTERVAL_MINUTES(new DomainField("Job", "intervalMinutes", Double.class, false, false, false, "interval_minutes",
      "auto_job", java.sql.Types.DOUBLE)),
  RUN_TIMES_UTC(new DomainField("Job", "runTimesUtc", String.class, false, false, false, "run_times_utc",
      "auto_job", java.sql.Types.VARCHAR)),
  RUN_DAYS(new DomainField("Job", "runDays", String.class, false, false, false, "run_days",
      "auto_job", java.sql.Types.VARCHAR));

  private final DomainField attribute;

  JobField(DomainField attribute) {
    this.attribute = attribute;
  }

  @Override
  public DomainField attribute() {
    return attribute;
  }

}
