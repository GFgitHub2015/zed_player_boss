package com.zed.service.player.spiderkeyword.impl;

import java.util.HashMap;

import javax.annotation.Resource;

import com.zed.dao.player.spiderkeyword.PlayerSpiderKeyWordDao;
import com.zed.domain.player.spiderkeyword.PlayerSpiderKeyWord;
import com.zed.exception.AppValidationException;
import com.zed.service.player.spiderkeyword.PlayerSpiderKeyWordService;
import com.zed.system.page.Page;

public class PlayerSpiderKeyWordServiceImpl implements PlayerSpiderKeyWordService {
	
	@Resource(name="playerSpiderKeyWordDao")
	private PlayerSpiderKeyWordDao playerSpiderKeyWordDao;

	@Override
	public PlayerSpiderKeyWord findById(String keyWordId) throws AppValidationException {
		return (PlayerSpiderKeyWord) playerSpiderKeyWordDao.findById(keyWordId);
	}

	@Override
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException {
		return playerSpiderKeyWordDao.findByPage(page);
	}

	@Override
	public void add(PlayerSpiderKeyWord playerSpiderkeyWord) throws AppValidationException {
		playerSpiderKeyWordDao.add(playerSpiderkeyWord);
	}

	@Override
	public void update(PlayerSpiderKeyWord playerSpiderkeyWord) throws AppValidationException {
		playerSpiderKeyWordDao.update(playerSpiderkeyWord);
	}

	@Override
	public void delete(String[] keyWordIds) throws AppValidationException {
		playerSpiderKeyWordDao.delete(keyWordIds);
	}

	@Override
	public void updateStatus(PlayerSpiderKeyWord playerSpiderkeyWord) throws AppValidationException {
		playerSpiderKeyWordDao.updateStatus(playerSpiderkeyWord);
	}

}
