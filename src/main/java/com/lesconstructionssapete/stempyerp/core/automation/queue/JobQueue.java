package com.lesconstructionssapete.stempyerp.core.automation.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import com.lesconstructionssapete.stempyerp.core.automation.job.Job;

public class JobQueue {

  private final BlockingQueue<Job> queue = new LinkedBlockingDeque<>();

  public void enqueue(Job job) {
    queue.offer(job);
  }

  public Job take() throws InterruptedException {
    return queue.take();
  }

}
