package com.github.nicoraynaud.batch.dto.mapping;

import com.github.nicoraynaud.batch.dto.StepExecutionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.batch.core.StepExecution;

import java.util.List;

@Mapper(componentModel = "spring", uses = {DateMapper.class} )
public interface StepExecutionMapper {

    @Mapping(source = "stepName", target = "name")
    @Mapping(source = "exitStatus.exitCode", target = "exitCode")
    @Mapping(source = "exitStatus.exitDescription", target = "exitMessage")
    StepExecutionDTO toDTO(StepExecution object);

    List<StepExecutionDTO> toDTO(List<StepExecution> object);
}
