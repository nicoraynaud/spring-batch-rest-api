package com.github.nicoraynaud.batch.test.job;

import com.github.nicoraynaud.batch.test.item.DummyItemReader;
import com.github.nicoraynaud.batch.test.item.LogItemProcessor;
import com.github.nicoraynaud.batch.test.item.LogItemWriter;
import com.github.nicoraynaud.batch.test.service.DummyService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;


@Configuration
public class SimpleJobConfiguration {

    public static final String JOB_TEST_NAME = "job";
    public static final String JOB_TEST_STEP_NAME = "step";

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return jobBuilderFactory.get(JOB_TEST_NAME)
                .incrementer(new RunIdIncrementer())
                .start(step())
                .build();
    }


    @Bean
    public Step step() {
        return stepBuilderFactory.get(JOB_TEST_STEP_NAME)
                .<String, String>chunk(3)
                .reader(reader())
                .processor(processor(null, null))
                .writer(writer())
                .build();
    }

    @Bean
    public LogItemWriter writer() {
        return new LogItemWriter();
    }

    @JobScope
    @Bean
    public LogItemProcessor processor(DummyService dummyService, @Value("#{jobParameters['threadSleepTime']}") Long threadSleepTime) {
        return new LogItemProcessor(dummyService, threadSleepTime);
    }


    @Bean
    public DummyItemReader reader() {
        return new DummyItemReader(Arrays.asList("Good", "morning!","This","is","your","ItemReader","speaking!"));
    }

}
