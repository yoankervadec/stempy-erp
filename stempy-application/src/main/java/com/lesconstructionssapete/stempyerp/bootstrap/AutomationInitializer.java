package com.lesconstructionssapete.stempyerp.bootstrap;

import java.sql.Connection;

import com.lesconstructionssapete.stempyerp.automation.execution.AutomationManager;
import com.lesconstructionssapete.stempyerp.db.ConnectionProvider;
import com.lesconstructionssapete.stempyerp.infrastructure.persistence.repository.automation.AutomationRepositoryImpl;
import com.lesconstructionssapete.stempyerp.repository.AutomationRepository;

public class AutomationInitializer {

  public static void initialize(ConnectionProvider provider) {
    try (Connection con = provider.getConnection()) {

      AutomationRepository repo = new AutomationRepositoryImpl();
      var jobs = repo.fetchAll(con);

      AutomationManager.create(provider, jobs);
      AutomationManager manager = AutomationManager.getInstance();
      manager.start();

    } catch (Exception e) {
      throw new RuntimeException("Failed to initialize scheduler: ", e);
    }
  }
}
