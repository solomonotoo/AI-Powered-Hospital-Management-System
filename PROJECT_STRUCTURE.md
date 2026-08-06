# ═══════════════════════════════════════════════════════════════════════════════
# AI-Native HMS — Complete Project Structure
# Spring Boot 3 · Spring Modulith · DDD · Hexagonal Architecture
# ═══════════════════════════════════════════════════════════════════════════════

hms/
│
├── .env.example                          # Environment variable template
├── .gitignore
├── Dockerfile                            # Multi-stage build (JDK21 → JRE21)
├── pom.xml                               # Maven build descriptor
├── mvnw / mvnw.cmd                       # Maven wrapper (no local Maven required)
├── README.md
│
├── docker/
│   ├── docker-compose.dev.yml            # Dev: infra only (DB, Redis, MinIO, etc.)
│   ├── docker-compose.yml                # Full stack: app + all services
│   └── observability/
│       ├── prometheus.yml                # Prometheus scrape config
│       └── grafana/
│           └── provisioning/
│               ├── dashboards/           # Pre-built Grafana dashboard JSONs
│               └── datasources/          # Prometheus datasource config
│
├── docs/
│   ├── hms-master-documentation.docx    # Full product & architecture doc
│   └── diagrams/                        # Source diagram files
│
└── src/
    ├── main/
    │   ├── java/com/hms/
    │   │
    │   │   ├── HMSApplication.java           # @SpringBootApplication entry point
    │   │   │
    │   │   # ══════════════════════════════════════════════════════════════════
    │   │   # SHARED KERNEL  (no Spring annotations — pure Java)
    │   │   # ══════════════════════════════════════════════════════════════════
    │   │   ├── shared/
    │   │   │   ├── kernel/
    │   │   │   │   ├── ids/
    │   │   │   │   │   ├── PatientId.java        # record PatientId(UUID value)
    │   │   │   │   │   ├── StaffId.java
    │   │   │   │   │   ├── EncounterId.java
    │   │   │   │   │   ├── AppointmentId.java
    │   │   │   │   │   ├── OpdVisitId.java
    │   │   │   │   │   ├── AdmissionId.java
    │   │   │   │   │   ├── InvoiceId.java
    │   │   │   │   │   ├── BedId.java
    │   │   │   │   │   └── WardId.java
    │   │   │   │   │
    │   │   │   │   ├── events/
    │   │   │   │   │   ├── DomainEvent.java          # Base interface / record
    │   │   │   │   │   ├── PatientRegisteredEvent.java
    │   │   │   │   │   ├── AppointmentCreatedEvent.java
    │   │   │   │   │   ├── AppointmentCancelledEvent.java
    │   │   │   │   │   ├── OpdVisitStartedEvent.java
    │   │   │   │   │   ├── EncounterCompletedEvent.java
    │   │   │   │   │   ├── LabOrderRequestedEvent.java
    │   │   │   │   │   ├── LabResultAvailableEvent.java
    │   │   │   │   │   ├── IpdAdmissionCreatedEvent.java
    │   │   │   │   │   ├── BedAllocatedEvent.java
    │   │   │   │   │   ├── BedReleasedEvent.java
    │   │   │   │   │   ├── DischargeInitiatedEvent.java
    │   │   │   │   │   ├── InvoiceGeneratedEvent.java
    │   │   │   │   │   ├── ClaimSubmittedEvent.java
    │   │   │   │   │   ├── ClaimDeniedEvent.java
    │   │   │   │   │   ├── MedicationDispensedEvent.java
    │   │   │   │   │   └── AlertTriggeredEvent.java
    │   │   │   │   │
    │   │   │   │   └── valueobjects/
    │   │   │   │       ├── Email.java
    │   │   │   │       ├── PhoneNumber.java
    │   │   │   │       ├── Money.java
    │   │   │   │       ├── MRN.java
    │   │   │   │       ├── Address.java
    │   │   │   │       └── DateRange.java
    │   │   │   │
    │   │   │   └── config/
    │   │   │       ├── SecurityConfig.java       # OAuth2 + JWT + RBAC
    │   │   │       ├── JwtConfig.java            # Token parsing & generation
    │   │   │       ├── WebConfig.java            # CORS, interceptors
    │   │   │       ├── CacheConfig.java          # Redis cache setup
    │   │   │       ├── OpenApiConfig.java        # Swagger / SpringDoc setup
    │   │   │       ├── MinioConfig.java          # MinIO client bean
    │   │   │       ├── AuditConfig.java          # JPA auditing (@EnableJpaAuditing)
    │   │   │       └── ModulithConfig.java       # Spring Modulith configuration
    │   │   │
    │   │   # ══════════════════════════════════════════════════════════════════
    │   │   # PATIENT MODULE  — Supporting Domain
    │   │   # ══════════════════════════════════════════════════════════════════
    │   │   ├── patient/
    │   │   │   ├── package-info.java             # @ApplicationModule descriptor
    │   │   │   ├── domain/
    │   │   │   │   ├── model/
    │   │   │   │   │   ├── Patient.java          # Aggregate Root
    │   │   │   │   │   ├── PatientStatus.java    # Enum: ACTIVE, INACTIVE, DECEASED
    │   │   │   │   │   └── PatientType.java      # Enum: OPD, IPD, EMERGENCY
    │   │   │   │   ├── repository/
    │   │   │   │   │   └── PatientRepository.java  # Port (interface, no Spring)
    │   │   │   │   └── service/
    │   │   │   │       └── MrnGenerationService.java
    │   │   │   ├── application/
    │   │   │   │   ├── command/
    │   │   │   │   │   ├── RegisterPatientCommand.java
    │   │   │   │   │   └── UpdatePatientCommand.java
    │   │   │   │   ├── query/
    │   │   │   │   │   ├── FindPatientByIdQuery.java
    │   │   │   │   │   └── SearchPatientsQuery.java
    │   │   │   │   ├── handler/
    │   │   │   │   │   ├── RegisterPatientHandler.java
    │   │   │   │   │   └── PatientQueryHandler.java
    │   │   │   │   └── dto/
    │   │   │   │       ├── PatientRequest.java
    │   │   │   │       ├── PatientResponse.java
    │   │   │   │       └── PatientSummaryResponse.java
    │   │   │   ├── infrastructure/
    │   │   │   │   └── persistence/
    │   │   │   │       ├── JpaPatientRepository.java   # Adapter — implements port
    │   │   │   │       └── PatientJpaEntity.java       # @Entity mapping
    │   │   │   └── web/
    │   │   │       ├── PatientController.java          # /api/v1/patients
    │   │   │       └── PatientMapper.java              # MapStruct DTO ↔ Domain
    │   │   │
    │   │   # ══════════════════════════════════════════════════════════════════
    │   │   # APPOINTMENT MODULE  — Supporting Domain
    │   │   # ══════════════════════════════════════════════════════════════════
    │   │   ├── appointment/
    │   │   │   ├── package-info.java
    │   │   │   ├── domain/model/
    │   │   │   │   ├── Appointment.java          # Aggregate Root
    │   │   │   │   ├── AppointmentStatus.java
    │   │   │   │   └── DoctorAvailability.java
    │   │   │   ├── application/
    │   │   │   │   ├── command/BookAppointmentCommand.java
    │   │   │   │   ├── handler/
    │   │   │   │   │   ├── BookAppointmentHandler.java
    │   │   │   │   │   └── CancelAppointmentHandler.java
    │   │   │   │   └── dto/
    │   │   │   │       ├── AppointmentRequest.java
    │   │   │   │       └── AppointmentResponse.java
    │   │   │   ├── infrastructure/persistence/
    │   │   │   │   └── JpaAppointmentRepository.java
    │   │   │   └── web/
    │   │   │       └── AppointmentController.java    # /api/v1/appointments
    │   │   │
    │   │   # ══════════════════════════════════════════════════════════════════
    │   │   # CLINICAL MODULE  — Core Domain  (OPD + IPD + Encounters)
    │   │   # ══════════════════════════════════════════════════════════════════
    │   │   ├── clinical/
    │   │   │   ├── package-info.java
    │   │   │   ├── domain/
    │   │   │   │   ├── model/
    │   │   │   │   │   ├── OpdVisit.java         # Aggregate Root
    │   │   │   │   │   ├── IpdAdmission.java     # Aggregate Root
    │   │   │   │   │   ├── Encounter.java        # Aggregate Root
    │   │   │   │   │   ├── Diagnosis.java        # Child entity
    │   │   │   │   │   ├── VitalSigns.java       # Child entity
    │   │   │   │   │   ├── ClinicalOrder.java    # Lab / Imaging order
    │   │   │   │   │   ├── SourceType.java       # OPD_VISIT | IPD_ADMISSION | EMERGENCY
    │   │   │   │   │   └── DispositionCode.java  # DISCHARGED | REFERRED_IPD | FOLLOW_UP …
    │   │   │   │   ├── repository/
    │   │   │   │   │   ├── OpdVisitRepository.java
    │   │   │   │   │   ├── IpdAdmissionRepository.java
    │   │   │   │   │   └── EncounterRepository.java
    │   │   │   │   └── service/
    │   │   │   │       ├── ClinicalWorkflowService.java
    │   │   │   │       └── RiskAssessmentPort.java   # Port → ai module adapter
    │   │   │   ├── application/
    │   │   │   │   ├── command/
    │   │   │   │   │   ├── CreateOpdVisitCommand.java
    │   │   │   │   │   ├── AdmitPatientCommand.java
    │   │   │   │   │   ├── CreateEncounterCommand.java
    │   │   │   │   │   ├── AddDiagnosisCommand.java
    │   │   │   │   │   ├── RecordVitalsCommand.java
    │   │   │   │   │   ├── DischargePatientCommand.java
    │   │   │   │   │   └── CloseOpdVisitCommand.java
    │   │   │   │   ├── handler/
    │   │   │   │   │   ├── OpdVisitHandler.java
    │   │   │   │   │   ├── IpdAdmissionHandler.java
    │   │   │   │   │   ├── EncounterHandler.java
    │   │   │   │   │   └── DischargeHandler.java
    │   │   │   │   └── dto/
    │   │   │   │       ├── OpdVisitRequest.java / OpdVisitResponse.java
    │   │   │   │       ├── IpdAdmissionRequest.java / IpdAdmissionResponse.java
    │   │   │   │       ├── EncounterRequest.java / EncounterResponse.java
    │   │   │   │       └── VitalsRequest.java / VitalsResponse.java
    │   │   │   ├── infrastructure/
    │   │   │   │   ├── persistence/
    │   │   │   │   │   ├── JpaOpdVisitRepository.java
    │   │   │   │   │   ├── JpaIpdAdmissionRepository.java
    │   │   │   │   │   └── JpaEncounterRepository.java
    │   │   │   │   └── ai/
    │   │   │   │       └── AiRiskAssessmentAdapter.java  # Implements RiskAssessmentPort
    │   │   │   └── web/
    │   │   │       ├── OpdController.java          # /api/v1/opd
    │   │   │       ├── IpdController.java          # /api/v1/ipd
    │   │   │       └── EncounterController.java    # /api/v1/encounters
    │   │   │
    │   │   # ══════════════════════════════════════════════════════════════════
    │   │   # AI MODULE  — Core Domain
    │   │   # ══════════════════════════════════════════════════════════════════
    │   │   ├── ai/
    │   │   │   ├── package-info.java
    │   │   │   ├── domain/
    │   │   │   │   ├── model/
    │   │   │   │   │   ├── RiskScore.java
    │   │   │   │   │   ├── DiagnosticSuggestion.java
    │   │   │   │   │   └── ScribeSession.java
    │   │   │   │   └── port/
    │   │   │   │       └── AiInferencePort.java    # Outbound port → Python sidecar
    │   │   │   ├── application/
    │   │   │   │   ├── handler/
    │   │   │   │   │   ├── RiskScoringHandler.java
    │   │   │   │   │   ├── DiagnosticSuggestionHandler.java
    │   │   │   │   │   ├── ScribeSessionHandler.java
    │   │   │   │   │   └── CodingSuggestionHandler.java
    │   │   │   │   └── listener/
    │   │   │   │       ├── EncounterCompletedListener.java   # → AI coding suggestion
    │   │   │   │       └── LabResultListener.java            # → anomaly detection
    │   │   │   ├── infrastructure/
    │   │   │   │   └── client/
    │   │   │   │       └── PythonAiClientAdapter.java  # WebClient → FastAPI sidecar
    │   │   │   └── web/
    │   │   │       └── AiController.java              # /api/v1/ai
    │   │   │
    │   │   # ══════════════════════════════════════════════════════════════════
    │   │   # RESOURCE MODULE  — Supporting Domain  (Wards, Beds, Capacity)
    │   │   # ══════════════════════════════════════════════════════════════════
    │   │   ├── resource/
    │   │   │   ├── package-info.java
    │   │   │   ├── domain/model/
    │   │   │   │   ├── Ward.java                 # Aggregate Root
    │   │   │   │   ├── Bed.java
    │   │   │   │   └── BedStatus.java            # AVAILABLE | OCCUPIED | MAINTENANCE
    │   │   │   ├── application/
    │   │   │   │   ├── command/
    │   │   │   │   │   ├── AllocateBedCommand.java
    │   │   │   │   │   └── ReleaseBedCommand.java
    │   │   │   │   ├── handler/
    │   │   │   │   │   └── BedManagementHandler.java
    │   │   │   │   └── listener/
    │   │   │   │       ├── AdmissionCreatedListener.java  # → allocate bed
    │   │   │   │       └── DischargeListener.java         # → release bed
    │   │   │   └── web/
    │   │   │       └── ResourceController.java    # /api/v1/resources
    │   │   │
    │   │   # ══════════════════════════════════════════════════════════════════
    │   │   # BILLING MODULE  — Supporting Domain
    │   │   # ══════════════════════════════════════════════════════════════════
    │   │   ├── billing/
    │   │   │   ├── package-info.java
    │   │   │   ├── domain/model/
    │   │   │   │   ├── Invoice.java              # Aggregate Root
    │   │   │   │   ├── InvoiceLineItem.java
    │   │   │   │   ├── InsuranceClaim.java
    │   │   │   │   └── PaymentStatus.java
    │   │   │   ├── application/
    │   │   │   │   ├── handler/
    │   │   │   │   │   ├── InvoiceGenerationHandler.java
    │   │   │   │   │   ├── ClaimSubmissionHandler.java
    │   │   │   │   │   └── PaymentRecordHandler.java
    │   │   │   │   └── listener/
    │   │   │   │       └── EncounterCompletedListener.java  # → generate invoice
    │   │   │   └── web/
    │   │   │       └── BillingController.java    # /api/v1/billing
    │   │   │
    │   │   # ══════════════════════════════════════════════════════════════════
    │   │   # PHARMACY MODULE  — Supporting Domain
    │   │   # ══════════════════════════════════════════════════════════════════
    │   │   ├── pharmacy/
    │   │   │   ├── package-info.java
    │   │   │   ├── domain/model/
    │   │   │   │   ├── Medication.java           # Aggregate Root
    │   │   │   │   ├── Prescription.java         # Aggregate Root
    │   │   │   │   └── PrescriptionItem.java
    │   │   │   └── web/
    │   │   │       └── PharmacyController.java   # /api/v1/pharmacy
    │   │   │
    │   │   # ══════════════════════════════════════════════════════════════════
    │   │   # LABORATORY MODULE  — Supporting Domain
    │   │   # ══════════════════════════════════════════════════════════════════
    │   │   ├── laboratory/
    │   │   │   ├── package-info.java
    │   │   │   ├── domain/model/
    │   │   │   │   ├── LabOrder.java             # Aggregate Root
    │   │   │   │   └── LabOrderStatus.java
    │   │   │   ├── application/
    │   │   │   │   ├── listener/
    │   │   │   │   │   └── LabOrderRequestedListener.java  # → process order
    │   │   │   │   └── handler/
    │   │   │   │       └── LabResultHandler.java           # → publish result event
    │   │   │   └── web/
    │   │   │       └── LaboratoryController.java  # /api/v1/labs
    │   │   │
    │   │   # ══════════════════════════════════════════════════════════════════
    │   │   # ENGAGEMENT MODULE  — Supporting Domain
    │   │   # ══════════════════════════════════════════════════════════════════
    │   │   ├── engagement/
    │   │   │   ├── package-info.java
    │   │   │   ├── domain/model/
    │   │   │   │   ├── Notification.java
    │   │   │   │   ├── ChatSession.java
    │   │   │   │   └── Reminder.java
    │   │   │   ├── application/
    │   │   │   │   ├── handler/
    │   │   │   │   │   └── NotificationDispatchHandler.java
    │   │   │   │   └── listener/
    │   │   │   │       ├── PatientRegisteredListener.java    # → welcome notification
    │   │   │   │       ├── AppointmentCreatedListener.java  # → confirmation + reminder
    │   │   │   │       ├── LabResultListener.java           # → patient notification
    │   │   │   │       └── InvoiceGeneratedListener.java    # → billing notification
    │   │   │   └── web/
    │   │   │       └── EngagementController.java  # /api/v1/engagement
    │   │   │
    │   │   # ══════════════════════════════════════════════════════════════════
    │   │   # ANALYTICS MODULE  — Generic
    │   │   # ══════════════════════════════════════════════════════════════════
    │   │   └── analytics/
    │   │       ├── package-info.java
    │   │       └── web/
    │   │           └── AnalyticsController.java  # /api/v1/analytics
    │   │
    │   └── resources/
    │       ├── application.yml               # Base config (all profiles)
    │       ├── application-dev.yml           # Development overrides
    │       ├── application-prod.yml          # Production overrides
    │       ├── application-test.yml          # Test overrides
    │       └── db/
    │           └── migration/
    │               ├── V1__create_shared_schema.sql      # event_publication table
    │               ├── V2__create_patient_tables.sql
    │               ├── V3__create_staff_tables.sql
    │               ├── V4__create_appointment_tables.sql
    │               ├── V5__create_clinical_core_tables.sql  # OPD, IPD, Encounter, Diagnosis
    │               ├── V6__create_pharmacy_tables.sql
    │               ├── V7__create_laboratory_tables.sql
    │               ├── V8__create_billing_tables.sql
    │               ├── V9__create_resource_tables.sql
    │               ├── V10__create_engagement_tables.sql
    │               └── V11__seed_reference_data.sql
    │
    └── test/
        └── java/com/hms/
            ├── ModularityTests.java              # Spring Modulith boundary verification
            ├── DocumentationTests.java           # Generates Modulith module diagram
            ├── ArchitectureTests.java            # ArchUnit hexagonal arch rules
            ├── patient/
            │   ├── domain/
            │   │   └── PatientTest.java          # Pure unit tests — no Spring
            │   ├── application/
            │   │   └── RegisterPatientHandlerTest.java
            │   └── PatientModuleIntegrationTest.java  # Testcontainers
            ├── clinical/
            │   ├── OpdVisitTest.java
            │   ├── IpdAdmissionTest.java
            │   └── EncounterTest.java
            ├── billing/
            │   └── InvoiceGenerationTest.java
            └── shared/
                └── kernel/
                    └── TypedIdTest.java

