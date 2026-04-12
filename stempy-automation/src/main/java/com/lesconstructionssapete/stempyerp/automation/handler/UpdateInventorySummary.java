package com.lesconstructionssapete.stempyerp.automation.handler;

import com.lesconstructionssapete.stempyerp.automation.Job;
import com.lesconstructionssapete.stempyerp.automation.JobLog;
import com.lesconstructionssapete.stempyerp.automation.definition.JobExecutable;
import com.lesconstructionssapete.stempyerp.db.ConnectionProvider;

public class UpdateInventorySummary extends Job implements JobExecutable {

  public UpdateInventorySummary(Job job) {
    super(job);
  }

  @Override
  public JobLog execute(ConnectionProvider provider, JobLog log) {
    System.out.println("UPDATING INVENTORY SUMMARY");
    return log;
  }

  @Override
  public Job meta() {
    return this;
  }

}
