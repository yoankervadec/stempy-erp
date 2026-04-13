package com.lesconstructionssapete.stempyerp.infrastructure.field.automation;

import java.sql.Types;
import java.util.Map;

import com.lesconstructionssapete.stempyerp.domain.field.DomainField;
import com.lesconstructionssapete.stempyerp.domain.field.automation.JobLogField;
import com.lesconstructionssapete.stempyerp.infrastructure.field.SQLField;

public final class JobLogSQLField {

  private JobLogSQLField() {
  }

  private static final String AUTO_JOB_LOG = "auto_job_log";

  private static final SQLField ID = new SQLField(
      JobLogField.ID,
      AUTO_JOB_LOG,
      "id",
      Types.BIGINT);

  private static final SQLField JOB_ID = new SQLField(
      JobLogField.JOB_ID,
      AUTO_JOB_LOG,
      "job_id",
      Types.BIGINT);

  private final static SQLField CREATED_AT = new SQLField(
      JobLogField.CREATED_AT,
      AUTO_JOB_LOG,
      "created_at",
      Types.TIMESTAMP);

  private static final SQLField STARTED_AT = new SQLField(
      JobLogField.STARTED_AT,
      AUTO_JOB_LOG,
      "started_at",
      Types.TIMESTAMP);

  private static final SQLField ENDED_AT = new SQLField(
      JobLogField.ENDED_AT,
      AUTO_JOB_LOG,
      "ended_at",
      Types.TIMESTAMP);

  private static final SQLField EXECUTION_TIME = new SQLField(
      JobLogField.EXECUTION_TIME_MS,
      AUTO_JOB_LOG,
      "execution_time_ms",
      Types.INTEGER);

  private static final SQLField ERROR = new SQLField(
      JobLogField.ERROR,
      AUTO_JOB_LOG,
      "error",
      Types.BOOLEAN);

  private static final SQLField MESSAGE = new SQLField(
      JobLogField.MESSAGE,
      AUTO_JOB_LOG,
      "message",
      Types.VARCHAR);

  private static final Map<DomainField, SQLField> LOOKUP = Map.ofEntries(
      Map.entry(JobLogField.ID, ID),
      Map.entry(JobLogField.CREATED_AT, CREATED_AT),
      Map.entry(JobLogField.JOB_ID, JOB_ID),
      Map.entry(JobLogField.STARTED_AT, STARTED_AT),
      Map.entry(JobLogField.ENDED_AT, ENDED_AT),
      Map.entry(JobLogField.EXECUTION_TIME_MS, EXECUTION_TIME),
      Map.entry(JobLogField.ERROR, ERROR),
      Map.entry(JobLogField.MESSAGE, MESSAGE));

  public static SQLField get(DomainField field) {
    return LOOKUP.get(field);
  }

  public static Map<DomainField, SQLField> all() {
    return LOOKUP;
  }
}
