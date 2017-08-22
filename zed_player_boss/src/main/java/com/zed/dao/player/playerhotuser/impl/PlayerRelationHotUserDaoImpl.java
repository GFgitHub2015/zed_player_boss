package com.zed.dao.player.playerhotuser.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zed.dao.player.base.abstractdao.AbstractPlayerPageDao;
import com.zed.dao.player.playerhotuser.IPlayerRelationHotUserDao;
import com.zed.domain.player.playerhotuser.PlayerRelationHotUser;
import com.zed.exception.AppValidationException;

@Repository("playerRelationHotUserDao")
public class PlayerRelationHotUserDaoImpl<T> extends AbstractPlayerPageDao<PlayerRelationHotUser> implements IPlayerRelationHotUserDao<PlayerRelationHotUser> {

	@Override
	public PlayerRelationHotUser getLastSort() throws AppValidationException {
		return this.find("getLastSort", PlayerRelationHotUser.Status.ENABLE.getStatus().toString());
	}

	@Override
	public PlayerRelationHotUser findByUserId(String userId) throws AppValidationException {
		return this.find("findByUserId", userId);
	}

	@Override
	public void updateSortByUserId(List<PlayerRelationHotUser> playerHotUsers) {
		this.updateAll("updateSortByUserId", playerHotUsers);
	}

	@Override
	public List<PlayerRelationHotUser> findBySort(Long sort) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sort", sort);
		params.put("status", PlayerRelationHotUser.Status.ENABLE.getStatus());
		return this.findList("findBySort", params);
	}

}
