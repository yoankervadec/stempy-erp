package com.lesconstructionssapete.stempyerp.repository.automation;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.automation.Job;
import com.lesconstructionssapete.stempyerp.domain.automation.JobLog;
import com.lesconstructionssapete.stempyerp.mapper.automation.JobRowMapper;
import com.lesconstructionssapete.stempyerp.mapper.automation.JobSQLMapper;
import com.lesconstructionssapete.stempyerp.query.Query;
import com.lesconstructionssapete.stempyerp.query.QueryCache;
import com.lesconstructionssapete.stempyerp.query.SQLBinder;
import com.lesconstructionssapete.stempyerp.query.SQLBuilder;
import com.lesconstructionssapete.stempyerp.repository.AutomationRepository;

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

      SQLBinder.bind(stmt, params);

      try (var rs = stmt.executeQuery()) {
        while (rs.next()) {
          jobs.add(JobRowMapper.map(rs));
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

    SQLBuilder builder = new SQLBuilder(sqlBase);

    JobSQLMapper.bindUpdate(builder, job);

    builder
        .where("id = :jobid")
        .bind("jobId", job.getId());

    String sqlString = builder.build();
    List<SQLBuilder.SQLParam> params = builder.getParams();

    try (var stmt = connection.prepareStatement(sqlString)) {

      SQLBinder.bind(stmt, params);

      stmt.executeUpdate();
    }
  }

}
