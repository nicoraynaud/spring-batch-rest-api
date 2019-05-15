package com.github.nicoraynaud.batch.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BATCH_STEP_EXECUTION_CONTEXT")
public class JobStepExecutionContextEntity {

    @Id
    @Column(name = "STEP_EXECUTION_ID", updatable = false, insertable = false)
    private long id;
}
