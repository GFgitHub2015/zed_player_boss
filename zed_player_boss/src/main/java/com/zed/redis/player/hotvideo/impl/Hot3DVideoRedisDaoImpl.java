package com.zed.redis.player.hotvideo.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.zed.common.ConstantType.HotVideoPmtKeyType;
import com.zed.common.ConstantType.HotVideoPmtResultType;
import com.zed.common.exception.AppErrorException;
import com.zed.common.util.DateUtil;
import com.zed.common.util.EntityUtils;
import com.zed.common.util.JsonUtils;
import com.zed.common.util.StringUtil;
import com.zed.domain.player.hotvideo.Hot3DVideo;
import com.zed.redis.player.hotvideo.Hot3DVideoRedisDao;
import com.zed.util.RedisKey;

/**
 * @date : 2016年12月29日 上午9:45:40
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 热门影片推荐
*/
@Repository("hot3DVideoRedisDaoImpl")
public class Hot3DVideoRedisDaoImpl implements Hot3DVideoRedisDao{
	@Resource(name="cacheRedis")
	private RedisTemplate<String,String> cacheRedis;

	/**
	 * @date : 2016年12月28日 下午4:48:15
	 * @author : Iris.Xiao
	 * @param uid
	 * @return
	 * @description : 详情
	*/
	public Hot3DVideo getHotVideo(String uid){
		if(StringUtil.isBlank(uid)){
			return null;
		}
		String hkey = String.format(RedisKey.KEY_PLAYER_HOT_3D_VIDEO_HASH, uid);
		Map<Object,Object> map = cacheRedis.opsForHash().entries(hkey);
		if(map==null||map.size()==0){
			return null;
		}
		Map<String,Object> mapStr = new HashMap<String,Object>();
		Object obj=null;
		for (Entry<Object,Object> entry : map.entrySet() ) {
			obj =  entry.getKey();
			if(obj!=null){
				mapStr.put(obj.toString(), entry.getValue());
			}
		}
		Hot3DVideo hotVideo=null;
		if(map!=null&&map.size()>0){
			hotVideo = EntityUtils.hashToObject(mapStr, Hot3DVideo.class);
		}
		return hotVideo;
	}

	/**
	 * @date : 2016年12月28日 下午4:49:37
	 * @author : Iris.Xiao
	 * @param uid
	 * @description : 删除
	*/
	public void deleteHotVideo(String uid){
		String key = String.format(RedisKey.KEY_PLAYER_HOT_3D_VIDEO_HASH, uid);
		cacheRedis.delete(key);
	}

	/**
	 * @date : 2016年12月28日 下午4:49:37
	 * @author : Iris.Xiao
	 * @param uid
	 * @description : 添加
	*/
	public void addHotVideo(Hot3DVideo hotVideo){
		if(hotVideo==null){
			throw new AppErrorException("参数错误");
		}
		String uid = hotVideo.getUid();
		if(StringUtil.isBlank(uid)){
			throw new AppErrorException("参数错误");
		}
		String key = String.format(RedisKey.KEY_PLAYER_HOT_3D_VIDEO_HASH, uid);
		Map<String,String > map = hotVideo.toMap();
		cacheRedis.opsForHash().putAll(key, map);
	}
	
	/**
	 * @date : 2016年12月28日 下午4:49:37
	 * @author : Iris.Xiao
	 * @param uid
	 * @description : 添加到热门列表和活动的排序结果中
	*/
	public void addHotVideoAndPmtList(Hot3DVideo hotVideo){
		double startTime = hotVideo.getStartTime().getTime();
		String uid = hotVideo.getUid();
		String zkey = String.format(RedisKey.KEY_PLAYER_HOT_3D_VIDEO_LOCAL_ZSET,hotVideo.getCountryCode());
		String dataStr = getVideoAndPmtValue(uid);
		cacheRedis.opsForZSet().add(zkey, dataStr, startTime);
		//设置过期时间的key
		String ekey = String.format(RedisKey.KEY_PLAYER_HOT_3D_VIDEO_EXPIRE_HASH,  uid);
		cacheRedis.opsForValue().set(ekey, dataStr);
		//再设置过期时间
		long time = hotVideo.getEndTime().getTime()-DateUtil.getCurTime().getTime();
		cacheRedis.expire(ekey, time, TimeUnit.MILLISECONDS);
	}
	
	
	/**
	 * @date : 2016年12月28日 下午4:49:37
	 * @author : Iris.Xiao
	 * @param uid
	 * @description :删除热门列表和活动的排序结果
	*/
	public void deleteHotVideoAndPmtList(Hot3DVideo hotVideo){
		String zkey = String.format(RedisKey.KEY_PLAYER_HOT_3D_VIDEO_LOCAL_ZSET,hotVideo.getCountryCode());
		String dataStr = getVideoAndPmtValue(hotVideo.getUid());
		cacheRedis.opsForZSet().remove(zkey, dataStr);

		//设置过期时间的key
		String ekey = String.format(RedisKey.KEY_PLAYER_HOT_3D_VIDEO_EXPIRE_HASH,  hotVideo.getUid());
		cacheRedis.delete(ekey);
	}
	
	

	/**
	 * @date : 2017年1月3日 下午2:39:55
	 * @author : Iris.Xiao
	 * @param fileId
	 * @description :  删除fileid>uid文件id查找热门影片id
	*/
	public void deleteHotVideoByFileId(String fileId){
		cacheRedis.opsForHash().delete(RedisKey.KEY_PLAYER_HOT_3D_VIDEO_FILEID_HASH, fileId);
	}

	/**
	 * @date : 2017年1月3日 下午2:41:52
	 * @author : Iris.Xiao
	 * @param hotVideo
	 * @description : 添加fileid>uid文件id查找热门影片id
	*/
	public void addHotVideoByFileId(Hot3DVideo hotVideo){
		String uid = hotVideo.getUid();
		String fileId = hotVideo.getFileId();
		String key = RedisKey.KEY_PLAYER_HOT_3D_VIDEO_FILEID_HASH;
		cacheRedis.opsForHash().put(key, fileId, uid);
	}
	
	/**
	 * @date : 2017年1月3日 下午2:54:33
	 * @author : Iris.Xiao
	 * @param fileId
	 * @return
	 * @description : 
	*/
	public Hot3DVideo getHotVideoByFileId(String fileId){
		String key = RedisKey.KEY_PLAYER_HOT_3D_VIDEO_FILEID_HASH;
		Object obj = cacheRedis.opsForHash().get(key, fileId);
		if(obj==null){
			return null;
		}
		String uid = obj.toString();
		return getHotVideo(uid);
	}
	
	/**
	 * @date : 2017年2月15日 下午6:40:49
	 * @author : Iris.Xiao
	 * @param uid
	 * @return
	 * @description : 统一得到排序的值,类似"{\"dataType\":\"hotVideo\",\"dataUid\":\"6a4fad0115e945e9ada1e5a0f0218c8c\"}",
	 * 之前用hashmap,datatype和datauid顺序可能会乱
	*/
	public String getVideoAndPmtValue(String uid){
		Map<String,Object> data = new LinkedHashMap<String,Object>();
		data.put(HotVideoPmtKeyType.DATA_TYPE.getValue(), HotVideoPmtResultType.HOTVIDEO.getValue());
		data.put(HotVideoPmtKeyType.DATA_UID.getValue(), uid);
		String dataStr = JsonUtils.getJsonStrByMap(data);
		return dataStr;
	}
}
