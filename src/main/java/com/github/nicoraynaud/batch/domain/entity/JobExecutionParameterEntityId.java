package com.github.nicoraynaud.batch.domain.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Embeddable
public class JobExecutionParameterEntityId implements Serializable {

    @Column(name = "job_execution_id", updatable = false, insertable = false)
    private Long jobExecutionId;


    @Enumerated(EnumType.STRING)
    @Column(name = "type_cd", updatable = false, insertable = false)
    private JobExecutionParameterEntity.ParameterType type;

    @Column(name = "key_name", updatable = false, insertable = false)
    private String name;

    public Long getJobExecutionId() {
        return jobExecutionId;
    }

    public void setJobExecutionId(Long jobExecutionId) {
        this.jobExecutionId = jobExecutionId;
    }

    public JobExecutionParameterEntity.ParameterType getType() {
        return type;
    }

    public void setType(JobExecutionParameterEntity.ParameterType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
