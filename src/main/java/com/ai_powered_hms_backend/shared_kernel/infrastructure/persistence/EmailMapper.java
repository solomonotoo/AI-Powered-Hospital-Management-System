//package com.hms_application.shared_kernel.infrastructure.persistence;
//
//import com.hms_application.shared_kernel.valueobjects.Email;
//
//
////Note the null-safety here matters concretely — email is optional on both 
////Patient and Facility. @Embeddable fields that can be entirely absent need
////@Embedded combined with care: Hibernate by default will instantiate the 
////embeddable object even if all inner columns are null, which produces a
////non-null EmailEmbeddable wrapping null internally, rather than a null reference. 
////That's why the mapper explicitly re-checks embeddable.getValue() == null behavior
//public class EmailMapper {
//
//	public static EmailEmbeddable toEmbeddable(Email domain) {
//		return domain == null ? null : new EmailEmbeddable(domain.getValue());
//	}
//	
//	public static Email toDomain(EmailEmbeddable embeddable) {
//		if (embeddable == null || embeddable.getValue() == null) return null;
//	    return new Email(embeddable.getValue());
//	}
//}
