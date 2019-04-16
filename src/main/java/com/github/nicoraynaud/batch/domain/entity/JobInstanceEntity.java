package com.github.nicoraynaud.batch.domain.entity;


import javax.persistence.*;

@Entity
@Table(name = "batch_job_instance")
public class JobInstanceEntity {

    @Id
    @Column(name = "job_instance_id", updatable = false, insertable = false)
    private long id;

    @Column(name = "version", updatable = false, insertable = false)
    private Long version;

    @Column(name = "job_name", updatable = false, insertable = false)
    private String jobName;

    @Column(name = "job_key", updatable = false, insertable = false)
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
