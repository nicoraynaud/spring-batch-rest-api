package com.github.nicoraynaud.batch.test.job;

import com.github.nicoraynaud.batch.listener.MCDListener;
import com.github.nicoraynaud.batch.test.item.CustomMultiResourcePartitioner;
import com.github.nicoraynaud.batch.test.item.LogItemWriter;
import com.github.nicoraynaud.batch.test.service.DummyService;
import com.github.nicoraynaud.batch.test.item.DummyItemReader;
import com.github.nicoraynaud.batch.test.item.LogItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Arrays;

@EnableBatchProcessing
@Configuration
public class PartitionJobConfiguration {

    public static final String JOB_PARTITION_TEST_NAME = "partitionerJob";
    public static final String JOB_PARTITION_TEST_STEP_NAME = "stepPartition";

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean(name = JOB_PARTITION_TEST_NAME)
    public Job partitionerJob() {
        return jobBuilderFactory.get("partitionerJob")
                .start(partitionStep())
                .build();
    }

    @Bean
    public Step partitionStep() {
        return stepBuilderFactory.get("partitionStep")
                .partitioner("slaveStep", partitioner())
                .step(slaveStep(null))
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(5);
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setQueueCapacity(5);
        taskExecutor.afterPropertiesSet();
        return taskExecutor;
    }

    @Bean
    public Step slaveStep(MCDListener loggingListener) {
        return stepBuilderFactory.get("slaveStep").<String, String>chunk(1)
                .reader(readerPartition())
                .processor(processorPartition(null))
                .writer(writerPartition())
                .listener(loggingListener)
                .build();
    }


    @Bean
    public CustomMultiResourcePartitioner partitioner() {
        return new CustomMultiResourcePartitioner();
    }

    @Bean
    public Step stepPartition() {
        return stepBuilderFactory.get(JOB_PARTITION_TEST_STEP_NAME)
                .<String, String>chunk(2)
                .reader(readerPartition())
                .processor(processorPartition(null))
                .writer(writerPartition())
                .build();
    }

    @Bean
    public DummyItemReader readerPartition() {
        return new DummyItemReader(Arrays.asList("Bonjour", "Ceci", "est", "ton", "ItemReader", "qui", "parle!"));
    }

    @Bean
    public LogItemProcessor processorPartition(DummyService dummyService) {
        return new LogItemProcessor(dummyService);
    }

    @Bean
    public LogItemWriter writerPartition() {
        return new LogItemWriter();
    }


}
