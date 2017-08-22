package com.zed.domain.player.push;

import java.sql.Timestamp;

import com.zed.domain.common.base.BaseModel;

public class PlayerPushTarget extends BaseModel{
	
	private static final long serialVersionUID = 1L;
	private String clientId;
	private Timestamp createTime;
	private String scheduleId;
	private Integer status;
	private Integer targetType;
	private String toId;
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getTargetType() {
		return targetType;
	}
	public void setTargetType(Integer targetType) {
		this.targetType = targetType;
	}
	public String getToId() {
		return toId;
	}
	public void setToId(String toId) {
		this.toId = toId;
	}
}
