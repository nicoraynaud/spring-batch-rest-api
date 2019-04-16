package com.github.nicoraynaud.batch.repository;

import com.github.nicoraynaud.batch.domain.entity.JobExecutionEntity;
import com.github.nicoraynaud.batch.domain.entity.JobExecutionParameterEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JobExecutionEntityRepositoryTest {

    @Autowired
    private JobExecutionEntityRepository jobExecutionEntityRepository;

    @Test
    public void testFindEntity() {
        assertNotNull(jobExecutionEntityRepository);
        assertTrue(jobExecutionEntityRepository.existsById(150L));

        JobExecutionEntity jobExecutionEntity = jobExecutionEntityRepository.findById(150L).get();

        assertNotNull(jobExecutionEntity.getVersion());
        assertEquals("2018-05-25T13:44:05.690", jobExecutionEntity.getCreateTime().toString());

        assertEquals(2, jobExecutionEntity.getParameters().size());
        assertEquals("run.id", jobExecutionEntity.getParameters().get(0).getName());
        Assert.assertEquals(JobExecutionParameterEntity.ParameterType.LONG, jobExecutionEntity.getParameters().get(0).getType());
        assertEquals("76", jobExecutionEntity.getParameters().get(0).getValue());

        assertEquals("time", jobExecutionEntity.getParameters().get(1).getName());
        assertEquals(JobExecutionParameterEntity.ParameterType.STRING, jobExecutionEntity.getParameters().get(1).getType());
        assertEquals("1521779644490", jobExecutionEntity.getParameters().get(1).getValue());


        assertEquals(149, jobExecutionEntity.getJobInstance().getId());
        assertEquals("JobRetry", jobExecutionEntity.getJobInstance().getJobName());
    }

    @Test
    public void testFindAllByNameAndStatusesAndStartTimeAfterAndEndTimeBefore() {
        Pageable pageable = PageRequest.of(0, 2);
        Page<JobExecutionEntity> jobsExecutionEntity = jobExecutionEntityRepository.findAllByNameAndStatusesAndStartTimeAfterAndEndTimeBefore(pageable, "JobRetry", "FAILED", LocalDateTime.parse("2018-05-25T13:43:05"), LocalDateTime.parse("2018-05-25T13:46:05"));
        assertEquals(1, jobsExecutionEntity.getContent().size());

        jobsExecutionEntity = jobExecutionEntityRepository.findAllByNameAndStatusesAndStartTimeAfterAndEndTimeBefore(pageable, "JobRetry", "FAILED", LocalDateTime.parse("2018-05-25T13:43:05"), LocalDateTime.parse("2018-05-25T13:44:00"));
        assertEquals(0, jobsExecutionEntity.getContent().size());

        jobsExecutionEntity = jobExecutionEntityRepository.findAllByNameAndStatusesAndStartTimeAfterAndEndTimeBefore(pageable, "JobRetry", "FAILED", null, null);
        assertEquals(1, jobsExecutionEntity.getContent().size());

        jobsExecutionEntity = jobExecutionEntityRepository.findAllByNameAndStatusesAndStartTimeAfterAndEndTimeBefore(pageable, "JobRetry", null, LocalDateTime.parse("2018-05-25T13:43:05"), LocalDateTime.parse("2018-05-25T13:46:05"));
        assertEquals(1, jobsExecutionEntity.getContent().size());

        jobsExecutionEntity = jobExecutionEntityRepository.findAllByNameAndStatusesAndStartTimeAfterAndEndTimeBefore(pageable, "JobRetry", null, null, null);
        assertEquals(2, jobsExecutionEntity.getContent().size());
    }

    @Test
    public void testFindAllByStatusIsIn() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<JobExecutionEntity> jobsExecutionEntity = jobExecutionEntityRepository.findAllByStatusIsIn(pageable, Arrays.asList("FAILED", "COMPLETED"));
        assertEquals(5, jobsExecutionEntity.getContent().size());
    }

    @Test
    public void testGetLastJobExecution() {
        JobExecutionEntity jobExecutionEntity = jobExecutionEntityRepository.getLastJobExecution("JobRetry");
        assertEquals(150L, jobExecutionEntity.getId());
        jobExecutionEntity = jobExecutionEntityRepository.getLastJobExecution("jobReference");
        assertEquals(351L, jobExecutionEntity.getId());
    }

}