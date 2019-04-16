package com.github.nicoraynaud.batch.service.rest;

import com.github.nicoraynaud.batch.domain.JobDescription;
import com.github.nicoraynaud.batch.domain.entity.JobExecutionEntity;
import com.github.nicoraynaud.batch.dto.JobDescriptionDTO;
import com.github.nicoraynaud.batch.dto.JobExecutionDTO;
import com.github.nicoraynaud.batch.dto.StepExecutionDTO;
import com.github.nicoraynaud.batch.dto.mapping.entity.JobExecutionEntityMapper;
import com.github.nicoraynaud.batch.service.JobService;
import com.github.nicoraynaud.batch.dto.mapping.JobDescriptionMapper;
import com.github.nicoraynaud.batch.dto.mapping.JobExecutionMapper;
import com.github.nicoraynaud.batch.dto.mapping.JobExecutionParameterMapper;
import com.github.nicoraynaud.batch.dto.mapping.MapperSpringConfig;
import com.github.nicoraynaud.batch.dto.mapping.StepExecutionMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MapperSpringConfig.class)
public class JobRestServiceTest {

    private JobService jobService;
    @Autowired
    private JobDescriptionMapper jobDescriptionMapper;
    @Autowired
    private JobExecutionMapper jobExecutionMapper;
    @Autowired
    private JobExecutionEntityMapper jobExecutionEntityMapper;
    @Autowired
    private StepExecutionMapper stepExecutionMapper;
    @Autowired
    private JobExecutionParameterMapper jobExecutionParameterMapper;

    private JobRestService jobRestService;

    @Before
    public void setUp() {
        jobService = mock(JobService.class);

        jobRestService = new JobRestService(jobService, jobDescriptionMapper,
                jobExecutionMapper, jobExecutionEntityMapper,
                stepExecutionMapper, jobExecutionParameterMapper);
    }

    @Test
    public void testGetJobs_NoJobs() {
        Mockito.when(jobService.getJobs(null)).thenReturn(Collections.emptyList());

        List<JobDescriptionDTO> jobs = jobRestService.getJobs(null);

        Assert.assertNotNull(jobs);
        Assert.assertTrue(jobs.isEmpty());
    }

    @Test
    public void testGetJobs_WithJobName_NoJobs() {
        String jobname = "toto";
        Mockito.when(jobService.getJobs(jobname)).thenReturn(Collections.emptyList());

        List<JobDescriptionDTO> jobs = jobRestService.getJobs(jobname);

        Assert.assertNotNull(jobs);
        Assert.assertTrue(jobs.isEmpty());
    }

    @Test
    public void testGetJobs_WithStatusNotNull() {

        JobDescription job1 = new JobDescription();
        job1.setName("job1");
        job1.setDescription("jobDesc1");
        Mockito.when(jobService.getJobs(null)).thenReturn(Arrays.asList(job1));
        JobExecutionEntity job1Execution = new JobExecutionEntity();
        job1Execution.setStatus("OK");
        Mockito.when(jobService.getLastJobExecution("job1")).thenReturn(job1Execution);

        List<JobDescriptionDTO> jobs = jobRestService.getJobs(null);

        Assert.assertNotNull(jobs);
        Assert.assertEquals(1, jobs.size());
        Assert.assertEquals("job1", jobs.get(0).getName());
        Assert.assertEquals("jobDesc1", jobs.get(0).getDescription());
        Assert.assertEquals("OK", jobs.get(0).getLastExecutionStatus());
    }

    @Test
    public void testGetJobs_WithJobName_WithStatusNotNull() {
        String jobName="toto";

        JobDescription job1 = new JobDescription();
        job1.setName("job1");
        job1.setDescription("jobDesc1");
        Mockito.when(jobService.getJobs(jobName)).thenReturn(Arrays.asList(job1));
        JobExecutionEntity job1Execution = new JobExecutionEntity();
        job1Execution.setStatus("OK");
        Mockito.when(jobService.getLastJobExecution("job1")).thenReturn(job1Execution);

        List<JobDescriptionDTO> jobs = jobRestService.getJobs(jobName);

        Assert.assertNotNull(jobs);
        Assert.assertEquals(1, jobs.size());
        Assert.assertEquals("job1", jobs.get(0).getName());
        Assert.assertEquals("jobDesc1", jobs.get(0).getDescription());
        Assert.assertEquals("OK", jobs.get(0).getLastExecutionStatus());
    }

