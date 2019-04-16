package com.github.nicoraynaud.batch.domain.entity;

import com.github.nicoraynaud.batch.exception.JobException;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "batch_job_execution_params")
public class JobExecutionParameterEntity {

    @EmbeddedId
    private JobExecutionParameterEntityId id;

    @Column(name = "job_execution_id", updatable = false, insertable = false)
    private Long jobExecutionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_cd", updatable = false, insertable = false)
    private ParameterType type;

    @Column(name = "key_name", updatable = false, insertable = false)
    private String name;

    @Column(name = "string_val", updatable = false, insertable = false)
    private String stringVal;

    @Column(name = "date_val", updatable = false, insertable = false)
    private LocalDateTime dateVal;

    @Column(name = "long_val", updatable = false, insertable = false)
    private Long longVal;

    @Column(name = "double_val", updatable = false, insertable = false)
    private Double doubleVal;

    @Column(name = "identifying", updatable = false, insertable = false)
    private Character identifying;

    public JobExecutionParameterEntityId getId() {
        return id;
    }

    public void setId(JobExecutionParameterEntityId id) {
        this.id = id;
    }

    public Long getJobExecutionId() {
        return jobExecutionId;
    }

    public void setJobExecutionId(Long jobExecutionId) {
        this.jobExecutionId = jobExecutionId;
    }

    public ParameterType getType() {
        return type;
    }

    public void setType(ParameterType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStringVal() {
        return stringVal;
    }

    public void setStringVal(String stringVal) {
        this.stringVal = stringVal;
    }

    public LocalDateTime getDateVal() {
        return dateVal;
    }

    public void setDateVal(LocalDateTime dateVal) {
        this.dateVal = dateVal;
    }

    public Long getLongVal() {
        return longVal;
    }

    public void setLongVal(Long longVal) {
        this.longVal = longVal;
    }

    public Double getDoubleVal() {
        return doubleVal;
    }

    public void setDoubleVal(Double doubleVal) {
        this.doubleVal = doubleVal;
    }

    public Character getIdentifying() {
        return identifying;
    }

    public void setIdentifying(Character identifying) {
        this.identifying = identifying;
    }

    public String getValue() {
        String value;
        switch (getType()) {
            case STRING:
                value = getStringVal();
                break;
            case DATE:
                value = getDateVal().toString();
                break;
            case LONG:
                value = getLongVal().toString();
                break;
            case DOUBLE:
                value = getDoubleVal().toString();
                break;
            default:
                throw new JobException(String.format("le type de parameter '%s' n'existe pas", getType()));
        }
        return value;
    }

    public enum ParameterType {
        STRING, DATE, LONG, DOUBLE
    }
}
