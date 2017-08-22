package com.zed.service.player.youtube;

import java.util.HashMap;
import java.util.List;

import com.zed.domain.player.youtube.PlayerYouTuBeChannel;
import com.zed.exception.AppValidationException;
import com.zed.system.page.Page;

public interface PlayerYouTuBeChannelService {
	
	public PlayerYouTuBeChannel findById(String channelId) throws AppValidationException;
	
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException;

	public void add(PlayerYouTuBeChannel playerYouTuBeChannel) throws AppValidationException;
	
	public void update(PlayerYouTuBeChannel playerYouTuBeChannel) throws AppValidationException;
	
//	public void addBatch(List<PlayerYouTuBeChannel> playerYouTuBeChannelList) throws AppValidationException;
//	
//	public void updateBatch(List<PlayerYouTuBeChannel> playerYouTuBeChannelList) throws AppValidationException;
//	
	public void delete(String[] channelIds) throws AppValidationException;
	
	public Integer getLastSort() throws AppValidationException;
	
	public void deleteBySort(Integer sort) throws AppValidationException;
	
	public Boolean isExist(String channelId) throws AppValidationException;

}
