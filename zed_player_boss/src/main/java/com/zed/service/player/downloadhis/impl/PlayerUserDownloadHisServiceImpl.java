package com.zed.service.player.downloadhis.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zed.dao.player.downloadhis.PlayerUserDownloadHisDao;
import com.zed.exception.AppValidationException;
import com.zed.service.player.downloadhis.PlayerUserDownloadHisService;

@Service("playerUserDownloadHisService")
public class PlayerUserDownloadHisServiceImpl implements PlayerUserDownloadHisService {
	
	@Resource(name="playerUserDownloadHisDao")
	private PlayerUserDownloadHisDao playerUserDownloadHisDao;

	@Override
	public Set<String> findHisIdByUserId(String userId) throws AppValidationException {
		Set<String> result = new HashSet<String>();
		List<String> hisList = playerUserDownloadHisDao.findHisIdByUserId(userId);
		if (hisList != null && !hisList.isEmpty() && hisList.size()>0) {
			result.addAll(hisList);
		}
		return result;
	}

}
