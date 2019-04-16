package com.github.nicoraynaud.batch.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "batch_job_execution_context")
public class JobExecutionContextEntity {

    @Id
    @Column(name = "job_execution_id", updatable = false, insertable = false)
    private long id;
}
