//package com.hms_application.shared_kernel.infrastructure.persistence;
//
//import com.hms_application.shared_kernel.valueobjects.PhoneNumber;
//
//public class PhoneNumberMapper {
//
//	public static PhoneNumberEmbeddable toEmbeddable(PhoneNumber domain) {
//        return domain == null ? null : new PhoneNumberEmbeddable(domain.value());
//    }
//
//    public static PhoneNumber toDomain(PhoneNumberEmbeddable embeddable) {
//        if (embeddable == null || embeddable.getValue() == null) return null;
//        return new PhoneNumber(embeddable.getValue());
//    }
//}
