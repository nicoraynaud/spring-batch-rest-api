package com.github.nicoraynaud.batch.job;

import com.github.nicoraynaud.batch.repository.JobExecutionEntityRepository;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.time.LocalDateTime;


public class DeleteCascadeJobExecutionTasklet implements Tasklet, StepExecutionListener {


    private final JobExecutionEntityRepository jobExecutionEntityRepository;
    private LocalDateTime dateSuppression;
    private Long jobIdEnCours;

    public DeleteCascadeJobExecutionTasklet(JobExecutionEntityRepository jobExecutionEntityRepository, LocalDateTime dateSuppression) {
        this.jobExecutionEntityRepository = jobExecutionEntityRepository;
        this.dateSuppression = dateSuppression;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        contribution.incrementWriteCount(jobExecutionEntityRepository.deleteByCreateTimeBeforeAndIdNot(dateSuppression, jobIdEnCours));
        return RepeatStatus.FINISHED;
    }


    @Override
    public void beforeStep(StepExecution stepExecution) {
        this.jobIdEnCours = stepExecution.getJobExecutionId();

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }
}
