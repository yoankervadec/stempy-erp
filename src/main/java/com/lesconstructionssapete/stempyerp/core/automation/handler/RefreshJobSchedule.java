package com.lesconstructionssapete.stempyerp.core.automation.handler;

import java.sql.Connection;
import java.sql.SQLException;

import com.lesconstructionssapete.stempyerp.core.automation.definition.Job;
import com.lesconstructionssapete.stempyerp.core.automation.definition.JobExecutable;
import com.lesconstructionssapete.stempyerp.core.automation.definition.JobLog;
import com.lesconstructionssapete.stempyerp.core.automation.execution.AutomationManager;
import com.lesconstructionssapete.stempyerp.core.config.db.ConnectionPool;
import com.lesconstructionssapete.stempyerp.core.repository.base.automation.AutomationRepository;

public class RefreshJobSchedule extends Job implements JobExecutable {

  private final AutomationRepository automationRepository;

  public RefreshJobSchedule(Job baseJob, AutomationRepository automationRepository) {
    super(baseJob);
    this.automationRepository = automationRepository;
  }

  @Override
  public JobLog execute(JobLog log) {
    Connection connection = null;
    try {
      connection = ConnectionPool.getConnection();

      var jobs = automationRepository.findAll(connection);

      AutomationManager manager = AutomationManager.getInstance();
      manager.refresh(jobs);

      log.appendMessage("Refreshed " + jobs.size() + " jobs");

    } catch (SQLException e) {
      log.appendMessage("Failed to refresh jobs: " + e.getMessage());
    } finally {
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException e) {
        }
      }
    }
    return log;
  }

  @Override
  public Job meta() {
    return this;
  }

}
