package com.kadri.migration_service.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "patient_records")
@AllArgsConstructor
public class PatientRecord {

    @Id
    private UUID id;
    private String fullName;
    private String ssn;

    @Column(length = 2000)
    private String medical_summary;
    private LocalDate lastVisit;
}
