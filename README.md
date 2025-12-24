# Zero-Downtime Data Migration Simulator

This project simulates a **real-world zero-downtime data migration** from a legacy platform to a modern platform using **Java, Spring Boot, and Spring Batch**.

It demonstrates how to:
- Migrate historical data safely
- Validate data correctness
- Handle live traffic during migration (zero downtime)
- Gradually retire a legacy system

---

## 🏗 Architecture Overview

The repository contains **three independent Spring Boot applications** managed in a single monorepo:

zero-downtime-data-migration/  
├── old-platform/ # Legacy system (MySQL)  
├── new-platform/ # Modern system (PostgreSQL)  
├── migration-service/ # Batch migration + validation  


During migration, **new traffic is handled by the New Platform using dual writes** to guarantee zero downtime.

---

## 📦 Applications

### 1️⃣ Old Platform (Legacy)

**Purpose**
- Simulates a legacy system (Oracle)
- Schema is treated as **immutable**
- Read-only from a migration perspective

**Tech**
- Spring Boot
- MySQL
- JPA (no schema ownership)

---

### 2️⃣ New Platform (Modern)

**Purpose**
- Represents the modernized target system
- Handles **live traffic**
- Implements **dual writes** during migration

**Key responsibility**
- Writes to both:
    - New database (primary)
    - Old database (temporary, during migration)

**Tech**
- Spring Boot
- PostgreSQL
- JPA
- Feature flags for dual writes

---

### 3️⃣ Migration Service

**Purpose**
- Performs **historical data backfill**
- Validates migration correctness
- Produces audit-style validation results

**Key features**
- Spring Batch (chunk-based, restartable)
- JDBC reader (source)
- JPA writer (target)
- Post-migration validation:
    - Record count check
    - Sample-based deep validation (hash comparison)

**Tech**
- Spring Boot
- Spring Batch
- MySQL + PostgreSQL

---

## 🔁 Zero-Downtime Strategy (Phase 5)

This project uses the **Strangler Fig pattern with Dual Writes**.

### Migration timeline
T0 Old platform only  
T1 New platform live (dual write ON)  
T2 Batch migration runs  
T3 Validation passes  
T4 Dual write OFF  
T5 Old platform retired  

- **Migration Service** backfills historical data
- **New Platform** handles live traffic and dual writes
- **Validation** ensures correctness before cutover

---

## ✅ Data Validation (Phase 4)

Validation is executed by the **Migration Service** after batch completion:

1. **Count Validation**
    - Ensures source and target record counts match

2. **Deep Validation (Sampling)**
    - Randomly samples records
    - Re-applies transformation logic
    - Compares hashed values to detect data drift

A structured `ValidationReport` is produced for auditability.

---

## 🚀 How to Run (Local – High Level)

1. Start MySQL and PostgreSQL
2. Run **old-platform**
3. Run **new-platform**
4. Run **migration-service**
5. Verify validation results in logs

---

## 🎯 What This Project Demonstrates

- Realistic data migration patterns
- Separation of responsibilities across services
- Production-grade Spring Batch usage
- Zero-downtime migration strategy
- Validation-first mindset
---

## 📄 License

MIT

