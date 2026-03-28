package com.lesconstructionssapete.stempyerp.field.automation;

import com.lesconstructionssapete.stempyerp.field.DomainField;

public enum JobField implements DomainField {

  ID("id"),
  NAME("name"),
  ENABLED("enabled"),
  CREATED_AT("createdAt"),
  DESCRIPTION("description"),
  HANDLER_AS_STRING("handlerAsString"),
  RUN_BEFORE_JOB_ID("runBeforeJobId"),
  RUN_AFTER_JOB_ID("runAfterJobId"),
  ACTIVE("active"),
  DEACTIVATE_ON_FAILURE("deactivateOnFailure"),
  MAX_RETRIES("maxRetries"),
  INTERVAL_MINUTES("intervalMinutes"),
  RUN_TIMES_UTC("runTimesUTC"),
  RUN_DAYS("runDaysOfWeek");

  private static final String PREFIX = "Job.";
  private final String logicalName;

  JobField(String logicalName) {
    this.logicalName = logicalName;
  }

  @Override
  public String logicalName() {
    return PREFIX + logicalName;
  }

}
