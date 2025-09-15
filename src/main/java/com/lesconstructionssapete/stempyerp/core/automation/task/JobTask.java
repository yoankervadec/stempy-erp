package com.lesconstructionssapete.stempyerp.core.automation.task;

import java.util.TimerTask;

import com.lesconstructionssapete.stempyerp.core.automation.job.Job;

/**
 * Adapter around Job to make it run as a TimerTask.
 */
public class JobTask extends TimerTask {

  private final Job job;

  public JobTask(Job job) {
    this.job = job;
  }

  @Override
  public void run() {
    try {
      job.execute();
    } catch (Exception e) {
      System.err.println("Job " + job.getJobName() + " failed: " + e.getMessage());
    }

  }

}
