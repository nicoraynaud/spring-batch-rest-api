package com.github.nicoraynaud.batch.domain.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "BATCH_STEP_EXECUTION")
public class JobStepExecutionEntity {

    @Id
    @Column(name = "STEP_EXECUTION_ID", updatable = false, insertable = false)
    private long id;

    @Column(name = "JOB_EXECUTION_ID", updatable = false, insertable = false)
    private Long jobExecutionId;

    @OneToMany(mappedBy = "id", orphanRemoval = true)
    private List<JobStepExecutionContextEntity> contexts;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getJobExecutionId() {
        return jobExecutionId;
    }

    public void setJobExecutionId(Long jobExecutionId) {
        this.jobExecutionId = jobExecutionId;
    }

    public List<JobStepExecutionContextEntity> getContexts() {
        return contexts;
    }

    public void setContexts(List<JobStepExecutionContextEntity> contexts) {
        this.contexts = contexts;
    }
}
