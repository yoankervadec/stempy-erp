package com.lesconstructionssapete.stempyerp.core.automation.execution;

import com.lesconstructionssapete.stempyerp.core.automation.definition.JobExecutable;

public class JobWorker implements Runnable {

  private final JobQueue queue;
  private volatile boolean running = true;

  public JobWorker(JobQueue queue) {
    this.queue = queue;
  }

  @Override
  @SuppressWarnings("CallToPrintStackTrace")
  public void run() {
    while (running && !Thread.currentThread().isInterrupted()) {
      try {
        JobExecutable job = queue.take(); // blocks until a job arrives
        job.execute();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        running = false;
      } catch (Exception e) {
        System.err.println("Job execution failed: " + e.getMessage());
        e.printStackTrace();
      }
    }
  }

  public void stop() {
    running = false;
  }

}
