package com.zed.service.player.logicalfile.impl;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zed.dao.player.logicalfile.PlayerLogicalPlayTimesDao;
import com.zed.domain.player.logicalfile.PlayerLogicalPlayTimes;
import com.zed.exception.AppValidationException;
import com.zed.redis.player.logicalfile.PlayerLogicalPlayTimesRedisDao;
import com.zed.service.player.logicalfile.PlayerLogicalPlayTimesService;

@Service("playerLogicalPlayTimesService")
public class PlayerLogicalPlayTimesServiceImpl implements PlayerLogicalPlayTimesService{
	
	@Resource(name="playerLogicalPlayTimesRedisDao")
	private PlayerLogicalPlayTimesRedisDao playerLogicalPlayTimesRedisDao;
	
	@Resource(name="playerLogicalPlayTimesDao")
	private PlayerLogicalPlayTimesDao playerLogicalPlayTimesDao;

	@Override
	public PlayerLogicalPlayTimes getPlayerPlayTimesByFileId(String fileId) throws AppValidationException{
		PlayerLogicalPlayTimes plp = null;
		plp = playerLogicalPlayTimesRedisDao.getPlayerLogicalPlayTimes(fileId);
		if (plp == null) {
			plp = playerLogicalPlayTimesDao.findByFileId(fileId);
				if (plp != null) {
					playerLogicalPlayTimesRedisDao.addPlayerLogicalPlayTimes(plp);
				}
		}
		return plp;
	}

	@Override
	public boolean isTheTopTenFile(String fileId) throws AppValidationException {
		Set<String> fileIdSet = playerLogicalPlayTimesDao.findTopFileIds();
		if (fileIdSet != null) {
			if (fileIdSet.contains(fileId)) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

}
