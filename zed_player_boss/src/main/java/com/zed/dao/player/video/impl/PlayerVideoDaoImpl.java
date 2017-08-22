package com.zed.dao.player.video.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.zed.dao.player.base.abstractdao.AbstractPlayerPageDao;
import com.zed.dao.player.video.PlayerVideoDao;
import com.zed.domain.player.video.PlayerVideo;
import com.zed.exception.AppValidationException;

@Repository("playerVideoDao")
public class PlayerVideoDaoImpl extends AbstractPlayerPageDao<PlayerVideo> implements PlayerVideoDao<PlayerVideo> {

	@Override
	public List<String> findYears() throws AppValidationException {
		List<String> yearList = new ArrayList<>(); 
		List<Object> objectList  = findMore("findYears");
		for (Object object : objectList) {
			if (object instanceof String) {
				yearList.add((String)object);
			}
		}
		return yearList;
	}

	@Override
	public List<String> findResIdListByHisIds(String[] hisIds) throws AppValidationException {
		List<String> redIdList = new ArrayList<>(); 
		List<PlayerVideo> objectList  = findList("findPlayerVideoListByHisIds", hisIds);
		for (Object object : objectList) {
			if (object instanceof PlayerVideo) {
				redIdList.add(((PlayerVideo) object).getResId());
			}
		}
		return redIdList;
	}

}
