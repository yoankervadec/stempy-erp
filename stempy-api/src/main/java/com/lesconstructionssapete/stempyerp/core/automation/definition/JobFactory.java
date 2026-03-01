package com.lesconstructionssapete.stempyerp.core.automation.definition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JobFactory {

  private final Map<Class<?>, Object> dependencies = new HashMap<>();
  private static final String JOB_PACKAGE = "com.lesconstructionssapete.stempyerp.core.automation.handler";

  public JobFactory register(Class<?> type, Object instance) {
    dependencies.put(type, instance);
    return this;
  }

  public JobExecutable create(Job baseJob) {
    try {
      // Build full class name from handler string
      String className = JOB_PACKAGE + "." + baseJob.getHandlerAsString();
      // Load class dynamically
      Class<?> clazz = Class.forName(className);

      if (!JobExecutable.class.isAssignableFrom(clazz)) {
        throw new IllegalArgumentException("Handler does not implement JobExecutable: " + className);
      }

      // Look for the constructor with the most parameters
      for (Constructor<?> constructor : clazz.getConstructors()) {
        Object[] args = buildArgs(constructor, baseJob);
        if (args != null) {
          return (JobExecutable) constructor.newInstance(args);
        }
      }
      throw new IllegalStateException("No suitable constructor found for " + className);

    } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | IllegalStateException
        | InstantiationException | SecurityException | InvocationTargetException e) {
      throw new RuntimeException("Failed to create job for handler: " + baseJob.getHandlerAsString(), e);

    }
  }

  private Object[] buildArgs(Constructor<?> constructor, Job baseJob) {
    List<Object> args = new ArrayList<>();
    for (Class<?> paramType : constructor.getParameterTypes()) {
      if (paramType.equals(Job.class)) {
        args.add(baseJob);
      } else {
        args.add(dependencies.get(paramType)); // resolve from map
      }
    }
    return args.toArray();
  }

}
