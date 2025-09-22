package com.lesconstructionssapete.stempyerp.core.automation.execution;

import java.util.List;

import com.lesconstructionssapete.stempyerp.core.automation.definition.Job;
import com.lesconstructionssapete.stempyerp.core.automation.definition.JobExecutable;
import com.lesconstructionssapete.stempyerp.core.automation.definition.JobFactory;
import com.lesconstructionssapete.stempyerp.core.automation.scheduling.Scheduler;
import com.lesconstructionssapete.stempyerp.core.repository.base.automation.AutomationRepository;
import com.lesconstructionssapete.stempyerp.core.repository.base.automation.AutomationRepositoryImpl;

public class Manager {

  private final JobQueue queue = new JobQueue();
  private final WorkerThread worker = new WorkerThread(queue);
  private final Thread workerThread = new Thread(worker, "JobWorker");
  private final Scheduler scheduler = new Scheduler(queue);
  private final JobFactory factory = new JobFactory()
      .register(AutomationRepository.class, new AutomationRepositoryImpl())
      .register(Manager.class, this);

  private List<Job> jobs;

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

      // Convert Job to concrete executable implementation with dependencies
      JobExecutable executable = factory.create(job);

      scheduler.schedule(executable);
    }
  }

  public void stop() {
    scheduler.shutdown();
    worker.stop();
    workerThread.interrupt();
  }

  // Manual job trigger
  public void runNow(Job job) {
    JobExecutable executable = factory.create(job);
    scheduler.queueNow(executable);
  }

  // Refresh jobs
  public synchronized void refresh(List<Job> jobs) {
    this.jobs = jobs;
    // optionally re-schedule here if jobs changed
  }

}