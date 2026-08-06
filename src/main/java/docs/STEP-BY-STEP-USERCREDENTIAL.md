DDD-HEXAGONAL-CQRS STEPS USER CREDENTIALS

1. DECIDE ON WHETHERE TO USE A SHARED MODULE(ID,VO'S,ENUMS)
2.GET ALL YOUR VO'S AND ENUMS READY FOR YOUR START WITH THE AGGREGATE ROOT MODEL
3.CREATE YOUR DOMAIN MODULE(EG STAFF MODULE(StaffProfile.java))
	-- StaffProfile
		-- create aggregate variable
		-- private constructors for new staff creation and rehyration/reconstitution
		-- factory method for creating new staff and reconstitution
		-- business behaviour that implements business rules
		-- create your accessors(getters)
	-- application ports(out) interface
		-- StaffRepository.java
			-- void save(StaffProfile staff);
			-- Optional<StaffProfile> findById(StaffId id);
			-- boolean existsByEmployeeNumber(EmployeeNumber employeeNumber);
			-- boolean existsByWorkEmail(String workEmail);
    -- application command 
    	-- OnboardStaffCommand.java
    -- application port(in) interface
    	-- OnboardStaffUseCase.java
    -- application service
    	-- OnboardStaffService.java