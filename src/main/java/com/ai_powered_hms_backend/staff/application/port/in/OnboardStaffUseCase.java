package com.ai_powered_hms_backend.staff.application.port.in;

import com.ai_powered_hms_backend.shared_kernel.ids.StaffId;
import com.ai_powered_hms_backend.staff.application.command.OnboardStaffCommand;


//Its job is to define an application action that changes the system's state.

//Notice this interface doesn't know anything about HTTP, databases, or JPA. It simply says:
//"If someone gives me an OnboardStaffCommand, I will onboard a staff member."

// this is more or less a service class interface and it will be implemented in
//OnboardStaffService which has the annotation @Service
public interface OnboardStaffUseCase {

	//StaffId handle(OnboardStaffCommand command);
	StaffId onboard(OnboardStaffCommand command);
}

// these will be created late as separate interfaces
//public interface TransferStaffUseCase {
//    void handle(TransferStaffCommand command);
//}
//
//public interface UpdateStaffPhoneUseCase {
//    void handle(UpdateStaffPhoneCommand command);
//}
//
//public interface ChangeWorkingHoursUseCase {
//    void handle(ChangeWorkingHoursCommand command);
//}
//
//public interface EndEmploymentUseCase {
//    void handle(EndEmploymentCommand command);
//}
//
//public interface ReinstateStaffUseCase {
//    void handle(ReinstateStaffCommand command);
//}