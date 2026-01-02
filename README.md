# AI-Powered-Hospital-Management-System
A HMS that is comparable to Korle Bu Teaching Hospital

# AI-Powered Enterprise Hospital Management System

## Overview

This repository contains a **production-grade, AI-powered Hospital Management System (HMS)** designed at the scale and operational complexity of **Korle Bu Teaching Hospital**. The system is architected for **tertiary healthcare delivery**, **teaching & research**, **national health insurance workflows (NHIS – Ghana)**, and **high-availability clinical operations**.

The solution is built **from scratch**, optimized for **long-term maintainability**, **high development velocity**, and **enterprise scalability**, using:

* **Backend:** Spring Boot 5 (Java, JDK 25)
* **Frontend:** React + TypeScript
* **Architecture:** Modular Monolith (DDD-aligned) evolving into Microservices
* **AI Layer:** Clinical decision support, automation, analytics
* **Security:** Zero-trust, RBAC, audit-compliant

This README is intentionally exhaustive to eliminate architectural ambiguity and prevent rework during implementation.

---

## Design Philosophy 

* **No over-academic DDD** – practical DDD only
* **Clear bounded contexts**
* **Strict layering rules**
* **Minimal magic frameworks**
* **Explicit entity models**
* **Forward-compatible with microservices**

You will **not get stuck** if you follow this structure.

---

## High-Level Architecture

```
┌──────────────────────────┐
│        React UI          │
│  (ShadCN + RHF + Zod)    │
└───────────┬──────────────┘
            │ REST / JWT
┌───────────▼──────────────┐
│  Spring Boot API Gateway │
└───────────┬──────────────┘
            │
┌───────────▼────────────────────────────────────────────┐
│               Core Hospital Platform                    │
│ (Modular Monolith – DDD Bounded Contexts)               │
│                                                        │
│  • Patient Management                                  │
│  • Clinical Operations                                  │
│  • Staff & HR                                           │
│  • Pharmacy                                             │
│  • Laboratory                                           │
│  • Radiology                                            │
│  • Billing & NHIS                                       │
│  • Inventory & Stores                                   │
│  • Appointments                                         │
│  • EMR                                                  │
│  • AI & Analytics                                       │
└────────────────────────────────────────────────────────┘
            │
┌───────────▼──────────────┐
│   MySQL / PostgreSQL     │
└──────────────────────────┘
```

---

## Architectural Pattern to Follow

### 1. Modular Monolith (Recommended Starting Point)

You will implement **one Spring Boot application** with **strict module boundaries**.

Each module behaves like a microservice internally:

```
/hms-backend
 ├── patient/
 ├── staff/
 ├── clinical/
 ├── pharmacy/
 ├── lab/
 ├── radiology/
 ├── billing/
 ├── nhis/
 ├── inventory/
 ├── ai/
 ├── security/
 └── shared/
```

Later, any module can be extracted into a standalone microservice **without refactoring core logic**.

---

## Backend Design Pattern Stack

| Concern         | Pattern                              |
| --------------- | ------------------------------------ |
| Domain Modeling | DDD (Lightweight)                    |
| Object Creation | Factory Pattern                      |
| Business Rules  | Domain Services                      |
| Persistence     | Repository Pattern                   |
| API             | REST (Controller-Service separation) |
| Security        | JWT + RBAC + Policy Enforcement      |
| Auditing        | Event-driven logging                 |
| AI              | Strategy Pattern                     |

---

## Core Bounded Contexts & Modules

### 1. Patient Management

#### Entity: Patient

```java
UUID id
String hospitalNumber
String firstName
String lastName
LocalDate dateOfBirth
String gender
String phoneNumber
String address
String nationality
String nhisNumber
PatientStatus status
LocalDateTime createdAt
```

Relationships:

* One Patient → Many Appointments
* One Patient → One NHIS Record
* One Patient → Many Medical Records

---

### 2. Staff & HR Management

#### Entity: Staff

```java
UUID id
String staffNumber
String firstName
String lastName
StaffRole role
Department department
EmploymentType employmentType
String phone
boolean active
```

Roles include:

* Doctor
* Nurse
* Pharmacist
* Lab Scientist
* Admin

---

### 3. Appointment & Scheduling

#### Entity: Appointment

```java
UUID id
Patient patient
Staff doctor
LocalDateTime appointmentTime
AppointmentStatus status
String reason
```

---

### 4. Electronic Medical Records (EMR)

#### Entity: MedicalRecord

```java
UUID id
Patient patient
Staff createdBy
String diagnosis
String treatmentPlan
String notes
LocalDateTime createdAt
```

---

### 5. Pharmacy Management

#### Entity: Drug

```java
UUID id
String drugName
String batchNumber
LocalDate expiryDate
int quantityAvailable
BigDecimal unitPrice
```

---

### 6. Laboratory Management

#### Entity: LabTest

```java
UUID id
String testName
Patient patient
Staff labScientist
LabStatus status
String result
```

---

### 7. Billing & NHIS

#### Entity: Invoice

```java
UUID id
Patient patient
BigDecimal totalAmount
PaymentStatus status
LocalDateTime issuedAt
```

#### Entity: NHISClaim

```java
UUID id
Patient patient
String claimCode
ClaimStatus status
LocalDate submittedDate
```

---

### 8. Inventory & Stores (Aligned with Your Experience)

#### Entity: InventoryItem

```java
UUID id
String itemName
String category
int quantity
String location
```

---

## AI-Powered Features

| Area              | AI Capability          |
| ----------------- | ---------------------- |
| Triage            | Symptom classification |
| Diagnosis Support | Rule + ML hybrid       |
| Fraud Detection   | NHIS anomaly detection |
| Scheduling        | Load optimization      |
| Reporting         | Predictive analytics   |

AI Layer is **advisory**, never authoritative.

---

## Security Architecture

* JWT Authentication
* Role-Based Access Control
* Field-level permissions
* Audit logs (immutable)
* HIPAA-style access tracking

---

## Frontend Stack Decisions

### Validation: Zod + React Hook Form (Recommended)

**Why:**

* RHF handles performance & state
* Zod provides type-safe schemas
* Perfect alignment with Spring DTOs

**Decision:**
Use **React Hook Form + Zod** (together, not either/or).

---

### UI Library: ShadCN/UI

Reasons:

* Not a black box
* You own the components
* Tailwind-based
* Enterprise-friendly

ShadCN is ideal for someone who understands backend deeply and wants **frontend control without chaos**.

---

## Database Strategy

* Start with **MySQL 8**
* UUID primary keys
* Flyway migrations
* Strict foreign keys

---

## Development Workflow

1. Define entity
2. Create repository
3. Add service logic
4. Expose REST endpoint
5. Add frontend form (RHF + Zod)
6. Secure endpoint
7. Add audit logging

---

## Non-Functional Requirements

* Horizontal scalability
* High availability
* Transaction safety
* Observability (logs, metrics)
* Data integrity

---

## Final Guidance

This system is **enterprise-grade by design**. Do not shortcut:

* Entity modeling
* Module boundaries
* Security rules

If followed strictly, this architecture will **scale nationally** without refactor paralysis.

---

## License

Proprietary – Hospital & Government Use

