package com.lesconstructionssapete.stempyerp.automation.execution;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.lesconstructionssapete.stempyerp.automation.definition.JobExecutable;
import com.lesconstructionssapete.stempyerp.automation.definition.JobFactory;
import com.lesconstructionssapete.stempyerp.automation.scheduling.Scheduler;
import com.lesconstructionssapete.stempyerp.domain.automation.Job;
import com.lesconstructionssapete.stempyerp.domain.repository.AutomationRepository;
import com.lesconstructionssapete.stempyerp.infrastructure.persistence.repository.automation.AutomationRepositoryImpl;
import com.lesconstructionssapete.stempyerp.port.persistence.SQLConnectionProvider;

/**
 * Central manager responsible for controlling automation jobs within the
 * system.
 * <p>
 * The {@code AutomationManager} handles:
 * <ul>
 * <li>Singleton lifecycle – only one instance can exist at a time.</li>
 * <li>Job scheduling – delegates job execution timing to
 * {@link Scheduler}.</li>
 * <li>Job execution – jobs are queued into a background
 * {@link WorkerThread}.</li>
 * <li>Dependency injection – via {@link JobFactory} to build
 * {@link JobExecutable} instances.</li>
 * <li>Lifecycle control – starting, stopping, refreshing, and ad-hoc job
 * execution.</li>
 * </ul>
 *
 * Usage:
 * 
 * <pre>{@code
 * List<Job> jobs = repository.loadJobs();
 * AutomationManager manager = AutomationManager.create(jobs);
 * manager.start();
 * }</pre>
 */
public class AutomationManager {

  // ---- Singleton handling ----

  /** The single instance of the manager (lazy-initialized). */
  private static AutomationManager instance;

  /**
   * Creates the singleton instance if it does not already exist.
   *
   * @param jobs the initial set of jobs to manage
   * @return the singleton {@code AutomationManager} instance
   */
  public static synchronized AutomationManager create(SQLConnectionProvider provider, List<Job> jobs) {
    if (instance == null) {
      instance = new AutomationManager(provider, jobs);
    }
    return instance;
  }

  /**
   * Returns the singleton instance of the {@code AutomationManager}.
   *
   * @return the current manager instance
   * @throws IllegalStateException if {@link #create(List)} has not been called
   *                               first
   */
  public static AutomationManager getInstance() {
    if (instance == null) {
      throw new IllegalStateException("AutomationManager has not been created yet.");
    }
    return instance;
  }

  // ---- Instance fields ----

  /** Queue used to hold jobs awaiting execution. */
  private final JobQueue queue;

  /** Worker that continuously pulls jobs from the queue and executes them. */
  private final WorkerThread workerThread;

  /** Thread running the {@link WorkerThread}. */
  private final Thread thread;

  /** Scheduler for recurring and delayed job executions. */
  private final Scheduler scheduler;

  /**
   * Factory responsible for constructing {@link JobExecutable} instances with
   * dependencies.
   */
  private final JobFactory factory;

  /** The set of jobs currently known to the manager. */
  private List<Job> jobs;

  // ---- Constructor ----

  /**
   * Constructs a new {@code AutomationManager}.
   * <p>
   * This constructor is private because the manager is designed as a singleton.
   * Use {@link #create(List)} to obtain an instance.
   *
   * @param jobs initial set of jobs to manage
   */
  private AutomationManager(SQLConnectionProvider provider, List<Job> jobs) {
    this.jobs = jobs;

    this.queue = new JobQueue();
    this.workerThread = new WorkerThread(provider, queue);
    this.thread = new Thread(workerThread, "JobWorker");
    this.scheduler = new Scheduler(queue);

    // Register dependencies in the JobFactory
    this.factory = new JobFactory()
        .register(AutomationRepository.class, new AutomationRepositoryImpl());
  }

  // ---- Lifecycle ----

  /**
   * Starts the automation system:
   * <ul>
   * <li>Spawns the worker thread for executing queued jobs.</li>
   * <li>Schedules all enabled jobs from the initial job list.</li>
   * </ul>
   */
  public void start() {
    thread.start();

    for (Job job : jobs) {
      if (!job.isEnabled()) {
        continue; // Skip disabled jobs
      }

      job.updateNextRun(); // Ensure next run is calculated

      JobExecutable executable = factory.create(job);
      scheduler.schedule(executable);
    }
  }

  /**
   * Stops the automation system:
   * <ul>
   * <li>Shuts down the scheduler (cancels all futures).</li>
   * <li>Stops the worker thread safely.</li>
   * </ul>
   */
  public void stop() {
    scheduler.shutdown();
    workerThread.stop();
    thread.interrupt();
  }

  /**
   * Immediately queues a job for execution, bypassing its schedule.
   *
   * @param job the job definition to execute immediately
   */
  public void runNow(Job job) {
    JobExecutable executable = factory.create(job);
    scheduler.queueNow(executable);
  }

  /**
   * Refreshes the set of jobs:
   * <ul>
   * <li>Removes jobs that no longer exist or are disabled.</li>
   * <li>Adds or re-schedules jobs that are new or updated.</li>
   * </ul>
   *
   * @param newJobs the updated list of jobs from persistence
   */
  public synchronized void refresh(List<Job> newJobs) {

    Map<Long, Job> oldJobsMap = jobs.stream()
        .collect(Collectors.toMap(Job::getId, j -> j));

    Map<Long, Job> newJobsMap = newJobs.stream()
        .collect(Collectors.toMap(Job::getId, j -> j));

    this.jobs = newJobs; // update after extracting old map

    // 1. Cancel jobs that were removed or disabled
    for (Job oldJob : oldJobsMap.values()) {
      Job updated = newJobsMap.get(oldJob.getId());
      if (updated == null || !updated.isEnabled()) {
        scheduler.cancelByJobId(oldJob.getId());
      }
    }

    // 2. Add or reschedule new or updated jobs
    for (Job newJob : newJobs) {
      if (!newJob.isEnabled())
        continue;

      Job oldJob = oldJobsMap.get(newJob.getId());
      boolean shouldSchedule = !scheduler.isScheduled(newJob.getId())
          || hasRelevantChanges(oldJob, newJob);

      if (shouldSchedule) {
        scheduler.cancelByJobId(newJob.getId());
        scheduler.schedule(factory.create(newJob));
      }
    }
  }

  /**
   * Prints all currently scheduled jobs to the standard output.
   * <p>
   * Primarily for debugging purposes.
   */
  public void printScheduledJobs() {
    scheduler.printFutures();
  }

  private boolean hasRelevantChanges(Job oldJob, Job newJob) {
    if (oldJob == null)
      return true;

    return oldJob.isEnabled() != newJob.isEnabled()
        || oldJob.isActive() != newJob.isActive()
        || !Objects.equals(oldJob.getIntervalMinutes(), newJob.getIntervalMinutes())
        || !Objects.equals(oldJob.getRunTimesUTC(), newJob.getRunTimesUTC())
        || oldJob.isDeactivateOnFailure() != newJob.isDeactivateOnFailure()
        || oldJob.getMaxRetries() != newJob.getMaxRetries()
        || oldJob.isDeactivateOnFailure() != newJob.isDeactivateOnFailure();

  }

}