CREATE TABLE patient_records(
    id UUID PRIMARY KEY,
    full_name VARCHAR(100),
    ssn VARCHAR(20),
    medical_summary TEXT,
    last_visit DATE
);