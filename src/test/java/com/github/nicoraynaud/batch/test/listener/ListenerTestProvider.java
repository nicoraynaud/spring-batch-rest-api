package com.github.nicoraynaud.batch.test.listener;

import com.github.nicoraynaud.batch.listener.ListenerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;

@Component
public class ListenerTestProvider implements ListenerProvider {


    private final Logger log = LoggerFactory.getLogger(ListenerTestProvider.class);


    @Override
    public Set<JobExecutionListener> jobExecutionListeners() {
        return Collections.singleton(new JobExecutionListener() {
            @Override
            public void beforeJob(JobExecution jobExecution) {
                log.info("before job ListenerTestProvider");
            }

            @Override
            public void afterJob(JobExecution jobExecution) {
                log.info("after job ListenerTestProvider");

            }
        });
    }

    @Override
    public Set<StepExecutionListener> stepExecutionListeners() {
        return Collections.singleton(new StepExecutionListener() {
            @Override
            public void beforeStep(StepExecution stepExecution) {
                log.info("before step ListenerTestProvider");
            }

            @Override
            public ExitStatus afterStep(StepExecution stepExecution) {
                log.info("after step ListenerTestProvider");
                return null;
            }
        });
    }
}
