package com.kadri.migration_service.batch.processor;

import com.kadri.migration_service.domain.HealthRecord;
import com.kadri.migration_service.domain.PatientRecord;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class HealthToPatientProcessor implements ItemProcessor<HealthRecord, PatientRecord> {

    @Override
    public PatientRecord process(HealthRecord healthRecord) throws Exception {
        return new PatientRecord(
                UUID.randomUUID(),
                healthRecord.getPatientName(),
                healthRecord.getPatientSsn(),
                healthRecord.getDiagnosis() + " | " + healthRecord.getTreatment(),
                healthRecord.getVisitDate()
        );
    }
}
