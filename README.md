# 🏥 AI-Native Hospital Management System (HMS)

> **Spring Modulith · Domain-Driven Design · Hexagonal Architecture · AI-Native**

A production-grade, modular Hospital Management System built with Spring Boot 3, Spring Modulith, and embedded AI capabilities. Designed for OPD, IPD, billing, pharmacy, laboratory, and patient engagement — with AI decision support, ambient scribing, and predictive analytics.

---

## 📋 Table of Contents

- [Architecture Overview](#architecture-overview)
- [Module Structure](#module-structure)
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Environment Variables](#environment-variables)
- [Database & Migrations](#database--migrations)
- [Running the Application](#running-the-application)
- [Running Tests](#running-tests)
- [API Documentation](#api-documentation)
- [Docker Setup](#docker-setup)
- [Module Descriptions](#module-descriptions)
- [Key Design Decisions](#key-design-decisions)
- [Contributing](#contributing)
- [Roadmap](#roadmap)

---

## 🏛️ Architecture Overview

```
┌─────────────────────────────────────────────────────────────────────┐
│                      AI-Native HMS (Spring Modulith)                 │
│                                                                       │
│  ┌──────────┐  ┌──────────────┐  ┌──────────┐  ┌────────────────┐  │
│  │ patient  │  │ appointment  │  │ clinical │  │      ai        │  │
│  └──────────┘  └──────────────┘  └──────────┘  └────────────────┘  │
│  ┌──────────┐  ┌──────────────┐  ┌──────────┐  ┌────────────────┐  │
│  │ billing  │  │   resource   │  │ pharmacy │  │  laboratory    │  │
│  └──────────┘  └──────────────┘  └──────────┘  └────────────────┘  │
│  ┌──────────────────────┐  ┌───────────────┐  ┌─────────────────┐  │
│  │     engagement       │  │   analytics   │  │  shared/kernel  │  │
│  └──────────────────────┘  └───────────────┘  └─────────────────┘  │
└─────────────────────────────────────────────────────────────────────┘
```

**Three architectural patterns in one system:**

| Pattern | Role |
|---|---|
| **Domain-Driven Design (DDD)** | Defines bounded contexts, aggregates, ubiquitous language |
| **Hexagonal Architecture** | Isolates business logic from infrastructure inside each module |
| **Spring Modulith** | Enforces module boundaries at compile/test time within one deployable |

---

## 📁 Module Structure

```
com.hms
├── HMSApplication.java
│
├── shared/
│   ├── kernel/
│   │   ├── ids/                    # Typed IDs: PatientId, StaffId, EncounterId …
│   │   ├── events/                 # Base domain event classes
│   │   └── valueobjects/           # Email, PhoneNumber, Money, MRN …
│   └── config/                     # Security, JPA, OpenAPI, Flyway config
│
├── patient/                        # Patient registration & demographics
│   ├── domain/
│   │   ├── model/Patient.java
│   │   ├── repository/PatientRepository.java   (PORT)
│   │   └── service/PatientDomainService.java
│   ├── application/
│   │   ├── command/
│   │   ├── query/
│   │   ├── handler/
│   │   └── dto/
│   ├── infrastructure/
│   │   └── persistence/JpaPatientRepository.java  (ADAPTER)
│   ├── web/PatientController.java
│   └── package-info.java
│
├── appointment/                    # Scheduling & availability
├── clinical/                       # OPD visits, IPD admissions, encounters
├── ai/                             # Risk scoring, scribe, predictions
├── billing/                        # Invoicing, coding, claims
├── resource/                       # Wards, beds, capacity
├── pharmacy/                       # Medications, dispensing, inventory
├── laboratory/                     # Lab orders, results, LIS integration
├── engagement/                     # Patient portal, chatbot, notifications
└── analytics/                      # Dashboards, reports, KPIs
```

Each module follows the same internal structure:

```
<module>/
├── domain/
│   ├── model/          # Entities, Aggregates, Value Objects
│   ├── repository/     # Repository interfaces (Ports — no Spring annotations)
│   └── service/        # Domain services (pure business logic)
├── application/
│   ├── command/        # Command objects (writes)
│   ├── query/          # Query objects (reads)
│   ├── handler/        # Command & Query handlers
│   └── dto/            # Data Transfer Objects
├── infrastructure/
│   ├── persistence/    # JPA adapters implementing repository ports
│   ├── external/       # External API client adapters
│   └── messaging/      # Event publishers / listeners
├── web/
│   └── *Controller.java   # REST controllers (Driving Adapters)
└── package-info.java       # Spring Modulith module descriptor
```

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 (LTS) |
| Framework | Spring Boot 3.3 |
| Modularity | Spring Modulith 1.2 |
| Persistence | Spring Data JPA + Hibernate 6 |
| Database | PostgreSQL 16 |
| Migrations | Flyway 10 |
| Security | Spring Security 6 + OAuth2 Resource Server (JWT) |
| API Docs | SpringDoc OpenAPI 3 (Swagger UI) |
| Messaging | Spring Application Events (in-process, Modulith) |
| Caching | Spring Cache + Redis 7 |
| Object Storage | MinIO (self-hosted S3-compatible) |
| Containerisation | Docker + Docker Compose |
| Build | Maven 3.9+ |
| Testing | JUnit 5, Mockito, Testcontainers, Spring Modulith Test |
| Observability | Micrometer + Prometheus + Grafana |
| Tracing | OpenTelemetry + Tempo |
| AI / ML | Python FastAPI sidecar (REST) — pluggable AI inference endpoint |

---

## ✅ Prerequisites

| Tool | Version | Notes |
|---|---|---|
| JDK | 21+ | [Adoptium Temurin](https://adoptium.net/) recommended |
| Maven | 3.9+ | Or use included `./mvnw` wrapper |
| Docker | 24+ | Required for Compose-based local dev |
| Docker Compose | 2.20+ | Included in Docker Desktop |
| PostgreSQL | 16 | Run via Docker Compose (see below) |
| Redis | 7 | Run via Docker Compose |

Optional (for AI features):
- Python 3.11+ with FastAPI (AI inference sidecar)

---

## 🚀 Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/your-org/hms.git
cd hms
```

### 2. Start infrastructure services

```bash
docker compose -f docker/docker-compose.dev.yml up -d
```

This starts: PostgreSQL, Redis, MinIO, Prometheus, Grafana.

### 3. Configure environment

```bash
cp .env.example .env
# Edit .env with your local values (DB password, JWT secret, etc.)
```

### 4. Run database migrations

Flyway runs automatically on application startup. To run manually:

```bash
./mvnw flyway:migrate -Dflyway.url=jdbc:postgresql://localhost:5432/hms \
  -Dflyway.user=hms_user -Dflyway.password=your_password
```

### 5. Start the application

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

The application starts at: **http://localhost:8080**  
Swagger UI: **http://localhost:8080/swagger-ui.html**  
Actuator: **http://localhost:8080/actuator/health**

---

## 🔐 Environment Variables

Copy `.env.example` to `.env` and configure:

```env
# ── Database ──────────────────────────────────────────────────────────
DB_HOST=localhost
DB_PORT=5432
DB_NAME=hms
DB_USERNAME=hms_user
DB_PASSWORD=change_me_in_production

# ── Redis ─────────────────────────────────────────────────────────────
REDIS_HOST=localhost
REDIS_PORT=6379
REDIS_PASSWORD=

# ── JWT / Security ────────────────────────────────────────────────────
JWT_SECRET=your-256-bit-secret-change-me
JWT_ACCESS_TOKEN_EXPIRY_MINUTES=15
JWT_REFRESH_TOKEN_EXPIRY_DAYS=7

# ── AI Inference Sidecar ──────────────────────────────────────────────
AI_SERVICE_BASE_URL=http://localhost:8000
AI_SERVICE_API_KEY=

# ── File Storage (MinIO) ──────────────────────────────────────────────
MINIO_ENDPOINT=http://localhost:9000
MINIO_ACCESS_KEY=minioadmin
MINIO_SECRET_KEY=minioadmin
MINIO_BUCKET_DOCUMENTS=hms-documents

# ── Notifications ─────────────────────────────────────────────────────
SMS_PROVIDER_URL=
SMS_PROVIDER_API_KEY=
EMAIL_FROM=noreply@hms.local
SMTP_HOST=localhost
SMTP_PORT=1025

# ── Application ───────────────────────────────────────────────────────
SPRING_PROFILES_ACTIVE=dev
SERVER_PORT=8080
LOG_LEVEL=INFO
```

---

## 🗄️ Database & Migrations

Flyway is used for all schema changes. Migration files live in:

```
src/main/resources/db/migration/
├── V1__create_shared_schema.sql
├── V2__create_patient_tables.sql
├── V3__create_staff_tables.sql
├── V4__create_appointment_tables.sql
├── V5__create_clinical_tables.sql
├── V6__create_opd_ipd_tables.sql
├── V7__create_pharmacy_tables.sql
├── V8__create_laboratory_tables.sql
├── V9__create_billing_tables.sql
├── V10__create_resource_tables.sql
├── V11__create_engagement_tables.sql
├── V12__create_analytics_tables.sql
└── V13__seed_reference_data.sql
```

**Rules for migrations:**
- Never edit an existing migration — always add a new `Vn__` file
- Each migration must be idempotent where possible
- Use schema prefixes per module: `patient_`, `clinical_`, `billing_`, etc.

---

## 🐳 Docker Setup

### Development (with hot reload)

```bash
docker compose -f docker/docker-compose.dev.yml up -d
```

### Full stack (app + all services)

```bash
docker compose -f docker/docker-compose.yml up -d
```

### Build the application image

```bash
docker build -t hms-app:latest .
```

Services exposed locally:

| Service | URL |
|---|---|
| HMS API | http://localhost:8080 |
| Swagger UI | http://localhost:8080/swagger-ui.html |
| PostgreSQL | localhost:5432 |
| Redis | localhost:6379 |
| MinIO Console | http://localhost:9001 |
| Prometheus | http://localhost:9090 |
| Grafana | http://localhost:3000 |
| MailHog (dev email) | http://localhost:8025 |

---

## 🧪 Running Tests

```bash
# All tests
./mvnw test

# Unit tests only
./mvnw test -Dgroups=unit

# Integration tests (requires Docker for Testcontainers)
./mvnw test -Dgroups=integration

# Spring Modulith module boundary verification
./mvnw test -Dtest=ModularityTests

# Generate Modulith documentation (module diagrams)
./mvnw test -Dtest=DocumentationTests
```

**Test naming convention:**
- `*Test.java` — Unit tests (no Spring context)
- `*IntegrationTest.java` — Integration tests (Testcontainers)
- `*ControllerTest.java` — Web layer slice tests (`@WebMvcTest`)

---

## 📖 API Documentation

Interactive Swagger UI is available at `/swagger-ui.html` when the app is running.

API is grouped by module:
- `/api/v1/patients` — Patient Management
- `/api/v1/appointments` — Appointment Scheduling
- `/api/v1/opd` — OPD Visits
- `/api/v1/ipd` — IPD Admissions
- `/api/v1/encounters` — Clinical Encounters
- `/api/v1/prescriptions` — Prescriptions
- `/api/v1/labs` — Laboratory Orders & Results
- `/api/v1/billing` — Invoices & Claims
- `/api/v1/pharmacy` — Medication & Dispensing
- `/api/v1/resources` — Wards, Beds & Capacity
- `/api/v1/ai` — AI Decision Support & Scribe
- `/api/v1/engagement` — Notifications & Patient Portal

Authentication: **Bearer JWT** — obtain token at `POST /api/v1/auth/login`.

---

## 📦 Module Descriptions

| Module | Bounded Context | Key Aggregates |
|---|---|---|
| `patient` | Patient Management | Patient |
| `appointment` | Appointment & Scheduling | Appointment |
| `clinical` | Clinical Care (Core) | OpdVisit, IpdAdmission, Encounter, Diagnosis |
| `ai` | AI Intelligence (Core) | RiskScore, ScribeSession, Prediction |
| `billing` | Billing & Revenue | Invoice, InsuranceClaim |
| `resource` | Resource Management | Ward, Bed, StaffSchedule |
| `pharmacy` | Pharmacy | Medication, Prescription, DispensingRecord |
| `laboratory` | Laboratory | LabOrder, LabResult |
| `engagement` | Patient Engagement | Notification, ChatSession, Reminder |
| `analytics` | Analytics (Generic) | Report, Dashboard |
| `shared/kernel` | Shared Kernel | PatientId, StaffId, all typed IDs |

---

## 🔑 Key Design Decisions

1. **OpdVisit and IpdAdmission are separate aggregates** — different lifecycles, resources, and billing triggers.
2. **Encounter is the universal clinical note** — links to OPD or IPD via `sourceType` + `sourceId`.
3. **AI is a service provider** — never a decision maker. All AI outputs require clinician confirmation.
4. **Typed IDs from shared kernel** — `PatientId`, `StaffId`, etc. prevent identifier type confusion.
5. **Event-driven inter-module communication** — no direct entity access across module boundaries.
6. **Modulith-first, microservices-ready** — boundaries enforced today; extraction path clear for tomorrow.

---

## 🗺️ Roadmap

| Phase | Features |
|---|---|
| **Phase 1** | Patient, Clinical (OPD/IPD), AI, Appointment modules |
| **Phase 2** | Billing, Laboratory, Pharmacy, Resource Management |
| **Phase 3** | Analytics, Patient Engagement, HL7 FHIR, External integrations |
| **Phase 4** | Radiology, Telemedicine, Theatre/Surgical module |
| **Phase 5** | Population health, Advanced analytics, HIE integration |
| **Phase 6** | Microservices extraction (AI, Lab, Pharmacy) if required |

---

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/your-module-feature`
3. Follow the module internal structure (domain → application → infrastructure → web)
4. Ensure `ModularityTests` passes (no illegal cross-module access)
5. Add unit and integration tests
6. Open a pull request with a clear description

**Branch naming:** `feature/`, `fix/`, `refactor/`, `docs/`  
**Commit style:** Conventional Commits — `feat(patient): add MRN generation service`

---

## 📄 License

This project is proprietary and confidential. All rights reserved.

---

*Built with ❤️ using Spring Boot, Spring Modulith, and Domain-Driven Design.*
