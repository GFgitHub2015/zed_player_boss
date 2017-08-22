package com.zed.redis.iosplayer.video.impl;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.zed.common.util.CommUtil;
import com.zed.domain.iosplayer.video.IosPlayerVideo;
import com.zed.redis.iosplayer.video.IosPlayerVideoRedisDao;
import com.zed.util.RedisKey;

/**
 * @date : 2017年5月22日 下午2:38:38
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 
*/
@Repository("iosPlayerVideoRedisDao")
public class IosPlayerVideoRedisDaoImpl implements IosPlayerVideoRedisDao{
	@Resource(name="cacheRedis")
	private RedisTemplate<String,String> cacheRedis;
	
	/**
	 * @date : 2017年5月17日 上午9:51:15
	 * @author : Iris.Xiao
	 * @description : 删除已经排序的页码
	*/
	public void deletePageList(String countryCode){
		String key = String.format(RedisKey.KEY_IOS_PLAYER_HOTVIDEO_PAGELIST_HASH, countryCode);
		cacheRedis.delete(key);
		//还要删除活动相关排序
		key = String.format(RedisKey.KEY_IOS_PLAYER_TOP_PROMOTION_SET, countryCode);
		cacheRedis.delete(key);
		key = String.format(RedisKey.KEY_IOS_PLAYER_NOTTIME_PROMOTION_ZSET, countryCode);
		cacheRedis.delete(key);
	}

	/**
	 * @date : 2017年5月17日 上午9:51:15
	 * @author : Iris.Xiao
	 * @description : 删除已经排序的页码
	*/
	public void deletePageList(String countryCode,IosPlayerVideo video){
		deletePageList(countryCode);
		deleteVideo(video);
	}

	/**
	 * @date : 2017年5月17日 上午9:51:15
	 * @author : Iris.Xiao
	 * @description : 删除已经排序的页码
	*/
	public void delete3dPageList(String countryCode){
		String key = String.format(RedisKey.KEY_IOS_PLAYER_3DVIDEO_PAGELIST_HASH, countryCode);
		cacheRedis.delete(key);
	}

	/**
	 * @date : 2017年5月17日 上午9:51:15
	 * @author : Iris.Xiao
	 * @description : 删除已经排序的页码
	*/
	public void delete3dPageList(String countryCode,IosPlayerVideo video){
		delete3dPageList(countryCode);
		deleteVideo(video);
	}

	/**
	 * @date : 2017年5月17日 上午9:51:15
	 * @author : Iris.Xiao
	 * @description : 删除已经排序的页码
	*/
	public void deleteVideo(IosPlayerVideo video){
		if (video!=null) {
			String key = "";
			if(!CommUtil.isEmpty(video.getUid())){
				key = String.format(RedisKey.KEY_IOS_PLAYER_VIDEO_HASH, video.getUid());
				cacheRedis.delete(key);
			}
			if(!CommUtil.isEmpty(video.getVideoId())){
				key = String.format(RedisKey.KEY_IOS_PLAYER_VIDEO_HASH, video.getVideoId());
				cacheRedis.delete(key);
			}
		}
	}
	/**
	 * @date : 2017年5月17日 上午9:51:15
	 * @author : Iris.Xiao
	 * @description : 删除活动
	*/
	public void deletePmt(String uid){
		String key = String.format(RedisKey.KEY_IOS_PLAYER_PROMOTION_HASH, uid);
		cacheRedis.delete(key);
	}

}
