package com.github.nicoraynaud.batch.dto.mapping.entity;

import com.github.nicoraynaud.batch.domain.entity.JobExecutionParameterEntity;
import com.github.nicoraynaud.batch.dto.JobExecutionParameterDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface JobExecutionParameterEntityMapper {

    @Mapping(source = "identifying", target = "identifyCharacter")
    JobExecutionParameterDTO toDTO(JobExecutionParameterEntity jobExecutionEntity);

    List<JobExecutionParameterDTO> toDTO(List<JobExecutionParameterEntity> jobExecutionEntity);
}
