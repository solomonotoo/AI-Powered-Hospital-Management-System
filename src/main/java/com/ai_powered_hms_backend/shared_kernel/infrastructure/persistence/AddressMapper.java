package com.ai_powered_hms_backend.shared_kernel.infrastructure.persistence;

import com.ai_powered_hms_backend.shared_kernel.valueobjects.Address;

public class AddressMapper {

	public static AddressEmbeddable toEmbeddable(Address domain) {
        if (domain == null) return null;
        return new AddressEmbeddable(
                domain.line1(),
                domain.line2(),
                domain.city(),
                domain.state(),
                domain.postalCode(),
                domain.country()
        );
    }

    public static Address toDomain(AddressEmbeddable embeddable) {
        if (embeddable == null) return null;
        return new Address(
                embeddable.getLine1(),
                embeddable.getLine2(),
                embeddable.getCity(),
                embeddable.getState(),
                embeddable.getPostalCode(),
                embeddable.getCountry()
        );
    }
}
