package com.lesconstructionssapete.stempyerp.app.bootstrap;

import java.sql.Connection;

import com.lesconstructionssapete.stempyerp.core.automation.execution.AutomationManager;
import com.lesconstructionssapete.stempyerp.core.config.db.ConnectionPool;
import com.lesconstructionssapete.stempyerp.core.repository.base.automation.AutomationRepository;
import com.lesconstructionssapete.stempyerp.core.repository.base.automation.AutomationRepositoryImpl;

public class AutomationInitializer {

  public static void initialize() {
    try (Connection con = ConnectionPool.getConnection()) {

      AutomationRepository repo = new AutomationRepositoryImpl();
      var jobs = repo.fetchAll(con);

      AutomationManager.create(jobs);
      AutomationManager manager = AutomationManager.getInstance();
      manager.start();

    } catch (Exception e) {
      throw new RuntimeException("Failed to initialize scheduler: ", e);
    }
  }
}
