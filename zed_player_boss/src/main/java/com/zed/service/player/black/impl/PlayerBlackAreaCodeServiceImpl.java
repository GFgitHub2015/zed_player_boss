package com.zed.service.player.black.impl;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zed.dao.player.black.PlayerBlackAreaCodeDao;
import com.zed.domain.player.black.PlayerBlackAreaCode;
import com.zed.exception.AppValidationException;
import com.zed.redis.player.black.PlayerBlackAreaCodeRedisDao;
import com.zed.service.player.black.PlayerBlackAreaCodeService;
import com.zed.system.page.Page;

@Service("playerBlackAreaCodeService")
public class PlayerBlackAreaCodeServiceImpl implements PlayerBlackAreaCodeService {

	@Resource(name="playerBlackAreaCodeDao")
	private PlayerBlackAreaCodeDao playerBlackAreaCodeDao;
	
	@Resource(name="playerBlackAreaCodeRedisDao")
	private PlayerBlackAreaCodeRedisDao playerBlackAreaCodeRedisDao;
	
	@Override
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException {
		return playerBlackAreaCodeDao.findByPage(page);
	}

	@Override
	public PlayerBlackAreaCode findById(String whiteAreaCodeId) throws AppValidationException {
		return (PlayerBlackAreaCode) playerBlackAreaCodeDao.findById(whiteAreaCodeId);
	}

	@Override
	public void add(PlayerBlackAreaCode playerWhiteAreaCode) throws AppValidationException {
		playerBlackAreaCodeDao.add(playerWhiteAreaCode);
		playerBlackAreaCodeRedisDao.addPlayerBlackAreaCode(playerWhiteAreaCode);
	}

	@Override
	public void delete(String... blackIds) throws AppValidationException {
		playerBlackAreaCodeDao.delete(blackIds);
		playerBlackAreaCodeRedisDao.deletePlayerBlackAreaCode();
	}

	@Override
	public PlayerBlackAreaCode findByAreaCode(String areaCode) throws AppValidationException {
		return playerBlackAreaCodeDao.findByAreaCode(areaCode);
	}

}
