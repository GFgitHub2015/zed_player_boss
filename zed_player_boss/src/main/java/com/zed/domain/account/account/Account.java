package com.zed.domain.account.account;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.zed.util.CommUtil;

public class Account implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	private String userId;
	private Integer status;
	private String country;
	private String areaCode;
	private String phone;
	private String password;
	private String iconUrl;
	private String nickName;
	private String sex;
	private Timestamp bir;
	private Integer origin;
	private BigDecimal exps;
	private BigDecimal scores;
	private BigDecimal amount;
	private BigDecimal level;
	private Timestamp createTime;
	private Timestamp updateTime;
	private Integer userType;
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
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Timestamp getBir() {
		return bir;
	}
	public void setBir(Timestamp bir) {
		this.bir = bir;
	}
	public Integer getOrigin() {
		return origin;
	}
	public void setOrigin(Integer origin) {
		this.origin = origin;
	}
	public BigDecimal getExps() {
		return exps;
	}
	public void setExps(BigDecimal exps) {
		this.exps = exps;
	}
	public BigDecimal getScores() {
		return scores;
	}
	public void setScores(BigDecimal scores) {
		this.scores = scores;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getLevel() {
		return level;
	}
	public void setLevel(BigDecimal level) {
		this.level = level;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	
	public String generateId(){
		return CommUtil.random();
	}
}
