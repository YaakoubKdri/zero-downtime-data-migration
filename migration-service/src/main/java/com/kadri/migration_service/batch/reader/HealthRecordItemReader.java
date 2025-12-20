package com.kadri.migration_service.batch.reader;

import com.kadri.migration_service.domain.HealthRecord;
import org.springframework.batch.infrastructure.item.database.JdbcPagingItemReader;
import org.springframework.batch.infrastructure.item.database.Order;
import org.springframework.batch.infrastructure.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class HealthRecordItemReader {

    @Bean
    public JdbcPagingItemReader<HealthRecord> healthRecordReader(DataSource sourceDataSource) throws Exception {
        //Map<String, Object> params = new HashMap<>();
        return new JdbcPagingItemReaderBuilder<HealthRecord>()
                .name("healthRecordReader")
                .dataSource(sourceDataSource)
                .selectClause("SELECT id, patient_name, patient_ssn, diagnosis, treatment, visit_date")
                .fromClause("FROM health_records")
                .sortKeys(Map.of("id", Order.ASCENDING))
                .rowMapper((rs, rowNum) -> {
                    return HealthRecord.builder()
                            .id(rs.getLong("id"))
                            .patientName(rs.getString("patient_name"))
                            .PatientSsn(rs.getString("patient_ssn"))
                            .diagnosis(rs.getString("diagnosis"))
                            .treatment(rs.getString("treatment"))
                            .visitDate(rs.getDate("visit_date").toLocalDate()).build();
                })
                .pageSize(1000)
                .build();
    }
}
