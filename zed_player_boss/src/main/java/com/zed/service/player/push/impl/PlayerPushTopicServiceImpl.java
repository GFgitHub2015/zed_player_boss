package com.zed.service.player.push.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zed.dao.player.push.PlayerPushTopicDao;
import com.zed.domain.player.push.PlayerPushTopic;
import com.zed.exception.AppValidationException;
import com.zed.service.player.push.PlayerPushTopicService;
import com.zed.system.page.Page;

@Service("playerPushTopicService")
public class PlayerPushTopicServiceImpl implements PlayerPushTopicService {
	
	@Resource(name="playerPushTopicDao")
	private PlayerPushTopicDao<PlayerPushTopic> playerPushTopicDao;

	@Override
	public Page<PlayerPushTopic> findByPage(Page<PlayerPushTopic> page) {
		return playerPushTopicDao.findByPage(page);
	}

	@Override
	public PlayerPushTopic findById(String topicId) throws AppValidationException {
		return (PlayerPushTopic) playerPushTopicDao.findById(topicId);
	}

	@Override
	public void add(PlayerPushTopic playerPushTopic) throws AppValidationException {
		playerPushTopicDao.add(playerPushTopic);		
	}

	@Override
	public void update(PlayerPushTopic playerPushTopic) throws AppValidationException {
		playerPushTopicDao.update(playerPushTopic);		
	}

	@Override
	public void delete(String... topicIds) throws AppValidationException {
		playerPushTopicDao.delete(topicIds);		
	}

	@Override
	public void updateStatus(PlayerPushTopic playerPushTopic) throws AppValidationException {
		playerPushTopicDao.update(playerPushTopic);		
	}

	@Override
	public List<PlayerPushTopic> findAllWithStatus(Integer status) throws AppValidationException {
		return playerPushTopicDao.findAll(status);
	}

	@Override
	public List<PlayerPushTopic> findListByTopicTypeWithTypeValue(Map<String, Object> params) throws AppValidationException {
		return playerPushTopicDao.findListByTopicTypeWithTypeValue(params);
	}

}
