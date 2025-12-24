package com.kadri.new_platform;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class LegacyWriteAdapter {

    private final JdbcTemplate jdbcTemplate;

    public LegacyWriteAdapter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(PatientRecord patientRecord) {
        jdbcTemplate.update(
                """
                        INSERT INTO health_records
                        (patient_name, patient_ssn, diagnosis, treatment, visit_date)
                        VALUES (?, ?, ?, ?, ?)
                        """,
                patientRecord.getFullName(),
                patientRecord.getSsn(),
                "Derived",
                patientRecord.getMedical_summary(),
                patientRecord.getLastVisit()
        );
    }
}
