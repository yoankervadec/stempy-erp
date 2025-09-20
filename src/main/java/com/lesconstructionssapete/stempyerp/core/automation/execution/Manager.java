package com.lesconstructionssapete.stempyerp.core.automation.execution;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.lesconstructionssapete.stempyerp.core.automation.definition.Job;
import com.lesconstructionssapete.stempyerp.core.automation.definition.JobExecutable;
import com.lesconstructionssapete.stempyerp.core.automation.definition.JobFactory;
import com.lesconstructionssapete.stempyerp.core.automation.scheduling.Scheduler;

public class Manager {

  private final JobQueue queue = new JobQueue();
  private final JobWorker worker = new JobWorker(queue);
  private final Thread workerThread = new Thread(worker, "JobWorker");
  private final Scheduler scheduler = new Scheduler(queue);

  private final List<Job> jobs;

  public Manager(List<Job> jobs) {
    this.jobs = jobs;
  }

  public void start() {
    // Start the worker thread
    workerThread.start();

    for (Job job : jobs) {
      if (!job.isEnabled()) {
        continue;
      }

      // Convert Job to concrete implementation
      JobExecutable executable = JobFactory.create(job);

      // Determine scheduling strategy
      if (job.getIntervalMinutes() != null && job.getIntervalMinutes() != 0) {
        // Schedule immediately
        scheduler.scheduleAtFixedRate(
            executable,
            0,
            job.getInterval().toMillis(),
            TimeUnit.MILLISECONDS);
      } else if (job.getRunTimes() != null && !job.getRunTimes().isEmpty()) {
        // Schedule once at next run time
        scheduler.scheduleOnce(
            executable,
            computeDelayUntil(job.calculateNextRun()),
            TimeUnit.SECONDS);
      }
    }

  }

  public void stop() {
    scheduler.shutdown();
    worker.stop();
    workerThread.interrupt();
  }

  // Manual job trigger
  public void runNow(Job job) {
    JobExecutable executable = JobFactory.create(job);
    scheduler.addNow(executable);
  }

  private long computeDelayUntil(java.time.LocalDateTime nextRun) {
    return java.time.Duration.between(java.time.LocalDateTime.now(), nextRun).getSeconds();
  }

}
