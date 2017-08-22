package com.zed.domain.player.site;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import com.zed.common.util.JsonUtils;
import com.zed.util.CommUtil;

public class PlayerSiteNavigate implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	//site 使用状态
	public static enum SiteStatus{
		STOP(0), 	//禁用
		START(1); 	//启用
		private int status;
		SiteStatus(int status){
			this.status = status;
		}
		public int getStatus() {
			
			return status;
		}
	}
	private String siteNavigateId;
	private String creater;
	private String updater;
	private Timestamp createTime;
	private Timestamp updateTime;
	private String title;
	private String remark;
	private String siteUrl;
	private Integer sort;
	private String imgUrl;
	private Integer status;
	private String countryCode;
	public String getSiteNavigateId() {
		return siteNavigateId;
	}
	public void setSiteNavigateId(String siteNavigateId) {
		this.siteNavigateId = siteNavigateId;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public String getUpdater() {
		return updater;
	}
	public void setUpdater(String updater) {
		this.updater = updater;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSiteUrl() {
		return siteUrl;
	}
	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
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
	public Map<String, Object> forMap(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("siteNavigateId", this.getSiteNavigateId());
		map.put("title", this.getTitle());
		map.put("siteUrl", this.getSiteUrl());
		map.put("imgUrl", this.getImgUrl());
		map.put("sort", this.getSort());
		map.put("countryCode", this.getCountryCode());
		return map;
	}
	public static PlayerSiteNavigate getPlayerSiteNavigate(String jsonStr){
		return JsonUtils.jsonToObj(jsonStr,PlayerSiteNavigate.class);
	}
	
	public String toJson() {
		return JsonUtils.getJsonStrByMap(this.forMap());
	}
	
	public String generateId(){
		return CommUtil.random();
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
}
