package com.zed.domain.player.logicalfile;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.zed.search.model.SearchModel;
import com.zed.util.ES;

@Document(indexName = ES.ESProp.INDEX_PLAYER_LOGICAL_FILE_NAME, type = ES.ESProp.TYPE_PLAYER_LOGICAL_FILE_NAME)
public class PlayerVideoResources implements SearchModel{
	@Id
	@Field(type = FieldType.String, index = FieldIndex.not_analyzed, store = true)
	private String fileId;																//资源逻辑文件的fileId
	@Field(type = FieldType.Long, index = FieldIndex.not_analyzed, store = true)
    private Long duration;																//转码后的播放资源时长（从fileType=0的问价中获取）
	@Field(type = FieldType.String, index = FieldIndex.not_analyzed, store = true)
    private String imgUrl;																//转码后的资源截图
	@Field(type = FieldType.String, store = true)
    private String fileName;
	@Field(type = FieldType.String, index = FieldIndex.not_analyzed, store = true)
	private String fileSuffix;
	@Field(type = FieldType.Long, index = FieldIndex.not_analyzed, store = true)
    private Long fileSize;
	@Field(type = FieldType.Integer, index = FieldIndex.not_analyzed, store = true)
    private Integer status;																//搜索资源状态值  0:禁用 1:启用
	@Field(type = FieldType.String, index = FieldIndex.not_analyzed, store = true)
	private String userId;																//资源逻辑文件所属用户的id
	@Field(type = FieldType.String, index = FieldIndex.not_analyzed, store = true)
    private String score;
	@Field(type = FieldType.Integer, index = FieldIndex.not_analyzed, store = true)
	private Integer dimension;															//视频播放模式 2:2D,3:3D
	@Field(type = FieldType.Integer, index = FieldIndex.not_analyzed, store = true)
    private Integer shareStatus;														//用户分享状态
	@Field(type=FieldType.String, store = true)  
	private String  countryCode;              											//用户资源的共享区域，站长可多个,国家码之间用空格隔开
	
	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getFileName() {
		if (!StringUtils.isEmpty(fileName)) {
			if (fileName.lastIndexOf(".")>0) {
				String suffix = fileName.substring(fileName.lastIndexOf("."));
				String preffix = fileName.substring(0, fileName.lastIndexOf("."));
				String regEx="[`~!@#$%^&*+=|{}':;',\\\\.<>/?～！@#￥%……&*——+|{}【】‘；：”“’。，、？]";  
				Pattern p = Pattern.compile(regEx);     
				Matcher m = p.matcher(preffix);
				fileName = m.replaceAll(" ").trim()+suffix;
			}
		}
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getFileSuffix() {
		return fileSuffix;
	}

	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}
	
	public Integer getDimension() {
		return dimension;
	}

	public void setDimension(Integer dimension) {
		this.dimension = dimension;
	}

	public Integer getShareStatus() {
		return shareStatus;
	}

	public void setShareStatus(Integer shareStatus) {
		this.shareStatus = shareStatus;
	}

	public Map<String, Object> forMap(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fileId", this.getFileId());
		map.put("duration", this.getDuration());
		map.put("imgUrl", this.getImgUrl());
		map.put("fileName", this.getFileName());
		map.put("fileSize", this.getFileSize());
		map.put("fileSuffix", this.getFileSuffix());
		map.put("uid", this.getUserId());
		map.put("score", this.getScore());
		map.put("dimension", this.getDimension());
		map.put("shareStatus", this.getShareStatus());
		map.put("status", this.getStatus());
		map.put("countryCode", this.getCountryCode());
		return map;
	}

	@Override
	public String getSearchId() {
		return fileId;
	}

	@Override
	public String toString() {
		return "PlayerVideoResources2 [fileId=" + fileId + ", duration=" + duration + ", imgUrl=" + imgUrl
				+ ", fileName=" + fileName + ", fileSuffix=" + fileSuffix + ", fileSize=" + fileSize + ", status="
				+ status + ", userId=" + userId + ", score=" + score + ", dimension=" + dimension + ", shareStatus="
				+ shareStatus + ", countryCode="+countryCode+"]";
	}

}