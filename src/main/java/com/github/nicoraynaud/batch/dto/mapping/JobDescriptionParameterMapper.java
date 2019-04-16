package com.github.nicoraynaud.batch.dto.mapping;

import com.github.nicoraynaud.batch.domain.JobDescriptionParameter;
import com.github.nicoraynaud.batch.dto.JobDescriptionParameterDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface JobDescriptionParameterMapper {

    JobDescriptionParameterDTO toDTO(JobDescriptionParameter jobDescriptionParameter);

    List<JobDescriptionParameterDTO> toDTO(List<JobDescriptionParameter> jobsDescription);

}
