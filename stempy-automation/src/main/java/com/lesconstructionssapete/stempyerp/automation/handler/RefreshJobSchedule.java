package com.lesconstructionssapete.stempyerp.automation.handler;

import java.sql.Connection;
import java.sql.SQLException;

import com.lesconstructionssapete.stempyerp.automation.definition.JobExecutable;
import com.lesconstructionssapete.stempyerp.automation.execution.AutomationManager;
import com.lesconstructionssapete.stempyerp.domain.automation.Job;
import com.lesconstructionssapete.stempyerp.domain.automation.JobLog;
import com.lesconstructionssapete.stempyerp.domain.repository.AutomationRepository;
import com.lesconstructionssapete.stempyerp.port.persistence.SQLConnectionProvider;

public class RefreshJobSchedule extends Job implements JobExecutable {

  private final AutomationRepository automationRepository;

  public RefreshJobSchedule(Job baseJob, AutomationRepository automationRepository) {
    super(baseJob);
    this.automationRepository = automationRepository;
  }

  @Override
  public JobLog execute(SQLConnectionProvider provider, JobLog log) {
    Connection connection = null;
    try {
      connection = provider.getConnection();

      var jobs = automationRepository.fetchAll(connection);

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
