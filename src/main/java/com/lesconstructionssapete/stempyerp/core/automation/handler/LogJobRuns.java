package com.lesconstructionssapete.stempyerp.core.automation.handler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

import com.lesconstructionssapete.stempyerp.core.automation.definition.Job;
import com.lesconstructionssapete.stempyerp.core.automation.definition.JobExecutable;
import com.lesconstructionssapete.stempyerp.core.automation.definition.JobLog;
import com.lesconstructionssapete.stempyerp.core.config.db.ConnectionPool;
import com.lesconstructionssapete.stempyerp.core.repository.base.scheduler.AutomationRepository;

public class LogJobRuns extends Job implements JobExecutable {

  private final AutomationRepository automationRepository;

  public LogJobRuns(Job baseJob, AutomationRepository automationRepository) {
    super(baseJob);
    this.automationRepository = automationRepository;
  }

  private static final Queue<JobLog> logQueue = new ConcurrentLinkedDeque<>();
  private static final int MAX_BATCH_SIZE = 10;

  public static void enqueueLog(JobLog log) {
    logQueue.add(log);
  }

  public static Queue<JobLog> getLogs() {
    return logQueue;
  }

  @Override
  public JobLog execute(JobLog executionLog) {

    List<JobLog> batch = new ArrayList<>(MAX_BATCH_SIZE);

    while (!logQueue.isEmpty()) {
      batch.clear();

      for (int i = 0; i < MAX_BATCH_SIZE; i++) {
        JobLog queuedLog = logQueue.poll();
        if (queuedLog == null)
          break;
        batch.add(queuedLog);
      }

      if (!batch.isEmpty()) {
        // Batch insert
        try {
          Connection connection = ConnectionPool.getConnection();
          connection.setAutoCommit(false);
          automationRepository.batchLog(connection, batch);
          connection.commit();

        } catch (SQLException e) {
        }
      }
    }

    return executionLog;
  }

  @Override
  public Job meta() {
    return this;
  }

}
