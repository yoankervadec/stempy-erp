package com.lesconstructionssapete.stempyerp.automation.definition;

import com.lesconstructionssapete.stempyerp.domain.automation.Job;
import com.lesconstructionssapete.stempyerp.domain.automation.JobLog;
import com.lesconstructionssapete.stempyerp.port.persistence.SQLConnectionProvider;

public interface JobExecutable {

  public JobLog execute(SQLConnectionProvider provider, JobLog log);

  public Job meta();

}