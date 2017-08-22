package com.zed.redis.player.white.impl;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.zed.domain.player.white.PlayerWhiteAreaCode;
import com.zed.redis.player.white.PlayerWhiteAreaCodeRedisDao;

@Repository("playerWhiteAreaCodeRedisDao")
public class PlayerWhiteAreaCodeRedisDaoImpl implements PlayerWhiteAreaCodeRedisDao {

	@Resource(name="cacheRedis")
	private RedisTemplate<String,String> cacheRedis;

	@Override
	public void addPlayerWhiteAreaCode(PlayerWhiteAreaCode playerWhiteAreaCode) {
		cacheRedis.opsForSet().add("ply:whitelist:areacode:s", playerWhiteAreaCode.getAreaCode());
	}

	@Override
	public void deletePlayerWhiteAreaCode() {
		cacheRedis.delete("ply:whitelist:areacode:s");
	}
}
