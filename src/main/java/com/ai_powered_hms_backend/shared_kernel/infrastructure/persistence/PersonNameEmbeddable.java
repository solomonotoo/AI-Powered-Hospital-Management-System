package com.ai_powered_hms_backend.shared_kernel.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

//embeddable shadow for person name

@Embeddable
public class PersonNameEmbeddable {

	 @Column(name = "first_name", nullable = false, length = 45)
	    private String firstName;

	    @Column(name = "last_name", nullable = false, length = 45)
	    private String lastName;

	    @Column(name = "maiden_name", length = 45)
	    private String maidenName;

	    @Column(name = "preferred_name", length = 45)
	    private String preferredName;

	    protected PersonNameEmbeddable() {
	        // JPA
	    }
	    public PersonNameEmbeddable(String firstName, String lastName, String maidenName, String preferredName) {
	        this.firstName = firstName;
	        this.lastName = lastName;
	        this.maidenName = maidenName;
	        this.preferredName = preferredName;
	    }

	    public String getFirstName() { return firstName; }
	    public String getLastName() { return lastName; }
	    public String getMaidenName() { return maidenName; }
	    public String getPreferredName() { return preferredName; }
}
