package com.zed.service.player.clientinfo.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zed.dao.player.clientinfo.PlayerClientInfoDao;
import com.zed.domain.player.clientinfo.PlayerClientInfo;
import com.zed.exception.AppValidationException;
import com.zed.service.player.clientinfo.PlayerClientInfoService;
import com.zed.system.page.Page;

@Service("playerClientInfoService")
public class PlayerClientInfoServiceImpl implements PlayerClientInfoService {
	
	@Resource(name="playerClientInfoDao")
	private PlayerClientInfoDao playerClientInfoDao;

	@Override
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException {
		return playerClientInfoDao.findByPage(page);
	}

	@Override
	public PlayerClientInfo findById(String clientId) throws AppValidationException {
		return (PlayerClientInfo) playerClientInfoDao.findById(clientId);
	}

	@Override
	public List<String> findAppVersionList() throws AppValidationException {
		return playerClientInfoDao.findAppVersionList();
	}

	@Override
	public List<String> findChannelList() throws AppValidationException {
		return playerClientInfoDao.findChannelList();
	}

	@Override
	public List<String> findAreaCodeList() throws AppValidationException {
		return playerClientInfoDao.findAreaCodeList();
	}

	@Override
	public List<String> findPackageName() throws AppValidationException {
		return playerClientInfoDao.findPackageName();
	}

	@Override
	public List<String> findLanguageList() throws AppValidationException {
		return playerClientInfoDao.findLanguageList();
	}

	@Override
	public List<String> findSysType() throws AppValidationException {
		return playerClientInfoDao.findSysType();
	}

	@Override
	public PlayerClientInfo findByUserId(String userId) throws AppValidationException {
		return playerClientInfoDao.findByUserId(userId);
	}

}
