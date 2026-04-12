package com.lesconstructionssapete.stempyerp.automation.definition;

import com.lesconstructionssapete.stempyerp.automation.Job;
import com.lesconstructionssapete.stempyerp.automation.JobLog;
import com.lesconstructionssapete.stempyerp.db.ConnectionProvider;

public interface JobExecutable {

  public JobLog execute(ConnectionProvider provider, JobLog log);

  public Job meta();

}