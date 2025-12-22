package com.kadri.migration_service.validation;

public record SampleValidationResult(
        int
        sampleSize,
        int validatedSample,
        boolean success,
        String errorMessage
) {}
