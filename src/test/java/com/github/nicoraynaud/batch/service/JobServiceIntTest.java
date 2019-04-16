package com.github.nicoraynaud.batch.service;

import com.github.nicoraynaud.batch.config.BatchApiAutoConfiguration;
import com.github.nicoraynaud.batch.domain.JobDescription;
import com.github.nicoraynaud.batch.domain.JobDescriptionParameter;
import com.github.nicoraynaud.batch.domain.JobExecutionParameter;
import com.github.nicoraynaud.batch.domain.entity.JobExecutionEntity;
import com.github.nicoraynaud.batch.exception.JobBadArgumentException;
import com.github.nicoraynaud.batch.exception.JobException;
import com.github.nicoraynaud.batch.repository.JobExecutionEntityRepository;
import com.github.nicoraynaud.batch.repository.JobInstanceEntityRepository;
import com.github.nicoraynaud.batch.test.job.PartitionJobConfiguration;
import com.github.nicoraynaud.batch.test.job.SimpleJob2Configuration;
import com.github.nicoraynaud.batch.test.job.SimpleJobConfiguration;
import com.github.nicoraynaud.batch.test.service.DummyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BatchApiAutoConfiguration.class)
@EnableBatchProcessing
public class JobServiceIntTest {

    private static final int TIMEOUT_JOB = 30;

    @Autowired
    private JobInstanceEntityRepository jobInstanceEntityRepository;

    @Autowired
    private JobExecutionEntityRepository jobExecutionEntityRepository;

    @Autowired
    private JobService jobService;

    @Autowired
    private DummyService dummyService;

    @Test
    public void testGetJobs() {
        assertEquals(4, jobService.getJobs(null).size());

        JobDescription jobDescription = jobService.getJobs(null).get(0);
        assertEquals(SimpleJobConfiguration.JOB_TEST_NAME, jobDescription.getName());
        assertEquals("Ma description", jobDescription.getDescription());
        assertEquals(3, jobDescription.getParameters().size());

        JobDescriptionParameter jobDescriptionParameter0 = jobDescription.getParameters().get(0);
        assertEquals("StringParameter", jobDescriptionParameter0.getName());
        assertEquals("STRING", jobDescriptionParameter0.getType());
        assertEquals("valeur", jobDescriptionParameter0.getDefaultValue());

        JobDescriptionParameter jobDescriptionParameter1 = jobDescription.getParameters().get(1);
        assertEquals("IntegerParameter", jobDescriptionParameter1.getName());
        assertEquals("INTEGER", jobDescriptionParameter1.getType());
        assertEquals("1", jobDescriptionParameter1.getDefaultValue());

        JobDescriptionParameter jobDescriptionParameter2 = jobDescription.getParameters().get(2);
        assertEquals("BooleanParameter", jobDescriptionParameter2.getName());
        assertEquals("BOOLEAN", jobDescriptionParameter2.getType());
        assertEquals("false", jobDescriptionParameter2.getDefaultValue());

        JobDescription jobDescription1 = jobService.getJobs(null).get(1);
        assertEquals(SimpleJob2Configuration.JOB2_TEST_NAME, jobDescription1.getName());
        assertEquals("Ma description2", jobDescription1.getDescription());
        assertEquals(0, jobDescription1.getParameters().size());
    }

    @Test
    public void testGetJobs_WithJobName() {
        assertEquals(4, jobService.getJobs(null).size());

        List<JobDescription> listJobDescription = jobService.getJobs("artitioner");
        assertEquals(1,listJobDescription.size());
        JobDescription jobDescription2 = jobService.getJobs("artitioner").get(0);
        assertEquals(PartitionJobConfiguration.JOB_PARTITION_TEST_NAME, jobDescription2.getName());
        assertEquals("partitionerJob", jobDescription2.getDescription());
        assertEquals(0, jobDescription2.getParameters().size());
    }

    @Test
    public void testGetJobs_WithJobName_IgnoreCase() {
        assertEquals(4, jobService.getJobs(null).size());

        List<JobDescription> listJobDescription = jobService.getJobs("artiTIoner");
        assertEquals(1,listJobDescription.size());
        JobDescription jobDescription2 = jobService.getJobs("artitioner").get(0);
        assertEquals(PartitionJobConfiguration.JOB_PARTITION_TEST_NAME, jobDescription2.getName());
        assertEquals("partitionerJob", jobDescription2.getDescription());
        assertEquals(0, jobDescription2.getParameters().size());
    }

    @Test
    public void testGetJob() {
        JobDescription jobDescription = jobService.getJob(SimpleJobConfiguration.JOB_TEST_NAME);
        assertEquals(SimpleJobConfiguration.JOB_TEST_NAME, jobDescription.getName());
        assertEquals("Ma description", jobDescription.getDescription());
        assertEquals(3, jobDescription.getParameters().size());
    }

