package com.github.nicoraynaud.batch.helper;

import com.github.nicoraynaud.batch.domain.JobDescription;
import com.github.nicoraynaud.batch.domain.JobDescriptionParameter;
import com.github.nicoraynaud.batch.domain.entity.JobExecutionEntity;
import com.github.nicoraynaud.batch.domain.entity.JobExecutionParameterEntity;
import com.github.nicoraynaud.batch.domain.entity.JobExecutionParameterEntityId;
import com.github.nicoraynaud.batch.domain.entity.JobInstanceEntity;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PojoHelper {

    public static final String JOB_NAME_DEFAULT = "monNom";


    public static final String JOB_DESCRIPTION_DESCRIPTION_DEFAULT = "maDescription";
    public static final String JOB_DESCRIPTION_NAME_DEFAULT = JOB_NAME_DEFAULT;
    public static final String JOB_DESCRIPTION_PARAMETER_NAME_DATE_DEFAULT = "decriptionParameterNameDate";
    public static final String JOB_DESCRIPTION_PARAMETER_TYPE_DATE_DEFAULT = "decriptionParameterTypeDate";
    public static final String JOB_DESCRIPTION_PARAMETER_DEFAULT_VALUE_DEFAULT = "decriptionParameterDefaultValue";

    public static final long JOB_INSTANCE_ID_DEFAULT = 1L;
    public static final long JOB_EXECUTION_ID_DEFAULT = 2L;
    public static final String STEP_NAME_DEFAULT = "StepName";
    public static final long STEP_ID_DEFAULT = 3L;
    public static final String EXECUTION_PARAMETER_NAME_DEFAULT = "executionParameterDate";
    public static final JobExecutionParameterEntity.ParameterType EXECUTION_PARAMETER_TYPE_DEFAULT = JobExecutionParameterEntity.ParameterType.DATE;

    public static final String JOB_INSTANCE_JOB_KEY_DEFAULT = "jobKey";
    public static final Long JOB_INSTANCE_VERSION = 66L;
    public static final LocalDateTime EXECUTION_PARAMETER_DATE_VAL_DEFAULT = LocalDateTime.parse("2007-12-03T10:15:30");
    public static final Character EXECUTION_PARAMETER_IDENTIFYING_DEFAULT = 'Y';
    public static final OffsetDateTime JOB_EXECUTION_CREATE_TIME_DEFAULT = LocalDateTime.parse("2018-12-03T10:15:30").atZone(ZoneOffset.systemDefault()).toOffsetDateTime();
    public static final OffsetDateTime JOB_EXECUTION_START_TIME_DEFAULT = LocalDateTime.parse("2018-12-03T10:15:30").atZone(ZoneOffset.systemDefault()).toOffsetDateTime();
    public static final OffsetDateTime JOB_EXECUTION_END_TIME_DEFAULT = LocalDateTime.parse("2018-12-03T10:15:30").atZone(ZoneOffset.systemDefault()).toOffsetDateTime();
    public static final String JOB_EXECUTION_EXIT_CODE_DEFAULT = "COMPLETED";
    public static final OffsetDateTime JOB_EXECUTION_LAST_UPDATE_DEFAULT = LocalDateTime.parse("2018-12-03T10:15:30").atZone(ZoneOffset.systemDefault()).toOffsetDateTime();
    public static final String JOB_EXECUTION_STATUS_DEFAULT = "Statuss";
    public static final Long JOB_EXECUTION_VERSION_DEFAULT = 63L;
    public static final String JOB_EXECUTION_EXIT_MESSAGE_DEFAULT = "messageExit";
    public static final String JOB_EXECUTION_CONF_LOCATION_DEFAULT = "confLocation";

    private PojoHelper() {
    }

    public static JobExecution createJobExecutionDefault() {
        JobInstance jobInstance = new JobInstance(JOB_INSTANCE_ID_DEFAULT, JOB_NAME_DEFAULT);
        return new JobExecution(jobInstance, JOB_EXECUTION_ID_DEFAULT, null, null);
    }

    public static List<JobDescription> createJobsDescriptionDefault() {
        JobDescription jobDescription = new JobDescription();
        jobDescription.setDescription(JOB_DESCRIPTION_DESCRIPTION_DEFAULT);
        jobDescription.setName(JOB_DESCRIPTION_NAME_DEFAULT);
        jobDescription.setParameters(Collections.singletonList(createJobDescriptionParameterDefault()));
        return Collections.singletonList(jobDescription);
    }

    public static JobDescriptionParameter createJobDescriptionParameterDefault() {
        JobDescriptionParameter parameter = new JobDescriptionParameter();
        parameter.setName(JOB_DESCRIPTION_PARAMETER_NAME_DATE_DEFAULT);
        parameter.setType(JOB_DESCRIPTION_PARAMETER_TYPE_DATE_DEFAULT);
        parameter.setDefaultValue(JOB_DESCRIPTION_PARAMETER_DEFAULT_VALUE_DEFAULT);
        return parameter;
    }


    public static Page<JobExecutionEntity> createPageJobExecutionDefault() {
        List<JobExecutionEntity> jobExecutionEntities = new ArrayList<>();
        JobExecutionEntity jobExecutionEntity = new JobExecutionEntity();
        jobExecutionEntity.setId(JOB_EXECUTION_ID_DEFAULT);
        jobExecutionEntity.setCreateTime(JOB_EXECUTION_CREATE_TIME_DEFAULT.toLocalDateTime());
        jobExecutionEntity.setStartTime(JOB_EXECUTION_START_TIME_DEFAULT.toLocalDateTime());
        jobExecutionEntity.setEndTime(JOB_EXECUTION_END_TIME_DEFAULT.toLocalDateTime());
        jobExecutionEntity.setExitCode(JOB_EXECUTION_EXIT_CODE_DEFAULT);
        jobExecutionEntity.setLastUpdated(JOB_EXECUTION_LAST_UPDATE_DEFAULT.toLocalDateTime());
        jobExecutionEntity.setStatus(JOB_EXECUTION_STATUS_DEFAULT);
        jobExecutionEntity.setVersion(JOB_EXECUTION_VERSION_DEFAULT);
        jobExecutionEntity.setExitMessage(JOB_EXECUTION_EXIT_MESSAGE_DEFAULT);
        jobExecutionEntity.setJobConfigurationLocation(JOB_EXECUTION_CONF_LOCATION_DEFAULT);

        jobExecutionEntity.setJobInstance(createJobInstanceEntityDefault());
        jobExecutionEntity.setParameters(Collections.singletonList(createParameterDefault()));
        jobExecutionEntities.add(jobExecutionEntity);
        return new PageImpl<>(jobExecutionEntities, PageRequest.of(1, 1), 1L);
    }

    public static JobInstanceEntity createJobInstanceEntityDefault() {
        JobInstanceEntity jobInstanceEntity = new JobInstanceEntity();
        jobInstanceEntity.setId(JOB_INSTANCE_ID_DEFAULT);
        jobInstanceEntity.setJobName(JOB_NAME_DEFAULT);
        jobInstanceEntity.setJobKey(JOB_INSTANCE_JOB_KEY_DEFAULT);
        jobInstanceEntity.setVersion(JOB_INSTANCE_VERSION);
        return jobInstanceEntity;
    }

    public static JobExecutionParameterEntity createParameterDefault() {
        JobExecutionParameterEntity parameter = new JobExecutionParameterEntity();
        JobExecutionParameterEntityId jobExecutionParameterEntityId = new JobExecutionParameterEntityId();
        jobExecutionParameterEntityId.setJobExecutionId(JOB_EXECUTION_ID_DEFAULT);
        jobExecutionParameterEntityId.setName(EXECUTION_PARAMETER_NAME_DEFAULT);
        jobExecutionParameterEntityId.setType(EXECUTION_PARAMETER_TYPE_DEFAULT);
        parameter.setId(jobExecutionParameterEntityId);

        parameter.setJobExecutionId(JOB_EXECUTION_ID_DEFAULT);
        parameter.setName(EXECUTION_PARAMETER_NAME_DEFAULT);
        parameter.setDateVal(EXECUTION_PARAMETER_DATE_VAL_DEFAULT);
        parameter.setType(EXECUTION_PARAMETER_TYPE_DEFAULT);
        parameter.setIdentifying(EXECUTION_PARAMETER_IDENTIFYING_DEFAULT);
        return parameter;
    }

}
