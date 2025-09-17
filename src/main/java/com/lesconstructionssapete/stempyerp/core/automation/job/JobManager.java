package com.lesconstructionssapete.stempyerp.core.automation.job;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.lesconstructionssapete.stempyerp.core.automation.queue.JobQueue;

public class JobManager {

  private final List<JobExecutable> jobs = new ArrayList<>();
  private final JobQueue queue;

  public JobManager(JobQueue queue) {
    this.queue = queue;
  }

  public void loadJobs(List<Job> baseJobs) {
    jobs.clear();
    for (Job baseJob : baseJobs) {
      JobExecutable executable = JobFactory.create(baseJob);
      jobs.add(executable);
    }
  }

  /**
   * Enqueue jobs that should run based on intervalMinutes
   */
  public void enqueueIntervalJobs() {
    for (JobExecutable job : jobs) {
      Job baseJob = (Job) job;
      Double interval = baseJob.getIntervalMinutes();

      if (interval != null) {
        // TODO: check if enough time has passed since lastRun
        // if yes -> queue.add(job)
      }
    }
  }

  /**
   * Enqueue jobs that should run at fixed times of the day.
   */
  public void enqueueFixedTimeJobs() {
    LocalTime now = LocalTime.now();

    for (JobExecutable job : jobs) {
      Job baseJob = (Job) job;

      if (baseJob.getRunTimes() != null && !baseJob.getRunTimes().isEmpty()) {
        // TODO: check if now matches one of the runTimes (with some tolerance)
        // if yes -> queue.add(job)
      }
    }
  }

  /**
   * For debugging: see what jobs are currently loaded.
   */
  public List<JobExecutable> getJobs() {
    return jobs;
  }

}
