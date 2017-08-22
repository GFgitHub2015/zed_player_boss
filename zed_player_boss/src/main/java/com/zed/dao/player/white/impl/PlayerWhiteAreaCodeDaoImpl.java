package com.zed.dao.player.white.impl;

import org.springframework.stereotype.Repository;

import com.zed.dao.player.base.abstractdao.AbstractPlayerPageDao;
import com.zed.dao.player.white.PlayerWhiteAreaCodeDao;
import com.zed.domain.player.white.PlayerWhiteAreaCode;
import com.zed.exception.AppValidationException;

@Repository("playerWhiteAreaCodeDao")
public class PlayerWhiteAreaCodeDaoImpl<T> extends AbstractPlayerPageDao<PlayerWhiteAreaCode> implements PlayerWhiteAreaCodeDao<PlayerWhiteAreaCode> {

	@Override
	public PlayerWhiteAreaCode findByAreaCode(String areaCode) throws AppValidationException {
		return find("findByAreaCode", areaCode);
	}


}
