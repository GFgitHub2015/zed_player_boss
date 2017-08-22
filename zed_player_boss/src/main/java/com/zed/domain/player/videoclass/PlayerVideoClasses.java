package com.zed.domain.player.videoclass;

import java.sql.Timestamp;

import com.zed.util.CommUtil;

public class PlayerVideoClasses implements java.io.Serializable{
	
private static final long serialVersionUID = 1L;

	//classKey前缀 player_xxx为player的类型key值开头， youtube_xxxx为youtube的key值开头
	public static enum Prefix{
		PLAYER("player-"), 				//player
		YOUTUBE("youtube-"); 			//youtube
		private String key;
		Prefix(String key){
			this.key = key;
		}
		public String getKey() {
			return key;
		}
	}
	
	//使用状态
	public static enum Status{
		STOP(0),    //禁用
		START(1), 	//启用
		DELETE(-1); //删除
		private Integer status;
		Status(Integer status){
			this.status = status;
		}
		public Integer getStatus() {
			
			return status;
		}
	}
	
	//使用源头
	public static enum Origin{
		GBOX("0"), 				//gbox
		PLAYER("1"); 			//zillion player
		private String origin;
		Origin(String origin){
			this.origin = origin;
		}
		public String getOrigin() {
			return origin;
		}
	}
	
	//是否固定
	public static enum Fixed{
		UnFixed(0), 				//不固定
		Fixed(1); 				//固定
		private Integer status;
		Fixed(Integer status){
			this.status = status;
		}
		public Integer getStatus() {
			return status;
		}
	}
	
	//是否固定
	public static enum Recommended{
		UnRecommended(0), 				//不推荐
		Recommended(1); 					//推荐
		private Integer status;
		Recommended(Integer status){
			this.status = status;
		}
		public Integer getStatus() {
			return status;
		}
	}
	
	private String classId;
	private String classKey;			//分类key 用于获取相应分类类型的识别码
	private Integer sort;
	private String creater;
	private Timestamp createTime;
	private String updater;
	private Timestamp updateTime;
	private String origin;				//轮播图使用来源 0:站长app（网盘站长用户） 1:player 2:youtube
	private Integer status;				//使用状态 -1:已删除 0:禁用 1:启用
    private String description;			//描述，用于搜索（可用于类别名称）
    private Integer fixed;				//0:不固定 1:固定 默认为不固定
    private Integer recommended;			//0:不推荐 1:推荐， 默认为不推荐
    
	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getClassKey() {
		return classKey;
	}

	public void setClassKey(String classKey) {
		this.classKey = classKey;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getFixed() {
		return fixed;
	}

	public void setFixed(Integer fixed) {
		this.fixed = fixed;
	}

	public Integer getRecommended() {
		return recommended;
	}

	public void setRecommended(Integer recommended) {
		this.recommended = recommended;
	}

	public String generateId(){
		return CommUtil.random();
	}
}
