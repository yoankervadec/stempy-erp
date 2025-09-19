package com.lesconstructionssapete.stempyerp.core.automation.execution;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.lesconstructionssapete.stempyerp.core.automation.definition.JobExecutable;

public class JobQueue {

  private final BlockingQueue<JobExecutable> queue = new LinkedBlockingQueue<>();

  public void add(JobExecutable job) {
    queue.offer(job);
  }

  public JobExecutable take() throws InterruptedException {
    return queue.take();
  }

}