    @Test(expected = JobBadArgumentException.class)
    public void testGetJob_JobBadArgumentException() {
        jobService.getJob("toto");
    }

    @Test
    public void testStartJob() {
        List<JobExecutionParameter> jobExecutionParameters = new ArrayList<>();
        JobExecutionParameter jobExecutionParameter = new JobExecutionParameter("testName", "testValue");
        jobExecutionParameter.setType("STRING");
        jobExecutionParameter.setIdentifyCharacter("Y");
        jobExecutionParameters.add(jobExecutionParameter);

        JobExecution jobExecution = jobService.start(SimpleJobConfiguration.JOB_TEST_NAME, jobExecutionParameters);
        JobExecution jobExecution2 = jobService.start(SimpleJob2Configuration.JOB2_TEST_NAME, new ArrayList<>());
        JobExecution jobExecutionPartition = jobService.start(PartitionJobConfiguration.JOB_PARTITION_TEST_NAME, new ArrayList<>());

        dummyService.logService("from app");

        await().atMost(TIMEOUT_JOB, TimeUnit.SECONDS).until(() -> isCompleted(jobExecution));
        await().atMost(TIMEOUT_JOB, TimeUnit.SECONDS).until(() -> isCompleted(jobExecution2));
        await().atMost(TIMEOUT_JOB, TimeUnit.SECONDS).until(() -> isCompleted(jobExecutionPartition));

        List<StepExecution> stepsExecution = jobService.getJobExecutionStepExecutions(jobExecution.getJobId());
        assertEquals(1, stepsExecution.size());
        assertEquals("step", stepsExecution.get(0).getStepName());
        assertEquals(5, jobExecution.getJobParameters().getParameters().size());
        assertEquals("1", jobExecution.getJobParameters().getParameters().get("IntegerParameter").getValue());
        assertEquals("valeur", jobExecution.getJobParameters().getParameters().get("StringParameter").getValue());
        assertEquals("false", jobExecution.getJobParameters().getParameters().get("BooleanParameter").getValue());
        assertEquals("testValue", jobExecution.getJobParameters().getParameters().get("testName").getValue());
    }

    @Test(expected = JobException.class)
    public void testStartJob_JobException() {
        jobService.start("JobException", new ArrayList<>());
    }

    @Test(expected = JobBadArgumentException.class)
    public void testStopJob_JobBadArgumentException() {
        jobService.stop(9999999L);
    }

    @Test(expected = JobException.class)
    public void testStopJob_JobException() {
        JobExecution jobExecution = jobService.start(SimpleJobConfiguration.JOB_TEST_NAME, new ArrayList<>());
        await().atMost(TIMEOUT_JOB, TimeUnit.SECONDS).until(() -> isCompleted(jobExecution));

        jobService.stop(jobExecution.getId());
    }

    @Test(expected = JobBadArgumentException.class)
    public void testJobExecutionStepExecutions_JobBadArgumentException() {
        jobService.getJobExecutionStepExecutions(null);
    }

    @Test
    public void testGetLastJobExecution() {
        JobExecutionEntity jobExecution = jobService.getLastJobExecution("jobReference");
        assertEquals(351L, jobExecution.getId());
    }

    @Test
    public void testGetJobExecutionsByStatuses_null() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<JobExecutionEntity> jobsExecutionEntity = jobService.getJobExecutions(pageable, null);
        assertEquals(5, jobsExecutionEntity.getContent().size());
    }

    @Test
    public void testGetJobExecutionsByStatuses() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<JobExecutionEntity> jobsExecutionEntity = jobService.getJobExecutions(pageable, Arrays.asList("FAILED", "COMPLETED"));
        assertEquals(5, jobsExecutionEntity.getContent().size());
    }

    @Test
    public void testGetJobNameExecutions() {
        Page<JobExecutionEntity> jobsExecutionEntity = jobService.getJobNameExecutions(PageRequest.of(0, 2), "JobRetry", "FAILED", LocalDateTime.parse("2018-05-25T13:43:05"), LocalDateTime.parse("2018-05-25T13:46:05"));
        assertEquals(1, jobsExecutionEntity.getContent().size());
    }

    @Test
    public void testGetLastJobExecution_null() {
        JobExecutionEntity jobExecution = jobService.getLastJobExecution("jobReferenceNotExiste");
        assertNull(jobExecution);
    }

    private boolean isCompleted(JobExecution jobExecution) {
        return ExitStatus.COMPLETED.getExitCode().equals(jobService.getJobExecution(jobExecution.getJobId()).getExitStatus().getExitCode());
    }

}