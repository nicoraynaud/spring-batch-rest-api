package com.github.nicoraynaud.batch.service;

import com.github.nicoraynaud.batch.config.BatchProperties;
import com.github.nicoraynaud.batch.domain.JobExecutionParameter;
import com.github.nicoraynaud.batch.listener.AddListenerToJobService;
import com.github.nicoraynaud.batch.repository.JobExecutionEntityRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.springframework.batch.core.configuration.ListableJobLocator;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;

import java.util.ArrayList;
import java.util.List;


public class JobServiceTest {

    private JobService jobService;

    @Mock
    BatchProperties batchProperties;

    @Mock
    private JobOperator jobOperator;

    @Mock
    private JobExplorer jobExplorer;

    @Mock
    private ListableJobLocator jobRegistry;

    @Mock
    private JobLauncher jobLauncher;

    @Mock
    private AddListenerToJobService addListenerToJobService;

    @Mock
    private JobExecutionEntityRepository jobExecutionEntityRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        jobService = new JobService(batchProperties, jobOperator, jobExplorer,
                jobRegistry, jobLauncher, addListenerToJobService,
                jobExecutionEntityRepository);
    }

    @Test
    public void testStopJob() throws Exception {
        //Given
        List<JobExecutionParameter> jobExecutionParameters = new ArrayList<>();
        JobExecutionParameter jobExecutionParameter = new JobExecutionParameter();
        jobExecutionParameter.setName("threadSleepTime");
        jobExecutionParameter.setValue("20000");

        //When
        jobService.stop(1L);

        //Then
        Mockito.verify(jobOperator, Mockito.times(1)).stop(1L);


    }


}