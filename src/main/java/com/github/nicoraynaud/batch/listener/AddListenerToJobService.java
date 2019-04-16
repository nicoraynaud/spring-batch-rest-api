package com.github.nicoraynaud.batch.listener;

import com.github.nicoraynaud.batch.config.BatchProperties;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.job.AbstractJob;
import org.springframework.batch.core.step.AbstractStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * This service adds listeners to jobs. Listeners provided by beans implementing
 * ListenerProvider are added automatically.
 *
 * @author Tobias Flohre
 */
@Component
@ConditionalOnProperty(value = "batch.enable", matchIfMissing = true)
public class AddListenerToJobService {

    private final BatchProperties batchProperties;
    /**
     * All beans implementing ListenerProvider are injected here.
     */
    private final Set<ListenerProvider> listenerProviders;

    private ProtocolListener protocolListener;
    private MCDListener loggingListener;

    public AddListenerToJobService(BatchProperties batchProperties,
                                   @Autowired(required = false) Set<ListenerProvider> listenerProviders,
                                   ProtocolListener protocolListener,
                                   MCDListener loggingListener) {
        this.batchProperties = batchProperties;
        this.listenerProviders = listenerProviders;
        this.protocolListener = protocolListener;
        this.loggingListener = loggingListener;
    }

    public void addListenerToJob(AbstractJob job) {
        job.registerJobExecutionListener(protocolListener);
        job.registerJobExecutionListener(loggingListener);
        addListenerToStep(job, loggingListener);

        if (listenerProviders != null) {
            for (ListenerProvider listenerProvider : listenerProviders) {
                for (JobExecutionListener jobExecutionListener : listenerProvider.jobExecutionListeners()) {
                    job.registerJobExecutionListener(jobExecutionListener);
                }

                for (StepExecutionListener stepExecutionListener : listenerProvider.stepExecutionListeners()) {
                    addListenerToStep(job, stepExecutionListener);
                }
            }
        }

    }

    private void addListenerToStep(AbstractJob job, StepExecutionListener loggingListener) {
        if (batchProperties.isEnableStepListener()) {
            for (String stepName : job.getStepNames()) {
                AbstractStep step = (AbstractStep) job.getStep(stepName);
                step.registerStepExecutionListener(loggingListener);
            }
        }
    }
}