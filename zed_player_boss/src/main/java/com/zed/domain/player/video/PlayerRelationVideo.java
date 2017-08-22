package com.zed.domain.player.video;

import java.sql.Timestamp;

import com.zed.domain.common.base.BaseModel;

public class PlayerRelationVideo extends BaseModel {
	
	private static final long serialVersionUID = 1L;
	private String uid;
	private String fileId;
	private String fileName;
	private Integer status;
	private String iconUrl;
	private Integer origin;
	private Integer dimensionType;
	private Integer flag;
	private Timestamp startTime;
	private Timestamp endTime;
	private String createUser;
	private String updateUser;
	private Timestamp updateTime;
	private String countryCode;
	private String type; //用于验证从YouTube拿下的视频是否是直播视频
	 
	//1:有效、上架,-1:无效、下架 0:待上架
	public static enum Status {
		DISABLE(0), 	 //下架
		ENABLE(1); 		 //上架
		private Integer status;
		Status(Integer status) {
			this.status = status;
		}
		public Integer getStatus(){
			return status;
		}
	}
	
	//视频来源 0: GBox 1：YouTube
	public static enum Orgin {
		GBOX(0), 		
		YOUTUBE(1);	
		private Integer orign;
		Orgin(Integer orign) {
			this.orign = orign;
		}
		public Integer getOrign() {
			return orign;
		}
	}
		
	//标签手动设置,0:自动判断,1:最热,2:最新
	public static enum Flag {
		AUTO(2),  //自动
		NEWS(1),  //最新
		HOT(2); //最热
		private Integer flag;
		Flag(Integer flag) {
			this.flag = flag;
		}
		public Integer getFlag() {
			return flag;
		}
	}
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public Integer getOrigin() {
		return origin;
	}

	public void setOrigin(Integer origin) {
		this.origin = origin;
	}

	public Integer getDimensionType() {
		return dimensionType;
	}

	public void setDimensionType(Integer dimensionType) {
		this.dimensionType = dimensionType;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
		
}
