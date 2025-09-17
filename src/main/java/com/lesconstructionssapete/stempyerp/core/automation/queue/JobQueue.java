package com.lesconstructionssapete.stempyerp.core.automation.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.lesconstructionssapete.stempyerp.core.automation.job.JobExecutable;

public class JobQueue {

  private final BlockingQueue<JobExecutable> queue = new LinkedBlockingQueue<>();
  private final Thread workerThread;

  public JobQueue() {
    workerThread = new Thread(this::processJobs, "JobWorker");
  }

  public void start() {
    workerThread.start();
  }

  public void add(JobExecutable job) {
    queue.offer(job);
  }

  private void processJobs() {
    try {
      while (true) {
        JobExecutable job = queue.take();
        try {
          job.execute();
        } catch (Exception e) {
          System.err.println("Job failed: " + job.getClass().getSimpleName());
        }
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
