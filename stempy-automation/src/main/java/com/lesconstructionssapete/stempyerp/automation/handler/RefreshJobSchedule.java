package com.lesconstructionssapete.stempyerp.automation.handler;

import java.sql.Connection;
import java.sql.SQLException;

import com.lesconstructionssapete.stempyerp.automation.Job;
import com.lesconstructionssapete.stempyerp.automation.JobLog;
import com.lesconstructionssapete.stempyerp.automation.definition.JobExecutable;
import com.lesconstructionssapete.stempyerp.automation.execution.AutomationManager;
import com.lesconstructionssapete.stempyerp.db.ConnectionProvider;
import com.lesconstructionssapete.stempyerp.repository.AutomationRepository;

public class RefreshJobSchedule extends Job implements JobExecutable {

  private final AutomationRepository automationRepository;

  public RefreshJobSchedule(Job baseJob, AutomationRepository automationRepository) {
    super(baseJob);
    this.automationRepository = automationRepository;
  }

  @Override
  public JobLog execute(ConnectionProvider provider, JobLog log) {
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
