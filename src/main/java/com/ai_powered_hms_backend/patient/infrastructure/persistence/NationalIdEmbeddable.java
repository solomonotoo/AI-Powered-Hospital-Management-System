package com.ai_powered_hms_backend.patient.infrastructure.persistence;



import com.ai_powered_hms_backend.patient.domain.enums.IdType;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

//embeddable shadow for national id

@Embeddable
public class NationalIdEmbeddable {

	@Enumerated(EnumType.STRING)
    @Column(name = "national_id_type", length = 30)
    private IdType idType;

    @Column(name = "national_id_number", length = 40)
    private String idNumber;

    @Column(name = "national_id_issuing_country", length = 56)
    private String issuingCountry;

    protected NationalIdEmbeddable() {
        // JPA
    }
	
    public NationalIdEmbeddable(IdType idType, String idNumber, String issuingCountry) {
        this.idType = idType;
        this.idNumber = idNumber;
        this.issuingCountry = issuingCountry;
    }

    public IdType getIdType() { return idType; }
    public String getIdNumber() { return idNumber; }
    public String getIssuingCountry() { return issuingCountry; }
}
