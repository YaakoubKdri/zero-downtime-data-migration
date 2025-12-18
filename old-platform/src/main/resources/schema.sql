CREATE TABLE health_records (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patient_name VARCHAR(100),
    patient_ssn VARCHAR(20),
    diagnosis VARCHAR(255),
    treatment VARCHAR(255),
    visit_date DATE
);