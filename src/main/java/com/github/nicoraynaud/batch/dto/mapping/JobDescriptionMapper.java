package com.github.nicoraynaud.batch.dto.mapping;

import com.github.nicoraynaud.batch.domain.JobDescription;
import com.github.nicoraynaud.batch.dto.JobDescriptionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {JobDescriptionParameterMapper.class})
public interface JobDescriptionMapper {

    @Mapping(target = "lastExecutionStatus", ignore = true)
    JobDescriptionDTO toDTO(JobDescription jobDescription);

    List<JobDescriptionDTO> toDTO(List<JobDescription> jobDescription);
}
