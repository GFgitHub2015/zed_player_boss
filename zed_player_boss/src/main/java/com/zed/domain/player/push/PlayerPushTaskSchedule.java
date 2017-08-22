package com.zed.domain.player.push;

import java.io.Serializable;
import java.sql.Timestamp;

import com.zed.util.CommUtil;
/**
 * push任务调度
 *
 */
public class PlayerPushTaskSchedule implements Serializable {
	private static final long serialVersionUID = -453144277738691060L;
	private String scheduleId ;//调度标识
	private String taskId ;//任务标识
	private Timestamp endTime ;//调度结束时间
	private String status ;//调度状态1表示调度中，2表示调度成功，3表示调度失败，4 暂停，5 停止（停止的调度将不会自动再运行）
	private Timestamp createTime ;//创建时间(调度开始时间)
	private Long scheduleTimeout ;//调度超时时间(以小时为单位)
	private Long retries ;//重试次数
	private String targetStatement ;//查询发送目标的sql
	private String targetType ;//来源于zed_player_push_task
	private String content ;//来源于zed_player_push_task
	private String topicName;
	private String condition;
	private String messageComment;
	private String title;
	private String messageIcon;
	private String reminderType;
	public String getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public Timestamp getEndTime() {
		return endTime;
	}
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Long getScheduleTimeout() {
		return scheduleTimeout;
	}
	public void setScheduleTimeout(Long scheduleTimeout) {
		this.scheduleTimeout = scheduleTimeout;
	}
	public Long getRetries() {
		return retries;
	}
	public void setRetries(Long retries) {
		this.retries = retries;
	}
	public String getTargetStatement() {
		return targetStatement;
	}
	public void setTargetStatement(String targetStatement) {
		this.targetStatement = targetStatement;
	}
	public String getTargetType() {
		return targetType;
	}
	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTopicName() {
		return topicName;
	}
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getMessageComment() {
		return messageComment;
	}
	public void setMessageComment(String messageComment) {
		this.messageComment = messageComment;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessageIcon() {
		return messageIcon;
	}
	public void setMessageIcon(String messageIcon) {
		this.messageIcon = messageIcon;
	}
	public String generateId(){
		return CommUtil.random();
	}
	public String getReminderType() {
		return reminderType;
	}
	public void setReminderType(String reminderType) {
		this.reminderType = reminderType;
	}
	
}
