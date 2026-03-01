package com.lesconstructionssapete.stempyerp.domain.base.automation;

public interface JobExecutable {

  public JobLog execute(JobLog log);

  public Job meta();

}