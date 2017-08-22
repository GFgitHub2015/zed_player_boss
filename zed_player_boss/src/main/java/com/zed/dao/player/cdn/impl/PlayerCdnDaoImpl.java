package com.zed.dao.player.cdn.impl;

import org.springframework.stereotype.Repository;

import com.zed.dao.player.base.abstractdao.AbstractPlayerPageDao;
import com.zed.dao.player.cdn.PlayerCdnDao;
import com.zed.domain.player.cdn.PlayerCdn;
import com.zed.exception.AppValidationException;

@Repository("playerCdnDao")
public class PlayerCdnDaoImpl<T> extends AbstractPlayerPageDao<PlayerCdn> implements PlayerCdnDao<PlayerCdn> {

	@Override
	public void updateStatus(PlayerCdn playerCdn) throws AppValidationException {
		update(playerCdn);
	}

	@Override
	public PlayerCdn findByStatus() throws AppValidationException {
		return find("findByStatus");
	}

}
