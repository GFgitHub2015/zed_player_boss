package com.zed.dao.player.push;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.zed.dao.PageDao;
import com.zed.domain.player.push.PlayerPushTopic;
import com.zed.exception.AppValidationException;

public interface PlayerPushTopicDao<T extends Serializable> extends PageDao<PlayerPushTopic>  {
	
	
	public List<PlayerPushTopic> findAll(Integer status) throws AppValidationException;
	
	//根据topicType 和typeValue获取所有的主题信息集合
	public List<PlayerPushTopic> findListByTopicTypeWithTypeValue(Map<String, Object> params) throws AppValidationException;

}
