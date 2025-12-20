package com.kadri.migration_service.config;

import com.kadri.migration_service.domain.HealthRecord;
import com.kadri.migration_service.domain.PatientRecord;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class JobConfig {

    @Bean
    public Step migrationStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReader<HealthRecord> reader,
            ItemProcessor<HealthRecord, PatientRecord> processor,
            ItemWriter<PatientRecord> writer){
        return new StepBuilder("migrationStep", jobRepository)
                .<HealthRecord, PatientRecord>chunk(1000)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .transactionManager(transactionManager)
                .build();
    }

    @Bean
    public Job migrationJob(JobRepository jobRepository, Step migrationStep){
        return new JobBuilder("migrationJob", jobRepository)
                .start(migrationStep)
                .build();
    }
}
