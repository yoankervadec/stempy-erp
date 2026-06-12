package com.lesconstructionssapete.stempyerp.infrastructure.mapper.automation;

import java.sql.ResultSet;
import java.util.function.BiConsumer;

import com.lesconstructionssapete.stempyerp.domain.automation.JobLog;
import com.lesconstructionssapete.stempyerp.domain.field.EntityField;
import com.lesconstructionssapete.stempyerp.infrastructure.mapper.EntityMapper;

public final class JobLogMapper {

  private JobLogMapper() {
  }

  public static JobLog fromResultSet(ResultSet rs) {
    return new JobLog(
        // EntityMapper.read(rs, JobLog.Fields.ID),
        EntityMapper.read(rs, JobLog.Fields.JOB_ID),
        EntityMapper.read(rs, JobLog.Fields.CREATED_AT),
        EntityMapper.read(rs, JobLog.Fields.STARTED_AT),
        EntityMapper.read(rs, JobLog.Fields.ENDED_AT),
        // EntityMapper.read(rs, JobLog.Fields.EXECUTION_TIME_MS),
        EntityMapper.read(rs, JobLog.Fields.ERROR),
        EntityMapper.read(rs, JobLog.Fields.MESSAGE));
  }

  public static void bind(JobLog jobLog, BiConsumer<EntityField, Object> binder) {
    // binder.accept(JobLog.Fields.ID, jobLog.getId());
    binder.accept(JobLog.Fields.JOB_ID, jobLog.getJobId());
    // binder.accept(JobLog.Fields.CREATED_AT, null);
    binder.accept(JobLog.Fields.STARTED_AT, jobLog.getStartedAt());
    binder.accept(JobLog.Fields.ENDED_AT, jobLog.getEndedAt());
    binder.accept(JobLog.Fields.EXECUTION_TIME_MS, jobLog.getDurationMs());
    binder.accept(JobLog.Fields.ERROR, jobLog.isError());
    binder.accept(JobLog.Fields.MESSAGE, jobLog.getMessage());
  }

}
