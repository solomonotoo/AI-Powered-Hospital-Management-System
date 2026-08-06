package com.ai_powered_hms_backend.staff.infrastructure.persistence;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.ai_powered_hms_backend.shared_kernel.infrastructure.persistence.AuditMetadataEmbeddable;
import com.ai_powered_hms_backend.shared_kernel.infrastructure.persistence.EmailConverter;
import com.ai_powered_hms_backend.shared_kernel.infrastructure.persistence.PersonNameEmbeddable;
import com.ai_powered_hms_backend.shared_kernel.infrastructure.persistence.PhoneNumberConverter;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.Email;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.PhoneNumber;
import com.ai_powered_hms_backend.staff.domain.enums.StaffRole;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "staff")
public class StaffJpaEntity {
	
	@Id
	private UUID id;
	
	@Column(name = "employee_number", nullable = false, unique = true, length = 30)
    private String employeeNumber;
	
	@Embedded
	private PersonNameEmbeddable fullName;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false, length = 30)
	private StaffRole role;
	
	@Column(name = "specialisation", length = 100)
    private String specialisation;
	
	@Column(name = "department", length = 100)
    private String department;

    @Convert(converter = EmailConverter.class)
    @Column(name = "work_email", nullable = false, unique = true, length = 150)
    private Email workEmail;

    @Convert(converter = PhoneNumberConverter.class)
    @Column(name = "phone", length = 20)
    private PhoneNumber phone;

    @Column(name = "license_number", length = 80)
    private String licenseNumber;

    @Column(name = "qualifications", columnDefinition = "TEXT")
    private String qualifications;

    @Column(name = "joining_date", nullable = false)
    private LocalDate joiningDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "working_hours", length = 100)
    private String workingHours;

    @Column(name = "consultation_fee", precision = 10, scale = 2)
    private BigDecimal consultationFee;

    @Column(name = "is_active", nullable = false)
    private boolean active;

    @Embedded
    private AuditMetadataEmbeddable audit;

    protected StaffJpaEntity() {}
    
    public StaffJpaEntity(
    		UUID id, String employeeNumber, PersonNameEmbeddable fullName, StaffRole role,
            String specialisation, String department, Email workEmail, PhoneNumber phone,
            String licenseNumber, String qualifications, LocalDate joiningDate, LocalDate endDate,
            String workingHours, BigDecimal consultationFee, boolean active, AuditMetadataEmbeddable audit
    		) {
    	 this.id = id; this.employeeNumber = employeeNumber; this.fullName = fullName; this.role = role;
         this.specialisation = specialisation; this.department = department; this.workEmail = workEmail;
         this.phone = phone; this.licenseNumber = licenseNumber; this.qualifications = qualifications;
         this.joiningDate = joiningDate; this.endDate = endDate; this.workingHours = workingHours;
         this.consultationFee = consultationFee; this.active = active; this.audit = audit;
  	
    }

	public UUID getId() {
		return id;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public PersonNameEmbeddable getFullName() {
		return fullName;
	}

	public StaffRole getRole() {
		return role;
	}

	public String getSpecialisation() {
		return specialisation;
	}

	public String getDepartment() {
		return department;
	}

	public Email getWorkEmail() {
		return workEmail;
	}

	public PhoneNumber getPhone() {
		return phone;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public String getQualifications() {
		return qualifications;
	}

	public LocalDate getJoiningDate() {
		return joiningDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public String getWorkingHours() {
		return workingHours;
	}

	public BigDecimal getConsultationFee() {
		return consultationFee;
	}

	public boolean isActive() {
		return active;
	}

	public AuditMetadataEmbeddable getAudit() {
		return audit;
	}

    
    
}
