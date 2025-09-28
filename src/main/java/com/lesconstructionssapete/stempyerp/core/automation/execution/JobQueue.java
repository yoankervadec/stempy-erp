package com.lesconstructionssapete.stempyerp.core.automation.execution;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.lesconstructionssapete.stempyerp.core.automation.definition.JobExecutable;

/**
 * Thread-safe queue for managing job execution requests.
 * <p>
 * The {@code JobQueue} uses a blocking queue to hold
 * {@link JobExecutable} instances that are ready for execution.
 * Worker thread(s) can take jobs from the queue, blocking if none are
 * available.
 * <p>
 * This class provides methods to add jobs to the queue and to take jobs
 * from the queue in a thread-safe manner.
 */
public class JobQueue {

  private final BlockingQueue<JobExecutable> queue = new LinkedBlockingQueue<>();

  /**
   * Adds a job executable to the queue for execution.
   * 
   * @param job the job executable to add
   */
  public void add(JobExecutable job) {
    queue.offer(job);
  }

  /**
   * Takes and removes the next job executable from the queue,
   * blocking if necessary until one becomes available.
   * 
   * @return the next job executable from the queue
   * @throws InterruptedException if interrupted while waiting
   */
  public JobExecutable take() throws InterruptedException {
    return queue.take();
  }

}
