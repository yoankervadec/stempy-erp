package com.lesconstructionssapete.stempyerp.mapper.automation;

import com.lesconstructionssapete.stempyerp.domain.automation.JobLog;
import com.lesconstructionssapete.stempyerp.field.automation.JobLogField;
import com.lesconstructionssapete.stempyerp.query.SQLBuilder;

public final class JobLogSQLMapper {

  private JobLogSQLMapper() {
  }

  public static void bindInsert(SQLBuilder builder, JobLog jobLog) {
    builder
        .bind(JobLogField.JOB_ID, jobLog.getJobId())
        .bind(JobLogField.STARTED_AT, jobLog.getStartedAt())
        .bind(JobLogField.ENDED_AT, jobLog.getEndedAt())
        .bind(JobLogField.EXECUTION_TIME, jobLog.getDurationMs())
        .bind(JobLogField.ERROR, jobLog.isError())
        .bind(JobLogField.MESSAGE, jobLog.getMessage());
  }

  public static void bindUpdate(SQLBuilder builder, JobLog jobLog) {
    // For now, I don't support updating job logs, but we can implement this if
    // needed in the future
    throw new UnsupportedOperationException("Updating JobLog is not supported.");
  }

}
