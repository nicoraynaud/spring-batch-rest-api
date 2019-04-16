package com.github.nicoraynaud.batch.job;

import com.github.nicoraynaud.batch.repository.JobExecutionEntityRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class JobPurgeSpringBatchConfiguration {

    public static final String JOB_PURGE_SPRING_BATCH = "jobPurgeSpringBatch";
    public static final String STEP_DELETE_CASCADE_JOB_EXECUTION = "stepDeleteCascadeJobExecution";
    public static final String STEP_DELETE_ORPHELIN_JOB_INSTANCE = "stepDeleteOrphelineJobInstance";
    public static final String TASKLET_PURGE = "taskletPurge";
    public static final String DATE_SUPPRESSION = "dateToDelete";
    public static final Integer NB_MOIS_DATE_SUPPRESSION_NULL = 6;

    private final JobBuilderFactory jobBuilderFactory;

    public JobPurgeSpringBatchConfiguration(JobBuilderFactory jobBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
    }

    @Bean(name = JOB_PURGE_SPRING_BATCH)
    public Job jobPurgeSpringBatch(@Qualifier(STEP_DELETE_CASCADE_JOB_EXECUTION) Step stepDeleteCascadeJobExecution,
                                   @Qualifier(STEP_DELETE_ORPHELIN_JOB_INSTANCE) Step stepDeleteOrphelineJobInstance) {
        return jobBuilderFactory.get(JOB_PURGE_SPRING_BATCH)
                .incrementer(new RunIdIncrementer())
                .start(stepDeleteCascadeJobExecution)
                .next(stepDeleteOrphelineJobInstance)
                .build();
    }

    @Bean(name = STEP_DELETE_CASCADE_JOB_EXECUTION)
    public Step stepDeleteCascadeJobExecution(
            StepBuilderFactory stepBuilderFactory,
            DeleteCascadeJobExecutionTasklet deleteCascadeJobExecutionTasklet) {

        return stepBuilderFactory.get(STEP_DELETE_CASCADE_JOB_EXECUTION)
                .tasklet(deleteCascadeJobExecutionTasklet)
                .build();
    }

    @Bean(name = STEP_DELETE_ORPHELIN_JOB_INSTANCE)
    public Step stepDeleteOrphelineJobInstance(
            StepBuilderFactory stepBuilderFactory,
            DeleteOrphelinJobInstanceTasklet stepDeleteOrphelineJobInstance) {

        return stepBuilderFactory.get(STEP_DELETE_ORPHELIN_JOB_INSTANCE)
                .tasklet(stepDeleteOrphelineJobInstance)
                .build();
    }

    @Bean(name = TASKLET_PURGE)
    @JobScope
    public DeleteCascadeJobExecutionTasklet taskletPurgeCra(JobExecutionEntityRepository jobExecutionEntityRepository, @Value("#{jobParameters['" + DATE_SUPPRESSION + "']}") String dateSuppression) {
        LocalDate dateSuppFormatDate = null;
        if (dateSuppression == null) {
            dateSuppFormatDate = LocalDate.now().minusMonths(NB_MOIS_DATE_SUPPRESSION_NULL);
        } else {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            dateSuppFormatDate = LocalDate.parse(dateSuppression, format);
        }
        DeleteCascadeJobExecutionTasklet tasklet = new DeleteCascadeJobExecutionTasklet(jobExecutionEntityRepository, dateSuppFormatDate.atStartOfDay());
        return tasklet;
    }
}
