package com.zed.domain.player.logicalfile;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import com.zed.common.util.JsonUtils;
import com.zed.util.CommUtil;

public class PlayerLogicalFile implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	//是否是文件
	public static enum Is{
		FOLDER(0), 	//文件夹
		FILE(1); 	//文件
		private int status;
		Is(int status){
			this.status = status;
		}
		public int getStatus() {
			
			return status;
		}
	}
	
	//是否分享
	public static enum ShareStatus{
		UNSHARE(0), //文件夹
		SHARE(1); 	//文件
		private int status;
		ShareStatus(int status){
			this.status = status;
		}
		public int getStatus() {
			
			return status;
		}
	}
	
	private String fileId;
	private String sourceFileId;
	private String userId;
	private String creater;
	private String updater;
	private Timestamp createTime;
	private Timestamp updateTime;
	private String fileName;
	private Integer status; 				//-1：删除  0 待下载， 1：下载完成       2：资源下载中    3：资源下载暂停   4:资源下载失败   5：转码中  6：转码失败   7：转码完成
	private Integer shareStatus;			//0:不分享（未分享） 1:分享
	private Long fileSize;
	private Integer sourceType;
	private Integer isFile;					//1，文件夹，2，文件
	private String filePath;
	private String parentFileId;			//上一级的文件夹fileId
	
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getSourceFileId() {
		return sourceFileId;
	}
	public void setSourceFileId(String sourceFileId) {
		this.sourceFileId = sourceFileId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getShareStatus() {
		return shareStatus;
	}
	public void setShareStatus(Integer shareStatus) {
		this.shareStatus = shareStatus;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	public Integer getSourceType() {
		return sourceType;
	}
	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}
	public Integer getIsFile() {
		return isFile;
	}
	public void setIsFile(Integer isFile) {
		this.isFile = isFile;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getParentFileId() {
		return parentFileId;
	}
	public void setParentFileId(String parentFileId) {
		this.parentFileId = parentFileId;
	}
	public Map<String, Object> forMap(){
		Map<String, Object> map = new HashMap<String, Object>();
		return map;
	}
	public static PlayerLogicalFile getPlayerLogicalFile(String jsonStr){
		return JsonUtils.jsonToObj(jsonStr,PlayerLogicalFile.class);
	}
	
	public String toJson() {
		return JsonUtils.getJsonStrByMap(this.forMap());
	}
	
	public String generateId(){
		return CommUtil.random();
	}
}
