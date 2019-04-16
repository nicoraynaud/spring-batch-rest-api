package com.github.nicoraynaud.batch.domain;

import java.util.ArrayList;
import java.util.List;

public class JobDescription {

    private String name;
    private String description;
    private List<JobDescriptionParameter> parameters = new ArrayList<>();

    public JobDescription() {
    }

    public JobDescription(String name, String description) {
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

    public List<JobDescriptionParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<JobDescriptionParameter> parameters) {
        this.parameters = parameters;
    }

}
