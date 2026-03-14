package com.lesconstructionssapete.stempyerp.mapper.automation;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.lesconstructionssapete.stempyerp.domain.automation.Job;
import com.lesconstructionssapete.stempyerp.field.automation.JobField;
import com.lesconstructionssapete.stempyerp.util.DateTimeUtil;

public class JobRowMapper {

  private JobRowMapper() {
  }

  public static Job map(ResultSet rs) throws SQLException {
    return new Job(
        rs.getLong(JobField.ID.columnName()),
        rs.getString(JobField.NAME.columnName()),
        rs.getBoolean(JobField.ENABLED.columnName()),
        rs.getTimestamp(JobField.CREATED_AT.columnName()).toInstant(),
        rs.getString(JobField.DESCRIPTION.columnName()),
        rs.getString(JobField.HANDLER_AS_STRING.columnName()),
        rs.getLong(JobField.RUN_BEFORE_JOB_ID.columnName()),
        rs.getLong(JobField.RUN_AFTER_JOB_ID.columnName()),
        rs.getBoolean(JobField.ACTIVE.columnName()),
        rs.getBoolean(JobField.DEACTIVATE_ON_FAILUE.columnName()),
        rs.getInt(JobField.MAX_RETRIES.columnName()),
        rs.getDouble(JobField.INTERVAL_MINUTES.columnName()),
        DateTimeUtil.parseRunTimes(rs.getString(JobField.RUN_TIMES_UTC.columnName())),
        DateTimeUtil.parseRunDays(rs.getString(JobField.RUN_DAYS.columnName())));
  }
}
