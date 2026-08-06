package com.ai_powered_hms_backend.staff.application.port.out;

import java.util.Optional;

import com.ai_powered_hms_backend.shared_kernel.ids.StaffId;
import com.ai_powered_hms_backend.staff.domain.model.StaffProfile;
import com.ai_powered_hms_backend.staff.domain.valueobjects.EmployeeNumber;

//Ports (interfaces the application layer depends on, implemented by infrastructure)
//methods interfaces are defined here but its implementation is done in the RepositoryAdaptor
public interface StaffRepository {
	void save(StaffProfile staff);
	Optional<StaffProfile> findById(StaffId id);
	boolean existsByEmployeeNumber(EmployeeNumber employeeNumber);
	boolean existsByWorkEmail(String workEmail);

}
