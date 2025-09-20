package com.lesconstructionssapete.stempyerp.core.automation.handler;

import java.sql.Connection;
import java.sql.SQLException;

import com.lesconstructionssapete.stempyerp.core.automation.definition.Job;
import com.lesconstructionssapete.stempyerp.core.automation.definition.JobExecutable;
import com.lesconstructionssapete.stempyerp.core.automation.definition.JobLog;
import com.lesconstructionssapete.stempyerp.core.automation.execution.Manager;
import com.lesconstructionssapete.stempyerp.core.config.db.ConnectionPool;
import com.lesconstructionssapete.stempyerp.core.repository.base.scheduler.AutomationRepository;

public class RefreshJobSchedule extends Job implements JobExecutable {

  private final Manager manager;
  private final AutomationRepository repository;

  public RefreshJobSchedule(Job baseJob, Manager manager, AutomationRepository repository) {
    super(baseJob);
    this.manager = manager;
    this.repository = repository;
  }

  @Override
  public JobLog execute(JobLog log) {
    Connection connection = null;
    try {
      connection = ConnectionPool.getConnection();

      var jobs = repository.findAll(connection);
      manager.refresh(jobs);

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
