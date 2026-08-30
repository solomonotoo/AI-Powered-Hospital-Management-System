package com.ai_powered_hms_backend.staff.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import com.ai_powered_hms_backend.shared_kernel.base.AggregateRoot;
import com.ai_powered_hms_backend.shared_kernel.ids.StaffId;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.AuditMetadata;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.Email;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.PersonName;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.PhoneNumber;
import com.ai_powered_hms_backend.staff.domain.enums.StaffRole;
import com.ai_powered_hms_backend.staff.domain.enums.StaffStatus;
import com.ai_powered_hms_backend.staff.domain.valueobjects.EmployeeNumber;


//staff profile aggregate — assumptions flagged inline
public class StaffProfile extends AggregateRoot<StaffId>{

	 // Roles requiring a license number to be recorded — ASSUMPTION, confirm/adjust
	private static final Set<StaffRole> CLINICAL_ROLES = Set.of(
			StaffRole.DOCTOR, StaffRole.NURSE, StaffRole.LAB_TECH,
			StaffRole.PHARMACIST, StaffRole.RADIOLOGIST
			);

	private  EmployeeNumber employeeNumber;
	private PersonName fullName;
	/**
	 * Occupational/job-title classification (HR fact) — has NO bearing on system
	 * access. Authorization is governed entirely by Role/RoleAssignment/Permission
	 * (see identity module). This field only drives: (1) the license-required
	 * business rule for clinical roles, (2) staff directory/reporting, and
	 * (3) default role-assignment suggestions in the admin UI.
	 */
	private StaffRole role;
	private String specialisation; //doctors only, nullable
	private String department;
	private Email workEmail;
	private PhoneNumber phone;
	private String licenseNumber; //clinical roles only
	private String qualifications;
	private LocalDate joiningDate;
	private LocalDate endDate;
	private String workingHours;
	private BigDecimal consultationFee; // doctors only, nullable
	private StaffStatus status;
	private AuditMetadata audit;
	
	
	// ==========================================================
		// CONSTRUCTOR FOR NEW STAFF REGISTRATION
		// ==========================================================

	private StaffProfile(
			StaffId id, EmployeeNumber employeeNumber, PersonName fullName, StaffRole role,
			String specialisation, String department, Email workEmail, PhoneNumber phone, String licenseNumber,
			String qualifications, LocalDate joiningDate, String workingHours,
			BigDecimal consultationFee, UUID createdBy) {
		super(id);
		this.employeeNumber = Objects.requireNonNull(employeeNumber, "Employee number is required");
		this.fullName = Objects.requireNonNull(fullName, "Full name is required");
		this.role = Objects.requireNonNull(role, "Role is required");
		this.specialisation = specialisation;
		this.department = department;
		this.workEmail = Objects.requireNonNull(workEmail, "Work email is required");
		this.phone = phone;
		this.licenseNumber = licenseNumber;
		this.qualifications = qualifications;
		this.joiningDate = Objects.requireNonNull(joiningDate, "Joining date is required");
		this.workingHours = workingHours;
		this.consultationFee = consultationFee;
		
		if (CLINICAL_ROLES.contains(role) && (licenseNumber == null || licenseNumber.isBlank())) {
			throw new IllegalArgumentException("License number is required for role");
		}
		
		this.status = StaffStatus.ACTIVE;
		this.audit = AuditMetadata.create(createdBy);
	}


	// ==========================================================
	// CONSTRUCTOR FOR RECONSTITUTION OR REHYDRATION METHOD
	// ==========================================================

	private StaffProfile(
			StaffId id, EmployeeNumber employeeNumber, PersonName fullName, StaffRole role,
			String specialisation, String department, Email workEmail, PhoneNumber phone, String licenseNumber,
			String qualifications, LocalDate joiningDate, LocalDate endDate, String workingHours,
			BigDecimal consultationFee, StaffStatus status, AuditMetadata audit) {
		super(id);
		this.employeeNumber = employeeNumber;
		this.fullName = fullName;
		this.role = role;
		this.specialisation = specialisation;
		this.department = department;
		this.workEmail = workEmail;
		this.phone = phone;
		this.licenseNumber = licenseNumber;
		this.qualifications = qualifications;
		this.joiningDate = joiningDate;
		this.endDate = endDate;
		this.workingHours = workingHours;
		this.consultationFee = consultationFee;
		this.status = Objects.requireNonNull(status,"Status is required");
		this.audit = Objects.requireNonNull(audit, "Audit metadata is required");
	}
	
	
	// ==========================================================
	// FACTORY METHOD FOR NEW STAFF CREATION
	// ==========================================================
	
