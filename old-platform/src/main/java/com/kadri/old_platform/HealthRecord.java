package com.kadri.old_platform;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "health_records")
public class HealthRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String patientName;
    private String PatientSsn;
    private String diagnosis;
    private String treatment;
    private LocalDate visitDate;
}
