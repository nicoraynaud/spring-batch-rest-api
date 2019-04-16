package com.github.nicoraynaud.batch.job;

import com.github.nicoraynaud.batch.repository.JobInstanceEntityRepository;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class DeleteOrphelinJobInstanceTasklet implements Tasklet {

    private final JobInstanceEntityRepository jobInstanceEntityRepository;

    public DeleteOrphelinJobInstanceTasklet(JobInstanceEntityRepository jobInstanceEntityRepository) {
        this.jobInstanceEntityRepository = jobInstanceEntityRepository;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        contribution.incrementWriteCount(jobInstanceEntityRepository.deleteAllByIdNotInJobExecutionEntity());
        return RepeatStatus.FINISHED;
    }

}
