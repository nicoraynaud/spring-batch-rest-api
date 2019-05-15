package com.github.nicoraynaud.batch.domain.entity;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "BATCH_JOB_EXECUTION")
public class JobExecutionEntity {

    @Id
    @Column(name = "JOB_EXECUTION_ID", updatable = false, insertable = false)
    private long id;

    @Column(name = "VERSION", updatable = false, insertable = false)
    private Long version;

    @Column(name = "CREATE_TIME", updatable = false, insertable = false)
    private LocalDateTime createTime;

    @Column(name = "START_TIME", updatable = false, insertable = false)
    private LocalDateTime startTime;

    @Column(name = "END_TIME", updatable = false, insertable = false)
    private LocalDateTime endTime;

    @Column(name = "LAST_UPDATED", updatable = false, insertable = false)
    private LocalDateTime lastUpdated;

    @Column(name = "STATUS", updatable = false, insertable = false)
    private String status;

    @Column(name = "EXIT_CODE", updatable = false, insertable = false)
    private String exitCode;

    @Column(name = "EXIT_MESSAGE", updatable = false, insertable = false)
    private String exitMessage;

    @Column(name = "JOB_CONFIGURATION_LOCATION", updatable = false, insertable = false)
    private String jobConfigurationLocation;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "JOB_INSTANCE_ID", updatable = false, insertable = false)
    private JobInstanceEntity jobInstance;

    @OneToMany(mappedBy = "jobExecutionId", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<JobExecutionParameterEntity> parameters;

    @OneToMany(mappedBy = "jobExecutionId", orphanRemoval = true)
    private List<JobStepExecutionEntity> steps;

    @OneToMany(mappedBy = "id", orphanRemoval = true)
    private List<JobExecutionContextEntity> contexts;

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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExitCode() {
        return exitCode;
    }

    public void setExitCode(String exitCode) {
        this.exitCode = exitCode;
    }

    public String getExitMessage() {
        return exitMessage;
    }

    public void setExitMessage(String exitMessage) {
        this.exitMessage = exitMessage;
    }

    public String getJobConfigurationLocation() {
        return jobConfigurationLocation;
    }

    public void setJobConfigurationLocation(String jobConfigurationLocation) {
        this.jobConfigurationLocation = jobConfigurationLocation;
    }

    public JobInstanceEntity getJobInstance() {
        return jobInstance;
    }

    public void setJobInstance(JobInstanceEntity jobInstance) {
        this.jobInstance = jobInstance;
    }

    public List<JobExecutionParameterEntity> getParameters() {
        return parameters;
    }

    public void setParameters(List<JobExecutionParameterEntity> parameters) {
        this.parameters = parameters;
    }

    public List<JobStepExecutionEntity> getSteps() {
        return steps;
    }

    public void setSteps(List<JobStepExecutionEntity> steps) {
        this.steps = steps;
    }

    public List<JobExecutionContextEntity> getContexts() {
        return contexts;
    }

    public void setContexts(List<JobExecutionContextEntity> contexts) {
        this.contexts = contexts;
    }
}
