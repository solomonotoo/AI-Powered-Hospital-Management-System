package com.ai_powered_hms_backend.patient.infrastructure.persistence;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

//This requires ConsentInformation to expose a rehydration path, since right now its only
//public factory is empty() — there's no way to reconstruct one with existing consent state
//from the DB. Add a package-private-friendly rehydrate factory, same pattern as AuditMetadata:


@Embeddable
public class ConsentInformationEmbeddable {

    @Column(name = "consent_to_treat", nullable = false)
    private boolean consentToTreat;

    @Column(name = "consent_to_share_data", nullable = false)
    private boolean consentToShareData;

    @Column(name = "treatment_consent_given_at")
    private LocalDateTime treatmentConsentGivenAt;

    @Column(name = "data_consent_given_at")
    private LocalDateTime dataConsentGivenAt;

    @Column(name = "consent_updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "consent_updated_by")
    private UUID updatedBy;

    protected ConsentInformationEmbeddable() {
        // JPA
    }

    public ConsentInformationEmbeddable(
            boolean consentToTreat,
            boolean consentToShareData,
            LocalDateTime treatmentConsentGivenAt,
            LocalDateTime dataConsentGivenAt,
            LocalDateTime updatedAt,
            UUID updatedBy
    ) {
        this.consentToTreat = consentToTreat;
        this.consentToShareData = consentToShareData;
        this.treatmentConsentGivenAt = treatmentConsentGivenAt;
        this.dataConsentGivenAt = dataConsentGivenAt;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }

    public boolean isConsentToTreat() { return consentToTreat; }
    public boolean isConsentToShareData() { return consentToShareData; }
    public LocalDateTime getTreatmentConsentGivenAt() { return treatmentConsentGivenAt; }
    public LocalDateTime getDataConsentGivenAt() { return dataConsentGivenAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public UUID getUpdatedBy() { return updatedBy; }
}
