package com.zed.dao.player.youtube;

import java.io.Serializable;
import java.util.List;

import com.zed.dao.PageDao;
import com.zed.domain.player.youtube.PlayerYouTuBeChannel;
import com.zed.exception.AppValidationException;

public interface PlayerYouTuBeChannelDao<T extends Serializable> extends PageDao<PlayerYouTuBeChannel> {
	
	public PlayerYouTuBeChannel getLastSort() throws AppValidationException;
	
	public List<PlayerYouTuBeChannel> findBySortBy(Integer sort, String sorting, String comperatorStr) throws AppValidationException;

}
