package com.kadri.migration_service.validation;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class CountValidationService {

    private final DataSource sourceDataSource;
    private final DataSource targetDataSource;

    public CountValidationService(
            @Qualifier("sourceDataSource") DataSource source,
            @Qualifier("targetDataSource") DataSource target) {
        this.sourceDataSource = source;
        this.targetDataSource = target;
    }

    public CountValidationResult validate() {
        try {
            long source = count(sourceDataSource, "health_records");
            long target = count(targetDataSource, "patient_records");

            if (source != target) {
                return new CountValidationResult(
                        source, target, false,
                        "Count mismatch"
                );
            }

            return new CountValidationResult(source, target, true, null);

        } catch (Exception e) {
            return new CountValidationResult(0, 0, false, e.getMessage());
        }
    }

    private long count(DataSource ds, String table) throws Exception {
        try (var c = ds.getConnection();
             var s = c.createStatement();
             var rs = s.executeQuery("SELECT COUNT(*) FROM " + table)) {

            rs.next();
            return rs.getLong(1);
        }
    }
}