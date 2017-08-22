package com.zed.service.player.push;

import java.util.List;
import java.util.Map;

import com.zed.domain.player.push.PlayerPushTopic;
import com.zed.exception.AppValidationException;
import com.zed.system.page.Page;

public interface PlayerPushTopicService {
	
	public Page<PlayerPushTopic> findByPage(Page<PlayerPushTopic> page);
	
	//根据topicId获取对象
	public PlayerPushTopic findById(String topicId) throws AppValidationException;
	//添加对象
	public void add(PlayerPushTopic playerPushTopic) throws AppValidationException;
	//修改对象
	public void update(PlayerPushTopic playerPushTopic) throws AppValidationException;
	//根据topicIds删除对象(可批量)
	public void delete(String ... topicIds) throws AppValidationException;
	//修改对象的状态
	public void updateStatus(PlayerPushTopic playerPushTopic) throws AppValidationException;
	//获取所有的主题 1表示有效，-1表示废弃（删除）
	public List<PlayerPushTopic> findAllWithStatus(Integer status) throws AppValidationException;
	//根据countryCode topicType 和typeValue获取所有的主题信息集合
	public List<PlayerPushTopic> findListByTopicTypeWithTypeValue(Map<String, Object> params) throws AppValidationException;

}
