package com.zed.domain.iosplayer.playerhotuser;

import com.zed.domain.common.base.BaseModel;

/**
 * @date : 2017年7月03日 下午4:10:01
 * @author : X.Long
 * @version : 1.0
 * @description : 第三方YouTube Api返回的结果字段
*/
public class IosYouTubeHotUser extends BaseModel {

	private static final long serialVersionUID = 1L;
	private String userId;
	private String title;
	private String iconUrl;
	private Integer isRecommond;
	 
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public Integer getIsRecommond() {
		return isRecommond;
	}
	public void setIsRecommond(Integer isRecommond) {
		this.isRecommond = isRecommond;
	}
	
}
