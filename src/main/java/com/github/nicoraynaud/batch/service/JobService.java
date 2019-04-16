package com.github.nicoraynaud.batch.service;

import com.github.nicoraynaud.batch.exception.JobBadArgumentException;
import com.github.nicoraynaud.batch.exception.JobException;
import com.github.nicoraynaud.batch.listener.AddListenerToJobService;
import com.github.nicoraynaud.batch.repository.JobExecutionEntityRepository;
import com.github.nicoraynaud.batch.config.BatchProperties;
import com.github.nicoraynaud.batch.domain.JobDescription;
import com.github.nicoraynaud.batch.domain.JobExecutionParameter;
import com.github.nicoraynaud.batch.domain.entity.JobExecutionEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.ListableJobLocator;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.job.AbstractJob;
import org.springframework.batch.core.launch.JobExecutionNotRunningException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JobService {

    private final Logger log = LoggerFactory.getLogger(JobService.class);

    private final JobLauncher jobLauncher;
    private final AddListenerToJobService addListenerToJobService;
    private final JobExecutionEntityRepository jobExecutionEntityRepository;

    private final JobOperator jobOperator;
    private final JobExplorer jobExplorer;
    private final ListableJobLocator jobRegistry;

    private final BatchProperties batchProperties;

    public JobService(BatchProperties batchProperties, JobOperator jobOperator, JobExplorer jobExplorer,
                      ListableJobLocator jobRegistry, JobLauncher jobLauncher, AddListenerToJobService addListenerToJobService,
                      JobExecutionEntityRepository jobExecutionEntityRepository) {
        this.batchProperties = batchProperties;
        this.jobOperator = jobOperator;
        this.jobExplorer = jobExplorer;
        this.jobRegistry = jobRegistry;
        this.jobLauncher = jobLauncher;
        this.addListenerToJobService = addListenerToJobService;
        this.jobExecutionEntityRepository = jobExecutionEntityRepository;
    }

    public List<JobDescription> getJobs(String jobName) {
        if(jobName != null ) {
            return jobOperator.getJobNames().stream().filter(e -> StringUtils.containsIgnoreCase(e,jobName)).map(this::findOrCreateJobDescription).collect(Collectors.toList());
        }
        return jobOperator.getJobNames().stream().map(this::findOrCreateJobDescription).collect(Collectors.toList());
    }

    public JobDescription getJob(String name) {
        for (String jobName : jobOperator.getJobNames()) {
            if (jobName.equals(name)) {
                return findOrCreateJobDescription(name);
            }
        }
        throw new JobBadArgumentException(String.format("Aucun job n'a été trouvé avec le nom '%s'", name));
    }

    /**
     * Récupère le dernier job execution d'un job
     */
    public JobExecutionEntity getLastJobExecution(String jobName) {
        return jobExecutionEntityRepository.getLastJobExecution(jobName);
    }

    private JobDescription findOrCreateJobDescription(String name) {
        JobDescription jobDescription = batchProperties.getJob(name);
        if (jobDescription != null) {
            return jobDescription;
        }
        return new JobDescription(name, name);
    }

    /**
     * Lance un Job, celui-ci sera lancé de façon asynchrone
     * L'appel de la méthode ne doit pas etre lancé dans un contexte transactionnel
     */
    @Transactional(propagation = Propagation.NEVER)
    public JobExecution start(String jobName, List<JobExecutionParameter> parameters) {
        try {
            log.debug("REST request to start : {}", jobName);
            Job job = jobRegistry.getJob(jobName);
            if (job instanceof AbstractJob) {
                addListenerToJobService.addListenerToJob((AbstractJob) job);
            }

            Long jobExecutionId = jobLauncher.run(job, getJobParameters(jobName, parameters)).getId();
            return jobExplorer.getJobExecution(jobExecutionId);
        } catch (Exception e) {
            throw new JobException("Erreur lors du lancement du job " + jobName, e);
        }
    }

    private JobParameters getJobParameters(String jobName, List<JobExecutionParameter> parameters) throws NoSuchJobException {

        JobParametersBuilder builder = new JobParametersBuilder();

        // Start by fetching parameters with a default value from the configuration
        // setting these first will ensure that if those are overriden by current execution,
        // the current execution's values will be taken (because set after the default values)
        JobDescription configuredJob = batchProperties.getJob(jobName);
        if (configuredJob != null) {
            configuredJob.getParameters().stream().filter(p -> StringUtils.isNotBlank(p.getDefaultValue()))
                    .forEach(p -> builder.addString(p.getName(), p.getDefaultValue()));
        }

        // Then fetch parameters as normal (passed as parameters for this execution)
        parameters.forEach(p -> builder.addString(p.getName(), p.getValue()));
        JobParameters nextParameters = getNextJobParameters(jobName);
        Map<String, JobParameter> map = new HashMap<>(nextParameters.getParameters());
        map.putAll(builder.toJobParameters().getParameters());
        return new JobParameters(map);
    }

    private JobParameters getNextJobParameters(String jobName) throws NoSuchJobException {
        Job job = jobRegistry.getJob(jobName);
        JobParametersIncrementer incrementer = job.getJobParametersIncrementer();
        if (incrementer != null) {
            List<JobInstance> lastInstances = jobExplorer.getJobInstances(jobName, 0, 1);
            if (lastInstances.isEmpty()) {
                return incrementer.getNext(new JobParameters());
            } else {
                List<JobExecution> lastExecutions = jobExplorer.getJobExecutions(lastInstances.get(0));
                return incrementer.getNext(lastExecutions.get(0).getJobParameters());
            }
        }
        return new JobParameters();
    }

    /**
     * Récupère le {@link JobExecution} depuis son id
     */
    public JobExecution getJobExecution(Long executionId) {
        return jobExplorer.getJobExecution(executionId);
    }

    /**
     * Arrete un job.
     * Permet de passer le status du job à STOPPED
     */
    public Boolean stop(Long jobExecutionId) {
        try {
            return jobOperator.stop(jobExecutionId);
        } catch (NoSuchJobExecutionException e) {
            throw new JobBadArgumentException(String.format("le job execution d'id %s n'existe pas, il ne peut pas être stoppé", jobExecutionId), e);
        } catch (JobExecutionNotRunningException e) {
            throw new JobException(String.format("le job execution d'id %s n'est pas encore d'execution", jobExecutionId), e);
        }
    }

    public List<StepExecution> getJobExecutionStepExecutions(Long id) {
        if (id != null) {
            JobExecution execution = jobExplorer.getJobExecution(id);
            return new ArrayList<>(execution.getStepExecutions());
        }
        throw new JobBadArgumentException("l'id ne doit pas être null");
    }

    public Page<JobExecutionEntity> getJobNameExecutions(Pageable pageable, String name, String status, LocalDateTime executionBeginDate, LocalDateTime executionEndDate) {
        return jobExecutionEntityRepository.findAllByNameAndStatusesAndStartTimeAfterAndEndTimeBefore(pageable, name, status, executionBeginDate, executionEndDate);
    }

    public Page<JobExecutionEntity> getJobExecutions(Pageable pageable, List<String> statuses) {
        if (statuses == null || statuses.isEmpty()) {
            return jobExecutionEntityRepository.findAll(pageable);
        }
        return jobExecutionEntityRepository.findAllByStatusIsIn(pageable, statuses);
    }
}
