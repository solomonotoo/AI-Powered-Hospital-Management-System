package com.ai_powered_hms_backend.identity.domain.model;

import java.time.LocalDateTime;

import com.ai_powered_hms_backend.shared_kernel.base.AggregateRoot;
import com.ai_powered_hms_backend.shared_kernel.ids.SessionId;
import com.ai_powered_hms_backend.shared_kernel.ids.StaffId;

public final class UserSession extends AggregateRoot<SessionId>{

	private StaffId staffId;
	private LocalDateTime issuedAt;
	private LocalDateTime expiresAt;// = refresh token's expiry, not access token's
	private String userAgent;
	private boolean revoked;
	
	
	  private UserSession(SessionId id, StaffId staffId, LocalDateTime issuedAt, LocalDateTime expiresAt, String userAgent, boolean revoked) {
	        super(id);
	        this.staffId = staffId;
	        this.issuedAt = issuedAt;
	        this.expiresAt = expiresAt;
	        this.userAgent = userAgent;
	        this.revoked = revoked;
	    }
	  
	public static UserSession issue(SessionId id, StaffId staffId, LocalDateTime issuedAt, LocalDateTime expiresAt, String userAgent) {
        return new UserSession(id, staffId, issuedAt, expiresAt, userAgent, false);
    }
	
	public static UserSession reconstitute(SessionId id,StaffId staffId, LocalDateTime issuedAt,
			LocalDateTime expiresAt, String userAgent, boolean revoked) {
		
		return new UserSession(id, staffId, issuedAt, expiresAt, userAgent, revoked);
	}
	
	public void revoke() {
		this.revoked = true;
	}
	
	public boolean isValid() {
		return !revoked && expiresAt.isAfter(LocalDateTime.now());
	}
	
	public SessionId sessionId() {return getId();}
	public StaffId staffId() {
		return staffId;
	}
	public LocalDateTime issuedAt() {
		return issuedAt;
	}
	public LocalDateTime expiresAt() {
		return expiresAt;
	}
	public String userAgent() {
		return userAgent;
	}
	public boolean isRevoked() {
		return revoked;
	}
	
}