    @Test
    public void testGetJobs_WithStatusNoExecution() {

        JobDescription job1 = new JobDescription();
        job1.setName("job1");
        job1.setDescription("jobDesc1");
        Mockito.when(jobService.getJobs(null)).thenReturn(Arrays.asList(job1));
        JobExecutionEntity job1Execution = new JobExecutionEntity();
        job1Execution.setStatus("OK");
        Mockito.when(jobService.getLastJobExecution("job1")).thenReturn(null);

        List<JobDescriptionDTO> jobs = jobRestService.getJobs(null);

        Assert.assertNotNull(jobs);
        Assert.assertEquals(1, jobs.size());
        Assert.assertEquals("job1", jobs.get(0).getName());
        Assert.assertEquals("jobDesc1", jobs.get(0).getDescription());
        Assert.assertEquals(null, jobs.get(0).getLastExecutionStatus());
    }

    @Test
    public void testGetJobs_WithStatusNull() {

        JobDescription job1 = new JobDescription();
        job1.setName("job1");
        job1.setDescription("jobDesc1");
        Mockito.when(jobService.getJobs(null)).thenReturn(Arrays.asList(job1));
        JobExecutionEntity job1Execution = new JobExecutionEntity();
        job1Execution.setStatus(null);
        Mockito.when(jobService.getLastJobExecution("job1")).thenReturn(job1Execution);

        List<JobDescriptionDTO> jobs = jobRestService.getJobs(null);

        Assert.assertNotNull(jobs);
        Assert.assertEquals(1, jobs.size());
        Assert.assertEquals("job1", jobs.get(0).getName());
        Assert.assertEquals("jobDesc1", jobs.get(0).getDescription());
        Assert.assertEquals(null, jobs.get(0).getLastExecutionStatus());
    }

    @Test
    public void testGetJob_WithStatusNotNull() {

        JobDescription job1 = new JobDescription();
        job1.setName("job1");
        job1.setDescription("jobDesc1");
        Mockito.when(jobService.getJob("job1")).thenReturn(job1);
        JobExecutionEntity job1Execution = new JobExecutionEntity();
        job1Execution.setStatus("OK");
        Mockito.when(jobService.getLastJobExecution("job1")).thenReturn(job1Execution);

        JobDescriptionDTO jobDTO = jobRestService.getJob("job1");

        Assert.assertNotNull(jobDTO);
        Assert.assertEquals("job1", jobDTO.getName());
        Assert.assertEquals("jobDesc1", jobDTO.getDescription());
        Assert.assertEquals("OK", jobDTO.getLastExecutionStatus());
    }

    @Test
    public void testGetJob_WithStatusNoExecution() {

        JobDescription job1 = new JobDescription();
        job1.setName("job1");
        job1.setDescription("jobDesc1");
        Mockito.when(jobService.getJob("job1")).thenReturn(job1);
        JobExecutionEntity job1Execution = new JobExecutionEntity();
        job1Execution.setStatus("OK");
        Mockito.when(jobService.getLastJobExecution("job1")).thenReturn(null);

        JobDescriptionDTO jobDTO = jobRestService.getJob("job1");

        Assert.assertNotNull(jobDTO);
        Assert.assertEquals("job1", jobDTO.getName());
        Assert.assertEquals("jobDesc1", jobDTO.getDescription());
        Assert.assertEquals(null, jobDTO.getLastExecutionStatus());
    }

    @Test
    public void testGetJob_WithStatusNull() {

        JobDescription job1 = new JobDescription();
        job1.setName("job1");
        job1.setDescription("jobDesc1");
        Mockito.when(jobService.getJob("job1")).thenReturn(job1);
        JobExecutionEntity job1Execution = new JobExecutionEntity();
        job1Execution.setStatus(null);
        Mockito.when(jobService.getLastJobExecution("job1")).thenReturn(job1Execution);

        JobDescriptionDTO jobDTO = jobRestService.getJob("job1");

        Assert.assertNotNull(jobDTO);
        Assert.assertEquals("job1", jobDTO.getName());
        Assert.assertEquals("jobDesc1", jobDTO.getDescription());
        Assert.assertEquals(null, jobDTO.getLastExecutionStatus());
    }

