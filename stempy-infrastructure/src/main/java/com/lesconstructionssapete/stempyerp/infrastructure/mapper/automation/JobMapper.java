package com.lesconstructionssapete.stempyerp.infrastructure.mapper.automation;

import java.sql.ResultSet;
import java.util.function.BiConsumer;

import com.lesconstructionssapete.stempyerp.domain.automation.Job;
import com.lesconstructionssapete.stempyerp.domain.field.EntityField;
import com.lesconstructionssapete.stempyerp.infrastructure.mapper.EntityMapper;

public final class JobMapper {

  private JobMapper() {
  }

  public static Job fromResultSet(ResultSet rs) {
    return new Job(
        EntityMapper.read(rs, Job.Fields.ID),
        EntityMapper.read(rs, Job.Fields.NAME),
        EntityMapper.read(rs, Job.Fields.ENABLED),
        EntityMapper.read(rs, Job.Fields.CREATED_AT),
        EntityMapper.read(rs, Job.Fields.DESCRIPTION),
        EntityMapper.read(rs, Job.Fields.HANDLER_AS_STRING),
        EntityMapper.read(rs, Job.Fields.RUN_BEFORE_JOB_ID),
        EntityMapper.read(rs, Job.Fields.RUN_AFTER_JOB_ID),
        EntityMapper.read(rs, Job.Fields.ACTIVE),
        EntityMapper.read(rs, Job.Fields.DEACTIVATE_ON_FAILURE),
        EntityMapper.read(rs, Job.Fields.MAX_RETRIES),
        EntityMapper.read(rs, Job.Fields.INTERVAL_MINUTES),
        EntityMapper.read(rs, Job.Fields.RUN_TIMES_UTC),
        EntityMapper.read(rs, Job.Fields.RUN_DAYS));
  }

  public static void bind(Job job, BiConsumer<EntityField, Object> binder) {
    binder.accept(Job.Fields.ID, job.getId());
    binder.accept(Job.Fields.NAME, job.getName());
    binder.accept(Job.Fields.ENABLED, job.isEnabled());
    binder.accept(Job.Fields.CREATED_AT, job.getCreatedAt());
    binder.accept(Job.Fields.DESCRIPTION, job.getDescription());
    binder.accept(Job.Fields.HANDLER_AS_STRING, job.getHandlerAsString());
    binder.accept(Job.Fields.RUN_BEFORE_JOB_ID, job.getRunBeforeJobId());
    binder.accept(Job.Fields.RUN_AFTER_JOB_ID, job.getRunAfterJobId());
    binder.accept(Job.Fields.ACTIVE, job.isActive());
    binder.accept(Job.Fields.DEACTIVATE_ON_FAILURE, job.isDeactivateOnFailure());
    binder.accept(Job.Fields.MAX_RETRIES, job.getMaxRetries());
    binder.accept(Job.Fields.INTERVAL_MINUTES, job.getIntervalMinutes());
    binder.accept(Job.Fields.RUN_TIMES_UTC, job.getRunTimesUTC());
    binder.accept(Job.Fields.RUN_DAYS, job.getRunDaysOfWeek());
  }
}
