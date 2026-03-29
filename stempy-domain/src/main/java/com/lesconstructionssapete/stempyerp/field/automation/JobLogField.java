package com.lesconstructionssapete.stempyerp.field.automation;

import com.lesconstructionssapete.stempyerp.field.DomainField;

public enum JobLogField implements DomainField {

  ID("id"),
  JOB_ID("jobId"),
  CREATED_AT("createdAt"),
  STARTED_AT("startedAt"),
  ENDED_AT("endedAt"),
  EXECUTION_TIME_MS("executionTimeMs"),
  ERROR("error"),
  MESSAGE("message");

  private static final String PREFIX = "JobLog.";
  private final String logicalName;

  JobLogField(String logicalName) {
    this.logicalName = logicalName;
  }

  @Override
  public String logicalName() {
    return PREFIX + logicalName;
  }

}
