package com.github.nicoraynaud.batch.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BATCH_JOB_EXECUTION_CONTEXT")
public class JobExecutionContextEntity {

    @Id
    @Column(name = "JOB_EXECUTION_ID", updatable = false, insertable = false)
    private long id;
}
