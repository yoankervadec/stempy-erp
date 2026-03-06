package com.lesconstructionssapete.stempyerp.automation.definition;

import com.lesconstructionssapete.stempyerp.db.ConnectionProvider;
import com.lesconstructionssapete.stempyerp.domain.automation.Job;
import com.lesconstructionssapete.stempyerp.domain.automation.JobLog;

public interface JobExecutable {

  public JobLog execute(ConnectionProvider provider, JobLog log);

  public Job meta();

}