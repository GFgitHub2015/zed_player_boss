package com.zed.domain.player.hotvideo;

import java.sql.Timestamp;

import com.zed.domain.common.base.BaseModel;

/**
 * @date : 2017年06月28日 上午11:57:28
 * @author : XLong
 * @version : 1.0
 * @description : YouTube3d专区列表
*/
public class Youtube3DVideo extends BaseModel {

	private static final long serialVersionUID = 2461811377222964202L;
	private String uid ;//主键
	private String videoId ;//影片ID
	private Long playTime; //影片播放量
	private String videoName ;//片名
	private String videoMemo ;//简介,备注
	private String videoUrl ;//播放地址
	private Integer videoState ;//状态,1:上架,-1下架
	private String videoDuration ;//时长
	private String iconUrl ;//海报地址
	private Integer dimensionType ;//2d:3d
	private String countryCode ;//国家码
	private Integer recommendType; //是否首页推荐,1:推荐,0,不推荐
	private String createUser ;//创建人
	private String updateUser ;//修改人
	private Timestamp updateTime ;//修改时间
	
	private String type; //当type=='live'时代表的是直播，如果是直播类型则不添加影片信息
	
	public enum VideoState {
		/**
		 * 有效
		 */
		STATE_USEFUL(1),
		/**
		 * 无效
		 */
		STATE_USELESS(-1);
		private Integer state;

		private VideoState(Integer state) {
			this.state = state;
		}

		public Integer getState() {
			return this.state;
		}
	}
	
	public enum RecommendType {
		/**
		 * 推荐
		 */
		TYPE_RECOMMEND(1),
		/**
		 * 不推荐
		 */
		TYPE_NO_RECOMMEND(0);
		private Integer type;

		private RecommendType(Integer type) {
			this.type = type;
		}

		public Integer getType() {
			return this.type;
		}
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
	public Long getPlayTime() {
		return playTime;
	}
	public void setPlayTime(Long playTime) {
		this.playTime = playTime;
	}
	public String getVideoName() {
		return videoName;
	}
	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}
	public String getVideoMemo() {
		return videoMemo;
	}
	public void setVideoMemo(String videoMemo) {
		this.videoMemo = videoMemo;
	}
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	public Integer getVideoState() {
		return videoState;
	}
	public void setVideoState(Integer videoState) {
		this.videoState = videoState;
	}
	public String getVideoDuration() {
		return videoDuration;
	}
	public void setVideoDuration(String videoDuration) {
		this.videoDuration = videoDuration;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public Integer getDimensionType() {
		return dimensionType;
	}
	public void setDimensionType(Integer dimensionType) {
		this.dimensionType = dimensionType;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
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
	public Integer getRecommendType() {
		return recommendType;
	}
	public void setRecommendType(Integer recommendType) {
		this.recommendType = recommendType;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}	
