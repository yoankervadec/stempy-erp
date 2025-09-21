package com.lesconstructionssapete.stempyerp.core.repository.base.automation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.lesconstructionssapete.stempyerp.core.automation.definition.Job;
import com.lesconstructionssapete.stempyerp.core.automation.definition.JobLog;

public interface AutomationRepository {

  public List<Job> findAll(Connection connection) throws SQLException;

  public void save(Connection connection, Job job) throws SQLException;

  public void batchLog(Connection connection, List<JobLog> logsBatch) throws SQLException;
}
