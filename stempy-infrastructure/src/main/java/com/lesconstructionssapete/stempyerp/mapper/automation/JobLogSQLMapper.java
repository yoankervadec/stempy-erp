package com.lesconstructionssapete.stempyerp.mapper.automation;

import com.lesconstructionssapete.stempyerp.domain.automation.JobLog;
import com.lesconstructionssapete.stempyerp.field.automation.JobLogField;
import com.lesconstructionssapete.stempyerp.field.automation.JobLogSQLField;
import com.lesconstructionssapete.stempyerp.query.SQLBuilder;

public final class JobLogSQLMapper {

  private JobLogSQLMapper() {
  }

  public static void bindInsert(SQLBuilder builder, JobLog jobLog) {
    builder
        .bind(JobLogSQLField.get(JobLogField.JOB_ID), jobLog.getJobId())
        .bind(JobLogSQLField.get(JobLogField.STARTED_AT), jobLog.getStartedAt())
        .bind(JobLogSQLField.get(JobLogField.ENDED_AT), jobLog.getEndedAt())
        .bind(JobLogSQLField.get(JobLogField.EXECUTION_TIME_MS), jobLog.getDurationMs())
        .bind(JobLogSQLField.get(JobLogField.ERROR), jobLog.isError())
        .bind(JobLogSQLField.get(JobLogField.MESSAGE), jobLog.getMessage());
  }

  public static void bindUpdate(SQLBuilder builder, JobLog jobLog) {
    // For now, I don't support updating job logs, but we can implement this if
    // needed in the future
    throw new UnsupportedOperationException("Updating JobLog is not supported.");
  }

}
