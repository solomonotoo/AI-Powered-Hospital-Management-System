package com.ai_powered_hms_backend.patient.domain.valueobjects;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;


public class ConsentInformation {

	private boolean consentToTreat;
	private boolean consentToShareData;
	private LocalDateTime treatmentConsentGivenAt;
	private LocalDateTime dataConsentGivenAt;
	private LocalDateTime updatedAt;
	private UUID updatedBy;

	protected ConsentInformation() {
		// JPA
	}

	private ConsentInformation(boolean consentToTreat, boolean consentToShareData) {

		this.consentToTreat = consentToTreat;
		this.consentToShareData = consentToShareData;

		this.updatedAt = LocalDateTime.now();
	}

	public static ConsentInformation empty() {

		return new ConsentInformation(false, false);
	}

	public void giveTreatmentConsent(UUID userId) {

		if (!consentToTreat) {

			consentToTreat = true;

			treatmentConsentGivenAt = LocalDateTime.now();

			updateAudit(userId);
		}
	}

	public void withdrawTreatmentConsent(UUID userId) {

		if (consentToTreat) {

			consentToTreat = false;

			updateAudit(userId);
		}
	}

	public void giveDataSharingConsent(UUID userId) {

		if (!consentToShareData) {

			consentToShareData = true;

			dataConsentGivenAt = LocalDateTime.now();

			updateAudit(userId);
		}
	}

	public void withdrawDataSharingConsent(UUID userId) {

		if (consentToShareData) {

			consentToShareData = false;

			updateAudit(userId);
		}
	}

	private void updateAudit(UUID userId) {

		this.updatedBy = userId;

		this.updatedAt = LocalDateTime.now();
	}
	
	public static ConsentInformation rehydrate(
	        boolean consentToTreat,
	        boolean consentToShareData,
	        LocalDateTime treatmentConsentGivenAt,
	        LocalDateTime dataConsentGivenAt,
	        LocalDateTime updatedAt,
	        UUID updatedBy
	) {
	    ConsentInformation consent = new ConsentInformation(consentToTreat, consentToShareData);
	    consent.treatmentConsentGivenAt = treatmentConsentGivenAt;
	    consent.dataConsentGivenAt = dataConsentGivenAt;
	    consent.updatedAt = updatedAt;
	    consent.updatedBy = updatedBy;
	    return consent;
	}

	public boolean hasTreatmentConsent() {

		return consentToTreat;
	}

	public boolean hasDataSharingConsent() {

		return consentToShareData;
	}

	public LocalDateTime getTreatmentConsentGivenAt() {

		return treatmentConsentGivenAt;
	}

	public LocalDateTime getDataConsentGivenAt() {

		return dataConsentGivenAt;
	}
	
	public LocalDateTime getUpdatedAt() {
	    return updatedAt;
	}

	public UUID getUpdatedBy() {
	    return updatedBy;
	}
}