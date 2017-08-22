package com.zed.domain.player.spaceactiveuser;

import java.sql.Timestamp;

import com.zed.domain.common.base.BaseModel;

/**
 * @date : 2017年5月10日 上午11:31:29
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 站长日活跃用户统计
*/
public class PlayerSpaceActiveUser  extends BaseModel{
	private static final long serialVersionUID = 2640012768854285290L;
	private String uid ;//主键
	private String statDate ;//统计日期
	private Long activeCount ;//日活跃数
	private Long adgActiveCount ;//日增活跃
	private String channel ;//渠道
	private String updateUser ;//修改人
	private Timestamp updateTime ;//修改时间
	private Long sevenStat;//七日数据
	private Long thirtyStat;//30日数据
	private String appname;//应用名
	private Long dataCount;
	private String allDate;
	
	private Double scale;
	private Double activeCountScale;
	
	public Double getActiveCountScale() {
		return activeCountScale;
	}
	public void setActiveCountScale(Double activeCountScale) {
		this.activeCountScale = activeCountScale;
	}
	
	public Long getAdgActiveCount() {
		return adgActiveCount;
	}
	public void setAdgActiveCount(Long adgActiveCount) {
		this.adgActiveCount = adgActiveCount;
	}
	public Double getScale() {
		return scale;
	}
	public void setScale(Double scale) {
		this.scale = scale;
	}
	public String getAllDate() {
		return allDate;
	}
	public void setAllDate(String allDate) {
		this.allDate = allDate;
	}
	public String getAppname() {
		return appname;
	}
	public void setAppname(String appname) {
		this.appname = appname;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getStatDate() {
		return statDate;
	}
	public void setStatDate(String statDate) {
		this.statDate = statDate;
	}
	public Long getActiveCount() {
		return activeCount;
	}
	public void setActiveCount(Long activeCount) {
		this.activeCount = activeCount;
	}

	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
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
	public Long getSevenStat() {
		return sevenStat;
	}
	public void setSevenStat(Long sevenStat) {
		this.sevenStat = sevenStat;
	}
	public Long getThirtyStat() {
		return thirtyStat;
	}
	public void setThirtyStat(Long thirtyStat) {
		this.thirtyStat = thirtyStat;
	}
	public Long getDataCount() {
		return dataCount;
	}
	public void setDataCount(Long dataCount) {
		this.dataCount = dataCount;
	}

}
