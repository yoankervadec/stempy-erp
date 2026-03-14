package com.lesconstructionssapete.stempyerp.mapper.automation;

import com.lesconstructionssapete.stempyerp.domain.automation.Job;
import com.lesconstructionssapete.stempyerp.field.automation.JobField;
import com.lesconstructionssapete.stempyerp.query.SQLBuilder;
import com.lesconstructionssapete.stempyerp.util.DateTimeUtil;

public class JobSQLMapper {

  private JobSQLMapper() {
  }

  public static void bindInsert(SQLBuilder builder, Job job) {
    // Must be inserted from SQL client with specific handler class name, so I don't
    // want to allow inserting from Java code for now
    throw new UnsupportedOperationException(
        " Inserting Job from Java code is not supported. Use SQL client with specific handler class name instead.");
  }

  public static void bindUpdate(SQLBuilder builder, Job job) {
    builder
        .bind(JobField.ENABLED, job.isEnabled())
        .bind(JobField.DESCRIPTION, job.getDescription())
        .bind(JobField.HANDLER_AS_STRING, job.getHandlerAsString())
        .bind(JobField.RUN_BEFORE_JOB_ID, job.getRunBeforeJobId())
        .bind(JobField.RUN_AFTER_JOB_ID, job.getRunAfterJobId())
        .bind(JobField.ACTIVE, job.isActive())
        .bind(JobField.DEACTIVATE_ON_FAILUE, job.isDeactivateOnFailure())
        .bind(JobField.MAX_RETRIES, job.getMaxRetries())
        .bind(JobField.INTERVAL_MINUTES, job.getIntervalMinutes())
        .bind(JobField.RUN_TIMES_UTC, DateTimeUtil.toRunTimesJson(job.getRunTimesUTC()))
        .bind(JobField.RUN_DAYS, DateTimeUtil.toRunDaysJson(job.getRunDaysOfWeek()));
  }

}
