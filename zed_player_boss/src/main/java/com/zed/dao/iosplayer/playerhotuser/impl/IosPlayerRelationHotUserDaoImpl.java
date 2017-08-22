package com.zed.dao.iosplayer.playerhotuser.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zed.dao.iosplayer.playerhotuser.IosPlayerRelationHotUserDao;
import com.zed.dao.player.base.abstractdao.AbstractPlayerPageDao;
import com.zed.domain.iosplayer.playerhotuser.IosPlayerRelationHotUser;
import com.zed.exception.AppValidationException;

@Repository("iosPlayerRelationHotUserDao")
public class IosPlayerRelationHotUserDaoImpl<T> extends AbstractPlayerPageDao<IosPlayerRelationHotUser> implements IosPlayerRelationHotUserDao<IosPlayerRelationHotUser> {

	@Override
	public IosPlayerRelationHotUser getLastSort() throws AppValidationException {
		return this.find("getLastSort", IosPlayerRelationHotUser.Status.ENABLE.getStatus().toString());
	}

	@Override
	public IosPlayerRelationHotUser findByUserId(String userId) throws AppValidationException {
		return this.find("findByUserId", userId);
	}

	@Override
	public void updateSortByUserId(List<IosPlayerRelationHotUser> playerHotUsers) {
		this.updateAll("updateSortByUserId", playerHotUsers);
	}

	@Override
	public List<IosPlayerRelationHotUser> findBySort(Long sort) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sort", sort);
		params.put("status", IosPlayerRelationHotUser.Status.ENABLE.getStatus());
		return this.findList("findBySort", params);
	}

}
