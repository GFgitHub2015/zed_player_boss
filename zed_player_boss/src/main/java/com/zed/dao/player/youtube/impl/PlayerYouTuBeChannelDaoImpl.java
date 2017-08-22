package com.zed.dao.player.youtube.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zed.dao.player.base.abstractdao.AbstractPlayerPageDao;
import com.zed.dao.player.youtube.PlayerYouTuBeChannelDao;
import com.zed.domain.player.videoclass.PlayerVideoClasses;
import com.zed.domain.player.youtube.PlayerYouTuBeChannel;
import com.zed.exception.AppValidationException;

@Repository("playerYouTuBeChannelDao")
public class PlayerYouTuBeChannelDaoImpl<T> extends AbstractPlayerPageDao<PlayerYouTuBeChannel> implements PlayerYouTuBeChannelDao<PlayerYouTuBeChannel> {

	@Override
	public PlayerYouTuBeChannel getLastSort() throws AppValidationException {
		return find("getLastSort");
	}

	@Override
	public List<PlayerYouTuBeChannel> findBySortBy(Integer sort, String sorting, String comperatorStr)
			throws AppValidationException {
		Map<String, Object> params = new HashMap<String, Object>();
		if (comperatorStr.contains(">")) {
			params.put("g", "gt");
			if (comperatorStr.contains("=")) {
				sort--;
			}
		}
		
		if (comperatorStr.contains("<")) {
			params.put("l", "lt");
			if (comperatorStr.contains("=")) {
				sort++;
			}
		}
		params.put("sort", sort);
		params.put("sorting", sorting);
		return findList("findBySortBy", params);
	}

}
