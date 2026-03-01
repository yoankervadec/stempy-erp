package com.lesconstructionssapete.stempyerp.bootstrap;

import java.sql.Connection;

import com.lesconstructionssapete.stempyerp.automation.execution.AutomationManager;
import com.lesconstructionssapete.stempyerp.config.db.ConnectionPool;
import com.lesconstructionssapete.stempyerp.repository.AutomationRepository;
import com.lesconstructionssapete.stempyerp.repository.AutomationRepositoryImpl;

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
