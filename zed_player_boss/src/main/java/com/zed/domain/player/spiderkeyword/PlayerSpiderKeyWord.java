package com.zed.domain.player.spiderkeyword;

import java.sql.Timestamp;

import com.zed.domain.common.base.BaseModel;
import com.zed.util.CommUtil;

public class PlayerSpiderKeyWord extends BaseModel {
	
	private static final long serialVersionUID = 1L;
	private String keywordId;
	private Integer status;
	private String keyword;
	private Timestamp updateTime;
	private String updater;
	private Integer retry;
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

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public Integer getRetry() {
		return retry;
	}

	public void setRetry(Integer retry) {
		this.retry = retry;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String generateId(){
		return CommUtil.random();
	}
}
