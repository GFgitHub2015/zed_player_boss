package com.zed.redis.player.activate.impl;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.zed.redis.player.activate.ActivateRedisDao;

@Repository("activateRedisDao")
public class ActivateRedisDaoImpl implements ActivateRedisDao {
	@Resource(name="cacheRedis")
	private RedisTemplate<String,String> cacheRedis;
}
