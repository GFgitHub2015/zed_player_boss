package com.zed.domain.account.loginrecord;

import java.io.Serializable;
import java.sql.Timestamp;

public class LoginRecord implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String loginRecordId;
	private String userId;
	private Timestamp createTime;
	private String creater;
	private String loginSite;
	private String loginIp;
	private String loginOrigin;
	private Double lon;
	private Double lat;
	private String token;
	public String getLoginRecordId() {
		return loginRecordId;
	}
	public void setLoginRecordId(String loginRecordId) {
		this.loginRecordId = loginRecordId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public String getLoginSite() {
		return loginSite;
	}
	public void setLoginSite(String loginSite) {
		this.loginSite = loginSite;
	}
	public String getLoginIp() {
		return loginIp;
	}
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	public String getLoginOrigin() {
		return loginOrigin;
	}
	public void setLoginOrigin(String loginOrigin) {
		this.loginOrigin = loginOrigin;
	}
	public Double getLon() {
		return lon;
	}
	public void setLon(Double lon) {
		this.lon = lon;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
}
