package com.github.nicoraynaud.batch.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class JobDescriptionDTO implements Serializable {

    private String name;
    private String description;
    private String lastExecutionStatus;
    private List<JobDescriptionParameterDTO> parameters = new ArrayList<>();

    public JobDescriptionDTO() {
    }

    public JobDescriptionDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLastExecutionStatus() {
        return lastExecutionStatus;
    }

    public void setLastExecutionStatus(String lastExecutionStatus) {
        this.lastExecutionStatus = lastExecutionStatus;
    }

    public List<JobDescriptionParameterDTO> getParameters() {
        return parameters;
    }

    public void setParameters(List<JobDescriptionParameterDTO> parameters) {
        this.parameters = parameters;
    }

    public void addToParametersJobDTO(JobDescriptionParameterDTO jobDescriptionParameterDTO) {
        this.parameters.add(jobDescriptionParameterDTO);
    }
}
