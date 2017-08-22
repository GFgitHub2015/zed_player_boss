package com.zed.redis.player.white.impl;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.zed.domain.player.white.PlayerWhiteIp;
import com.zed.redis.player.white.PlayerWhiteIpRedisDao;

@Repository("playerWhiteIpRedisDao")
public class PlayerWhiteIpRedisDaoImpl implements PlayerWhiteIpRedisDao {

	@Resource(name="cacheRedis")
	private RedisTemplate<String,String> cacheRedis;

	@Override
	public void addPlayerWhiteIp(PlayerWhiteIp playerWhiteIp) {
		cacheRedis.opsForSet().add("ply:whitelist:ip:s", playerWhiteIp.getIp());
		
	}

	@Override
	public void deletePlayerWhiteIp() {
		cacheRedis.delete("ply:whitelist:ip:s");
	}
}
