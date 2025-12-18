package com.kadri.new_platform;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PatientRecordRepository extends JpaRepository<PatientRecord, UUID> {
}
