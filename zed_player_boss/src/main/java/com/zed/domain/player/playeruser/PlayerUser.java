package com.zed.domain.player.playeruser;

import java.sql.Timestamp;

import com.zed.util.CommUtil;

public class PlayerUser implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private String nickName;
	private String iconUrl;
	private String phone;
	private String areaCode;
	private String userId;
	private String sign;
	private Integer status;
	private Timestamp createTime;
	private Timestamp updateTime;
	private Long iFollowCount;
	private Long followMeCount;
	private Integer shareCount;
	private Integer userRoleStatus;
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
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
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
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
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public Long getiFollowCount() {
		return iFollowCount;
	}
	public void setiFollowCount(Long iFollowCount) {
		this.iFollowCount = iFollowCount;
	}
	public Long getFollowMeCount() {
		return followMeCount;
	}
	public void setFollowMeCount(Long followMeCount) {
		this.followMeCount = followMeCount;
	}
	public Integer getShareCount() {
		return shareCount;
	}
	public void setShareCount(Integer shareCount) {
		this.shareCount = shareCount;
	}
	public Integer getUserRoleStatus() {
		return userRoleStatus;
	}
	public void setUserRoleStatus(Integer userRoleStatus) {
		this.userRoleStatus = userRoleStatus;
	}
	
	public String generateId(){
		return CommUtil.random();
	}

}
