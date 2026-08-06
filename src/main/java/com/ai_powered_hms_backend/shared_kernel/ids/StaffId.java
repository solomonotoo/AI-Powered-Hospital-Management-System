package com.ai_powered_hms_backend.shared_kernel.ids;

import java.util.Objects;
import java.util.UUID;

public record StaffId(UUID value) {

	//Constructor validation This is called the compact constructor, 
	//and it's the recommended way to validate record components.
	public StaffId{
		Objects.requireNonNull(value,"StaffId value must not be null");
	}
	
	public static StaffId newId() {
		return new StaffId(UUID.randomUUID());
	}
	
	public static StaffId of(UUID value) {
		return new StaffId(value);
	}
	
	//It makes converting database or API values much easier.
	public static StaffId of(String value) {
		return new StaffId(UUID.fromString(value));
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return value.toString();
	}
		
}

//public final class StaffId {
//    private final UUID value;
//
//    private StaffId(UUID value) {
//        this.value = Objects.requireNonNull(value, "Staff ID value must not be null");
//    }
//
//    public static StaffId newId() { return new StaffId(UUID.randomUUID()); }
//    public static StaffId of(UUID value) { return new StaffId(value); }
//    public UUID value() { return value; }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof StaffId other)) return false;
//        return value.equals(other.value);
//    }
//
//    @Override
//    public int hashCode() { return value.hashCode(); }
//
//    @Override
//    public String toString() { return value.toString(); }
//}
