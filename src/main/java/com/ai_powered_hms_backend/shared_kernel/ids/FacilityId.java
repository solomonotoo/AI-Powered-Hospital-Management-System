package com.ai_powered_hms_backend.shared_kernel.ids;

import java.util.Objects;
import java.util.UUID;

public final class FacilityId {
	private final UUID value;

    private FacilityId(UUID value) {
        this.value = Objects.requireNonNull(value, "Facility ID value must not be null");
    }

    public static FacilityId newId() {
        return new FacilityId(UUID.randomUUID());
    }

    public static FacilityId of(UUID value) {
        return new FacilityId(value);
    }

    public UUID value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FacilityId other)) return false;
        return value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value.toString();
    }

	
}
