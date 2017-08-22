package com.zed.service.player.playeruser.impl;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zed.dao.player.playeruser.PlayerUserHotSortDao;
import com.zed.domain.player.playeruserhotsort.PlayerUserHotSort;
import com.zed.exception.AppValidationException;
import com.zed.service.player.playeruser.PlayerUserHotSortService;
import com.zed.system.page.Page;

@Service("playerUserHotSortService")
public class PlayerUserHotSortServiceImpl implements PlayerUserHotSortService {
	
	@Resource(name="playerUserHotSortDao")
	private PlayerUserHotSortDao playerUserHotSortDao;
	
	@Override
	public PlayerUserHotSort findById(String userSortId) {
		return (PlayerUserHotSort) playerUserHotSortDao.findById(userSortId);
	}

	@Override
	public PlayerUserHotSort findByUserId(String userId) {
		return playerUserHotSortDao.findByUserId(userId);
	}

	@Override
	public void addPlayerUserHotSort(PlayerUserHotSort playerUserHotSort) {
		playerUserHotSortDao.add(playerUserHotSort);
	}

	@Override
	public void deleteById(String[] userSortIds) {
		playerUserHotSortDao.delete(userSortIds);		
	}

	@Override
	public void deleteByUserId(String[] userId) {
		playerUserHotSortDao.deleteByUserId(userId);		
	}

	@Override
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException {
		return playerUserHotSortDao.findByPage(page);
	}

	@Override
	public void updateSort(PlayerUserHotSort playerUserHotSort) throws AppValidationException {
		playerUserHotSortDao.updateSort(playerUserHotSort);		
	}

	@Override
	public PlayerUserHotSort findByCountryCodeWithSort(String countryCode, Integer sort) throws AppValidationException {
		return playerUserHotSortDao.findByCountryCodeWithSort(countryCode, sort);		
	}

}