	public static StaffProfile onboard(
			EmployeeNumber employeeNumber, PersonName fullName, StaffRole role,
			String specialisation, String department, Email workEmail, PhoneNumber phone, String licenseNumber,
			String qualifications, LocalDate joiningDate, String workingHours,
			BigDecimal consultationFee, UUID createdBy
			) {
		return new StaffProfile(
			StaffId.newId(), employeeNumber, fullName, role, specialisation, department,
			workEmail, phone, licenseNumber, qualifications, joiningDate, workingHours,
			consultationFee, createdBy
		);
	}

	
	// ==========================================================
	// RECONSTITUTION METHOD 
	// ==========================================================
	public static StaffProfile reconsitute(
			StaffId id, EmployeeNumber employeeNumber, PersonName fullName, StaffRole role,
			String specialisation, String department, Email workEmail, PhoneNumber phone, String licenseNumber,
			String qualifications, LocalDate joiningDate, LocalDate endDate, String workingHours,
			BigDecimal consultationFee, StaffStatus status, AuditMetadata audit
			) {
		return new StaffProfile(
			id, employeeNumber, fullName, role, specialisation, department, workEmail, phone,
			licenseNumber, qualifications, joiningDate, endDate, workingHours, consultationFee,
			status, audit
		);
	}
	
	
	// ==========================================================
	// STAFF INFORMATION BEHAVOUR COMMANDS THAT CHANGE AGGREGATE STATE
	// ==========================================================
	
	public void updateDepartment(String department, UUID modifiedBy) {
		this.department = department;
		recordChangeBy(modifiedBy);
	}
	
	public void updatePhone(PhoneNumber phone, UUID modifiedBy) {
		this.phone = phone;
		recordChangeBy(modifiedBy);
	}
	
	public void updateWorkingHours(String workingHours, UUID modifiedBy) {
		this.workingHours = workingHours;
		recordChangeBy(modifiedBy);
	}
	
	public void updateWorkemail(Email workEmail, UUID modifiedBy) {
		this.workEmail = workEmail;
		recordChangeBy(modifiedBy);
	}
	
	public void updateQualifications(String qualifications,UUID modifiedBy) {
		this.qualifications = qualifications;
		recordChangeBy(modifiedBy);
	}
	
	public void updateConsultationFee(BigDecimal consultationFee,UUID modifiedBy) {
		this.consultationFee = consultationFee;
		recordChangeBy(modifiedBy);
	}
	
	public void updateSpecialisation(String specialisation, UUID modifiedBy) {
		this.specialisation = specialisation;
		recordChangeBy(modifiedBy);
	}
	
	public void endEmployment(LocalDate enddDate, UUID modifiedBy) {
		Objects.requireNonNull(enddDate, "End date is required");
		if(enddDate.isBefore(joiningDate)) {
			throw new IllegalArgumentException("End date cannot be before joining date");
		}
		
		this.endDate = enddDate;
		changeStatus(StaffStatus.INACTIVE, modifiedBy);
	}
	
	public void reinstate(UUID modifiedBy) {
		this.endDate = null;
		changeStatus(StaffStatus.ACTIVE, modifiedBy);
	}
	
	
	public void changeStatus(StaffStatus newStatus, UUID modified) {
		Objects.requireNonNull(newStatus, "Status must not br null");
		if(this.status == newStatus) return;//no-op, avoid pointless audit touch
		this.status = newStatus;
		recordChangeBy(modified);
	}
	
	public boolean canAuthenticate() {
	    return status == StaffStatus.ACTIVE || status == StaffStatus.ON_DUTY;
	}
	
	private void recordChangeBy(UUID modifiedBy) {
		Objects.requireNonNull(modifiedBy,"Modified by must not be null");
		audit.update(modifiedBy);
	}


	// ==========================================================
	// ACCESSORS/Getters
	// ==========================================================
	
	public StaffId staffId() { return getId(); }
    public EmployeeNumber employeeNumber() { return employeeNumber; }
    public PersonName fullName() { return fullName; }
    public StaffRole role() { return role; }
    public String specialisation() { return specialisation; }
    public String department() { return department; }
    public Email workEmail() { return workEmail; }
    public PhoneNumber phone() { return phone; }
    public String licenseNumber() { return licenseNumber; }
    public String qualifications() { return qualifications; }
    public LocalDate joiningDate() { return joiningDate; }
    public LocalDate endDate() { return endDate; }
    public String workingHours() { return workingHours; }
    public BigDecimal consultationFee() { return consultationFee; }
    public StaffStatus status() { return status; }
    public AuditMetadata audit() { return audit; }
	

	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(!(o instanceof StaffProfile other)) return false;
		return Objects.equals(getId(), other.getId());
		
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}
	

}
