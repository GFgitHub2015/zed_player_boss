package com.zed.domain.iosplayer.slidershow;

import java.sql.Timestamp;

import com.zed.util.CommUtil;

public class IosPlayerSliderShow implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	//使用状态
	public static enum Status{
		STOP(0), 		//禁用
		START(1), 	//启用
		DELETE(-1); 	//删除
		private Integer status;
		Status(Integer status){
			this.status = status;
		}
		public Integer getStatus() {
			return status;
		}
	}
	
	//使用源头
	public static enum Origin{
		GBOX(0), 			//gbox
		PLAYER(1); 		//zillion player
		private Integer origin;
		Origin(Integer origin){
			this.origin = origin;
		}
		public Integer getOrigin() {
			return origin;
		}
	}
	
	private String sliderShowId;
	private String type;				//轮播图类型 0:链接地址 1:影片fileId
	private String value;
	private Timestamp lastTime;
	private String creater;
	private String imgUrl;
	private Integer status;				//使用状态 -1:已删除 0:禁用 1:启用
	private Integer origin;				//轮播图使用来源 0:站长app（网盘站长用户） 1:player
	private String areaCode;
    private Integer sort;
    private String description;
    private Timestamp startTime;
    private Timestamp endTime;
	
	public String getSliderShowId() {
		return sliderShowId;
	}
	public void setSliderShowId(String sliderShowId) {
		this.sliderShowId = sliderShowId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Timestamp getLastTime() {
		return lastTime;
	}
	public void setLastTime(Timestamp lastTime) {
		this.lastTime = lastTime;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public String generateId(){
		return CommUtil.random();
	}
	public Integer getOrigin() {
		return origin;
	}
	public void setOrigin(Integer origin) {
		this.origin = origin;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
}
