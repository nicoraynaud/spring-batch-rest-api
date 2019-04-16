package com.github.nicoraynaud.batch.dto.mapping;

import com.github.nicoraynaud.batch.dto.JobExecutionDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MapperSpringConfig.class)
public class AbstractJobExecutionMapperDecoratorTest {

    private static final String JOB_EXECUTION_STATUS_DEFAULT = "STARTING";
    private static final String JOB_EXECUTION_EXIT_CODE_DEFAULT = ExitStatus.UNKNOWN.getExitCode();
    private static final String JOB_EXECUTION_EXIT_DESCRIPTION_DEFAULT = ExitStatus.UNKNOWN.getExitDescription();
    private static final long JOB_EXECUTION_ID_DEFAULT = 1L;

    private static final String PARAMTER_NAME_DEFAULT = "fileName";
    private static final String PARAMETER_VALUE_DEFAULT = "monFichier.csv";
    private static final String PARAMETER_TYPE_DEFAULT = "STRING";
    private static final String PARAMETER_IDENTIFYING_DEFAULT = "true";

    @Autowired
    private JobExecutionMapper jobExecutionMapper;

    @Test
    public void testMapperDecoratorValid() {
        Assert.assertNotNull(jobExecutionMapper);
        Assert.assertTrue(jobExecutionMapper instanceof AbstractJobExecutionMapperDecorator);
    }

    @Test
    public void testMapperDecorator() {
        Map<String, JobParameter> parameters = new HashMap<>();
        parameters.put(PARAMTER_NAME_DEFAULT, new JobParameter(PARAMETER_VALUE_DEFAULT, Boolean.valueOf(PARAMETER_IDENTIFYING_DEFAULT)));
        JobParameters jobParameters = new JobParameters(parameters);
        JobExecution jobExecution = new JobExecution(JOB_EXECUTION_ID_DEFAULT, jobParameters, "maConfiguration");

        JobExecutionDTO jobExecutionDTO = jobExecutionMapper.toDTO(jobExecution);

        Assert.assertNotNull(jobExecutionDTO);
        Assert.assertEquals(PARAMTER_NAME_DEFAULT, jobExecutionDTO.getParameters().get(0).getName());
        Assert.assertEquals(PARAMETER_VALUE_DEFAULT, jobExecutionDTO.getParameters().get(0).getValue());
        Assert.assertEquals(PARAMETER_TYPE_DEFAULT, jobExecutionDTO.getParameters().get(0).getType());
        Assert.assertEquals(PARAMETER_IDENTIFYING_DEFAULT, jobExecutionDTO.getParameters().get(0).getIdentifyCharacter());


        Assert.assertEquals(JOB_EXECUTION_ID_DEFAULT, jobExecutionDTO.getId());
        Assert.assertNull(null, jobExecutionDTO.getJobName());
        Assert.assertNotNull(jobExecutionDTO.getCreateTime());
        Assert.assertNull(null, jobExecutionDTO.getStartTime());
        Assert.assertNull(null, jobExecutionDTO.getEndTime());
        Assert.assertNull(null, jobExecutionDTO.getLastUpdated());
        Assert.assertEquals(JOB_EXECUTION_STATUS_DEFAULT, jobExecutionDTO.getStatus());
        Assert.assertEquals(JOB_EXECUTION_EXIT_CODE_DEFAULT, jobExecutionDTO.getExitCode());
        Assert.assertEquals(JOB_EXECUTION_EXIT_DESCRIPTION_DEFAULT, jobExecutionDTO.getExitMessage());

    }

    @Test
    public void testMapperDecorator_parameterNull() {
        Map<String, JobParameter> parameters = new HashMap<>();
        parameters.put(PARAMTER_NAME_DEFAULT, new JobParameter((String)null));
        JobParameters jobParameters = new JobParameters(parameters);
        JobExecution jobExecution = new JobExecution(JOB_EXECUTION_ID_DEFAULT, jobParameters, "maConfiguration");

        JobExecutionDTO jobExecutionDTO = jobExecutionMapper.toDTO(jobExecution);

        Assert.assertNotNull(jobExecutionDTO);
        Assert.assertEquals("", jobExecutionDTO.getParameters().get(0).getValue());
     }
}