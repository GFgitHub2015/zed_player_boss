package com.zed.service.iosplayer.video.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zed.common.util.CommUtil;
import com.zed.common.util.HttpUtil;
import com.zed.common.util.JsonUtils;
import com.zed.dao.iosplayer.video.IosPlayerVideoDao;
import com.zed.domain.iosplayer.video.IosPlayerVideo;
import com.zed.listener.SystemConfig;
import com.zed.redis.iosplayer.video.IosPlayerVideoRedisDao;
import com.zed.service.iosplayer.video.IosPlayerVideoService;
import com.zed.system.page.Page;
import com.zed.util.DateUtil;

/**
 * @date : 2017年5月9日 下午3:35:46
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 
*/
@Service("iosPlayerVideoService")
public class IosPlayerVideoServiceImpl implements IosPlayerVideoService {
	
	@Resource(name="iosPlayerVideoDao")
	private IosPlayerVideoDao<IosPlayerVideo> iosPlayerVideoDao;

	@Resource(name="iosPlayerVideoRedisDao")
	private IosPlayerVideoRedisDao iosPlayerVideoRedisDao;
	/**
	 * @date : 2017年5月9日 下午3:35:18
	 * @author : Iris.Xiao
	 * @param page
	 * @return
	 * @description : 分页查询
	*/
	public Page<IosPlayerVideo> findByPage(Page<IosPlayerVideo> page){
		return iosPlayerVideoDao.findByPage(page);
	}
	

	/**
	 * @date : 2017年5月9日 下午4:44:57
	 * @author : Iris.Xiao
	 * @param uid
	 * @return
	 * @description : 
	*/
	public IosPlayerVideo getVideo(String uid){
		return iosPlayerVideoDao.findById(uid);
	}

