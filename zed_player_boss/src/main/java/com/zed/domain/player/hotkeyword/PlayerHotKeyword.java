package com.zed.domain.player.hotkeyword;

import java.sql.Timestamp;

import com.zed.domain.common.base.BaseModel;
import com.zed.util.CommUtil;

public class PlayerHotKeyword extends BaseModel {

	private static final long serialVersionUID = 1L;
	private String keywordId;
    private Integer status;
    private Timestamp updateTime;
    private String creater;
    private String updater;
    private Integer sort;
    private String keyword;
    private String description;
    private String areaCode;
	public String getKeywordId() {
		return keywordId;
	}
	public void setKeywordId(String keywordId) {
		this.keywordId = keywordId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
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
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	@Override
	public String toString() {
		return "HotKeyWord [keywordId=" + keywordId + ", status=" + status + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", creater=" + creater + ", updater=" + updater + ", sort=" + sort
				+ ", keyword=" + keyword + ", description=" + description + ", areaCode="+areaCode+"]";
	}
	
	public String generateId(){
		return CommUtil.random();
	}
}
