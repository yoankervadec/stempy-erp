package com.lesconstructionssapete.stempyerp.core.automation.job;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class JobFactory {

  private static final String JOB_PACKAGE = "com.lesconstructionssapete.stempyerp.core.automation.job";

  public static JobExecutable create(Job baseJob) {
    try {
      // Build full class name from handler string
      String className = JOB_PACKAGE + "." + baseJob.getHandlerAsString();

      // Load class dynamically
      Class<?> clazz = Class.forName(className);

      if (!JobExecutable.class.isAssignableFrom(clazz)) {
        throw new IllegalArgumentException("Handler does not implement JobExecutable: " + className);
      }

      // Find matching constructor (same signature as Job)
      Constructor<?> constructor = clazz.getConstructor(
          int.class,
          String.class,
          String.class,
          String.class,
          boolean.class,
          boolean.class,
          int.class,
          Double.class,
          java.util.List.class,
          java.time.LocalDateTime.class,
          java.time.LocalDateTime.class,
          int.class,
          boolean.class);

      // Instantiate job
      return (JobExecutable) constructor.newInstance(
          baseJob.getJobId(),
          baseJob.getJobName(),
          baseJob.getJobDescription(),
          baseJob.getHandlerAsString(),
          baseJob.isActive(),
          baseJob.isDeactivateOnFailure(),
          baseJob.getRetriesOnFailure(),
          baseJob.getIntervalMinutes(),
          baseJob.getRunTimes(),
          baseJob.getLastRun(),
          baseJob.getNextRun(),
          baseJob.getPriority(),
          baseJob.isEnabled());

    } catch (
        ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException
        | NoSuchMethodException | SecurityException | InvocationTargetException e) {
      throw new RuntimeException("Failed to create job for handler: " + baseJob.getHandlerAsString());
    }
  }
}
