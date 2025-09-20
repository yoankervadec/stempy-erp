package com.lesconstructionssapete.stempyerp.core.automation.definition;

public interface JobExecutable {

  public JobLog execute(JobLog log);

  public Job meta();

}