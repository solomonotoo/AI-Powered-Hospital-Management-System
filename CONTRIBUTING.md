## Value Object Persistence Convention

Our persistence strategy depends on the shape of the Value Object, not on a one-size-fits-all mapping approach.

### Single-value Value Objects

Single-value Value Objects (e.g. `Email`, `PhoneNumber`, `Money`, `DateOfBirth`) **must** be persisted directly on the entity using a JPA `AttributeConverter`. The domain type remains the entity field, with persistence handled transparently via `@Convert`. Do **not** introduce `@Embeddable` wrappers, persistence DTOs, or mapper classes for these types—they duplicate the converter's responsibility and increase maintenance without adding architectural value.

```java
@Entity
public class StaffEntity {

    @Convert(converter = PhoneNumberConverter.class)
    @Column(name = "contact_phone", nullable = false, length = 20)
    private PhoneNumber contactPhone;

    @Convert(converter = EmailConverter.class)
    @Column(name = "contact_email", length = 150)
    private Email contactEmail;
}
```

---

### Multi-field Value Objects

### Multi-field Value Objects

Multi-field Value Objects (e.g. `Address`, `PersonName`, `NextOfKin`) should be persisted using an `@Embeddable` persistence model because they map to multiple database columns. `AttributeConverter` is not appropriate for multi-column mappings.

**Persistence**

```java
@Entity
public class StaffEntity {

    @Embedded
    private AddressEmbeddable homeAddress;
}

@Embeddable
public class AddressEmbeddable {

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;

    @Column(name = "region")
    private String region;

    @Column(name = "postal_code")
    private String postalCode;
}
```

**Mapper**

```java
public final class AddressMapper {

    private AddressMapper() {}

    public static Address toDomain(AddressEmbeddable embeddable) {
        if (embeddable == null) {
            return null;
        }

        return Address.of(
            embeddable.getStreet(),
            embeddable.getCity(),
            embeddable.getRegion(),
            embeddable.getPostalCode()
        );
    }

    public static AddressEmbeddable toEmbeddable(Address address) {
        if (address == null) {
            return null;
        }

        AddressEmbeddable embeddable = new AddressEmbeddable();
        embeddable.setStreet(address.street());
        embeddable.setCity(address.city());
        embeddable.setRegion(address.region());
        embeddable.setPostalCode(address.postalCode());
        return embeddable;
    }
}
```

The embeddable exists solely as the persistence representation. The domain model continues to work exclusively with the `Address` Value Object, while the mapper isolates all persistence concerns from the domain layer.


---

### Parameter-grouping Objects

Objects that exist only to make constructors or factory methods readable (e.g. `PersonalDetails`, `ContactDetails`, `MedicalDetails`) are **not** persistence concepts. Their fields should be flattened onto the owning entity rather than persisted as nested objects.

**Domain**

```java
Staff.register(
    staffId,
    personalDetails,
    contactDetails,
    medicalDetails
);
```

**Persistence**

```java
@Entity
public class StaffEntity {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Convert(converter = EmailConverter.class)
    @Column(name = "contact_email")
    private Email contactEmail;

    @Convert(converter = PhoneNumberConverter.class)
    @Column(name = "contact_phone")
    private PhoneNumber contactPhone;

    @Column(name = "blood_group")
    private String bloodGroup;

    @Column(name = "allergies")
    private String allergies;
}
```

The grouping objects exist purely in the domain model to express intent and improve API readability. They are reconstructed when mapping from the persistence model and should not have corresponding persistence classes unless they later acquire independent domain semantics.

---

This convention keeps the domain model explicit while minimizing persistence boilerplate. Each persistence mechanism is chosen based on the structural characteristics of the Value Object rather than applying a single mapping pattern universally.
