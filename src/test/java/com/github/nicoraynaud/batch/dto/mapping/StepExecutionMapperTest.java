package com.github.nicoraynaud.batch.dto.mapping;

import com.github.nicoraynaud.batch.config.BatchApiAutoConfiguration;
import com.github.nicoraynaud.batch.dto.StepExecutionDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Calendar;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = BatchApiAutoConfiguration.class)
public class StepExecutionMapperTest {

    private static final String STEP_EXECUTION_NAME = "monNom";
    private static final long STEP_EXECUTION_ID = 1L;
    private static final long STEP_EXECUTION_JOB_EXECUTION_ID = 2L;
    private static final String STEP_EXECUTION_EXIT_CODE = ExitStatus.EXECUTING.getExitCode();
    private static final String STEP_EXECUTION_EXIT_MESSAGE = ExitStatus.EXECUTING.getExitDescription();

    @Autowired
    private StepExecutionMapper stepExecutionMapper;

    @Test
    public void testToDTO() {
        Calendar c = Calendar.getInstance();
        c.set(1985,2,26,12,32,10);
        c.set(Calendar.MILLISECOND,0);

        StepExecution stepExecution = new StepExecution(STEP_EXECUTION_NAME, new JobExecution(2L), 1L);
        stepExecution.setStartTime(c.getTime());

        StepExecutionDTO stepExecutionDTO = stepExecutionMapper.toDTO(stepExecution);

        Assert.assertNotNull(stepExecutionDTO.getName());


        Assert.assertEquals(STEP_EXECUTION_ID, stepExecutionDTO.getId());
        Assert.assertEquals(STEP_EXECUTION_NAME, stepExecutionDTO.getName());
        Assert.assertEquals("STARTING", stepExecutionDTO.getStatus());
        Assert.assertEquals(STEP_EXECUTION_JOB_EXECUTION_ID, stepExecutionDTO.getJobExecutionId());
        Assert.assertNotNull(stepExecutionDTO.getStartTime());
        Assert.assertEquals(LocalDateTime.of(1985,03,26,12,32,10), stepExecutionDTO.getStartTime());
        Assert.assertNull(stepExecutionDTO.getEndTime());
        Assert.assertNull(stepExecutionDTO.getLastUpdated());
        Assert.assertEquals(0, stepExecutionDTO.getCommitCount());
        Assert.assertEquals(0, stepExecutionDTO.getReadCount());
        Assert.assertEquals(0, stepExecutionDTO.getWriteCount());
        Assert.assertEquals(0, stepExecutionDTO.getReadSkipCount());
        Assert.assertEquals(0, stepExecutionDTO.getWriteSkipCount());
        Assert.assertEquals(0, stepExecutionDTO.getProcessSkipCount());
        Assert.assertEquals(0, stepExecutionDTO.getRollbackCount());
        Assert.assertEquals(STEP_EXECUTION_EXIT_CODE, stepExecutionDTO.getExitCode());
        Assert.assertEquals(STEP_EXECUTION_EXIT_MESSAGE, stepExecutionDTO.getExitMessage());
    }
}