package com.zed.domain.system;

import java.io.Serializable;

public class AdminSession implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String adminId;
	private String sessionId;
	
	
	public String getAdminId() {
		return adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	
	
		
}