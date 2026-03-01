package com.lesconstructionssapete.stempyerp.core.automation.execution;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.lesconstructionssapete.stempyerp.core.automation.definition.Job;
import com.lesconstructionssapete.stempyerp.core.automation.definition.JobExecutable;
import com.lesconstructionssapete.stempyerp.core.automation.definition.JobFactory;
import com.lesconstructionssapete.stempyerp.core.automation.scheduling.Scheduler;
import com.lesconstructionssapete.stempyerp.core.repository.base.automation.AutomationRepository;
import com.lesconstructionssapete.stempyerp.core.repository.base.automation.AutomationRepositoryImpl;

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
  public static synchronized AutomationManager create(List<Job> jobs) {
    if (instance == null) {
      instance = new AutomationManager(jobs);
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
  private AutomationManager(List<Job> jobs) {
    this.jobs = jobs;

    this.queue = new JobQueue();
    this.workerThread = new WorkerThread(queue);
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

    Map<Integer, Job> oldJobsMap = jobs.stream()
        .collect(Collectors.toMap(Job::getJobId, j -> j));

    Map<Integer, Job> newJobsMap = newJobs.stream()
        .collect(Collectors.toMap(Job::getJobId, j -> j));

    this.jobs = newJobs; // update after extracting old map

    // 1. Cancel jobs that were removed or disabled
    for (Job oldJob : oldJobsMap.values()) {
      Job updated = newJobsMap.get(oldJob.getJobId());
      if (updated == null || !updated.isEnabled()) {
        scheduler.cancelByJobId(oldJob.getJobId());
      }
    }

    // 2. Add or reschedule new or updated jobs
    for (Job newJob : newJobs) {
      if (!newJob.isEnabled())
        continue;

      Job oldJob = oldJobsMap.get(newJob.getJobId());
      boolean shouldSchedule = !scheduler.isScheduled(newJob.getJobId())
          || hasRelevantChanges(oldJob, newJob);

      if (shouldSchedule) {
        scheduler.cancelByJobId(newJob.getJobId());
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
        || !Objects.equals(oldJob.getRunTimes(), newJob.getRunTimes())
        || oldJob.isDeactivateOnFailure() != newJob.isDeactivateOnFailure()
        || oldJob.getRetriesOnFailure() != newJob.getRetriesOnFailure()
        || oldJob.isDeactivateOnFailure() != newJob.isDeactivateOnFailure();

  }

}