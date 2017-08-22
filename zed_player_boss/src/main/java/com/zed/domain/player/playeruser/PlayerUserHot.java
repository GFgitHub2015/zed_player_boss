package com.zed.domain.player.playeruser;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.zed.search.model.SearchModel;
import com.zed.util.ES;

@Document(indexName = ES.ESProp.INDEX_PLAYER_USER_HOT_NAME, type = ES.ESProp.TYPE_PLAYER_USER_HOT_NAME)
public class PlayerUserHot implements SearchModel{
	@Id
	@Field(type = FieldType.String, index = FieldIndex.not_analyzed, store = true)
	private String userId;																		//用户的id
	@Field(type = FieldType.Long, index = FieldIndex.not_analyzed, store = true)
    private Long shareCount;																	//用户分享资源量
	@Field(type = FieldType.Long, index = FieldIndex.not_analyzed, store = true)
    private Long iFollowCount;																//我所关注的总数
	@Field(type = FieldType.Long, index = FieldIndex.not_analyzed, store = true)
    private Long followMeCount;																//关注我的总数
	@Field(type = FieldType.Integer, index = FieldIndex.not_analyzed, store = true)
    private Integer status;																	//状态值  0:禁用 1:启用
	@Field(type = FieldType.String, index = FieldIndex.not_analyzed, store = true)
	private Integer countryCode;
	@Field(type = FieldType.Integer, index = FieldIndex.not_analyzed, store = true)
	private Integer userRoleStatus;															//网盘用户角色
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getShareCount() {
		return shareCount;
	}

	public void setShareCount(Long shareCount) {
		this.shareCount = shareCount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(Integer countryCode) {
		this.countryCode = countryCode;
	}

	public void setiFollowCount(Long iFollowCount) {
		this.iFollowCount = iFollowCount;
	}

	public void setFollowMeCount(Long followMeCount) {
		this.followMeCount = followMeCount;
	}

	public Long getiFollowCount() {
		return iFollowCount;
	}

	public Long getFollowMeCount() {
		return followMeCount;
	}

	public Integer getUserRoleStatus() {
		return userRoleStatus;
	}

	public void setUserRoleStatus(Integer userRoleStatus) {
		this.userRoleStatus = userRoleStatus;
	}

	public Map<String, Object> forMap(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", this.getUserId());
		map.put("shareCount", this.getShareCount());
		map.put("iFollowCount", this.getiFollowCount());
		map.put("followMeCount", this.getFollowMeCount());
		map.put("status", this.getStatus());
		map.put("countryCode", this.getCountryCode());
		map.put("userRoleStatus", this.getUserRoleStatus());
		return map;
	}

	@Override
	public String getSearchId() {
		return userId;
	}

	@Override
	public String toString() {
		return "PlayerUserHot [userId=" + userId + ", shareCount=" + shareCount + ", iFollowCount=" + iFollowCount
				+ ", followMeCount=" + followMeCount + ", status=" + status + ", countryCode=" + countryCode + "]";
	}

}