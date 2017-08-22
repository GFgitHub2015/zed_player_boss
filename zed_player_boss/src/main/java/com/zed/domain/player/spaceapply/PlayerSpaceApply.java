package com.zed.domain.player.spaceapply;

import java.sql.Timestamp;

public class PlayerSpaceApply implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String companyName;
	private String videoSiteUrl;
	private String name;
	private String areaCode;
	private String phone;
	private String email;
	private Integer status;
	private Timestamp createTime;
	private String userId;
	private String websiteLink;
	private Integer moviesNum;
	private String countryName;
	private String app;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getVideoSiteUrl() {
		return videoSiteUrl;
	}
	public void setVideoSiteUrl(String videoSiteUrl) {
		this.videoSiteUrl = videoSiteUrl;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getWebsiteLink() {
		return websiteLink;
	}
	public void setWebsiteLink(String websiteLink) {
		this.websiteLink = websiteLink;
	}
	public Integer getMoviesNum() {
		return moviesNum;
	}
	public void setMoviesNum(Integer moviesNum) {
		this.moviesNum = moviesNum;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getApp() {
		return app;
	}
	public void setApp(String app) {
		this.app = app;
	}
	
}
