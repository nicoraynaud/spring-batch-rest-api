package com.github.nicoraynaud.batch.web.rest;

import com.github.nicoraynaud.batch.config.BatchApiAutoConfiguration;
import com.github.nicoraynaud.batch.helper.PojoHelper;
import com.github.nicoraynaud.batch.service.JobService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.StepExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BatchApiAutoConfiguration.class)
public class JobsResourceTest {


    private MockMvc mockMvc;

    @MockBean
    private JobService jobService;

    @Autowired
    private JobsResource jobsResource;


    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(jobsResource)
                .setCustomArgumentResolvers(pageableArgumentResolver).build();
    }

    @Test
    public void testGetJobs() throws Exception {
        when(jobService.getJobs(null)).thenReturn(PojoHelper.createJobsDescriptionDefault());
        mockMvc.perform(get("/management/jobs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].name").value(Matchers.hasItem(PojoHelper.JOB_NAME_DEFAULT)))
                .andExpect(jsonPath("$.[*].description").value(Matchers.hasItem(PojoHelper.JOB_DESCRIPTION_DESCRIPTION_DEFAULT)));
    }

    @Test
    public void testGetJob() throws Exception {
        when(jobService.getJob(any())).thenReturn(PojoHelper.createJobsDescriptionDefault().get(0));
        mockMvc.perform(MockMvcRequestBuilders.get("/management/jobs/" + PojoHelper.JOB_NAME_DEFAULT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(PojoHelper.JOB_DESCRIPTION_NAME_DEFAULT))
                .andExpect(jsonPath("$.description").value(PojoHelper.JOB_DESCRIPTION_DESCRIPTION_DEFAULT))
                .andExpect(jsonPath("$.parameters[0].name").value(PojoHelper.JOB_DESCRIPTION_PARAMETER_NAME_DATE_DEFAULT))
                .andExpect(jsonPath("$.parameters[0].type").value(PojoHelper.JOB_DESCRIPTION_PARAMETER_TYPE_DATE_DEFAULT))
                .andExpect(jsonPath("$.parameters[0].defaultValue").value(PojoHelper.JOB_DESCRIPTION_PARAMETER_DEFAULT_VALUE_DEFAULT));
    }

    @Test
    public void testStart() throws Exception {
        when(jobService.start(eq(PojoHelper.JOB_NAME_DEFAULT), any())).thenReturn(PojoHelper.createJobExecutionDefault());
        mockMvc.perform(MockMvcRequestBuilders.post("/management/jobs/" + PojoHelper.JOB_NAME_DEFAULT + "/start")
                .contentType(ResourcesTestUtil.APPLICATION_JSON_UTF8)
                .content(ResourcesTestUtil.convertObjectToJsonBytes(new ArrayList<>())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(PojoHelper.JOB_EXECUTION_ID_DEFAULT))
                .andExpect(jsonPath("$.jobName").value(PojoHelper.JOB_NAME_DEFAULT));
    }

    @Test
    public void testGetJobNameExecutions() throws Exception {
        when(jobService.getJobNameExecutions(any(), eq(PojoHelper.JOB_NAME_DEFAULT), any(), any(), any()))
                .thenReturn(PojoHelper.createPageJobExecutionDefault());
        mockMvc.perform(MockMvcRequestBuilders.get("/management/jobs/" + PojoHelper.JOB_NAME_DEFAULT + "/executions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(PojoHelper.JOB_EXECUTION_ID_DEFAULT))
                .andExpect(jsonPath("$.content[0].jobName").value(PojoHelper.JOB_NAME_DEFAULT))
                .andExpect(jsonPath("$.content[0].createTime").value(PojoHelper.JOB_EXECUTION_CREATE_TIME_DEFAULT.toString()))
                .andExpect(jsonPath("$.content[0].startTime").value(PojoHelper.JOB_EXECUTION_START_TIME_DEFAULT.toString()))
                .andExpect(jsonPath("$.content[0].endTime").value(PojoHelper.JOB_EXECUTION_END_TIME_DEFAULT.toString()))
                .andExpect(jsonPath("$.content[0].lastUpdated").value(PojoHelper.JOB_EXECUTION_LAST_UPDATE_DEFAULT.toString()))
                .andExpect(jsonPath("$.content[0].status").value(PojoHelper.JOB_EXECUTION_STATUS_DEFAULT))
                .andExpect(jsonPath("$.content[0].version").doesNotExist())
                .andExpect(jsonPath("$.content[0].exitMessage").value(PojoHelper.JOB_EXECUTION_EXIT_MESSAGE_DEFAULT))
                .andExpect(jsonPath("$.content[0].jobConfigurationLocation").doesNotExist())
                .andExpect(jsonPath("$.content[0].parameters[0].name").value(PojoHelper.EXECUTION_PARAMETER_NAME_DEFAULT))
                .andExpect(jsonPath("$.content[0].parameters[0].type").value(PojoHelper.EXECUTION_PARAMETER_TYPE_DEFAULT.name()))
                .andExpect(jsonPath("$.content[0].parameters[0].value").value(PojoHelper.EXECUTION_PARAMETER_DATE_VAL_DEFAULT.toString()))
                .andExpect(jsonPath("$.content[0].parameters[0].identifyCharacter").value(PojoHelper.EXECUTION_PARAMETER_IDENTIFYING_DEFAULT.toString()));
    }


    @Test
    public void testGetJobExecutions() throws Exception {
        when(jobService.getJobExecutions(any(), any())).thenReturn(PojoHelper.createPageJobExecutionDefault());
        mockMvc.perform(get("/management/jobs/executions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(PojoHelper.JOB_EXECUTION_ID_DEFAULT))
                .andExpect(jsonPath("$.content[0].jobName").value(PojoHelper.JOB_NAME_DEFAULT));
    }

    @Test
    public void testGetJobExecution() throws Exception {
        when(jobService.getJobExecution(ArgumentMatchers.eq(PojoHelper.JOB_EXECUTION_ID_DEFAULT))).thenReturn(PojoHelper.createJobExecutionDefault());
        mockMvc.perform(MockMvcRequestBuilders.get("/management/jobs/executions/" + PojoHelper.JOB_EXECUTION_ID_DEFAULT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(PojoHelper.JOB_EXECUTION_ID_DEFAULT))
                .andExpect(jsonPath("$.jobName").value(PojoHelper.JOB_NAME_DEFAULT));
    }


    @Test
    public void testStop() throws Exception {
        when(jobService.stop(ArgumentMatchers.eq(PojoHelper.JOB_EXECUTION_ID_DEFAULT))).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.post("/management/jobs/executions/" + PojoHelper.JOB_EXECUTION_ID_DEFAULT + "/stop"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void testGetJobExecutionStepExecutions() throws Exception {
        when(jobService.getJobExecutionStepExecutions(ArgumentMatchers.eq(PojoHelper.JOB_EXECUTION_ID_DEFAULT))).thenReturn(getTestJobExecutionStepExecution());
        mockMvc.perform(MockMvcRequestBuilders.get("/management/jobs/executions/" + PojoHelper.JOB_EXECUTION_ID_DEFAULT + "/stepexecutions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(PojoHelper.STEP_ID_DEFAULT))
                .andExpect(jsonPath("$.[0].name").value(PojoHelper.STEP_NAME_DEFAULT));
    }

    private List<StepExecution> getTestJobExecutionStepExecution() {
        StepExecution stepExecution = new StepExecution(PojoHelper.STEP_NAME_DEFAULT, PojoHelper.createJobExecutionDefault(), PojoHelper.STEP_ID_DEFAULT);
        return Collections.singletonList(stepExecution);
    }
}
