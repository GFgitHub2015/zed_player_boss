package com.zed.domain.system;

import java.io.Serializable;
import java.sql.Timestamp;

public class AdminLogin implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String adminId;
	private Timestamp loginTime;
	private String loginIp;
	
	
	public String getAdminId() {
		return adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	public String getLoginIp() {
		return loginIp;
	}
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	public Timestamp getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Timestamp loginTime) {
		this.loginTime = loginTime;
	}
	
}
