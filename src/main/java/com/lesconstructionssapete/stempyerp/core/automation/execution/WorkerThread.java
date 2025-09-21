package com.lesconstructionssapete.stempyerp.core.automation.execution;

import java.time.LocalDateTime;

import com.lesconstructionssapete.stempyerp.core.automation.definition.JobExecutable;
import com.lesconstructionssapete.stempyerp.core.automation.definition.JobLog;

public class WorkerThread implements Runnable {

  private final JobQueue queue;
  private volatile boolean running = true;

  public WorkerThread(JobQueue queue) {
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
        log.setMessage("Executing: " + job.meta().getJobName() + "...");
        log = job.execute(log);

        log.markSuccess(job.meta().getJobName());

        return;
      } catch (Exception e1) {

        log.markFailure(job.meta().getJobName(), e1, attempt, maxAttempts);

        attempt++;
        if (attempt >= maxAttempts) {
          job.meta().setActive(false);
          log.markFinalFailure(job.meta().getJobName(), e1);
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
