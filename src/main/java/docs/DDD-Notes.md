# DDD Repository vs Spring MVC Repository

## Key Idea

In **Spring MVC**, the repository is usually both the **abstraction** and the **implementation** because it extends `JpaRepository`.

```java
public interface StaffRepository extends JpaRepository<StaffEntity, UUID> {

    boolean existsByEmployeeNumber(String employeeNumber);

    boolean existsByWorkEmail(String workEmail);

}
```

Spring Data automatically generates the implementation at runtime.

Typical architecture:

```text
Controller
    │
    ▼
Service
    │
    ▼
JpaRepository
    │
    ▼
Database
```

---

# DDD Repository

In DDD, the repository is **only a contract (interface)**. It belongs to the **domain layer** and should not depend on Spring, JPA, Hibernate, SQL, or any persistence technology.

Example:

```java
public interface StaffRepository {

    void save(StaffProfile staff);

    Optional<StaffProfile> findById(StaffId id);

    boolean existsByEmployeeNumber(EmployeeNumber employeeNumber);

    boolean existsByWorkEmail(Email workEmail);

}
```

Notice that the repository works with **domain objects**:

* `StaffProfile`
* `StaffId`
* `EmployeeNumber`
* `Email`

rather than JPA entities or database types.

---

# Infrastructure Repository

The infrastructure layer contains the Spring Data repository.

```java
public interface SpringDataStaffRepository
        extends JpaRepository<StaffJpaEntity, UUID> {

    boolean existsByEmployeeNumber(String employeeNumber);

    boolean existsByWorkEmail(String workEmail);

}
```

This interface is responsible for communicating with the database.

---

# Repository Adapter

The adapter implements the domain repository by delegating to the Spring Data repository.

```java
@Repository
public class JpaStaffRepositoryAdapter implements StaffRepository {

    private final SpringDataStaffRepository repository;
    private final StaffMapper mapper;

    public JpaStaffRepositoryAdapter(
            SpringDataStaffRepository repository,
            StaffMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void save(StaffProfile staff) {

        StaffJpaEntity entity = mapper.toEntity(staff);

        repository.save(entity);
    }

    @Override
    public Optional<StaffProfile> findById(StaffId id) {

        return repository.findById(id.value())
                .map(mapper::toDomain);
    }

    @Override
    public boolean existsByEmployeeNumber(EmployeeNumber employeeNumber) {
        return repository.existsByEmployeeNumber(
                employeeNumber.value());
    }

    @Override
    public boolean existsByWorkEmail(Email workEmail) {
        return repository.existsByWorkEmail(
                workEmail.value());
    }
}
```

The adapter translates between:

* Domain Aggregate ↔ JPA Entity
* Domain Value Objects ↔ Database values

---

# Application Service

The application service coordinates the use case.

```java
@Service
@Transactional
public class OnboardStaffService implements OnboardStaffUseCase {

    private final StaffRepository repository;

    public OnboardStaffService(StaffRepository repository) {
        this.repository = repository;
    }

    @Override
    public StaffId handle(OnboardStaffCommand command) {

        StaffProfile staff = StaffProfile.onboard(
                EmployeeNumber.of(command.employeeNumber()),
                PersonName.of(
                        command.firstName(),
                        command.lastName()),
                command.role(),
                command.specialisation(),
                command.department(),
                Email.of(command.email()),
                PhoneNumber.of(command.phone()),
                command.licenseNumber(),
                command.qualifications(),
                command.joiningDate(),
                command.workingHours(),
                command.consultationFee(),
                command.createdBy()
        );

        repository.save(staff);

        return staff.staffId();
    }
}
```

Notice that the application service depends only on the **domain repository interface**, not on Spring Data.

---

# Architecture Comparison

## Traditional Spring MVC

```text
Controller
     │
     ▼
Service
     │
     ▼
JpaRepository
     │
     ▼
Database
```

---

## DDD

```text
Controller
      │
      ▼
Application Service
      │
      ▼
StaffRepository (Domain Interface)
      │
      ▼
JpaStaffRepositoryAdapter
      │
      ▼
SpringDataStaffRepository
      │
      ▼
Database
```

---

# Responsibilities

## Application Service

Responsible for:

* Executing a use case
* Loading aggregates
* Calling domain behavior
* Saving aggregates
* Managing transactions
* Publishing domain events

Example:

```java
StaffProfile staff = repository.findById(id)
        .orElseThrow(...);

staff.transferToDepartment("ICU", modifiedBy);

repository.save(staff);
```

The service **coordinates** the work.

---

## Repository

Responsible for:

* Saving aggregates
* Loading aggregates
* Querying aggregates
* Mapping between domain objects and persistence objects

The repository **does not contain business logic**.

---

# Important Principle

The Domain Layer should never depend on Spring Data.

❌ Bad

```java
public interface StaffRepository
        extends JpaRepository<StaffProfile, UUID> {
}
```

This makes the domain depend on Spring.

---

✅ Good

```java
public interface StaffRepository {

    void save(StaffProfile staff);

    Optional<StaffProfile> findById(StaffId id);

}
```

Only the infrastructure layer knows about Spring Data.

---

# Simple Mental Model

Think of the repository as a contract.

The application says:

> "I need someone who can save and load StaffProfile aggregates."

The infrastructure answers:

> "I'll implement that contract using JPA."

This keeps the domain independent of any database technology.

---

# One-Line Summary

**Spring MVC**

```
Service → JpaRepository → Database
```

**DDD**

```
Application Service
        ↓
Domain Repository (Contract)
        ↓
Repository Adapter (Implementation)
        ↓
Spring Data JpaRepository
        ↓
Database
```

The adapter and Spring Data repository together provide the persistence mechanism, while the domain repository keeps the application and domain layers independent of JPA and other infrastructure concerns.
