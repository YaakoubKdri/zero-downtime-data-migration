package com.kadri.new_platform;

import com.fasterxml.jackson.core.JsonFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatientService {
    private final PatientRecordRepository repository;
    private final DualWriteConfig dualWriteConfig;
    private final LegacyWriteAdapter legacyAdapter;

    @Transactional
    public void save(PatientRecord patientRecord){
        repository.save(patientRecord);

        if(dualWriteConfig.isEnabled()){
            legacyAdapter.save(patientRecord);
        }
    }
}
