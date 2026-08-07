package com.ai_powered_hms_backend.facility.application.port.out;

import java.util.List;
import java.util.Optional;

import com.ai_powered_hms_backend.facility.domain.eums.FacilityStatus;
import com.ai_powered_hms_backend.facility.domain.eums.FacilityType;
import com.ai_powered_hms_backend.facility.domain.model.Facility;
import com.ai_powered_hms_backend.shared_kernel.ids.FacilityId;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.FacilityCode;

public interface FacilityRepository {

	void save(Facility facilty);
	Optional<Facility> findById(FacilityId id);
	Optional<Facility> findByCode(FacilityCode code);
	
//	existsByCode is there deliberately — facility onboarding needs a uniqueness 
//	check before creating a new Facility, and that check belongs in the application 
//	layer (can't be enforced inside the aggregate itself, since an aggregate has no 
//			visibility into its siblings)
	boolean existsByCode(FacilityCode code);
	List<Facility> findAllActive();
	
	

    /** Paginated, optionally filtered listing for the admin facility list screen. */
	FacilityPage findAll(FacilityStatus status, FacilityType type,int page, int size);
	
}
