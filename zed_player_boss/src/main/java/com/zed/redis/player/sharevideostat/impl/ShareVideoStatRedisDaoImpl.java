package com.zed.redis.player.sharevideostat.impl;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.zed.redis.player.sharevideostat.ShareVideoStatRedisDao;

/**
 * @date : 2017年4月6日 下午1:51:17
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 影片分享统计
*/
@Repository("shareVideoStatRedisDao")
public class ShareVideoStatRedisDaoImpl implements ShareVideoStatRedisDao{

	@Resource(name="cacheRedis")
	private RedisTemplate<String,String> cacheRedis;
	
	
}
