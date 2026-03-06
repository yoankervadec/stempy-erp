package com.lesconstructionssapete.stempyerp.repository.automation;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.automation.Job;
import com.lesconstructionssapete.stempyerp.domain.automation.JobLog;
import com.lesconstructionssapete.stempyerp.query.Query;
import com.lesconstructionssapete.stempyerp.query.QueryCache;
import com.lesconstructionssapete.stempyerp.query.SQLBuilder;
import com.lesconstructionssapete.stempyerp.repository.AutomationRepository;
import com.lesconstructionssapete.stempyerp.util.DateTimeUtil;

public class AutomationRepositoryImpl implements AutomationRepository {

  @Override
  public List<Job> fetchAll(Connection connection) throws SQLException {

    List<Job> jobs = new ArrayList<>();

    String sqlBase = QueryCache.get(
        Query.SELECT_AUTO_JOB);

    SQLBuilder builder = new SQLBuilder(sqlBase);

    String sqlString = builder.build();
    List<SQLBuilder.SQLParam> params = builder.getParams();

    try (var stmt = connection.prepareStatement(sqlString)) {
      int idx = 1;
      for (SQLBuilder.SQLParam p : params) {
        stmt.setObject(idx++, p.value(), p.sqlType());
      }

      try (var rs = stmt.executeQuery()) {
        while (rs.next()) {

          String runTimesUTCString = rs.getString("run_times_utc");
          List<LocalTime> runTimes = DateTimeUtil.parseRunTimes(runTimesUTCString);

          jobs.add(
              new Job(
                  rs.getLong("id"),
                  rs.getString("name"),
                  rs.getBoolean("enabled"),
                  rs.getTimestamp("created_at").toLocalDateTime(),
                  rs.getString("description"),
                  rs.getString("handler"),
                  rs.getLong("run_before_job_id"),
                  rs.getLong("run_after_job_id"),
                  rs.getBoolean("active"),
                  rs.getBoolean("deactivate_on_failure"),
                  rs.getInt("max_retries"),
                  rs.getDouble("interval_minutes"),
                  runTimes,
                  null)); // TODO: map String of days of week to List<DayOfWeek>
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
        stmt.setLong(1, log.getJobId());
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

    String sqlBase = QueryCache.get(Query.UPDATE_AUTO_JOB);

    SQLBuilder builder = new SQLBuilder(sqlBase)
        .bindTyped(job.isEnabled(), Types.TINYINT)
        .bind(job.getCreatedAt())
        .bindTyped(job.getDescription(), Types.VARCHAR)
        .bindTyped(job.getHandlerAsString(), Types.VARCHAR)
        .bindTyped(job.getRunBeforeJobId(), Types.BIGINT)
        .bindTyped(job.getRunAfterJobId(), Types.BIGINT)
        .bindTyped(job.isActive(), Types.TINYINT)
        .bindTyped(job.isDeactivateOnFailure(), Types.TINYINT)
        .bindTyped(job.getMaxRetries(), Types.INTEGER)
        .bindTyped(job.getIntervalMinutes(), Types.DOUBLE)
        .bindTyped(DateTimeUtil.toRunTimesJson(job.getRunTimesUTC()), Types.VARCHAR)
        .bindTyped("", Types.VARCHAR)
        .where("id = :jobid", job.getId());

    String sqlString = builder.build();
    List<SQLBuilder.SQLParam> params = builder.getParams();

    try (var stmt = connection.prepareStatement(sqlString)) {

      int idx = 1;
      for (SQLBuilder.SQLParam p : params) {
        stmt.setObject(idx, p.value(), p.sqlType());
      }

      stmt.executeUpdate();
    }
  }

}
