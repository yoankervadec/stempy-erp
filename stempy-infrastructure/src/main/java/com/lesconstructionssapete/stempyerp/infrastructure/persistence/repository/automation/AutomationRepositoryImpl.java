package com.lesconstructionssapete.stempyerp.infrastructure.persistence.repository.automation;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.lesconstructionssapete.stempyerp.automation.Job;
import com.lesconstructionssapete.stempyerp.automation.JobLog;
import com.lesconstructionssapete.stempyerp.field.automation.JobField;
import com.lesconstructionssapete.stempyerp.infrastructure.field.automation.JobSQLField;
import com.lesconstructionssapete.stempyerp.infrastructure.mapper.automation.JobLogSQLMapper;
import com.lesconstructionssapete.stempyerp.infrastructure.mapper.automation.JobRowMapper;
import com.lesconstructionssapete.stempyerp.infrastructure.mapper.automation.JobSQLMapper;
import com.lesconstructionssapete.stempyerp.infrastructure.persistence.SQLExecutor;
import com.lesconstructionssapete.stempyerp.infrastructure.query.Query;
import com.lesconstructionssapete.stempyerp.infrastructure.query.QueryCache;
import com.lesconstructionssapete.stempyerp.infrastructure.query.SQLBuilder;
import com.lesconstructionssapete.stempyerp.repository.AutomationRepository;

public class AutomationRepositoryImpl implements AutomationRepository {

  @Override
  public List<Job> fetchAll(Connection connection) {

    String sql = QueryCache.get(
        Query.SELECT_AUTO_JOB);

    SQLBuilder builder = new SQLBuilder(sql);

    return SQLExecutor.query(
        connection,
        builder.build(),
        builder.getParams(),
        rs -> {
          List<Job> list = new ArrayList<>();
          while (rs.next()) {
            list.add(JobRowMapper.map(rs));
          }
          return list;
        });
  }

  @Override
  public void batchLog(Connection connection, List<JobLog> jobLogs) {

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
  public int save(Connection connection, Job job) {

    String sql = QueryCache.get(Query.UPDATE_AUTO_JOB);

    SQLBuilder builder = new SQLBuilder(sql);

    JobSQLMapper.bindUpdate(builder, job);

    builder.where("auto_job.id = :id")
        .bind(JobSQLField.get(JobField.ID), job.getId());

    int rowsAffected = SQLExecutor.update(connection, builder.build(), builder.getParams());

    return rowsAffected;
  }

}