	/**
	 * @date : 2017年5月9日 下午4:44:53
	 * @author : Iris.Xiao
	 * @param videoId
	 * @return
	 * @throws Exception 
	 * @description : 根据videoId查找
	*/
	public IosPlayerVideo getVideoByVideoId(String videoId) throws Exception{
		List<IosPlayerVideo>list = iosPlayerVideoDao.getVideoByVideoId(videoId);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

	/**
	 * @date : 2017年5月9日 下午5:02:47
	 * @author : Iris.Xiao
	 * @param uids
	 * @description : 
	*/
	public void deleteViedeo(String[] uids){
		if(uids!=null&&uids.length>0){
			for (String id : uids) {
				IosPlayerVideo video = iosPlayerVideoDao.findById(id);
				iosPlayerVideoDao.delete(id);
				iosPlayerVideoRedisDao.deleteVideo(video);
			}
		}
	}

	/**
	 * @date : 2017年5月9日 下午5:06:36
	 * @author : Iris.Xiao
	 * @param listToUpdate
	 * @description : 
	*/
	public void updateVideo(List<IosPlayerVideo> listToUpdate){
		if(listToUpdate!=null&&listToUpdate.size()>0){
			for (IosPlayerVideo video : listToUpdate) {
				iosPlayerVideoDao.update(video);
			}
		}
	}
	
	/**
	 * @date : 2017年5月15日 下午6:48:54
	 * @author : Iris.Xiao
	 * @param Video
	 * @description : 增
	*/
	public void addVideo(IosPlayerVideo video){
		if(video!=null){
			iosPlayerVideoDao.add(video);
		}
	}

	/**
	 * @date : 2017年5月9日 下午5:06:39
	 * @author : Iris.Xiao
	 * @param Video
	 * @description : 
	*/
	public void updateViedeo(IosPlayerVideo Video){
		iosPlayerVideoDao.update(Video);
	}

	/**
	 * @date : 2017年5月9日 下午5:06:42
	 * @author : Iris.Xiao
	 * @param uids
	 * @return
	 * @description : 
	*/
	public List<IosPlayerVideo> findAllVideoByUids(String[] uids){
		List<IosPlayerVideo> result = new ArrayList<IosPlayerVideo>();
		for (String uid : uids) {
			IosPlayerVideo hv = this.getVideo(uid);
			if (hv != null) {
				result.add(hv);
			}
		}
		return result;
	}
	

	/**
	 * @date : 2017年5月22日 上午11:27:59
	 * @author : Iris.Xiao
	 * @param videoId
	 * @return
	 * @throws Exception 
	 * @description : 得到youtube影片信息
	*/
	@SuppressWarnings("unchecked")
	public IosPlayerVideo getYouTubeVideoDetail(String videoId ) throws Exception{
		List<IosPlayerVideo> list = iosPlayerVideoDao.getVideoByVideoId(videoId);
		IosPlayerVideo detail = new IosPlayerVideo();
		if(list!=null&&list.size()>0){
			return detail;
		}
		String url = SystemConfig.getProperty("youtube.videos");
		String apikey = SystemConfig.getProperty("youtube.apikey");
		Map<String,String[]> params = new HashMap<String,String[]>();
		params.put("id", new String[]{videoId});
		params.put("key", new String[]{apikey});
		params.put("part", new String[]{"contentDetails,snippet"});//contentDetails,snippet,statistics,player
		String str = HttpUtil.doGet(url, params);
		Map<String,Object> json = JsonUtils.getMapByJsonStr(str);
		detail.setVideoId(videoId);
		Object items = json.get("items");
		if(items!=null&&items instanceof List){
			List<Map<String,Object>> itemsList = (List<Map<String,Object>>) items;
			if(itemsList.size()>0){
				Map<String,Object> item = itemsList.get(0);
				Object snippetObj = item.get("snippet");
				if(snippetObj!=null&&snippetObj instanceof Map){
					Map<String,Object> snippet = (Map<String,Object>) snippetObj;
					detail.setVideoName(snippet.get("title").toString().replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", ""));//片名
//					detail.setVideoMemo(snippet.get("description").toString());//描述
					//描述,替换emoji表情,为utf8mb4编码
					detail.setVideoMemo(snippet.get("description").toString().replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", ""));
					Object thumbnailsObj = snippet.get("thumbnails");
					if(thumbnailsObj!=null&&thumbnailsObj instanceof Map){
						Map<String,Object> thumbnails = (Map<String,Object>) thumbnailsObj;
						if(thumbnails.get("standard")!=null){
							Map<String,Object> thumbnailsStandard = (Map<String,Object>) thumbnails.get("standard");
							detail.setIconUrl(thumbnailsStandard.get("url").toString());//海报
						}else{
							if(thumbnails.get("default")!=null){
								Map<String,Object> thumbnailsdefault = (Map<String,Object>) thumbnails.get("default");
								detail.setIconUrl(thumbnailsdefault.get("url").toString());//海报
							}
						}
					}
					Object liveBroadcastContentObj = snippet.get("liveBroadcastContent");//判断是否直播依据
					if(liveBroadcastContentObj!=null){
						detail.setLiveBroadcastContent(liveBroadcastContentObj.toString());
					}
				}

				Object contentDetailsObj = item.get("contentDetails");
				if(contentDetailsObj!=null&&contentDetailsObj instanceof Map){
					Map<String,Object> contentDetails = (Map<String,Object>) contentDetailsObj;
					Object dimensionObj = contentDetails.get("dimension");
					if(dimensionObj!=null){
						String dimensionStr = dimensionObj.toString().toLowerCase();
						dimensionStr = dimensionStr.replaceAll("d", "");
						if(!CommUtil.isEmpty(dimensionStr)){
							detail.setDimensionType(Long.parseLong(dimensionStr));//2d,3d
						}
					}
					Object durationObj = contentDetails.get("duration");
					if(durationObj!=null){
						String durationStr = durationObj.toString();
						//PT2H10M7S>02:10:07
						durationStr = DateUtil.getDurationStr(durationStr);
						detail.setVideoDuration(durationStr);//时长
					}
				}

//				Object statisticsObj = item.get("statistics");
//				if(statisticsObj!=null&&statisticsObj instanceof Map){
//					Map<String,Object> statistics = (Map<String,Object>) statisticsObj;
//					if(statistics.get("viewCount")!=null){
//						detail.setViewCount(statistics.get("viewCount").toString());//观看次数
//					}
//				}
			}
		}
		return detail;
	}
	
	/**
	 * @date : 2017年5月22日 下午2:45:43
	 * @author : Iris.Xiao
	 * @param countryCode
	 * @description : 删除缓存
	*/
	public void delete3dPageList(String countryCode,IosPlayerVideo video){
		iosPlayerVideoRedisDao.delete3dPageList(countryCode,video);
	}
	
	/**
	 * @date : 2017年5月22日 下午2:45:43
	 * @author : Iris.Xiao
	 * @param countryCode
	 * @description : 删除缓存
	*/
	public void deletePageList(String countryCode,IosPlayerVideo video){
		iosPlayerVideoRedisDao.deletePageList(countryCode,video);
	}
	

	/**
	 * @date : 2017年6月5日 下午6:26:06
	 * @author : Iris.Xiao
	 * @param durationStr
	 * @return
	 * @description : 转换YouTube的时间 ,PT10M7S>10:07,PT2H>02:00:00
	*/
	public String getDurationStr(String durationStr){
		String timeStr="";
		String hour = "0";
		if(CommUtil.isEmpty(durationStr)){
			return "";
		}
		durationStr = durationStr.replace("PT", "");
		if(durationStr.contains("H")){
			hour = durationStr.substring(0, durationStr.indexOf("H"));
			durationStr = durationStr.substring(durationStr.indexOf("H")+1, durationStr.length());
		}
		String minite = "0";
		if(durationStr.contains("M")){
			minite = durationStr.substring(0, durationStr.indexOf("M"));
			durationStr = durationStr.substring(durationStr.indexOf("M")+1, durationStr.length());
		}
		String second = "0";
		if(durationStr.contains("S")){
			second = durationStr.substring(0, durationStr.indexOf("S"));
			durationStr = durationStr.substring(durationStr.indexOf("S")+1, durationStr.length());
		}
		if("0".equals(hour)){
			timeStr = String.format("%02d:%02d", Integer.parseInt(minite),Integer.parseInt(second));
		}else{
			timeStr = String.format("%02d:%02d:%02d",Integer.parseInt(hour), Integer.parseInt(minite),Integer.parseInt(second));
		}
		return timeStr;
	}
}
