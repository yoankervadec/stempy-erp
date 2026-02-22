package com.lesconstructionssapete.stempyerp.core.repository.base.automation;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.lesconstructionssapete.stempyerp.core.automation.definition.Job;
import com.lesconstructionssapete.stempyerp.core.automation.definition.JobLog;
import com.lesconstructionssapete.stempyerp.core.repository.query.SqlBuilder;
import com.lesconstructionssapete.stempyerp.core.shared.query.Query;
import com.lesconstructionssapete.stempyerp.core.shared.query.QueryCache;
import com.lesconstructionssapete.stempyerp.core.shared.util.DateTimeUtil;

public class AutomationRepositoryImpl implements AutomationRepository {

  @Override
  public List<Job> fetchAll(Connection connection) throws SQLException {

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
  public void batchLog(Connection connection, List<JobLog> jobLogs) throws SQLException {

    String sqlString = QueryCache.get(Query.INSERT_JOB_LOG);

    try (var stmt = connection.prepareStatement(sqlString)) {
      for (JobLog log : jobLogs) {
        stmt.clearParameters();
        stmt.setInt(1, log.getJobId());
        stmt.setTimestamp(2, Timestamp.valueOf(log.getStartedAt()));
        stmt.setTimestamp(3, Timestamp.valueOf(log.getEndedAt()));
        stmt.setInt(4, log.getDurationMs());
        stmt.setBoolean(5, log.isError());
        stmt.setString(6, log.getMessage());
        stmt.addBatch();
      }

      stmt.executeBatch();
    }

  }

  @Override
  public void save(Connection connection, Job job) throws SQLException {

    String sqlBase = QueryCache.get(Query.UPDATE_JOB);

    SqlBuilder builder = new SqlBuilder(sqlBase)
        .bindTyped(job.getJobDescription(), Types.VARCHAR)
        .bindTyped(job.getHandlerAsString(), Types.VARCHAR)
        .bindTyped(job.isActive(), Types.TINYINT)
        .bindTyped(job.isDeactivateOnFailure(), Types.TINYINT)
        .bindTyped(job.getRetriesOnFailure(), Types.INTEGER)
        .bindTyped(job.getIntervalMinutes(), Types.DOUBLE)
        .bindTyped(DateTimeUtil.toRunTimesJson(job.getRunTimes()), Types.VARCHAR)
        .bind(job.getLastRun())
        .bind(job.getNextRun())
        .bindTyped(job.getPriority(), Types.INTEGER)
        .bindTyped(job.isEnabled(), Types.TINYINT)
        .where("id = :jobid", job.getJobId());

    String sqlString = builder.build();
    List<SqlBuilder.SqlParam> params = builder.getParams();

    try (var stmt = connection.prepareStatement(sqlString)) {

      int idx = 1;
      for (SqlBuilder.SqlParam p : params) {
        stmt.setObject(idx, p.value(), p.sqlType());
      }

      stmt.executeUpdate();
    }
  }

}
