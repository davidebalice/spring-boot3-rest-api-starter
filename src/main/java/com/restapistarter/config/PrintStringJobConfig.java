package com.restapistarter.config;


/*
package com.springboot.catalogue;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.repeat.RepeatPolicy;
import org.springframework.batch.repeat.support.CronTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class PrintStringJobConfig {

    @Autowired
    private JobRepository jobRepository;

    @Bean
    public Job printStringJob() {
        return new JobBuilder("printStringJob", jobRepository)
                .start(printStringStep())
                .build();
    }

    @Bean
    public Step printStringStep() {
        return new Step("printStringStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("Printing string every 2 minutes!");
                    return RepeatPolicy.FINISHED;  // Indicate task completion
                })
                .trigger(new CronTrigger("0 0/2 * * * ?"))  // Schedule every 2 minutes
                .build();
    }








}
*/