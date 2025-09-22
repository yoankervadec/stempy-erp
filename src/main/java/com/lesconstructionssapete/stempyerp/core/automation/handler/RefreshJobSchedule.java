package com.lesconstructionssapete.stempyerp.core.automation.handler;

import java.sql.Connection;
import java.sql.SQLException;

import com.lesconstructionssapete.stempyerp.core.automation.definition.Job;
import com.lesconstructionssapete.stempyerp.core.automation.definition.JobExecutable;
import com.lesconstructionssapete.stempyerp.core.automation.definition.JobLog;
import com.lesconstructionssapete.stempyerp.core.automation.execution.Manager;
import com.lesconstructionssapete.stempyerp.core.config.db.ConnectionPool;
import com.lesconstructionssapete.stempyerp.core.repository.base.automation.AutomationRepository;

public class RefreshJobSchedule extends Job implements JobExecutable {

  private final Manager manager;
  private final AutomationRepository automationRepository;

  public RefreshJobSchedule(Job baseJob, Manager manager, AutomationRepository automationRepository) {
    super(baseJob);
    this.manager = manager;
    this.automationRepository = automationRepository;
  }

  @Override
  public JobLog execute(JobLog log) {
    Connection connection = null;
    try {
      connection = ConnectionPool.getConnection();

      var jobs = automationRepository.findAll(connection);
      manager.refresh(jobs);

      log.appendMessage("Refreshed " + jobs.size() + " jobs");

    } catch (SQLException e) {
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
