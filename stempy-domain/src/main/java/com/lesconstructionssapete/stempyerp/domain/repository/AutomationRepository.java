package com.lesconstructionssapete.stempyerp.domain.repository;

import java.sql.Connection;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.automation.Job;
import com.lesconstructionssapete.stempyerp.domain.automation.JobLog;

public interface AutomationRepository {

  public List<Job> fetchAll(Connection connection);

  public int save(Connection connection, Job job);

  public void batchLog(Connection connection, List<JobLog> logsBatch);
}
