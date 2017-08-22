package com.zed.domain.player.hotvideo;

import java.sql.Timestamp;

import com.zed.domain.common.base.BaseModel;

/**
 * @date : 2016年12月28日 下午4:37:22
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 热门影片
*/
public class Hot3DVideo extends BaseModel {
	private static final long serialVersionUID = 2461811377222964202L;
	private String uid ;//主键 
	private String videoName ;//片名
	private String fileId ;//影片ID
	private String playTime ;//播放次数
	private String iconUrl ;//海报地址
	private Timestamp startTime ;//上线时间
	private Timestamp endTime ;//下线时间
	private Long videoState ;//状态
	private String hotReason ;//推荐理由
	private String createUser ;//创建人
	private Timestamp createTime ;//创建时间
	private String updateUser ;//修改人
	private Timestamp updateTime ;//修改时间
	private Long tagType ;//标签手动设置,0:自动判断,1:最热,2:最新
	private Long playTimesReal;//播放次数
	private Long downTimes;//下载次数
	private String countryCode;//国家码
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getVideoName() {
		return videoName;
	}
	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getPlayTime() {
		return playTime;
	}
	public void setPlayTime(String playTime) {
		this.playTime = playTime;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public Long getVideoState() {
		return videoState;
	}
	public void setVideoState(Long videoState) {
		this.videoState = videoState;
	}
	public String getHotReason() {
		return hotReason;
	}
	public void setHotReason(String hotReason) {
		this.hotReason = hotReason;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public Timestamp getEndTime() {
		return endTime;
	}
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	public Long getTagType() {
		return tagType;
	}
	public void setTagType(Long tagType) {
		this.tagType = tagType;
	}
	public Long getPlayTimesReal() {
		return playTimesReal;
	}
	public void setPlayTimesReal(Long playTimesReal) {
		this.playTimesReal = playTimesReal;
	}
	public Long getDownTimes() {
		return downTimes;
	}
	public void setDownTimes(Long downTimes) {
		this.downTimes = downTimes;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
}
