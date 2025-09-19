package com.lesconstructionssapete.stempyerp.core.repository.base.scheduler;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.lesconstructionssapete.stempyerp.core.automation.definition.Job;
import com.lesconstructionssapete.stempyerp.core.shared.query.Query;
import com.lesconstructionssapete.stempyerp.core.shared.query.QueryCache;
import com.lesconstructionssapete.stempyerp.core.shared.query.SqlBuilder;
import com.lesconstructionssapete.stempyerp.core.shared.util.DateTimeUtil;

public class SchedulerRepositoryImpl implements SchedulerRepository {

  @Override
  public List<Job> findAll(Connection connection) throws SQLException {

    List<Job> jobs = new ArrayList<>();

    String sqlBase = QueryCache.get(
        Query.SELECT_JOBS);

    SqlBuilder builder = new SqlBuilder(sqlBase);

    String sqlString = builder.build();
    List<SqlBuilder.SqlParam> params = builder.getParams();

    try (var stmt = connection.prepareStatement(sqlString)) {

      int idx = 1;
      for (SqlBuilder.SqlParam p : params) {
        stmt.setObject(idx++, p.value(), p.sqlType());
      }

      try (var rs = stmt.executeQuery()) {

        while (rs.next()) {

          String runTimesString = rs.getString("run_times");
          List<LocalTime> runTimes = DateTimeUtil.parseRunTimes(runTimesString);

          jobs.add(
              new Job(
                  rs.getInt("id"),
                  rs.getString("name"),
                  rs.getString("description"),
                  rs.getString("handler"),
                  rs.getBoolean("is_active"),
                  rs.getBoolean("deactivate_on_failure"),
                  rs.getInt("retries_on_failure"),
                  rs.getDouble("interval_minutes"),
                  runTimes,
                  rs.getObject("last_run", LocalDateTime.class),
                  rs.getObject("next_run", LocalDateTime.class),
                  rs.getInt("priority"),
                  rs.getBoolean("is_enabled")));
        }
      }
      return jobs;
    }

  }

  @Override
  public void log(
      Connection connection,
      int jobId,
      LocalDateTime startedAt,
      LocalDateTime endedAt,
      int durationMs,
      boolean isError,
      String message) throws SQLException {

  }

  @Override
  public void save(Connection connection, Job job) throws SQLException {

  }

}
