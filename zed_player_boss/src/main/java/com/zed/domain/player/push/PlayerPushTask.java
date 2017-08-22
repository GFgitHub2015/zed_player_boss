package com.zed.domain.player.push;

import java.io.Serializable;
import java.sql.Timestamp;

import com.zed.util.CommUtil;

public class PlayerPushTask implements Serializable {
	private static final long serialVersionUID = -4207843879668047933L;
	//task 使用状态
	public static enum Status{
		DELETE(-1), 	//禁用
		WAITING(0), 	//等待调度
		STARTING(1),	//启用
		SUCCESS(2),		//调度成功
		FAILED(3),		//调度失败
		PAUSE(4); 		//暂停
		private int status;
		Status(int status){
			this.status = status;
		}
		public int getStatus() {
			
			return status;
		}
	}
	
	public static enum Type{
		MOVIE("1001"),				//推荐影片
		PROMOTION("1002"),			//活动
		OPEN_APPLICATION("1003"); 	//打开应用	
		private String type;
		Type(String type){
			this.type = type;
		}
		public String getType() {
			return type;
		}
	}
	
	public static enum TargetType{
		USER(1),						//指定用户
		CONDITION(2),					//条件推送
		TOPIC(3),						//按主题推送
		TOPIC_CONDITION(4); 			//按主题条件推送
		private Integer targetType;
		TargetType(Integer targetType){
			this.targetType = targetType;
		}
		public Integer getTargetType() {
			return targetType;
		}
	}
	
	private String taskId;							//任务编号
	private Timestamp createTime;					//创建时间
	private String creater;							//创建人
	private String updater;							//修改人
	private String title;							//标题
	private Timestamp startTime;					//任务开始时间
	private Long scheduleTimeout;					//任务调度超时时间，以小时为单位，默认为0表示不超时(不超时的任务不会在调度失败的时候自动重新调度)
	private Integer status;							//任务状态 -1表示删除，0表示未调度，1表示调度中，2表示调度成功，3调度失败，4暂停
	private String type;							//任务类型 1001表示有新的影片提醒
	private Integer targetType;						//PUSH目标类型，1指定用户推送，2按查询指定用户推送
	private String targetStatement;					//生成的查询发送目标用户编号的sql
	private String content;							//消息文本
	private String topicName;						//多个主题名称以逗号分隔
	private String condition;						//按主题条件推送
	private String messageComment;					//消息扩展属性（以json格式存储）
	private String reminderType;					//0表示所有，1表示震动，2表示响铃
	private String messageIcon;						//默认为空
	private String description;
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
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
	public String getUpdater() {
		return updater;
	}
	public void setUpdater(String updater) {
		this.updater = updater;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public Long getScheduleTimeout() {
		return scheduleTimeout;
	}
	public void setScheduleTimeout(Long scheduleTimeout) {
		this.scheduleTimeout = scheduleTimeout;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getTargetType() {
		return targetType;
	}
	public void setTargetType(Integer targetType) {
		this.targetType = targetType;
	}
	public String getTargetStatement() {
		return targetStatement;
	}
	public void setTargetStatement(String targetStatement) {
		this.targetStatement = targetStatement;
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
	public String getReminderType() {
		return reminderType;
	}
	public void setReminderType(String reminderType) {
		this.reminderType = reminderType;
	}
	public String getMessageIcon() {
		return messageIcon;
	}
	public void setMessageIcon(String messageIcon) {
		this.messageIcon = messageIcon;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String generateId(){
		return CommUtil.random();
	}
}
