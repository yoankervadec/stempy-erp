package com.lesconstructionssapete.stempyerp.mapper.automation;

import com.lesconstructionssapete.stempyerp.domain.automation.Job;
import com.lesconstructionssapete.stempyerp.query.SQLBuilder;

public class JobSQLMapper {

  private JobSQLMapper() {
  }

  public static void bindInsert(SQLBuilder builder, Job j) {
  }

  public static void bindUpdate(SQLBuilder builder, Job j) {
    builder
        .bind("enabled", j.isEnabled())
        .bind("description", j.getDescription())
        .bind("handler", j.getHandlerAsString())
        .bind("run_before_id", j.getRunBeforeJobId())
        .bind("run_after_id", j.getRunAfterJobId())
        .bind("active", j.isActive())
        .bind("deactivate_on_failure", j.isDeactivateOnFailure())
        .bind("max_retries", j.getMaxRetries())
        .bind("interval_minutes", j.getIntervalMinutes())
        .bind("run_times_utc", j.getRunTimesUTC())
        .bind("run_days", j.getRunDaysOfWeek());
  }

}
