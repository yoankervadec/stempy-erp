package com.lesconstructionssapete.stempyerp.infrastructure.mapper.automation;

import com.lesconstructionssapete.stempyerp.domain.automation.Job;
import com.lesconstructionssapete.stempyerp.domain.field.automation.JobField;
import com.lesconstructionssapete.stempyerp.infrastructure.field.automation.JobSQLField;
import com.lesconstructionssapete.stempyerp.infrastructure.query.SQLBuilder;
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
        .bind(JobSQLField.get(JobField.ENABLED), job.isEnabled())
        .bind(JobSQLField.get(JobField.DESCRIPTION), job.getDescription())
        .bind(JobSQLField.get(JobField.HANDLER_AS_STRING), job.getHandlerAsString())
        .bind(JobSQLField.get(JobField.RUN_BEFORE_JOB_ID), job.getRunBeforeJobId())
        .bind(JobSQLField.get(JobField.RUN_AFTER_JOB_ID), job.getRunAfterJobId())
        .bind(JobSQLField.get(JobField.ACTIVE), job.isActive())
        .bind(JobSQLField.get(JobField.DEACTIVATE_ON_FAILURE), job.isDeactivateOnFailure())
        .bind(JobSQLField.get(JobField.MAX_RETRIES), job.getMaxRetries())
        .bind(JobSQLField.get(JobField.INTERVAL_MINUTES), job.getIntervalMinutes())
        .bind(JobSQLField.get(JobField.RUN_TIMES_UTC), DateTimeUtil.toRunTimesJson(job.getRunTimesUTC()))
        .bind(JobSQLField.get(JobField.RUN_DAYS), DateTimeUtil.toRunDaysJson(job.getRunDaysOfWeek()));
  }

}
