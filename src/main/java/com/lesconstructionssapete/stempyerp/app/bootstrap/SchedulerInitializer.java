package com.lesconstructionssapete.stempyerp.app.bootstrap;

import java.sql.Connection;

import com.lesconstructionssapete.stempyerp.core.automation.execution.Manager;
import com.lesconstructionssapete.stempyerp.core.config.db.ConnectionPool;
import com.lesconstructionssapete.stempyerp.core.repository.base.scheduler.AutomationRepository;
import com.lesconstructionssapete.stempyerp.core.repository.base.scheduler.AutomationRepositoryImpl;

public class SchedulerInitializer {

  public static void initialize() {
    try (Connection con = ConnectionPool.getConnection()) {

      AutomationRepository repo = new AutomationRepositoryImpl();
      var jobs = repo.findAll(con);

      Manager manager = new Manager(jobs);
      manager.start();

    } catch (Exception e) {
      throw new RuntimeException("Failed to initialize scheduler: ", e);
    }
  }
}
