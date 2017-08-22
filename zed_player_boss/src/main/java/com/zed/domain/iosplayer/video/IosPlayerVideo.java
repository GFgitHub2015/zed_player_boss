package com.zed.domain.iosplayer.video;

import java.sql.Timestamp;

import com.zed.domain.common.base.BaseModel;


/**
 * @date : 2017年5月9日 下午3:10:09
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 
*/
public class IosPlayerVideo extends BaseModel {
	private static final long serialVersionUID = 2461811377222964202L;
	private String uid ;//主键
	private String videoId ;//影片ID
	private String videoName ;//片名
	private String videoMemo ;//简介,备注
	private String videoUrl ;//播放地址
	private Long videoState ;//状态,1:上架,-1下架
	private String videoDuration ;//时长
	private String iconUrl ;//海报地址
	private String createUser ;//创建人
	private Timestamp createTime ;//创建时间
	private String updateUser ;//修改人
	private Timestamp updateTime ;//修改时间
	private Timestamp startTime ;//开始时间
	private String countryCode ;//国家码

	private Long dimensionType ;//2d:3d
	private Long recommendType;//是否首页推荐
	private String liveBroadcastContent;//直播

	public String getLiveBroadcastContent() {
		return liveBroadcastContent;
	}

	public void setLiveBroadcastContent(String liveBroadcastContent) {
		this.liveBroadcastContent = liveBroadcastContent;
	}

	public Long getDimensionType() {
		return dimensionType;
	}

	public void setDimensionType(Long dimensionType) {
		this.dimensionType = dimensionType;
	}

	public Long getRecommendType() {
		return recommendType;
	}

	public void setRecommendType(Long recommendType) {
		this.recommendType = recommendType;
	}
	public String getVideoMemo() {
		return videoMemo;
	}

	public void setVideoMemo(String videoMemo) {
		this.videoMemo = videoMemo;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public Long getVideoState() {
		return videoState;
	}

	public void setVideoState(Long videoState) {
		this.videoState = videoState;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
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

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getVideoDuration() {
		return videoDuration;
	}

	public void setVideoDuration(String videoDuration) {
		this.videoDuration = videoDuration;
	}


}
