package com.lesconstructionssapete.stempyerp.core.automation.execution;

import java.time.LocalDateTime;

import com.lesconstructionssapete.stempyerp.core.automation.definition.JobExecutable;
import com.lesconstructionssapete.stempyerp.core.automation.definition.JobLog;

public class JobWorker implements Runnable {

  private final JobQueue queue;
  private volatile boolean running = true;

  public JobWorker(JobQueue queue) {
    this.queue = queue;
  }

  @Override
  public void run() {
    while (running && !Thread.currentThread().isInterrupted()) {
      try {
        JobExecutable executable = queue.take(); // blocks until a job arrives
        executeAndLog(executable);

      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        running = false;
      } catch (Exception e) {
        System.err.println("Job execution failed: " + e.getMessage());
      }
    }
  }

  public void stop() {
    running = false;
  }

  private void executeAndLog(JobExecutable job) throws InterruptedException {

    int attempt = 0;
    int maxAttempts = job.meta().getRetriesOnFailure(); // 0 = only one try

    while (attempt <= maxAttempts) {
      var log = new JobLog(job.meta().getJobId());

      try {
        // Execute
        log = job.execute(log);
        // Log
        log.setError(false);
        log.setMessage("ran");
        // Exit
        return;
      } catch (Exception e1) {
        // Log
        log.setError(true);
        log.setMessage("error");

        attempt++;
        if (attempt >= maxAttempts) {
          job.meta().setActive(false);
          log.setMessage("failed");
          return;
        }

        Thread.sleep(1000L * attempt);

      } finally {
        log.setEndedAt(LocalDateTime.now());
        log.save();
      }
    }
  }

}
