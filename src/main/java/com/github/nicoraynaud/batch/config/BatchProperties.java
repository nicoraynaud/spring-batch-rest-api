package com.github.nicoraynaud.batch.config;

import com.github.nicoraynaud.batch.domain.JobDescription;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component("batchProperties")
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "batch")
public class BatchProperties {
    private List<JobDescription> jobs = new ArrayList<>();

    private boolean enableStepListener = true;

    private Map<String, JobDescription> jobsMap;

    @PostConstruct
    public void init() {
        jobsMap = jobs.stream().collect(Collectors.toMap(JobDescription::getName, Function.identity()));
    }

    public JobDescription getJob(String jobName) {
        return jobsMap.get(jobName);
    }

    public List<JobDescription> getJobs() {
        return jobs;
    }

    public void setJobs(List<JobDescription> jobs) {
        this.jobs = jobs;
    }

    public boolean isEnableStepListener() {
        return enableStepListener;
    }

    public void setEnableStepListener(boolean enableStepListener) {
        this.enableStepListener = enableStepListener;
    }

}
