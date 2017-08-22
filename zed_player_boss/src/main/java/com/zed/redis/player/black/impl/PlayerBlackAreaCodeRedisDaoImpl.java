package com.zed.redis.player.black.impl;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;

import com.zed.domain.player.black.PlayerBlackAreaCode;
import com.zed.redis.player.black.PlayerBlackAreaCodeRedisDao;

@Repository("playerBlackAreaCodeRedisDao")
public class PlayerBlackAreaCodeRedisDaoImpl implements PlayerBlackAreaCodeRedisDao {
	@Resource(name="cacheRedis")
	private RedisTemplate<String,String> cacheRedis;

	@Override
	public void addPlayerBlackAreaCode(final PlayerBlackAreaCode playerBlackAreaCode) {
		cacheRedis.execute(new RedisCallback<Object>(){
			
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection conn =(StringRedisConnection) connection;
				StringRedisSerializer serializer = new StringRedisSerializer();
				conn.sAdd(serializer.serialize("ply:blacklist:areacode:s"), serializer.serialize(playerBlackAreaCode.getAreaCode()));
				return null;
			}
		});
	}

	@Override
	public Boolean isExist(final String areaCode) {
		return cacheRedis.execute(new RedisCallback<Boolean>(){
				
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection conn =(StringRedisConnection) connection;
				StringRedisSerializer serializer = new StringRedisSerializer();
				return conn.sIsMember(serializer.serialize("ply:blacklist:areacode:s"), serializer.serialize(areaCode));
			}
		});
	}

	@Override
	public void deletePlayerBlackAreaCode() {
		cacheRedis.delete("ply:blacklist:areacode:s");
	}
}
