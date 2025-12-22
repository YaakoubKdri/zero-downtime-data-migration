package com.kadri.migration_service.validation;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class MigrationValidationRunner {

    private final CountValidationService countService;
    private final SampleHashValidationService sampleService;

    public MigrationValidationRunner(
            CountValidationService countService,
            SampleHashValidationService sampleService) {
        this.countService = countService;
        this.sampleService = sampleService;
    }

    @PostConstruct
    public void run() {
        ValidationReport report = new ValidationReport();

        CountValidationResult countResult = countService.validate();
        report.apply(countResult);

        SampleValidationResult sampleResult = sampleService.validate(100);
        report.apply(sampleResult);

        report.markFinished();

        if (!report.isSuccess()) {
            throw new IllegalStateException(
                    "Migration validation failed: " + report.getErrors()
            );
        }
    }
}