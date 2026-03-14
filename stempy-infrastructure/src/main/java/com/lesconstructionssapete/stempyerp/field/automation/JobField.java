package com.lesconstructionssapete.stempyerp.field.automation;

import java.sql.Types;
import java.util.Map;

import com.lesconstructionssapete.stempyerp.mapper.SQLField;

public final class JobField {

  private JobField() {
  }

  private static final String AUTO_JOB = "auto_job";

  public static final SQLField ID = new SQLField(
      "id",
      AUTO_JOB,
      "id",
      Types.BIGINT);

  public static final SQLField NAME = new SQLField(
      "name",
      AUTO_JOB,
      "name",
      Types.VARCHAR);

  public final static SQLField ENABLED = new SQLField(
      "enabled",
      AUTO_JOB,
      "enabled",
      Types.BOOLEAN);

  public final static SQLField CREATED_AT = new SQLField(
      "createdAt",
      AUTO_JOB,
      "created_at",
      Types.TIMESTAMP);

  public final static SQLField DESCRIPTION = new SQLField(
      "description",
      AUTO_JOB,
      "description",
      Types.VARCHAR);

  public final static SQLField HANDLER_AS_STRING = new SQLField(
      "handlerAsString",
      AUTO_JOB,
      "handler",
      Types.VARCHAR);

  public static final SQLField RUN_BEFORE_JOB_ID = new SQLField(
      "runBeforeJobId",
      AUTO_JOB,
      "run_before_id",
      Types.BIGINT);

  public static final SQLField RUN_AFTER_JOB_ID = new SQLField(
      "runAfterJobId",
      AUTO_JOB,
      "run_after_id",
      Types.BIGINT);

  public final static SQLField ACTIVE = new SQLField(
      "active",
      AUTO_JOB,
      "active",
      Types.BOOLEAN);

  public final static SQLField DEACTIVATE_ON_FAILUE = new SQLField(
      "deactivateOnFailure",
      AUTO_JOB,
      "deactivate_on_failure",
      Types.BOOLEAN);

  public final static SQLField MAX_RETRIES = new SQLField(
      "maxRetries",
      AUTO_JOB,
      "max_retries",
      Types.INTEGER);

  public final static SQLField INTERVAL_MINUTES = new SQLField(
      "intervalMinutes",
      AUTO_JOB,
      "interval_minutes",
      Types.DOUBLE);

  public final static SQLField RUN_TIMES_UTC = new SQLField(
      "runTimesUTC",
      AUTO_JOB,
      "run_times_utc",
      Types.VARCHAR);

  public final static SQLField RUN_DAYS = new SQLField(
      "runDaysOfWeek",
      AUTO_JOB,
      "run_days",
      Types.VARCHAR);

  private static final Map<String, SQLField> LOOKUP = Map.ofEntries(
      Map.entry(ID.logicalName(), ID),
      Map.entry(NAME.logicalName(), NAME),
      Map.entry(ENABLED.logicalName(), ENABLED),
      Map.entry(CREATED_AT.logicalName(), CREATED_AT),
      Map.entry(DESCRIPTION.logicalName(), DESCRIPTION),
      Map.entry(HANDLER_AS_STRING.logicalName(), HANDLER_AS_STRING),
      Map.entry(RUN_BEFORE_JOB_ID.logicalName(), RUN_BEFORE_JOB_ID),
      Map.entry(RUN_AFTER_JOB_ID.logicalName(), RUN_AFTER_JOB_ID),
      Map.entry(ACTIVE.logicalName(), ACTIVE),
      Map.entry(DEACTIVATE_ON_FAILUE.logicalName(), DEACTIVATE_ON_FAILUE),
      Map.entry(MAX_RETRIES.logicalName(), MAX_RETRIES),
      Map.entry(INTERVAL_MINUTES.logicalName(), INTERVAL_MINUTES),
      Map.entry(RUN_TIMES_UTC.logicalName(), RUN_TIMES_UTC),
      Map.entry(RUN_DAYS.logicalName(), RUN_DAYS));

  public static SQLField get(String logicalName) {
    return LOOKUP.get(logicalName);
  }

  public static Map<String, SQLField> all() {
    return LOOKUP;
  }

}
