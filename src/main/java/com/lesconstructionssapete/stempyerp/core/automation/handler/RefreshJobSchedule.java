package com.lesconstructionssapete.stempyerp.core.automation.handler;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.lesconstructionssapete.stempyerp.core.automation.definition.Job;
import com.lesconstructionssapete.stempyerp.core.automation.definition.JobExecutable;

public class RefreshJobSchedule extends Job implements JobExecutable {

  public RefreshJobSchedule(
      int jobId,
      String jobName,
      String jobDescription,
      String handlerAString,
      boolean isActive,
      boolean deactivateOnFailure,
      int retriesOnFailure,
      Double intervalMinutes,
      List<LocalTime> runTimes,
      LocalDateTime lastRun,
      LocalDateTime nextRun,
      int priority,
      boolean isEnabled) {
    super(
        jobId,
        jobName,
        jobDescription,
        handlerAString,
        isActive,
        deactivateOnFailure,
        retriesOnFailure,
        intervalMinutes,
        runTimes,
        lastRun,
        nextRun,
        priority,
        isEnabled);
  }

  @Override
  public void execute() {
    System.out.println("REFRESH JOB SCHEDULE");

  }

}
