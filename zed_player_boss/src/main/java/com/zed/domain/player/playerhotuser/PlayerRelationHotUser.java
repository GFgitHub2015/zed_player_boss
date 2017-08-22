package com.zed.domain.player.playerhotuser;

import com.zed.domain.common.base.BaseModel;

public class PlayerRelationHotUser extends BaseModel {

	private static final long serialVersionUID = 1L;
	private String uid;
	private String userId;
	private Integer status;
	private Long sort;
	private Integer origin;
	private String title;
	private String iconUrl;
	
	//热门用户状态
	public static enum Status {
		DISABLE_ALL(-1), //全部禁用包括资源
		DISABLE(0), 	 //禁用
		ENABLE(1); 		 //启用
		private Integer status;
		Status(Integer status) {
			this.status = status;
		}
		public Integer getStatus(){
			return status;
		}
	}
	
	//来源
	public static enum UserSource {
		GBOX(0), 		
		YOUTUBE(1);	
		private Integer source;
		UserSource(Integer source) {
			this.source = source;
		}
		public Integer getSource() {
			return source;
		}
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
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

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	public Integer getOrigin() {
		return origin;
	}

	public void setOrigin(Integer origin) {
		this.origin = origin;
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
		
}
