package com.ai_powered_hms_backend.staff.infrastructure.persistence;

import com.ai_powered_hms_backend.shared_kernel.ids.StaffId;
import com.ai_powered_hms_backend.shared_kernel.infrastructure.persistence.AuditMetadataMapper;
import com.ai_powered_hms_backend.shared_kernel.infrastructure.persistence.PersonNameMapper;
import com.ai_powered_hms_backend.staff.domain.model.StaffProfile;
import com.ai_powered_hms_backend.staff.domain.valueobjects.EmployeeNumber;

//maps staff object from Jpaentity to Domain object and vice versa
public class StaffPersistenceMapper {

	public static StaffJpaEntity toEntity(StaffProfile staff) {
		return new StaffJpaEntity(
				staff.staffId().value(), staff.employeeNumber().value(),
                PersonNameMapper.toEmbeddable(staff.fullName()), staff.role(),
                staff.specialisation(), staff.department(), staff.workEmail(), staff.phone(),
                staff.licenseNumber(), staff.qualifications(), staff.joiningDate(), staff.endDate(),
                staff.workingHours(), staff.consultationFee(), staff.status(),
                AuditMetadataMapper.toEmbeddable(staff.audit())
			);
	}
	
	public static StaffProfile toDomain(StaffJpaEntity entity) {
		return StaffProfile.reconsitute(StaffId.of(entity.getId()),new EmployeeNumber(entity.getEmployeeNumber()),
				PersonNameMapper.toDomain(entity.getFullName()), entity.getRole(), entity.getSpecialisation(),
				entity.getDepartment(), entity.getWorkEmail(), entity.getPhone(), entity.getLicenseNumber(),
				entity.getQualifications(), entity.getJoiningDate(), entity.getEndDate(), entity.getWorkingHours(),
				entity.getConsultationFee(), entity.getStatus(), AuditMetadataMapper.toDomain(entity.getAudit())
		);
	}
}