    @Test
    public void testStart() {

        JobExecution e = new JobExecution(1L);
        when(jobService.start(any(), any())).thenReturn(e);

        JobExecutionDTO dto = jobRestService.start("job1", Collections.emptyList());

        Assert.assertNotNull(dto);
        Assert.assertEquals(1L, dto.getId());
        Mockito.verify(jobService, Mockito.times(1)).start("job1", Collections.emptyList());

    }

    @Test
    public void testStop_OK() {
        when(jobService.stop(1L)).thenReturn(Boolean.TRUE);
        Assert.assertTrue(jobRestService.stop(1L));
        Mockito.verify(jobService, Mockito.times(1)).stop(1L);
    }

    @Test
    public void testStop_NOK() {
        when(jobService.stop(1L)).thenReturn(Boolean.FALSE);
        Assert.assertFalse(jobRestService.stop(1L));
        Mockito.verify(jobService, Mockito.times(1)).stop(1L);
    }

    @Test
    public void testGetJobNameExecutions() {
        JobExecutionEntity e = new JobExecutionEntity();
        e.setId(1L);
        Page pageResult = new PageImpl(Arrays.asList(e));
        when(jobService.getJobNameExecutions(any(), any(), any(), any(), any())).thenReturn(pageResult);

        Page<JobExecutionDTO> dtos = jobRestService.getJobNameExecutions(PageRequest.of(0,1), "job1", "status", null, null);

        Assert.assertNotNull(dtos);
        Assert.assertEquals(1, dtos.getTotalElements());
        Assert.assertEquals(1, dtos.getContent().get(0).getId());
    }

    @Test
    public void testGetJobExecutions_StatusesNull() {
        PageRequest pageRequest = PageRequest.of(0,1);
        JobExecutionEntity e = new JobExecutionEntity();
        e.setId(1L);
        Page pageResult = new PageImpl(Arrays.asList(e));
        when(jobService.getJobExecutions(pageRequest, null)).thenReturn(pageResult);

        Page<JobExecutionDTO> dtos = jobRestService.getJobExecutions(pageRequest, null);

        Assert.assertNotNull(dtos);
        Assert.assertEquals(1, dtos.getTotalElements());
        Assert.assertEquals(1, dtos.getContent().get(0).getId());
    }

    @Test
    public void testGetJobExecutions_WithStatuses() {
        PageRequest pageRequest = PageRequest.of(0,1);
        JobExecutionEntity e = new JobExecutionEntity();
        e.setId(1L);
        Page pageResult = new PageImpl(Arrays.asList(e));
        when(jobService.getJobExecutions(pageRequest, Arrays.asList("OK"))).thenReturn(pageResult);

        Page<JobExecutionDTO> dtos = jobRestService.getJobExecutions(pageRequest, new String[]{"OK"});

        Assert.assertNotNull(dtos);
        Assert.assertEquals(1, dtos.getTotalElements());
        Assert.assertEquals(1, dtos.getContent().get(0).getId());
    }

    @Test
    public void testGetJobExecution() {
        JobExecution e = new JobExecution(1L);
        when(jobService.getJobExecution(1L)).thenReturn(e);

        JobExecutionDTO dto = jobRestService.getJobExecution(1L);

        Assert.assertNotNull(dto);
        Assert.assertEquals(1, dto.getId());
    }

    @Test
    public void testGetJobExecutionStepExecutions() {
        JobExecution je = new JobExecution(1L);
        StepExecution e = new StepExecution("step", je, 1L);
        when(jobService.getJobExecutionStepExecutions(1L)).thenReturn(Arrays.asList(e));

        List<StepExecutionDTO> dtos = jobRestService.getJobExecutionStepExecutions(1L);

        Assert.assertNotNull(dtos);
        Assert.assertEquals(1, dtos.size());
        Assert.assertEquals(1L, dtos.get(0).getId());
    }
}
