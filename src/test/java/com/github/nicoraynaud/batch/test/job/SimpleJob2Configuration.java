package com.github.nicoraynaud.batch.test.job;

import com.github.nicoraynaud.batch.test.item.DummyItemReader;
import com.github.nicoraynaud.batch.test.item.LogItemProcessor;
import com.github.nicoraynaud.batch.test.item.LogItemWriter;
import com.github.nicoraynaud.batch.test.service.DummyService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@EnableBatchProcessing
@Configuration
public class SimpleJob2Configuration {

    public static final String JOB2_TEST_NAME = "job2";
    public static final String JOB2_TEST_STEP_NAME = "step2";

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job2() {
        return jobBuilderFactory.get(JOB2_TEST_NAME)
                .start(step2())
                .build();
    }


    @Bean
    public Step step2() {
        return stepBuilderFactory.get(JOB2_TEST_STEP_NAME)
                .<String, String>chunk(2)
                .reader(reader2())
                .processor(processor2(null))
                .writer(writer2())
                .build();
    }

    @Bean
    public LogItemWriter writer2() {
        return new LogItemWriter();
    }

    @Bean
    public LogItemProcessor processor2(DummyService dummyService) {
        return new LogItemProcessor(dummyService);
    }


    @Bean
    public DummyItemReader reader2() {
        return new DummyItemReader(Arrays.asList("Bonjour","Ceci","est","ton","ItemReader","qui", "parle!"));
    }

}
