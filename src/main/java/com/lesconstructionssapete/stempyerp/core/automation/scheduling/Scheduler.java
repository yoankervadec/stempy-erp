package com.lesconstructionssapete.stempyerp.core.automation.scheduling;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.lesconstructionssapete.stempyerp.core.automation.definition.JobExecutable;
import com.lesconstructionssapete.stempyerp.core.automation.execution.JobQueue;

public class Scheduler {

  private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
  private final JobQueue queue;

  public Scheduler(JobQueue queue) {
    this.queue = queue;
  }

  // schedule at fixed interval
  public void scheduleAtFixedRate(JobExecutable job, long initialDelay, long period, TimeUnit unit) {
    scheduler.scheduleAtFixedRate(() -> queue.add(job), initialDelay, period, unit);
  }

  // schedule once after delay
  public void scheduleOnce(JobExecutable job, long delay, TimeUnit unit) {
    scheduler.schedule(() -> queue.add(job), delay, unit);
  }

  // manual add
  public void addNow(JobExecutable job) {
    queue.add(job);
  }

  public void shutdown() {
    scheduler.shutdown();
  }

}
