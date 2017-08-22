package com.zed.service.player.logicalfile.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zed.dao.player.logicalfile.PlayerLogicalDownloadTimesDao;
import com.zed.domain.player.logicalfile.PlayerLogicalDownloadTimes;
import com.zed.exception.AppValidationException;
import com.zed.redis.player.logicalfile.PlayerLogicalDownloadTimesRedisDao;
import com.zed.service.player.logicalfile.PlayerLogicalDownloadTimesService;

@Service("playerLogicalDownloadTimesService")
public class PlayerLogicalDownloadTimesServiceImpl implements PlayerLogicalDownloadTimesService {
	
	@Resource(name="playerLogicalDownloadTimesRedisDao")
	private PlayerLogicalDownloadTimesRedisDao playerLogicalDownloadTimesRedisDao;
	
	@Resource(name="playerLogicalDownloadTimesDao")
	private PlayerLogicalDownloadTimesDao playerLogicalDownloadTimesDao;
	
	@Override
	public PlayerLogicalDownloadTimes getPlayerDownloadTimesByFileId(String fileId)  throws AppValidationException{
		PlayerLogicalDownloadTimes pld = null;
		pld = playerLogicalDownloadTimesRedisDao.getPlayerLogicalDownloadTimes(fileId);
		if (pld == null) {
			pld = playerLogicalDownloadTimesDao.findByFileId(fileId);
				if (pld != null) {
					playerLogicalDownloadTimesRedisDao.addPlayerLogicalDownloadTimes(pld);
				}
		}
		return pld;
	}

}
