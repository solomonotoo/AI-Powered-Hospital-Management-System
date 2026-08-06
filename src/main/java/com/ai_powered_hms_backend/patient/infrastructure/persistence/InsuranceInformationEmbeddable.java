package com.ai_powered_hms_backend.patient.infrastructure.persistence;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class InsuranceInformationEmbeddable {

	@Column(name = "insurance_provider", length = 150)
    private String provider;

    @Column(name = "insurance_policy_number", length = 100)
    private String policyNumber;

    @Column(name = "insurance_group_number", length = 100)
    private String groupNumber;

    @Column(name = "insurance_coverage_start_date")
    private LocalDate coverageStartDate;

    @Column(name = "insurance_expiration_date")
    private LocalDate expirationDate;

    protected InsuranceInformationEmbeddable() {
        // JPA
    }
    
    public InsuranceInformationEmbeddable(String provider, String policyNumber, String groupNumber, LocalDate coverageStartDate, LocalDate expirationDate) {
        this.provider = provider;
        this.policyNumber = policyNumber;
        this.groupNumber = groupNumber;
        this.coverageStartDate = coverageStartDate;
        this.expirationDate = expirationDate;
    }

    public String getProvider() { return provider; }
    public String getPolicyNumber() { return policyNumber; }
    public String getGroupNumber() { return groupNumber; }
    public LocalDate getCoverageStartDate() { return coverageStartDate; }
    public LocalDate getExpirationDate() { return expirationDate; }
}
