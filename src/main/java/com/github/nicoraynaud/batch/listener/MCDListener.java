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

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import static com.github.nicoraynaud.batch.utils.MDCUtils.hasJobMarkerInJob;
import static com.github.nicoraynaud.batch.utils.MDCUtils.insertJobValues;
import static com.github.nicoraynaud.batch.utils.MDCUtils.insertStepValues;
import static com.github.nicoraynaud.batch.utils.MDCUtils.removeJobMarkerInJob;
import static com.github.nicoraynaud.batch.utils.MDCUtils.removeJobValues;
import static com.github.nicoraynaud.batch.utils.MDCUtils.removeStepValues;
import static com.github.nicoraynaud.batch.utils.MDCUtils.setJobMarkerInJob;

/**
 * This listener writes the job log file name to the MDC so that it can be picked up by the logging
 * framework for logging to it. It's a {@link JobExecutionListener} and a {@link StepExecutionListener}
 * because in partitioning we may have a lot of {@link StepExecution}s running in different threads.
 * Due to the fact that the afterStep - method would remove the variable from the MDC in single threaded
 * execution we need to use a marker to know if it in the same threa than the job.
 * Note that, of the three local parallelization features in Spring Batch, log file separation only
 * works for partitioning and parallel step, not for multi-threaded step.
 * <p>
 *
 * @author Tobias Flohre
 *         <p>
 *         Cette classe à été copié puis modifié de
 * @see {https://github.com/codecentric/spring-boot-starter-batch-web/blob/master/src/main/java/de/codecentric/batch/listener/LoggingListener.java}
 */
@Component
@ConditionalOnProperty(value = "batch.enable", matchIfMissing = true)
public class MCDListener implements JobExecutionListener, StepExecutionListener, Ordered {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        insertJobValues(jobExecution);
        setJobMarkerInJob();
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        removeJobValues();
        removeJobMarkerInJob();
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        if (!hasJobMarkerInJob()) {
            insertJobValues(stepExecution.getJobExecution());
        }
        insertStepValues(stepExecution);
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        if (!hasJobMarkerInJob()) {
            removeJobValues();
        }
        removeStepValues();
        return null;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

}
