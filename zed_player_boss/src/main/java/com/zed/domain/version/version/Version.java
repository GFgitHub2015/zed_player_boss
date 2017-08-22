package com.zed.domain.version.version;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import com.zed.common.util.JsonUtils;
import com.zed.util.CommUtil;

public class Version implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String versionId;
	private Integer status;			//状态1在用，0老版本
	private Timestamp createTime;
	private Timestamp updateTime;
	private String creater;
	private String editer;
	private String versionCode;
	private String systemType;
	private String downloadUrl;
	private String description;		//更新描述
	private Integer origin;			//1:Live 2:Player
	private Integer isMust;			//1:强制升级 0:非强制升级
	private String md5;
	private String appType;			//1:正常版本，2:player精简版.....其他
	private String packageName;		//app包名
	private String channel;		//渠道
	private String autoUpLoadAppFileName;
	private Integer source;		//下载来源
	
	public String getVersionId() {
		return versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getEditer() {
		return editer;
	}

	public void setEditer(String editer) {
		this.editer = editer;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public String getSystemType() {
		return systemType;
	}

	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getOrigin() {
		return origin;
	}

	public void setOrigin(Integer origin) {
		this.origin = origin;
	}

	public Integer getIsMust() {
		return isMust;
	}

	public void setIsMust(Integer isMust) {
		this.isMust = isMust;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String generateId(){
		return CommUtil.random();
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getAutoUpLoadAppFileName() {
		return autoUpLoadAppFileName;
	}

	public void setAutoUpLoadAppFileName(String autoUpLoadAppFileName) {
		this.autoUpLoadAppFileName = autoUpLoadAppFileName;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}
	
}
