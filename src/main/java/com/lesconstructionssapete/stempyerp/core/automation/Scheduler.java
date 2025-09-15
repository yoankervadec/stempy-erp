package com.lesconstructionssapete.stempyerp.core.automation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lesconstructionssapete.stempyerp.core.automation.job.Job;
import com.lesconstructionssapete.stempyerp.core.automation.queue.JobDispatcher;
import com.lesconstructionssapete.stempyerp.core.automation.queue.JobQueue;
import com.lesconstructionssapete.stempyerp.core.automation.task.JobTask;
import com.lesconstructionssapete.stempyerp.core.config.db.ConnectionPool;
import com.lesconstructionssapete.stempyerp.core.repository.base.scheduler.SchedulerRepository;
import com.lesconstructionssapete.stempyerp.core.repository.base.scheduler.SchedulerRepositoryImpl;

public class Scheduler {

  private static final long INITIAL_DELAY = 30_000;

  private final Timer timer = new Timer("scheduler-timer", true);
  private final JobQueue jobQueue = new JobQueue();
  private final ExecutorService dispatcherExecutor = Executors.newSingleThreadExecutor();

  public Scheduler() {
    dispatcherExecutor.submit(new JobDispatcher(jobQueue));
  }

  public void scheduleAtFixRate(Job job) {
    timer.scheduleAtFixedRate(new JobTask(job), INITIAL_DELAY, job.getInterval().toMillis());
  }

  public void enqueue(Job job) {
    jobQueue.enqueue(job);
  }

  public void init() {
    try {

      Connection connection = ConnectionPool.getConnection();
      SchedulerRepository repo = new SchedulerRepositoryImpl();

      var jobs = repo.findAll(connection);

    } catch (SQLException e) {
    }
  }

  public void shutdown() {
    timer.cancel();
    dispatcherExecutor.shutdown();
  }

}
