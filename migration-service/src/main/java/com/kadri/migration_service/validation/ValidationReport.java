package com.kadri.migration_service.validation;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ValidationReport {
    private final Instant startedAt;
    private Instant finishedAt;
    private long sourceCount;
    private long targetCount;
    private int sampleSize;
    private int samplesValidated;
    private boolean success;
    private final List<String> errors = new ArrayList<>();

    public ValidationReport() {
        this.startedAt = Instant.now();
        this.success = true;
    }

    public void markFinished(){
        this.finishedAt = Instant.now();
    }

    public void setSourceCount(long sourceCount) {
        this.sourceCount = sourceCount;
    }

    public void setTargetCount(long targetCount) {
        this.targetCount = targetCount;
    }

    public void setSampleSize(int sampleSize){
        this.sampleSize = sampleSize;
    }

    public void incrementSamplesValidated() {
        this.samplesValidated++;
    }

    public void addError(String error){
        this.errors.add(error);
        this.success = false;
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    public Instant getFinishedAt() {
        return finishedAt;
    }

    public long getSourceCount() {
        return sourceCount;
    }

    public long getTargetCount() {
        return targetCount;
    }

    public int getSampleSize() {
        return sampleSize;
    }

    public int getSamplesValidated() {
        return samplesValidated;
    }

    public boolean isSuccess() {
        return success;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void apply(CountValidationResult result) {
        this.sourceCount = result.sourceCount();
        this.targetCount = result.targetCount();

        if (!result.success()) {
            addError(result.errorMessage());
        }
    }

    public void apply(SampleValidationResult result) {
        this.sampleSize = result.sampleSize();
        this.samplesValidated = result.validatedSample();

        if (!result.success()) {
            addError(result.errorMessage());
        }
    }

}
