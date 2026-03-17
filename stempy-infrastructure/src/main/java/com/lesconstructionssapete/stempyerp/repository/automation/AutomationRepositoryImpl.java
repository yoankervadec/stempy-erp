package com.lesconstructionssapete.stempyerp.repository.automation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lesconstructionssapete.stempyerp.config.db.SQLExecutor;
import com.lesconstructionssapete.stempyerp.domain.automation.Job;
import com.lesconstructionssapete.stempyerp.domain.automation.JobLog;
import com.lesconstructionssapete.stempyerp.field.automation.JobField;
import com.lesconstructionssapete.stempyerp.mapper.automation.JobLogSQLMapper;
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

    String sql = QueryCache.get(
        Query.SELECT_AUTO_JOB);

    SQLBuilder builder = new SQLBuilder(sql);

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

    String sql = QueryCache.get(Query.INSERT_JOB_LOG);

    SQLBuilder builder = new SQLBuilder(sql);
    String sqlFinal = builder.build();

    List<List<SQLBuilder.SQLParam>> batchParams = new ArrayList<>();

    for (JobLog log : jobLogs) {
      builder.clearParams();

      JobLogSQLMapper.bindInsert(builder, log);

      batchParams.add(builder.getParams());
    }

    SQLExecutor.batch(connection, sqlFinal, batchParams);

  }

  @Override
  public void save(Connection connection, Job job) throws SQLException {

    String sql = QueryCache.get(Query.UPDATE_AUTO_JOB);

    SQLBuilder builder = new SQLBuilder(sql);

    JobSQLMapper.bindUpdate(builder, job);

    builder.where("auto_job.id = :id")
        .bind(JobField.ID, job.getId());

    String sqlFinal = builder.build();
    List<SQLBuilder.SQLParam> params = builder.getParams();

    try (var stmt = connection.prepareStatement(sqlFinal)) {

      SQLBinder.bind(stmt, params);

      stmt.executeUpdate();
    }
  }

}
