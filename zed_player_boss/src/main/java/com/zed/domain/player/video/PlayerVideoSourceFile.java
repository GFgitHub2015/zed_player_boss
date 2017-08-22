package com.zed.domain.player.video;

import java.sql.Timestamp;

import com.zed.domain.common.base.BaseModel;

public class PlayerVideoSourceFile extends BaseModel{
	private static final long serialVersionUID = 1L;

	//文件使用状态
	public static enum Status{
		DELETE(-1), 					//屏蔽
		STATUS_WAIT_TRANSCODING(0), 	//待转码
		STATUS_TRANSCODE_SUCCESS(1), 	//转码完成
		STATUS_TRANSCODING(2), 			//转码中transcoding
		STATUS_TRANSCODE_FAILD(4); 		//转码失败
		private int status;
		Status(int status){
			this.status = status;
		}
		public int getStatus() {
			
			return status;
		}
	}
	
	private String userHisId;
	private String hisId;
	private String userId;
	private Short status;				//状态：-1:删除  0为待转码，2为转码中，1为转码转码成功，4为转码失败
	private Timestamp updateTime;
	private Integer taskType;
	private String fileId;
    private String parentFileId;
	public String getUserHisId() {
		return userHisId;
	}
	public void setUserHisId(String userHisId) {
		this.userHisId = userHisId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getTaskType() {
		return taskType;
	}
	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public Short getStatus() {
		return status;
	}
	public void setStatus(Short status) {
		this.status = status;
	}
	public String getParentFileId() {
		return parentFileId;
	}
	public void setParentFileId(String parentFileId) {
		this.parentFileId = parentFileId;
	}
	public String getHisId() {
		return hisId;
	}
	public void setHisId(String hisId) {
		this.hisId = hisId;
	}
}