package com.ai_powered_hms_backend.staff.application.port.out;

import java.util.Optional;

import com.ai_powered_hms_backend.shared_kernel.ids.StaffId;
import com.ai_powered_hms_backend.staff.application.query.StaffSummaryResult;
import com.ai_powered_hms_backend.staff.domain.enums.StaffRole;
import com.ai_powered_hms_backend.staff.domain.model.StaffProfile;
import com.ai_powered_hms_backend.staff.domain.valueobjects.EmployeeNumber;

//Ports (interfaces the application layer depends on, implemented by infrastructure)
//methods interfaces are defined here but its implementation is done in the RepositoryAdaptor
public interface StaffRepository {
	void save(StaffProfile staff);
	Optional<StaffProfile> findById(StaffId id);
	boolean existsByEmployeeNumber(EmployeeNumber employeeNumber);
	boolean existsByWorkEmail(String workEmail);
	
	/** Paginated, optionally filtered listing for the admin staff list screen. */
	StaffPage findAll(StaffRole staffRole, int page, int size);
	
	StaffSummaryResult getSummary();
	
	
}
