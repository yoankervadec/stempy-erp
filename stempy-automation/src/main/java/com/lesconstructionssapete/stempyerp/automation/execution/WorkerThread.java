package com.lesconstructionssapete.stempyerp.automation.execution;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lesconstructionssapete.stempyerp.automation.definition.JobExecutable;
import com.lesconstructionssapete.stempyerp.automation.handler.LogJobRuns;
import com.lesconstructionssapete.stempyerp.domain.automation.JobLog;
import com.lesconstructionssapete.stempyerp.port.persistence.SQLConnectionProvider;

public class WorkerThread implements Runnable {

  private static final Logger LOGGER = LoggerFactory.getLogger(WorkerThread.class);

  private final SQLConnectionProvider provider;
  private final JobQueue queue;
  private volatile boolean running = true;

  public WorkerThread(SQLConnectionProvider provider, JobQueue queue) {
    this.provider = provider;
    this.queue = queue;
  }

  /**
   * Continuously processes jobs from the queue until stopped.
   * Each job is executed with retry logic and logging.
   */
  @Override
  public void run() {
    while (running && !Thread.currentThread().isInterrupted()) {
      try {
        JobExecutable executable = queue.take(); // blocks until a job arrives

        LOGGER.info(
            "{} ({}) executing at {}",
            executable.meta().getName(),
            executable.meta().getId(),
            Instant.now().truncatedTo(ChronoUnit.SECONDS));

        executeAndLog(executable);

      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        running = false;
      } catch (Exception e) {
        LOGGER.error("Job execution failed: {}", e.getMessage(), e);
      }
    }
  }

  public void stop() {
    running = false;
  }

  /**
   * Executes the given job executable with retry logic and logging.
   * If the job fails, it will be retried up to its configured number of retries.
   * After exhausting retries, if configured, the job will be deactivated.
   * 
   * @param executable the job executable to run
   * @throws InterruptedException if the thread is interrupted during execution
   */

  @SuppressWarnings("BusyWait") // Sleep is intentional for retry backoff
  private void executeAndLog(JobExecutable executable) throws InterruptedException {

    int attempt = 0;
    final int maxAttempts = executable.meta().getMaxRetries(); // 0 = only one try

    while (attempt <= maxAttempts) {
      var log = new JobLog(executable.meta().getId());

      try {
        log.setMessage("Executing: " + executable.meta().getName() + "...");

        log = executable.execute(provider, log);

        log.markSuccess(executable.meta().getName());

        return;
      } catch (Exception e1) {

        log.markFailure(executable.meta().getName(), e1, attempt, maxAttempts);

        attempt++;
        if (attempt >= maxAttempts) {
          executable.meta().setActive(false); // Deactivate job after final failure
          log.markFinalFailure(executable.meta().getName(), e1);
          return;
        }

        Thread.sleep(1000L * attempt);

      } finally {
        log.setEndedAt(Instant.now());
        log.save();
        LogJobRuns.enqueueLog(log);
      }
    }
  }

}
