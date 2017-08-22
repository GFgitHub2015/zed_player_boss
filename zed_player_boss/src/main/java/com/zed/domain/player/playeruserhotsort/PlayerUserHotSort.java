package com.zed.domain.player.playeruserhotsort;

import com.zed.util.CommUtil;

public class PlayerUserHotSort implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String userSortId;					//主键
	private String	userId;						//用户编号
	private Integer sort;						//用户排序序号
	private String countryCode;					//用户注册时的国家码
	public String getUserSortId() {
		return userSortId;
	}
	public void setUserSortId(String userSortId) {
		this.userSortId = userSortId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
	public String generateId(){
		return CommUtil.random();
	}

}
