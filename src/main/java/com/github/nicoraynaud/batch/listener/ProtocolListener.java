/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.nicoraynaud.batch.listener;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.Map.Entry;

/**
 * This listener adds a protocol header and a protocol summary to the log.
 *
 * @author Tobias Flohre
 * @author Dennis Schulte
 *         Cette classe à été copié puis modifié de
 * @see {https://github.com/codecentric/spring-boot-starter-batch-web/blob/master/src/main/java/de/codecentric/batch/listener/LoggingListener.java}
 */
@Component
@ConditionalOnProperty(value = "batch.enable", matchIfMissing = true)
public class ProtocolListener implements JobExecutionListener, Ordered {

    protected static final int OFFSET_PROTOCOL_LISTENER_HIGHER_PRIORITY = 10;
    private static final int DEFAULT_WIDTH = 80;
    private static final Log LOGGER = LogFactory.getLog(ProtocolListener.class);

    @Override
    public void afterJob(JobExecution jobExecution) {
        StringBuilder protocol = new StringBuilder();
        protocol.append("\n");
        protocol.append(createFilledLine('*'));
        protocol.append(createFilledLine('-'));
        protocol.append("Protocol for ").append(jobExecution.getJobInstance().getJobName()).append(" \n");
        protocol.append("  Started:      ").append(jobExecution.getStartTime()).append("\n");
        protocol.append("  Finished:     ").append(jobExecution.getEndTime()).append("\n");
        protocol.append("  Duration:     ").append(jobExecution.getEndTime().getTime() - jobExecution.getStartTime().getTime()).append(" ms\n");
        protocol.append("  Exit-Code:    ").append(jobExecution.getExitStatus().getExitCode()).append("\n");
        protocol.append("  Exit-Descr:   ").append(jobExecution.getExitStatus().getExitDescription()).append("\n");
        protocol.append("  Status:       ").append(jobExecution.getStatus()).append("\n");
        protocol.append("  Content of Job-ExecutionContext:\n");
        jobExecution.getExecutionContext().entrySet().forEach(entry -> protocol.append("  ").append(entry.getKey()).append("=").append(entry.getValue()).append("\n"));
        protocol.append("  Job-Parameter: \n");
        JobParameters jp = jobExecution.getJobParameters();
        jp.getParameters().forEach((key, value) -> protocol.append("  ").append(key).append("=").append(value).append("\n"));
        protocol.append(createFilledLine('-'));
        for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
            protocol.append("Step ").append(stepExecution.getStepName()).append(" \n");
            protocol.append("  Duration:     ").append(stepExecution.getEndTime().getTime() - stepExecution.getStartTime().getTime()).append(" ms\n");
            protocol.append("  ReadCount:    ").append(stepExecution.getReadCount()).append("\n");
            protocol.append("  WriteCount:   ").append(stepExecution.getWriteCount()).append("\n");
            protocol.append("  Commits:      ").append(stepExecution.getCommitCount()).append("\n");
            protocol.append("  SkipCount:    ").append(stepExecution.getSkipCount()).append("\n");
            protocol.append("  Rollbacks:    ").append(stepExecution.getRollbackCount()).append("\n");
            protocol.append("  Filter:       ").append(stepExecution.getFilterCount()).append("\n");
            protocol.append("  Content of Step-ExecutionContext:\n");
            for (Entry<String, Object> entry : stepExecution.getExecutionContext().entrySet()) {
                protocol.append("  ").append(entry.getKey()).append("=").append(entry.getValue()).append("\n");
            }
            protocol.append(createFilledLine('-'));
        }
        protocol.append(createFilledLine('*'));
        LOGGER.info(protocol.toString());

    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        StringBuilder protocol = new StringBuilder();
        protocol.append("\n");
        protocol.append(createFilledLine('-'));
        protocol.append("Job ").append(jobExecution.getJobInstance().getJobName()).append(" started with Job-Execution-Id ").append(jobExecution.getId()).append(" \n");
        protocol.append("Job-Parameter: \n");
        JobParameters jp = jobExecution.getJobParameters();
        jp.getParameters().forEach((key, value) -> protocol.append("  ").append(key).append("=").append(value).append("\n"));
        protocol.append(createFilledLine('-'));
        LOGGER.info(protocol.toString());
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + OFFSET_PROTOCOL_LISTENER_HIGHER_PRIORITY;
    }

    /**
     * Create line with defined char
     */
    private String createFilledLine(char filler) {
        return StringUtils.leftPad("", DEFAULT_WIDTH, filler) + "\n";
    }

}
