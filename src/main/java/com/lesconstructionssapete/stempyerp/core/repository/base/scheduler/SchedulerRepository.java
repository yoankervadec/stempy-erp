package com.lesconstructionssapete.stempyerp.core.repository.base.scheduler;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import com.lesconstructionssapete.stempyerp.core.automation.definition.Job;

public interface SchedulerRepository {

  public List<Job> findAll(Connection connection) throws SQLException;

  public void save(Connection connection, Job job) throws SQLException;

  public void log(
      Connection connection,
      int jobId,
      LocalDateTime startedAt,
      LocalDateTime endedAt,
      int durationMs,
      boolean isError,
      String message) throws SQLException;
}
