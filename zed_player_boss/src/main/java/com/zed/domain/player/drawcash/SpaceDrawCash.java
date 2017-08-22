package com.zed.domain.player.drawcash;

import com.zed.domain.common.base.BaseModel;

import java.sql.Timestamp;

/**
 * @author : Iris
 * @date : 2017-08-04 14:31
 * @description :
 */
public class SpaceDrawCash extends BaseModel {

    private String id ;//主键
    private String channel ;//所属应用渠道
    private Double amount ;//金额
    private String sourceType ;//提现来源,广告收入,3:奖励收入,2:活动收入
    private Timestamp updateTime ;//修改时间
    private String createUser ;//创建人
    private String updateUser ;//创建人
    private String paymentId ;//广告钱包流水id
    private String cardId ;//提现卡号id
    private String memo ;//备注
    private String state ;//状态,-1:删除
    private String paymentIdExists ;//广告钱包流水id是否存在
    private String appname;
    private Double adAmount;//广告金额
    private Double rewardAmount;//奖励金额

    
    public Double getAdAmount() {
		return adAmount;
	}

	public void setAdAmount(Double adAmount) {
		this.adAmount = adAmount;
	}

	public Double getRewardAmount() {
		return rewardAmount;
	}

	public void setRewardAmount(Double rewardAmount) {
		this.rewardAmount = rewardAmount;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
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

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


}
