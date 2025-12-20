package com.kadri.migration_service.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class HealthRecord {
    private long id;
    private String patientName;
    private String PatientSsn;
    private String diagnosis;
    private String treatment;
    private LocalDate visitDate;
}
