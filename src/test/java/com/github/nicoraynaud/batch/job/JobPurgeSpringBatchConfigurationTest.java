package com.github.nicoraynaud.batch.job;

import com.github.nicoraynaud.batch.config.BatchApiAutoConfiguration;
import com.github.nicoraynaud.batch.domain.entity.JobExecutionEntity;
import com.github.nicoraynaud.batch.domain.entity.JobInstanceEntity;
import com.github.nicoraynaud.batch.repository.JobExecutionEntityRepository;
import com.github.nicoraynaud.batch.repository.JobInstanceEntityRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BatchApiAutoConfiguration.class)
public class JobPurgeSpringBatchConfigurationTest {

    private SimpleJobLauncher jobLauncher;

    @Autowired
    private JobRepository jobRepository;


    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    @Qualifier(JobPurgeSpringBatchConfiguration.JOB_PURGE_SPRING_BATCH)
    private Job jobPurgeSpringBatch;

    @Autowired
    private JobExecutionEntityRepository jobExecutionEntityRepository;

    @Autowired
    private JobInstanceEntityRepository jobInstanceEntityRepository;


    @Test
    @Sql({"/dataPurgeSpringBatch.sql"})
    public void testJobPurgeSpringBatch() throws Exception {

        //Given
        //Dans le script on ajoute 2 vieilles job_execution
        List<JobExecutionEntity> entitiesBefore = jobExecutionEntityRepository.findAll();
        Assert.assertEquals(11, entitiesBefore.size());

        //When
        JobExecution jobExecution = launchJob(jobPurgeSpringBatch);

        //Then
        checkJobExecution(jobExecution, JobPurgeSpringBatchConfiguration.STEP_DELETE_CASCADE_JOB_EXECUTION, "COMPLETED", 0, 2);
        List<JobExecutionEntity> entitiesExecution = jobExecutionEntityRepository.findAll();
        Assert.assertEquals(10, entitiesExecution.size()); // 11 du depart moins 2 supprimés par le job + 1 pour le job de purgeSpringBatch
        checkJobExecution(jobExecution, JobPurgeSpringBatchConfiguration.STEP_DELETE_CASCADE_JOB_EXECUTION, "COMPLETED", 0, 2);
        List<JobInstanceEntity> entitiesInstance = jobInstanceEntityRepository.findAll();
        Assert.assertEquals(10, entitiesInstance.size());

    }


    private JobExecution launchJob(Job job) throws Exception {
        Map<String, JobParameter> parametersJob = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        parametersJob.put(JobPurgeSpringBatchConfiguration.DATE_SUPPRESSION, new JobParameter("01/01/2000"));

        return launchJob(job, new JobParameters(parametersJob));
    }


    protected JobExecution launchJob(Job job, JobParameters jobParameters) throws Exception {
        jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.afterPropertiesSet();

        jobLauncherTestUtils = new JobLauncherTestUtils();
        jobLauncherTestUtils.setJobLauncher(jobLauncher);
        jobLauncherTestUtils.setJobRepository(jobRepository);
        jobLauncherTestUtils.setJob(job);
        return jobLauncherTestUtils.launchJob(jobParameters);
    }

    private void checkJobExecution(JobExecution jobExecution, String stepName, String status, int nbRead, int nbWrite) {
        assertEquals(status, jobExecution.getExitStatus().getExitCode());
        assertEquals(nbRead, jobExecution.getStepExecutions().stream().filter(s -> s.getStepName().equals(stepName)).mapToInt(StepExecution::getReadCount).sum());
        assertEquals(nbWrite, jobExecution.getStepExecutions().stream().filter(s -> s.getStepName().equals(stepName)).mapToInt(StepExecution::getWriteCount).sum());
    }

}