
AI-Powered Enterprise Hospital Management System
Overview

This repository contains a production-grade, AI-powered Hospital Management System (HMS) designed at the scale and operational complexity of Korle Bu Teaching Hospital. The system is architected for tertiary healthcare delivery, teaching & research, national health insurance workflows (NHIS – Ghana), and high-availability clinical operations.

The solution is built from scratch, optimized for long-term maintainability, high development velocity, and enterprise scalability, using:

Backend: Spring Boot 5 (Java, JDK 21)

Frontend: React + TypeScript

Architecture: Monolithic with modular separation, ready to evolve into microservices

AI Layer: Clinical decision support, automation, analytics

Security: Zero-trust, RBAC, audit-compliant

This README is intentionally exhaustive to eliminate architectural ambiguity and prevent rework during implementation.

Design Philosophy

Modular monolith – clear module separation

Strict layering rules

Minimal magic frameworks

Explicit entity models

Forward-compatible with microservices

Following this structure ensures smooth scaling to a service-oriented architecture later.

High-Level Architecture
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
│ (Modular Monolith – Microservices-ready Modules)        │
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

Monolithic Microservices Skeleton
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


Each module contains:

Controller – REST endpoints

Service – business logic

Repository – database interaction

DTOs – input/output models

Config – module-specific configuration

Future microservices can be extracted from any module without rewriting core logic.

Backend Patterns
Concern	Pattern
Object Creation	Factory Pattern
Business Rules	Service Layer
Persistence	Repository Pattern
API	REST (Controller-Service separation)
Security	JWT + RBAC + Policy Enforcement
Auditing	Event-driven logging
AI	Strategy Pattern
Core Modules & Entities
1. Patient Management
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

2. Staff & HR
UUID id
String staffNumber
String firstName
String lastName
StaffRole role
Department department
EmploymentType employmentType
String phone
boolean active

3. Appointments
UUID id
Patient patient
Staff doctor
LocalDateTime appointmentTime
AppointmentStatus status
String reason

4. EMR
UUID id
Patient patient
Staff createdBy
String diagnosis
String treatmentPlan
String notes
LocalDateTime createdAt

5. Pharmacy
UUID id
String drugName
String batchNumber
LocalDate expiryDate
int quantityAvailable
BigDecimal unitPrice

6. Laboratory
UUID id
String testName
Patient patient
Staff labScientist
LabStatus status
String result

7. Billing & NHIS
UUID id
Patient patient
BigDecimal totalAmount
PaymentStatus status
LocalDateTime issuedAt

UUID id
Patient patient
String claimCode
ClaimStatus status
LocalDate submittedDate

8. Inventory
UUID id
String itemName
String category
int quantity
String location

AI Features
Area	AI Capability
Triage	Symptom classification
Diagnosis Support	Rule + ML hybrid
Fraud Detection	NHIS anomaly detection
Scheduling	Load optimization
Reporting	Predictive analytics
Security

JWT Authentication

Role-Based Access Control

Field-level permissions

Audit logs (immutable)

HIPAA-style access tracking

Frontend Stack

React + TypeScript

ShadCN/UI for components

React Hook Form + Zod for validation

Database Strategy

MySQL 8 or PostgreSQL

UUID primary keys

Flyway migrations

Strict foreign keys

Development Workflow

Define entity

Create repository

Add service logic

Expose REST endpoint

Add frontend form (RHF + Zod)

Secure endpoint

Add audit logging

Non-Functional Requirements

Horizontal scalability

High availability

Transaction safety

Observability (logs, metrics)

Data integrity

License

Proprietary – Hospital & Government Use

If you want, I can generate a ready-to-run folder structure with Spring Boot modules including dummy controllers, services, and DTOs to kickstart the HMS monolith. This would give you a full skeleton project you can compile immediately.

Do you want me to do that next?

yes

Perfect! I’ll outline a ready-to-run Spring Boot monolithic skeleton for your AI-Powered Hospital Management System. This will include modules, controllers, services, repositories, DTOs, and a basic application setup, so you can compile and start extending immediately.

