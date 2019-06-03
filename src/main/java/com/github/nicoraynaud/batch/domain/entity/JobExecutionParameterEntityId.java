package com.github.nicoraynaud.batch.domain.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class JobExecutionParameterEntityId implements Serializable {

    @Column(name = "JOB_EXECUTION_ID", updatable = false, insertable = false)
    private Long jobExecutionId;


    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE_CD", updatable = false, insertable = false)
    private JobExecutionParameterEntity.ParameterType type;

    @Column(name = "KEY_NAME", updatable = false, insertable = false)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobExecutionParameterEntityId that = (JobExecutionParameterEntityId) o;
        return Objects.equals(jobExecutionId, that.jobExecutionId) &&
                type == that.type &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobExecutionId, type, name);
    }
}
