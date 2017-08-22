package com.zed.domain.player.youtube;

import java.io.Serializable;
import java.sql.Timestamp;

public class PlayerYouTuBeChannel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String channelId;		//youtube 的channelId,此处作为youtube 的用户的订阅者id
	private Integer sort;			//人工排序
	private Timestamp createTime;	//创建时间
	private String creater;			//创建者
	private Timestamp updateTime;	//修改时间
	private String updater;			//修改者
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
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
	
}
