package com.zed.domain.player.push;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import com.zed.common.util.JsonUtils;
import com.zed.util.CommUtil;

public class PlayerTaskParams implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String taskId;
	private String title;
	private String content;
	private String description;
	private String icon;
	private String messageIcon;
	private String reminderType;
	private Integer target;
	private String userId;
	private Integer topicType;
	private String areaCode;
	private Integer operation;
	private String type;
	private String url;
	private String fileId;
	private String picked;
	private Timestamp pushTime;
	private String creater;
	private Timestamp createTime;
	private String updater;
	private Integer status;
	private String version;
	private String language;
	private String channel;
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getReminderType() {
		return reminderType;
	}
	public void setReminderType(String reminderType) {
		this.reminderType = reminderType;
	}
	public Integer getTarget() {
		return target;
	}
	public void setTarget(Integer target) {
		this.target = target;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getTopicType() {
		return topicType;
	}
	public void setTopicType(Integer topicType) {
		this.topicType = topicType;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public Integer getOperation() {
		return operation;
	}
	public void setOperation(Integer operation) {
		this.operation = operation;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public Timestamp getPushTime() {
		return pushTime;
	}
	public void setPushTime(Timestamp pushTime) {
		this.pushTime = pushTime;
	}
	public String getPicked() {
		return picked;
	}
	public void setPicked(String picked) {
		this.picked = picked;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getUpdater() {
		return updater;
	}
	public void setUpdater(String updater) {
		this.updater = updater;
	}
	public String getMessageIcon() {
		return messageIcon;
	}
	public void setMessageIcon(String messageIcon) {
		this.messageIcon = messageIcon;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public Map<String, Object> forMap(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("taskId", this.getTaskId());
		map.put("title", this.getTitle());
		map.put("content", this.getContent());
		map.put("description", this.getDescription());
		map.put("icon", this.getIcon());
		map.put("messageIcon", this.getMessageIcon());
		map.put("reminderType", this.getReminderType());
		map.put("target", this.getTarget());
		map.put("userId", this.getUserId());
		map.put("topicType", this.getTopicType());
		map.put("areaCode", this.getAreaCode());
		map.put("operation", this.getOperation());
		map.put("type", this.getType());
		map.put("url", this.getUrl());
		map.put("fileId", this.getFileId());
		map.put("picked", this.getPicked());
		map.put("pushTime", this.getPushTime());
		map.put("creater", this.getCreater());
		map.put("createTime", this.getCreateTime());
		map.put("updater", this.getUpdater());
		map.put("status", this.getStatus());
		map.put("version", this.getVersion());
		map.put("language", this.getLanguage());
		map.put("channel", this.getChannel());
		return map;
	}
	
	public static PlayerTaskParams getPlayerTaskParams(String jsonStr){
		return JsonUtils.jsonToObj(jsonStr,PlayerTaskParams.class);
	}
	
	public String generateId(){
		return CommUtil.random();
	}
	
}
