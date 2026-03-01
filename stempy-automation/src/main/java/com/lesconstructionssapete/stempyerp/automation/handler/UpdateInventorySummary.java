package com.lesconstructionssapete.stempyerp.automation.handler;

import com.lesconstructionssapete.stempyerp.domain.base.automation.Job;
import com.lesconstructionssapete.stempyerp.domain.base.automation.JobExecutable;
import com.lesconstructionssapete.stempyerp.domain.base.automation.JobLog;

public class UpdateInventorySummary extends Job implements JobExecutable {

  public UpdateInventorySummary(Job job) {
    super(job);
  }

  @Override
  public JobLog execute(JobLog log) {
    System.out.println("UPDATING INVENTORY SUMMARY");
    return log;
  }

  @Override
  public Job meta() {
    return this;
  }

}
