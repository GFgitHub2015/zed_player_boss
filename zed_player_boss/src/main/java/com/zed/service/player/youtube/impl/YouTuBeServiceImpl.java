package com.zed.service.player.youtube.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.zed.common.util.HttpUtil;
import com.zed.common.util.StringUtil;
import com.zed.domain.params.YoutubeParams;
import com.zed.domain.player.playerhotuser.YouTubeHotUser;
import com.zed.domain.player.videoclass.PlayerVideoClasses;
import com.zed.exception.AppValidationException;
import com.zed.listener.SystemConfig;
import com.zed.service.iosplayer.playerhotuser.IosPlayerRelationHotUserService;
import com.zed.service.iosplayer.videoclass.IosPlayerVideoClassesService;
import com.zed.service.player.playerhotuser.IPlayerRelationHotUserService;
import com.zed.service.player.videoclass.PlayerVideoClassesService;
import com.zed.service.player.youtube.YouTuBeService;
import com.zed.system.page.YouTubePage;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("youTuBeService")
public class YouTuBeServiceImpl implements YouTuBeService {
	
	@Resource(name="playerVideoClassesService")
	private PlayerVideoClassesService playerVideoClassesService;
	
	@Resource(name="iosPlayerVideoClassesService")
	private IosPlayerVideoClassesService iosPlayerVideoClassesService;
	@Resource(name="playerRelationHotUserService")
	private IPlayerRelationHotUserService playerRelationHotUserService;
	@Resource(name="iosPlayerRelationHotUserService")
	private IosPlayerRelationHotUserService iosPlayerRelationHotUserService;
	
	@Override
	public List<Map<String, Object>> findCategoriesByRegionCodes() throws AppValidationException {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		String url = "https://www.googleapis.com/youtube/v3/videoCategories";
		String apikey = "AIzaSyA4Ym7I6fqoPivazOqvMzcuo5pt9CjEtAs";
		Map<String,String[]> params = new HashMap<String,String[]>();
		params.put("key", new String[]{apikey});
		params.put("regionCode", new String[]{"US"});
		params.put("part", new String[]{"snippet"});//contentDetails,snippet,statistics,player
		String str = HttpUtil.doGet(url, params);
		if (StringUtils.isNotBlank(str)) {
			JSONObject json = JSONObject.fromObject(str);
			
			JSONArray items = json.getJSONArray("items");
			if(items!=null&&items instanceof JSONArray){
				result.addAll(getObjectFromItems(items));
			}
		}
		return result;
	}
	
