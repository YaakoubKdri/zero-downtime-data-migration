package com.kadri.migration_service.validation;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

@Service
public class SampleHashValidationService {

    private final DataSource sourceDataSource;
    private final DataSource targetDataSource;

    public SampleHashValidationService(
            @Qualifier("sourceDataSource") DataSource source,
            @Qualifier("targetDataSource") DataSource target) {
        this.sourceDataSource = source;
        this.targetDataSource = target;
    }

    public SampleValidationResult validate(int sampleSize) {
        int validated = 0;

        try (var sc = sourceDataSource.getConnection();
             var ss = sc.createStatement();
             var rs = ss.executeQuery(
                     "SELECT * FROM health_records ORDER BY RAND() LIMIT " + sampleSize)) {

            while (rs.next()) {
                validateSingle(rs);
                validated++;
            }

            return new SampleValidationResult(
                    sampleSize, validated, true, null
            );

        } catch (Exception e) {
            return new SampleValidationResult(
                    sampleSize, validated, false, e.getMessage()
            );
        }
    }

    private void validateSingle(ResultSet rs) throws Exception {

        String ssn = rs.getString("patient_ssn");
        String diagnosis = rs.getString("diagnosis");
        String treatment = rs.getString("treatment");

        String expectedMedicalSummary = diagnosis + " | " + treatment;

        String expectedHash = hash(expectedMedicalSummary);

        try (Connection tc = targetDataSource.getConnection();
             PreparedStatement ps = tc.prepareStatement(
                     "SELECT medical_summary FROM patient_records WHERE ssn = ?")) {

            ps.setString(1, ssn);

            try (ResultSet tr = ps.executeQuery()) {

                if (!tr.next()) {
                    throw new IllegalStateException(
                            "Validation failed: missing target record for SSN=" + ssn
                    );
                }

                String actualMedicalSummary = tr.getString("medical_summary");
                String actualHash = hash(actualMedicalSummary);

                if (!expectedHash.equals(actualHash)) {
                    throw new IllegalStateException(
                            "Validation failed for SSN=" + ssn +
                                    " | expected='" + expectedMedicalSummary +
                                    "', actual='" + actualMedicalSummary + "'"
                    );
                }
            }
        }
    }

    private String hash(String value) throws Exception{
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(value.getBytes(StandardCharsets.UTF_8));

            StringBuilder hex = new StringBuilder();
            for (byte b : hashedBytes) {
                hex.append(String.format("%02x", b));
            }

            return hex.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 algorithm not available", e);
        }
    }

}
