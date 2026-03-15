package com.lesconstructionssapete.stempyerp.field.automation;

import java.sql.Types;
import java.util.Map;

import com.lesconstructionssapete.stempyerp.mapper.SQLField;

public final class JobLogField {

  private JobLogField() {
  }

  private static final String AUTO_JOB_LOG = "auto_job_log";

  public static final SQLField ID = new SQLField(
      "id",
      AUTO_JOB_LOG,
      "id",
      Types.BIGINT);

  public final static SQLField CREATED_AT = new SQLField(
      "createdAt",
      AUTO_JOB_LOG,
      "created_at",
      Types.TIMESTAMP);

  public static final SQLField JOB_ID = new SQLField(
      "jobId",
      AUTO_JOB_LOG,
      "job_id",
      Types.BIGINT);

  public static final SQLField STARTED_AT = new SQLField(
      "startedAt",
      AUTO_JOB_LOG,
      "started_at",
      Types.TIMESTAMP);

  public static final SQLField ENDED_AT = new SQLField(
      "endedAt",
      AUTO_JOB_LOG,
      "ended_at",
      Types.TIMESTAMP);

  public static final SQLField EXECUTION_TIME = new SQLField(
      "executionTimeMs",
      AUTO_JOB_LOG,
      "execution_time_ms",
      Types.INTEGER);

  public static final SQLField ERROR = new SQLField(
      "error",
      AUTO_JOB_LOG,
      "error",
      Types.BOOLEAN);

  public static final SQLField MESSAGE = new SQLField(
      "message",
      AUTO_JOB_LOG,
      "message",
      Types.VARCHAR);

  private static final Map<String, SQLField> LOOKUP = Map.ofEntries(
      Map.entry(ID.logicalName(), ID),
      Map.entry(CREATED_AT.logicalName(), CREATED_AT),
      Map.entry(JOB_ID.logicalName(), JOB_ID),
      Map.entry(STARTED_AT.logicalName(), STARTED_AT),
      Map.entry(ENDED_AT.logicalName(), ENDED_AT),
      Map.entry(EXECUTION_TIME.logicalName(), EXECUTION_TIME),
      Map.entry(ERROR.logicalName(), ERROR),
      Map.entry(MESSAGE.logicalName(), MESSAGE));

  public static SQLField get(String logicalName) {
    return LOOKUP.get(logicalName);
  }

  public static Map<String, SQLField> all() {
    return LOOKUP;
  }
}
