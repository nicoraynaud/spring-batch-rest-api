package com.github.nicoraynaud.batch.domain.entity;


import javax.persistence.*;

@Entity
@Table(name = "BATCH_JOB_INSTANCE")
public class JobInstanceEntity {

    @Id
    @Column(name = "JOB_INSTANCE_ID", updatable = false, insertable = false)
    private long id;

    @Column(name = "VERSION", updatable = false, insertable = false)
    private Long version;

    @Column(name = "JOB_NAME", updatable = false, insertable = false)
    private String jobName;

    @Column(name = "JOB_KEY", updatable = false, insertable = false)
    private String jobKey;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobKey() {
        return jobKey;
    }

    public void setJobKey(String jobKey) {
        this.jobKey = jobKey;
    }
}
