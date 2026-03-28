package com.lesconstructionssapete.stempyerp.field.automation;

import java.sql.Types;
import java.util.Map;

import com.lesconstructionssapete.stempyerp.field.DomainField;
import com.lesconstructionssapete.stempyerp.field.SQLField;

public final class JobSQLField {

  private JobSQLField() {
  }

  private static final String AUTO_JOB = "auto_job";

  private static final SQLField ID = new SQLField(
      JobField.ID,
      AUTO_JOB,
      "id",
      Types.BIGINT);

  private static final SQLField NAME = new SQLField(
      JobField.NAME,
      AUTO_JOB,
      "name",
      Types.VARCHAR);

  private final static SQLField ENABLED = new SQLField(
      JobField.ENABLED,
      AUTO_JOB,
      "enabled",
      Types.BOOLEAN);

  private final static SQLField CREATED_AT = new SQLField(
      JobField.CREATED_AT,
      AUTO_JOB,
      "created_at",
      Types.TIMESTAMP);

  private final static SQLField DESCRIPTION = new SQLField(
      JobField.DESCRIPTION,
      AUTO_JOB,
      "description",
      Types.VARCHAR);

  private final static SQLField HANDLER_AS_STRING = new SQLField(
      JobField.HANDLER_AS_STRING,
      AUTO_JOB,
      "handler",
      Types.VARCHAR);

  private static final SQLField RUN_BEFORE_JOB_ID = new SQLField(
      JobField.RUN_BEFORE_JOB_ID,
      AUTO_JOB,
      "run_before_id",
      Types.BIGINT);

  private static final SQLField RUN_AFTER_JOB_ID = new SQLField(
      JobField.RUN_AFTER_JOB_ID,
      AUTO_JOB,
      "run_after_id",
      Types.BIGINT);

  private final static SQLField ACTIVE = new SQLField(
      JobField.ACTIVE,
      AUTO_JOB,
      "active",
      Types.BOOLEAN);

  private final static SQLField DEACTIVATE_ON_FAILURE = new SQLField(
      JobField.DEACTIVATE_ON_FAILURE,
      AUTO_JOB,
      "deactivate_on_failure",
      Types.BOOLEAN);

  private final static SQLField MAX_RETRIES = new SQLField(
      JobField.MAX_RETRIES,
      AUTO_JOB,
      "max_retries",
      Types.INTEGER);

  private final static SQLField INTERVAL_MINUTES = new SQLField(
      JobField.INTERVAL_MINUTES,
      AUTO_JOB,
      "interval_minutes",
      Types.DOUBLE);

  private final static SQLField RUN_TIMES_UTC = new SQLField(
      JobField.RUN_TIMES_UTC,
      AUTO_JOB,
      "run_times_utc",
      Types.VARCHAR);

  private final static SQLField RUN_DAYS = new SQLField(
      JobField.RUN_DAYS,
      AUTO_JOB,
      "run_days",
      Types.VARCHAR);

  private static final Map<DomainField, SQLField> LOOKUP = Map.ofEntries(
      Map.entry(JobField.ID, ID),
      Map.entry(JobField.NAME, NAME),
      Map.entry(JobField.ENABLED, ENABLED),
      Map.entry(JobField.CREATED_AT, CREATED_AT),
      Map.entry(JobField.DESCRIPTION, DESCRIPTION),
      Map.entry(JobField.HANDLER_AS_STRING, HANDLER_AS_STRING),
      Map.entry(JobField.RUN_BEFORE_JOB_ID, RUN_BEFORE_JOB_ID),
      Map.entry(JobField.RUN_AFTER_JOB_ID, RUN_AFTER_JOB_ID),
      Map.entry(JobField.ACTIVE, ACTIVE),
      Map.entry(JobField.DEACTIVATE_ON_FAILURE, DEACTIVATE_ON_FAILURE),
      Map.entry(JobField.MAX_RETRIES, MAX_RETRIES),
      Map.entry(JobField.INTERVAL_MINUTES, INTERVAL_MINUTES),
      Map.entry(JobField.RUN_TIMES_UTC, RUN_TIMES_UTC),
      Map.entry(JobField.RUN_DAYS, RUN_DAYS));

  public static SQLField get(DomainField field) {
    return LOOKUP.get(field);
  }

  public static Map<DomainField, SQLField> all() {
    return LOOKUP;
  }

}
