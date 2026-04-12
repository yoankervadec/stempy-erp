package com.lesconstructionssapete.stempyerp.infrastructure.mapper.automation;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.lesconstructionssapete.stempyerp.automation.Job;
import com.lesconstructionssapete.stempyerp.field.automation.JobField;
import com.lesconstructionssapete.stempyerp.infrastructure.field.automation.JobSQLField;
import com.lesconstructionssapete.stempyerp.util.DateTimeUtil;

public class JobRowMapper {

  private JobRowMapper() {
  }

  public static Job map(ResultSet rs) throws SQLException {
    return new Job(
        rs.getLong(JobSQLField.get(JobField.ID).columnName()),
        rs.getString(JobSQLField.get(JobField.NAME).columnName()),
        rs.getBoolean(JobSQLField.get(JobField.ENABLED).columnName()),
        rs.getTimestamp(JobSQLField.get(JobField.CREATED_AT).columnName()).toInstant(),
        rs.getString(JobSQLField.get(JobField.DESCRIPTION).columnName()),
        rs.getString(JobSQLField.get(JobField.HANDLER_AS_STRING).columnName()),
        rs.getLong(JobSQLField.get(JobField.RUN_BEFORE_JOB_ID).columnName()),
        rs.getLong(JobSQLField.get(JobField.RUN_AFTER_JOB_ID).columnName()),
        rs.getBoolean(JobSQLField.get(JobField.ACTIVE).columnName()),
        rs.getBoolean(JobSQLField.get(JobField.DEACTIVATE_ON_FAILURE).columnName()),
        rs.getInt(JobSQLField.get(JobField.MAX_RETRIES).columnName()),
        rs.getDouble(JobSQLField.get(JobField.INTERVAL_MINUTES).columnName()),
        DateTimeUtil.parseRunTimes(rs.getString(JobSQLField.get(JobField.RUN_TIMES_UTC).columnName())),
        DateTimeUtil.parseRunDays(rs.getString(JobSQLField.get(JobField.RUN_DAYS).columnName())));
  }
}