# ═══════════════════════════════════════════════════════════════════════════════
# KEY FILES EXPLAINED
# ═══════════════════════════════════════════════════════════════════════════════
#
#  package-info.java         — Marks the package as a Spring Modulith module.
#                              Controls which packages are exported (public API).
#                              Example:
#                                @ApplicationModule(
#                                  displayName = "Patient Module",
#                                  allowedDependencies = "shared::kernel"
#                                )
#                                package com.hms.patient;
#
#  ModularityTests.java      — Verifies no illegal cross-module access:
#                                @Test void verifiesModularStructure() {
#                                    ApplicationModules.of(HMSApplication.class).verify();
#                                }
#
#  ArchitectureTests.java    — ArchUnit rules ensuring:
#                                • domain layer has no Spring annotations
#                                • infrastructure only implements domain ports
#                                • web layer only calls application layer
#
#  Typed IDs (e.g. PatientId):
#                              public record PatientId(UUID value) {
#                                  public static PatientId generate() {
#                                      return new PatientId(UUID.randomUUID());
#                                  }
#                              }
#
#  Domain Events:
#                              public record PatientRegisteredEvent(
#                                  PatientId patientId,
#                                  String mrn,
#                                  Instant occurredAt
#                              ) implements DomainEvent {}
#
# ═══════════════════════════════════════════════════════════════════════════════
