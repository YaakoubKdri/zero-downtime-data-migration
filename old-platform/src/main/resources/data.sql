INSERT INTO health_records
(patient_name, patient_ssn, diagnosis, treatment, visit_date)
SELECT
    CONCAT('Patient_', n),
    CONCAT('SSN-', n),
    'Hypertension',
    'Medication',
    CURDATE()
FROM (
    SELECT @row := @row + 1 AS n
    FROM information_schema.tables,
        (SELECT @row := 0) r
    LIMIT 5000
) numbers;