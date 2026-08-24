package com.ai_powered_hms_backend.shared_kernel.ids;

import java.util.Objects;
import java.util.UUID;

public record RoleAssignmentId(UUID value) {

	//Constructor validation This is called the compact constructor, 
			//and it's the recommended way to validate record components.
		public RoleAssignmentId{
			Objects.requireNonNull(value, "RoleId value cannot be null");
		}
		
		public static RoleAssignmentId newId() {
			return new RoleAssignmentId(UUID.randomUUID());
		}
		
		public static RoleAssignmentId of(UUID value) {
			return new RoleAssignmentId(value);
		}
		
		
		//It makes converting database or API values much easier.
		public static RoleAssignmentId of(String value) {
			return new RoleAssignmentId(UUID.fromString(value));
		}
		
		
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return value.toString();
		}
}
