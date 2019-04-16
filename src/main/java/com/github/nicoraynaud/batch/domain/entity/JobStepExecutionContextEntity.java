package com.github.nicoraynaud.batch.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "batch_step_execution_context")
public class JobStepExecutionContextEntity {

    @Id
    @Column(name = "step_execution_id", updatable = false, insertable = false)
    private long id;
}
