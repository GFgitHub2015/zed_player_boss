package com.zed.service.player.player.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zed.dao.player.video.PlayerVideoDao;
import com.zed.exception.AppValidationException;
import com.zed.service.player.player.PlayerVideoService;

@Service("playerVideoService")
public class PlayerVideoServiceImpl implements PlayerVideoService{
	
	@Resource(name="playerVideoDao")
	private PlayerVideoDao playerVideoDao;
	

	@Override
	public Set<String> findResIdListByHisIds(Collection<String> hisIds) throws AppValidationException {
		Set<String> result = new HashSet<String>();
		String [] hisIdsArray = hisIds.toArray(new String[hisIds.size()]);
		List<String> resIdList = this.findResIdListByHisIds(hisIdsArray);
		if (resIdList != null && !resIdList.isEmpty() && resIdList.size()>0) {
			result.addAll(resIdList);
		}
		return result;
	}
	
	private List<String> findResIdListByHisIds(String[] hisIds) throws AppValidationException{
		return playerVideoDao.findResIdListByHisIds(hisIds);
	}

}
