package com.zed.redis.player.downloadlist.impl;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.zed.redis.player.downloadlist.DownloadListRedisDao;

@Repository("downloadListRedisDao")
public class DownloadListRedisDaoImpl implements DownloadListRedisDao {
	@Resource(name="cacheRedis")
	private RedisTemplate<String,String> cacheRedis;
}
