package com.ai_powered_hms_backend.shared_kernel.enums;

//Human-readable values
//If you want to display O+ or AB- instead of O_POSITIVE and AB_NEGATIVE, 
//you can add display labels:
public enum BloodGroup {
	 A_POSITIVE("A+"),
	    A_NEGATIVE("A-"),
	    B_POSITIVE("B+"),
	    B_NEGATIVE("B-"),
	    AB_POSITIVE("AB+"),
	    AB_NEGATIVE("AB-"),
	    O_POSITIVE("O+"),
	    O_NEGATIVE("O-");

	    private final String displayName;

	    BloodGroup(String displayName) {
	        this.displayName = displayName;
	    }

	    public String getDisplayName() {
	        return displayName;
	    }
}
