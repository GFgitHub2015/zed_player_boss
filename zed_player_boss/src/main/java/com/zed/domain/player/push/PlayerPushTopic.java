package com.zed.domain.player.push;

import java.io.Serializable;
/**
 *推送主题(用于google fcm推送) 
 *
 */
public class PlayerPushTopic implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer topicId;				//主题标识
	private String countryCode;				//国家编码
	private Integer topicType;				//主题类型 1:版本，2：渠道，3：语言
	private String typeValue;				//主题类型对应的值，如类型为版本，则值为版本编号；类型为渠道值为渠道编号；类型为语言，值为语言代码
	private String topicName;				//TOPIC名称规则为/topics/${TOPIC_ID}
	private Integer  status;				//1表示有效，-1表示废弃（删除）
	public Integer getTopicId() {
		return topicId;
	}
	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public Integer getTopicType() {
		return topicType;
	}
	public void setTopicType(Integer topicType) {
		this.topicType = topicType;
	}
	public String getTopicName() {
		return topicName;
	}
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getTypeValue() {
		return typeValue;
	}
	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}
	
	
}
