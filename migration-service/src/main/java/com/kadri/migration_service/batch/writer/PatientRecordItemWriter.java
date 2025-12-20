package com.kadri.migration_service.batch.writer;

import com.kadri.migration_service.domain.PatientRecord;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.infrastructure.item.database.JdbcPagingItemReader;
import org.springframework.batch.infrastructure.item.database.JpaItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PatientRecordItemWriter {

    @Bean
    public JpaItemWriter<PatientRecord> patientRecordWriter(EntityManagerFactory entityManagerFactory){
        return new JpaItemWriter<>(entityManagerFactory);
    }
}
