package com.zed.dao.player.white.impl;

import org.springframework.stereotype.Repository;

import com.zed.dao.player.base.abstractdao.AbstractPlayerPageDao;
import com.zed.dao.player.white.PlayerWhiteIpDao;
import com.zed.domain.player.white.PlayerWhiteIp;
import com.zed.exception.AppValidationException;

@Repository("playerWhiteIpDao")
public class PlayerWhiteIpDaoImpl<T> extends AbstractPlayerPageDao<PlayerWhiteIp> implements PlayerWhiteIpDao<PlayerWhiteIp> {

	@Override
	public PlayerWhiteIp findByIp(String ip) throws AppValidationException {
		return findById(ip);
	}

}