	private List<Map<String, Object>> getObjectFromItems(JSONArray jsonArray){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < jsonArray.size(); i++) {
			Object object = jsonArray.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			if (object!=null) {
				if (object instanceof JSONObject) {
					JSONObject json = JSONObject.fromObject(object);
					JSONObject snippetJson = JSONObject.fromObject(json.get("snippet"));
					map.put("keyName", snippetJson.get("title"));
					String key = snippetJson.get("channelId")+"-"+json.get("id");
					map.put("key", key);
					map.put("isRecommond", playerVideoClassesService.getByClasskey(PlayerVideoClasses.Prefix.YOUTUBE.getKey()+key)?0:1);
					result.add(map);
				}
			}
		}
		return result;
	}

	/**
	 * @date : 2017年7月03日 下午4:10:01
	 * @author : X.Long
	 * @param   youtubeParam
	 * @version : 3.0
	 * @description :  YouTube第三方 API获取channelId信息
	*/
	public YouTubePage<YouTubeHotUser> findHotUserByName(YoutubeParams youtubeParam) throws AppValidationException {
		String url = "https://www.googleapis.com/youtube/v3/search";
		String apikey = "AIzaSyA4Ym7I6fqoPivazOqvMzcuo5pt9CjEtAs";
		Map<String, String[]> params = new HashMap<String, String[]>();
		params.put("key", new String[]{apikey});
		params.put("part", new String[]{"snippet"});
		params.put("type", new String[]{"channel"}); 
		params.put("maxResults", new String[]{ "20" }); 
		if(null != youtubeParam) {
			if(StringUtils.isNotEmpty(youtubeParam.getQ())) {
				params.put("q", new String[]{youtubeParam.getQ().replaceAll(" ", "+")});
			}
			if(StringUtils.isNotEmpty(youtubeParam.getPageToken())) {
				params.put("pageToken", new String[]{youtubeParam.getPageToken()});
			}
		}
		String str = HttpUtil.doGet(url, params);
		return getHotUserFromItems(str);
	}
	
	/**
	 * @date : 2017年7月03日 下午4:10:01
	 * @author : X.Long
	 * @param   youtubeParam
	 * @version : 3.0
	 * @description :  构造YouTube对象结果集
	*/
	private YouTubePage<YouTubeHotUser> getHotUserFromItems(String str) {
		YouTubePage<YouTubeHotUser> youTubePage = null;
		List<YouTubeHotUser> youTubeHotUsers = null;
		YouTubeHotUser youTubeHotUser = null;
		if (StringUtils.isNotBlank(str)) {
			JSONObject json = JSONObject.fromObject(str);
			JSONArray items = json.getJSONArray("items");
			if(items != null && items instanceof JSONArray) {
				youTubePage = new YouTubePage<YouTubeHotUser>();
				youTubeHotUsers = new ArrayList<YouTubeHotUser>();
				for (int i = 0; i < items.size(); i++) {
					youTubeHotUser = new YouTubeHotUser();
					Object object = items.get(i);
					if (object != null ) {
						if (object instanceof JSONObject) {
							JSONObject json_ = JSONObject.fromObject(object);
							JSONObject snippetJson = JSONObject.fromObject(json_.get("snippet"));
							String userId = "youtube-"+snippetJson.get("channelId").toString();
							youTubeHotUser.setUserId(userId);
							youTubeHotUser.setTitle(snippetJson.get("title").toString());
							JSONObject thumbnailsObj = JSONObject.fromObject(snippetJson.get("thumbnails"));
							JSONObject defaultObj = JSONObject.fromObject(thumbnailsObj.get("default"));
							youTubeHotUser.setIconUrl(defaultObj.get("url").toString());
							youTubeHotUser.setIsRecommond(playerRelationHotUserService.findByUserId(userId) != null ? 1 : 0);
							youTubeHotUsers.add(youTubeHotUser);
						}
					}
				}
				youTubePage.setPrevPageToken(json.get("prevPageToken") == null ? "" : json.get("prevPageToken").toString());
				youTubePage.setNextPageToken(json.get("nextPageToken") == null ? "": json.get("nextPageToken").toString());
				youTubePage.setResult(youTubeHotUsers);
			}
		}
		return youTubePage;
	}
		
	/**
	 * 根据videoId校验当前影片是否有效,如果无效则更改状态
	 * @param videoId
	 */
	public String getDetailByVideoId(String videoId) throws AppValidationException {
		String url = SystemConfig.getProperty("youtube.videos");
		String apikey = SystemConfig.getProperty("youtube.apikey");
		Map<String,String[]> params = new HashMap<String,String[]>();
		params.put("id", new String[]{videoId});
		params.put("key", new String[]{apikey});
		params.put("part", new String[]{"contentDetails,snippet"}); //contentDetails,snippet,statistics,player
		return HttpUtil.doGet(url, params);
	}

	@Override
	public List<Map<String, Object>> findCategoriesByRegionCodesForIOS() throws AppValidationException {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		String url = "https://www.googleapis.com/youtube/v3/videoCategories";
		String apikey = "AIzaSyA4Ym7I6fqoPivazOqvMzcuo5pt9CjEtAs";
		Map<String,String[]> params = new HashMap<String,String[]>();
		params.put("key", new String[]{apikey});
		params.put("regionCode", new String[]{"US"});
		params.put("part", new String[]{"snippet"});//contentDetails,snippet,statistics,player
		String str = HttpUtil.doGet(url, params);
		if (StringUtils.isNotBlank(str)) {
			JSONObject json = JSONObject.fromObject(str);
			
			JSONArray items = json.getJSONArray("items");
			if(items!=null&&items instanceof JSONArray){
				result.addAll(getObjectFromItemsForIOS(items));
			}
		}
		return result;
	}

	@Override
	public YouTubePage<YouTubeHotUser> findHotUserByNameForIOS(YoutubeParams youtubeParam)
			throws AppValidationException {
		String url = "https://www.googleapis.com/youtube/v3/search";
		String apikey = "AIzaSyA4Ym7I6fqoPivazOqvMzcuo5pt9CjEtAs";
		Map<String, String[]> params = new HashMap<String, String[]>();
		params.put("key", new String[]{apikey});
		params.put("part", new String[]{"snippet"});
		params.put("type", new String[]{"channel"}); 
		params.put("maxResults", new String[]{ "20" }); 
		if(null != youtubeParam) {
			if(StringUtils.isNotEmpty(youtubeParam.getQ())) {
				params.put("q", new String[]{youtubeParam.getQ().replaceAll(" ", "+")});
			}
			if(StringUtils.isNotEmpty(youtubeParam.getPageToken())) {
				params.put("pageToken", new String[]{youtubeParam.getPageToken()});
			}
		}
		String str = HttpUtil.doGet(url, params);
		return getHotUserFromItemsForIOS(str);
	}
	
	private List<Map<String, Object>> getObjectFromItemsForIOS(JSONArray jsonArray){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < jsonArray.size(); i++) {
			Object object = jsonArray.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			if (object!=null) {
				if (object instanceof JSONObject) {
					JSONObject json = JSONObject.fromObject(object);
					JSONObject snippetJson = JSONObject.fromObject(json.get("snippet"));
					boolean assignable = Boolean.parseBoolean(snippetJson.get("assignable").toString());
					if(assignable){//如果为true才添加展示
						map.put("keyName", snippetJson.get("title"));
						String key = snippetJson.get("channelId")+"-"+json.get("id");
						map.put("key", key);
						map.put("isRecommond", iosPlayerVideoClassesService.getByClasskey(PlayerVideoClasses.Prefix.YOUTUBE.getKey()+key)?0:1);
						result.add(map);
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * @date : 2017年7月03日 下午4:10:01
	 * @author : X.Long
	 * @param   youtubeParam
	 * @version : 3.0
	 * @description :  构造YouTube对象结果集For IOS
	*/
	private YouTubePage<YouTubeHotUser> getHotUserFromItemsForIOS(String str) {
		YouTubePage<YouTubeHotUser> youTubePage = null;
		List<YouTubeHotUser> youTubeHotUsers = null;
		YouTubeHotUser youTubeHotUser = null;
		if (StringUtils.isNotBlank(str)) {
			JSONObject json = JSONObject.fromObject(str);
			JSONArray items = json.getJSONArray("items");
			if(items != null && items instanceof JSONArray) {
				youTubePage = new YouTubePage<YouTubeHotUser>();
				youTubeHotUsers = new ArrayList<YouTubeHotUser>();
				for (int i = 0; i < items.size(); i++) {
					youTubeHotUser = new YouTubeHotUser();
					Object object = items.get(i);
					if (object != null ) {
						if (object instanceof JSONObject) {
							JSONObject json_ = JSONObject.fromObject(object);
							JSONObject snippetJson = JSONObject.fromObject(json_.get("snippet"));
							String userId = snippetJson.get("channelId").toString();
							youTubeHotUser.setUserId(userId);
							youTubeHotUser.setTitle(snippetJson.get("title").toString());
							JSONObject thumbnailsObj = JSONObject.fromObject(snippetJson.get("thumbnails"));
							JSONObject defaultObj = JSONObject.fromObject(thumbnailsObj.get("default"));
							youTubeHotUser.setIconUrl(defaultObj.get("url").toString());
							youTubeHotUser.setIsRecommond(iosPlayerRelationHotUserService.findByUserId(userId) != null ? 1 : 0);
							//根据channelId获取频道订阅数
							//Map<String,Object> channelMap = new HashMap<String,Object>();
							//channelMap = findChannelBySubscriptionsForIOS(userId);
							//youTubeHotUser.setSubscriberCount(channelMap.get("subscriberCount").toString());
							
							youTubeHotUsers.add(youTubeHotUser);
						}
					}
				}
				youTubePage.setPrevPageToken(json.get("prevPageToken") == null ? "" : json.get("prevPageToken").toString());
				youTubePage.setNextPageToken(json.get("nextPageToken") == null ? "": json.get("nextPageToken").toString());
				youTubePage.setResult(youTubeHotUsers);
			}
		}
		return youTubePage;
	}

	@Override
	public Map<String, Object> findChannelBySubscriptionsForIOS(String channel) throws AppValidationException {
		Map<String, Object> result = new HashMap<String, Object>();
		String url = "https://www.googleapis.com/youtube/v3/channels";
		String apikey = "AIzaSyA4Ym7I6fqoPivazOqvMzcuo5pt9CjEtAs";
		Map<String,String[]> params = new HashMap<String,String[]>();
		params.put("key", new String[]{apikey});
		params.put("id", new String[]{channel});
		params.put("part", new String[]{"statistics"});//contentDetails,snippet,statistics,player
		String str = HttpUtil.doGet(url, params);
		if (StringUtils.isNotBlank(str)) {
			JSONObject json = JSONObject.fromObject(str);
			
			JSONArray items = json.getJSONArray("items");
			if(items!=null&&items instanceof JSONArray){
				result = getChannelsObjectFromItems(items);
			}
		}
		return null;
	}
	
	private Map<String, Object> getChannelsObjectFromItems(JSONArray jsonArray){
		Object object = jsonArray.get(0);
		Map<String, Object> map = new HashMap<String, Object>();
		if (object!=null) {
			if (object instanceof JSONObject) {
				JSONObject json = JSONObject.fromObject(object);
				JSONObject snippetJson = JSONObject.fromObject(json.get("statistics"));
				Object  subscriberCount= snippetJson.get("subscriberCount");
				if(null == subscriberCount){
					subscriberCount = "0";
				}
				map.put("subscriberCount", subscriberCount);
				map.put("viewCount", snippetJson.get("viewCount"));
				map.put("commentCount", snippetJson.get("commentCount"));
				map.put("hiddenSubscriberCount", snippetJson.get("hiddenSubscriberCount"));
				map.put("videoCount", snippetJson.get("videoCount"));
				
				return map;
			}
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> findChannelsBySubscriptionsForIOS(String channelIds)
			throws AppValidationException {
		String channelIdstr = channelIds.substring(0, channelIds.length()-1);
		channelIds = channelIdstr.replaceAll(",", "%2C");
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		String url = "https://www.googleapis.com/youtube/v3/channels";
		String apikey = "AIzaSyA4Ym7I6fqoPivazOqvMzcuo5pt9CjEtAs";
		Map<String,String[]> params = new HashMap<String,String[]>();
		params.put("key", new String[]{apikey});
		params.put("id", new String[]{channelIds});
		params.put("part", new String[]{"statistics"});//contentDetails,snippet,statistics,player
		String str = HttpUtil.doGet(url, params);
		if (StringUtils.isNotBlank(str)) {
			JSONObject json = JSONObject.fromObject(str);
			
			JSONArray items = json.getJSONArray("items");
			if(items!=null&&items instanceof JSONArray){
				result.addAll(getChannelsFromItems(items));
			}
		}
		return result;
	}
	
	private List<Map<String, Object>> getChannelsFromItems(JSONArray jsonArray){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < jsonArray.size(); i++) {
			Object object = jsonArray.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			if (object!=null) {
				if (object instanceof JSONObject) {
					JSONObject json = JSONObject.fromObject(object);
					Object channelId = json.get("id");
					JSONObject statistics = JSONObject.fromObject(json.get("statistics"));
					Object  subscriberCount= statistics.get("subscriberCount");
					if(null == subscriberCount){
						subscriberCount = "0";
					}
					map.put("subscriberCount", subscriberCount);
					map.put("viewCount", statistics.get("viewCount"));
					map.put("commentCount", statistics.get("commentCount"));
					map.put("hiddenSubscriberCount", statistics.get("hiddenSubscriberCount"));
					map.put("videoCount", statistics.get("videoCount"));
					map.put("channelId", channelId.toString());
					
					result.add(map);
				}
			}
		}
		return result;
	}
}
