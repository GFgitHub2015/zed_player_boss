package com.zed.dao.player.black.impl;

import org.springframework.stereotype.Repository;

import com.zed.dao.player.base.abstractdao.AbstractPlayerPageDao;
import com.zed.dao.player.black.PlayerBlackAreaCodeDao;
import com.zed.domain.player.black.PlayerBlackAreaCode;
import com.zed.exception.AppValidationException;

@Repository("playerBlackAreaCodeDao")
public class PlayerBlackAreaCodeDaoImpl<T> extends AbstractPlayerPageDao<PlayerBlackAreaCode> implements PlayerBlackAreaCodeDao<PlayerBlackAreaCode> {

	@Override
	public PlayerBlackAreaCode findByAreaCode(String areaCode) throws AppValidationException {
		return find("findByAreaCode", areaCode);
	}


}
