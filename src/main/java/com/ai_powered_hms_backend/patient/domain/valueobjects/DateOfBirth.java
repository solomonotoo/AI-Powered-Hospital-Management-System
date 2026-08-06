package com.ai_powered_hms_backend.patient.domain.valueobjects;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

/**
 * Immutable value object wrapping a person's date of birth, with
 * sanity-checked bounds and derived helpers (age, minor status).
 */
//@Embeddable
public record DateOfBirth(LocalDate value) {
    private static final int MAX_PLAUSIBLE_AGE_YEARS = 150;

    public DateOfBirth {
   Objects.requireNonNull(value,"Date of birth is required");
            if (value.isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("Date of birth cannot be in the future");
            }
            if (value.isBefore(LocalDate.now().minusYears(MAX_PLAUSIBLE_AGE_YEARS))) {
                throw new IllegalArgumentException("Date of birth is not realistic");
            }
        
    }

    public static DateOfBirth of(LocalDate value) {
    	return new DateOfBirth(value);
    }
    
    public static DateOfBirth of(int year, int month, int day) {
        return new DateOfBirth(LocalDate.of(year, month, day));
    }

    /** Current age in whole years. */
    public int age() {
        return Period.between(value, LocalDate.now()).getYears();
    }

    public boolean isMinor() {
        return age() < 18;
    }
}
