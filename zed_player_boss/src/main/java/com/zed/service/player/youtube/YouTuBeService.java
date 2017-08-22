package com.zed.service.player.youtube;

import java.util.List;
import java.util.Map;

import com.zed.domain.params.YoutubeParams;
import com.zed.domain.player.playerhotuser.YouTubeHotUser;
import com.zed.exception.AppValidationException;
import com.zed.system.page.YouTubePage;

public interface YouTuBeService {
	
	public List<Map<String, Object>> findCategoriesByRegionCodes() throws AppValidationException;
	
	public List<Map<String, Object>> findCategoriesByRegionCodesForIOS() throws AppValidationException;
	
	public YouTubePage<YouTubeHotUser> findHotUserByName(YoutubeParams youtubeParam) throws AppValidationException;
	
	public YouTubePage<YouTubeHotUser> findHotUserByNameForIOS(YoutubeParams youtubeParam) throws AppValidationException;
	
	public String getDetailByVideoId(String videoId) throws AppValidationException;
	
	public Map<String, Object> findChannelBySubscriptionsForIOS(String channel) throws AppValidationException;
	
	public List<Map<String, Object>> findChannelsBySubscriptionsForIOS(String channelIds)throws AppValidationException;

		
}
