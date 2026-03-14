package com.lesconstructionssapete.stempyerp.mapper.automation;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.lesconstructionssapete.stempyerp.domain.automation.Job;
import com.lesconstructionssapete.stempyerp.util.DateTimeUtil;

public class JobRowMapper {

  private JobRowMapper() {
  }

  public static Job map(ResultSet rs) throws SQLException {
    return new Job(
        rs.getLong("id"),
        rs.getString("name"),
        rs.getBoolean("enabled"),
        rs.getTimestamp("created_at").toInstant(),
        rs.getString("description"),
        rs.getString("handler"),
        rs.getLong("run_before_job_id"),
        rs.getLong("run_after_job_id"),
        rs.getBoolean("active"),
        rs.getBoolean("deactivate_on_failure"),
        rs.getInt("max_retries"),
        rs.getDouble("interval_minutes"),
        DateTimeUtil.parseRunTimes(rs.getString("run_times_utc")),
        null); // TODO: map String of days of week to List<DayOfWeek>
  }
}
