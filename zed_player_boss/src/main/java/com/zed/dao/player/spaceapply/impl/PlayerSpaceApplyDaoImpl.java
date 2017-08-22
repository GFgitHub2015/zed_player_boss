package com.zed.dao.player.spaceapply.impl;

import org.springframework.stereotype.Repository;

import com.zed.dao.player.base.abstractdao.AbstractPlayerPageDao;
import com.zed.dao.player.spaceapply.PlayerSpaceApplyDao;
import com.zed.domain.player.spaceapply.PlayerSpaceApply;
import com.zed.exception.AppValidationException;

@Repository("playerSpaceApplyDao")
public class PlayerSpaceApplyDaoImpl<T> extends AbstractPlayerPageDao<PlayerSpaceApply> implements PlayerSpaceApplyDao<PlayerSpaceApply> {

	@Override
	public PlayerSpaceApply findByUserId(String userId) throws AppValidationException {
		return findById("findByUserId", userId);
	}


}
