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
import com.lesconstructionssapete.stempyerp.core.repository.base.automation.AutomationRepository;

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

    int totalLogs = logQueue.size();
    int totalBatches = (int) Math.ceil((double) totalLogs / MAX_BATCH_SIZE);

    executionLog.appendMessage(
        String.format("Queue length: %d, running %d batch%s...",
            totalLogs, totalBatches, totalBatches > 1 ? "es" : ""));

    List<JobLog> batch = new ArrayList<>(MAX_BATCH_SIZE);
    int batchCount = 0;

    while (!logQueue.isEmpty()) {
      batch.clear();

      for (int i = 0; i < MAX_BATCH_SIZE; i++) {
        JobLog queuedLog = logQueue.poll();
        if (queuedLog == null)
          break;
        batch.add(queuedLog);
      }

      batchCount++;
      executionLog.appendMessage(
          String.format("Processing batch %d/%d (%d records)", batchCount, totalBatches, batch.size()));

      if (!batch.isEmpty()) {
        executionLog.appendMessage("Persisting records...");

        try (Connection connection = ConnectionPool.getConnection()) {
          connection.setAutoCommit(false);

          automationRepository.batchLog(connection, batch);

          connection.commit();
        } catch (SQLException e) {
          executionLog.appendMessage("Error persisting batch: " + e.getMessage());
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
