package com.lesconstructionssapete.stempyerp.core.automation.handler;

import com.lesconstructionssapete.stempyerp.core.automation.definition.Job;
import com.lesconstructionssapete.stempyerp.core.automation.definition.JobExecutable;
import com.lesconstructionssapete.stempyerp.core.automation.definition.JobLog;

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
