package com.github.nicoraynaud.batch.dto.mapping;

import com.github.nicoraynaud.batch.domain.JobExecutionParameter;
import com.github.nicoraynaud.batch.dto.JobExecutionParameterInputDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface JobExecutionParameterMapper {

    @Mapping(target = "type", ignore = true)
    @Mapping(target = "identifyCharacter", ignore = true)
    JobExecutionParameter toEntity(JobExecutionParameterInputDTO jobExecutionParameterInputDTO);

    List<JobExecutionParameter> toEntity(List<JobExecutionParameterInputDTO> jobsExecutionParameterInputDTO);

}
