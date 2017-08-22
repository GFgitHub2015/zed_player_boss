package com.zed.service.player.white.impl;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zed.dao.player.white.PlayerWhiteAreaCodeDao;
import com.zed.domain.player.white.PlayerWhiteAreaCode;
import com.zed.exception.AppValidationException;
import com.zed.redis.player.white.PlayerWhiteAreaCodeRedisDao;
import com.zed.service.player.white.PlayerWhiteAreaCodeService;
import com.zed.system.page.Page;

@Service("playerWhiteAreaCodeService")
public class PlayerWhiteAreaCodeServiceImpl implements PlayerWhiteAreaCodeService {

	@Resource(name="playerWhiteAreaCodeDao")
	private PlayerWhiteAreaCodeDao playerWhiteAreaCodeDao;
	
	@Resource(name="playerWhiteAreaCodeRedisDao")
	private PlayerWhiteAreaCodeRedisDao playerWhiteAreaCodeRedisDao;
	
	@Override
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException {
		return playerWhiteAreaCodeDao.findByPage(page);
	}

	@Override
	public PlayerWhiteAreaCode findById(String whiteAreaCodeId) throws AppValidationException {
		return (PlayerWhiteAreaCode) playerWhiteAreaCodeDao.findById(whiteAreaCodeId);
	}

	@Override
	public void add(PlayerWhiteAreaCode playerWhiteAreaCode) throws AppValidationException {
		playerWhiteAreaCodeDao.add(playerWhiteAreaCode);
		playerWhiteAreaCodeRedisDao.addPlayerWhiteAreaCode(playerWhiteAreaCode);
	}

	/*@Override
	public void update(PlayerWhiteAreaCode playerWhiteAreaCode) throws AppValidationException {
//		playerWhiteAreaCodeDao.update(playerWhiteAreaCode);		
	}*/

	@Override
	public void delete(String... whiteAreaCodeIds) throws AppValidationException {
		playerWhiteAreaCodeDao.delete(whiteAreaCodeIds);
		playerWhiteAreaCodeRedisDao.deletePlayerWhiteAreaCode();
	}

	@Override
	public PlayerWhiteAreaCode findByAreaCode(String areaCode) throws AppValidationException {
		return playerWhiteAreaCodeDao.findByAreaCode(areaCode);
	}

}
