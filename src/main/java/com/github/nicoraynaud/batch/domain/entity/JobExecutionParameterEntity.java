package com.github.nicoraynaud.batch.domain.entity;

import com.github.nicoraynaud.batch.exception.JobException;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "BATCH_JOB_EXECUTION_PARAMS")
public class JobExecutionParameterEntity {

    @EmbeddedId
    private JobExecutionParameterEntityId id;

    @Column(name = "JOB_EXECUTION_ID", updatable = false, insertable = false)
    private Long jobExecutionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE_CD", updatable = false, insertable = false)
    private ParameterType type;

    @Column(name = "KEY_NAME", updatable = false, insertable = false)
    private String name;

    @Column(name = "STRING_VAL", updatable = false, insertable = false)
    private String stringVal;

    @Column(name = "DATE_VAL", updatable = false, insertable = false)
    private LocalDateTime dateVal;

    @Column(name = "LONG_VAL", updatable = false, insertable = false)
    private Long longVal;

    @Column(name = "DOUBLE_VAL", updatable = false, insertable = false)
    private Double doubleVal;

    @Column(name = "IDENTIFYING", updatable = false, insertable = false)
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
