package com.lesconstructionssapete.stempyerp.core.automation.queue;

import com.lesconstructionssapete.stempyerp.core.automation.job.Job;

public class JobDispatcher implements Runnable {

  private final JobQueue queue;
  private volatile boolean running = true;

  public JobDispatcher(JobQueue queue) {
    this.queue = queue;
  }

  @Override
  public void run() {
    while (running) {
      try {
        Job job = queue.take();
        job.execute();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      } catch (Exception e) {
        System.err.println("Dispatched job failed: " + e.getMessage());
      }
    }
  }

  public void stop() {
    running = false;
  }
}
