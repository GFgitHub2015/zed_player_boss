package com.zed.domain.account.accountphonecode;

import java.sql.Timestamp;

public class AccountPhoneCode implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	private String code;
	private Integer origin;
	private String userId;
	private Integer status;
	private Integer type;
	private String phone;
	private String phoneCode;
	private Timestamp createTime;
	private Timestamp expireTime;
	private String areaCode;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getOrigin() {
		return origin;
	}
	public void setOrigin(Integer origin) {
		this.origin = origin;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPhoneCode() {
		return phoneCode;
	}
	public void setPhoneCode(String phoneCode) {
		this.phoneCode = phoneCode;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Timestamp getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(Timestamp expireTime) {
		this.expireTime = expireTime;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

}
