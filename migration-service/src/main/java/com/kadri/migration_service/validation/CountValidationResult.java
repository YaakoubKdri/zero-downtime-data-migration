package com.kadri.migration_service.validation;

public record CountValidationResult(
        long sourceCount,
        long targetCount,
        boolean success,
        String errorMessage
) {}
