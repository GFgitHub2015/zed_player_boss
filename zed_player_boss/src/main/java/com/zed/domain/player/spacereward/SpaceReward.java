package com.zed.domain.player.spacereward;

import com.zed.domain.common.base.BaseModel;

import java.sql.Timestamp;

/**
 * @date : 2017年7月26日 下午7:10:16
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 奖励记录
*/
public class SpaceReward extends BaseModel{
	private static final long serialVersionUID = -6966107079425243637L;
	private String id ;//主键
	private String channel ;//所属应用渠道
	private Long earnings ;//奖励金额
	private String sourceType ;//奖励类型,徽章类型
	private String sourceTypeName ;//奖励类型,徽章类型,,1:广告,2:活动,3:奖励
	private Timestamp createTime ;//创建时间
	private Timestamp updateTime ;//修改时间
	private String createUser ;//创建人
	private String updateUser ;//创建人
	private String paymentId ;//广告钱包流水id
	private String paymentIdExists ;//广告钱包流水id是否存在
	private String memo ;//备注
	private String iconUrl;//徽章图标
	private String iconTitle;//徽标说明
	private String appname;
	private String state;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPaymentIdExists() {
		return paymentIdExists;
	}
	public void setPaymentIdExists(String paymentIdExists) {
		this.paymentIdExists = paymentIdExists;
	}
	public String getAppname() {
		return appname;
	}
	public void setAppname(String appname) {
		this.appname = appname;
	}
	public String getSourceTypeName() {
		return sourceTypeName;
	}
	public void setSourceTypeName(String sourceTypeName) {
		this.sourceTypeName = sourceTypeName;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public String getIconTitle() {
		return iconTitle;
	}
	public void setIconTitle(String iconTitle) {
		this.iconTitle = iconTitle;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
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
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Long getEarnings() {
		return earnings;
	}
	public void setEarnings(Long earnings) {
		this.earnings = earnings;
	}
	
}
