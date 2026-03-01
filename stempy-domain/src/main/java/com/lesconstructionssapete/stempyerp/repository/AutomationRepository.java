package com.lesconstructionssapete.stempyerp.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.base.automation.Job;
import com.lesconstructionssapete.stempyerp.domain.base.automation.JobLog;

public interface AutomationRepository {

  public List<Job> fetchAll(Connection connection) throws SQLException;

  public void save(Connection connection, Job job) throws SQLException;

  public void batchLog(Connection connection, List<JobLog> logsBatch) throws SQLException;
}