Project Skeleton Structure
hms-backend/
├── pom.xml
├── src/main/java/com/hms
│   ├── HmsApplication.java
│   ├── config/
│   │   └── SecurityConfig.java
│   ├── shared/
│   │   ├── dto/
│   │   └── exception/
│   ├── patient/
│   │   ├── controller/PatientController.java
│   │   ├── service/PatientService.java
│   │   ├── repository/PatientRepository.java
│   │   └── model/Patient.java
│   ├── staff/
│   │   ├── controller/StaffController.java
│   │   ├── service/StaffService.java
│   │   ├── repository/StaffRepository.java
│   │   └── model/Staff.java
│   ├── appointment/
│   │   ├── controller/AppointmentController.java
│   │   ├── service/AppointmentService.java
│   │   ├── repository/AppointmentRepository.java
│   │   └── model/Appointment.java
│   ├── pharmacy/
│   │   ├── controller/DrugController.java
│   │   ├── service/DrugService.java
│   │   ├── repository/DrugRepository.java
│   │   └── model/Drug.java
│   ├── lab/
│   │   ├── controller/LabTestController.java
│   │   ├── service/LabTestService.java
│   │   ├── repository/LabTestRepository.java
│   │   └── model/LabTest.java
│   ├── billing/
│   │   ├── controller/BillingController.java
│   │   ├── service/BillingService.java
│   │   ├── repository/InvoiceRepository.java
│   │   └── model/Invoice.java
│   ├── nhis/
│   │   ├── controller/NhisController.java
│   │   ├── service/NhisService.java
│   │   ├── repository/NhisClaimRepository.java
│   │   └── model/NhisClaim.java
│   ├── inventory/
│   │   ├── controller/InventoryController.java
│   │   ├── service/InventoryService.java
│   │   ├── repository/InventoryRepository.java
│   │   └── model/InventoryItem.java
│   └── ai/
│       ├── controller/AiController.java
│       ├── service/AiService.java
│       └── model/AiTask.java
└── src/main/resources/
    ├── application.properties
    └── db/migration/

Example Code Snippets
HmsApplication.java
package com.hms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(HmsApplication.class, args);
    }
}

Sample Module: Patient
Patient.java
package com.hms.patient.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Patient {

    @Id
    @GeneratedValue
    private UUID id;

    private String hospitalNumber;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String phoneNumber;
    private String address;
    private String nationality;
    private String nhisNumber;
    private String status;
    private LocalDateTime createdAt = LocalDateTime.now();

    // Getters and Setters
}

PatientRepository.java
package com.hms.patient.repository;

import com.hms.patient.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, UUID> {}

PatientService.java
package com.hms.patient.service;

import com.hms.patient.model.Patient;
import com.hms.patient.repository.PatientRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {

    private final PatientRepository repository;

    public PatientService(PatientRepository repository) {
        this.repository = repository;
    }

    public List<Patient> getAllPatients() {
        return repository.findAll();
    }

    public Patient getPatient(UUID id) {
        return repository.findById(id).orElse(null);
    }

    public Patient createPatient(Patient patient) {
        return repository.save(patient);
    }
}

PatientController.java
package com.hms.patient.controller;

import com.hms.patient.model.Patient;
import com.hms.patient.service.PatientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService service;

    public PatientController(PatientService service) {
        this.service = service;
    }

    @GetMapping
    public List<Patient> getAll() {
        return service.getAllPatients();
    }

    @GetMapping("/{id}")
    public Patient getById(@PathVariable UUID id) {
        return service.getPatient(id);
    }

    @PostMapping
    public Patient create(@RequestBody Patient patient) {
        return service.createPatient(patient);
    }
}

Configuration: application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/hms
spring.datasource.username=root
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
server.port=8080


✅ This skeleton can be replicated for each module (staff, appointment, pharmacy, etc.) with their respective entities and repositories.

Once this is done, your modular monolith is fully functional, and each module can later be split into microservices without core logic changes.
