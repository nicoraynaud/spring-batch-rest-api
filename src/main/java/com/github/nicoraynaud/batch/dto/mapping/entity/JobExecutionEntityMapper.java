package com.github.nicoraynaud.batch.dto.mapping.entity;

import com.github.nicoraynaud.batch.domain.entity.JobExecutionEntity;
import com.github.nicoraynaud.batch.dto.JobExecutionDTO;
import com.github.nicoraynaud.batch.dto.mapping.DateMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {DateMapper.class, JobExecutionParameterEntityMapper.class})
public interface JobExecutionEntityMapper {

    @Mapping(source = "jobInstance.jobName", target = "jobName")
    JobExecutionDTO toDTO(JobExecutionEntity jobExecutionEntity);
}
