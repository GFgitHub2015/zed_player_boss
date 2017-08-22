package com.zed.dao.player.push.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zed.dao.player.base.abstractdao.AbstractPlayerPageDao;
import com.zed.dao.player.push.PlayerPushTopicDao;
import com.zed.domain.player.push.PlayerPushTopic;
import com.zed.exception.AppValidationException;

@Repository("playerPushTopicDao")
public class PlayerPushTopicDaoImpl <T> extends AbstractPlayerPageDao<PlayerPushTopic> implements PlayerPushTopicDao< PlayerPushTopic> {

	@Override
	public List<PlayerPushTopic> findAll(Integer status) throws AppValidationException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", status);
		return findList("findAll",params);
	}

	@Override
	public List<PlayerPushTopic> findListByTopicTypeWithTypeValue(Map<String, Object> params)
			throws AppValidationException {
		return findList("findListByTopicTypeWithTypeValue",params);
	}

}
