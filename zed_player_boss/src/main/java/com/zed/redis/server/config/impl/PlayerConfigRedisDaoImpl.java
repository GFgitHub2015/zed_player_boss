package com.zed.redis.server.config.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;

import com.zed.domain.server.config.PlayerConfig;
import com.zed.redis.server.config.PlayerConfigRedisDao;


@Repository("playerConfigRedisDao")
public class PlayerConfigRedisDaoImpl implements PlayerConfigRedisDao {
	@Resource(name="cacheRedis")
	private RedisTemplate<String,String> cacheRedis;

	@Override
	public void addPlayerConfig(PlayerConfig playerConfig) {
		cacheRedis.opsForHash().put(String.format("ply:config:%s:h", playerConfig.getVersion()), playerConfig.getPackageName(), playerConfig.toJson());
	}

	/*@Override
	public void addPlayerConfigList(final List<PlayerConfig> playerConfigList) {
		cacheRedis.execute(new RedisCallback<Object>(){
			
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection conn =(StringRedisConnection) connection;
				StringRedisSerializer serializer = new StringRedisSerializer();
				Map<byte[], byte[]> hashes = new HashMap<byte[], byte[]>();
				for (PlayerConfig pc : playerConfigList) {
					hashes.put(serializer.serialize(String.format("ply:config:%s:h", pc.getVersion())), serializer.serialize(pc.toJson()));
				}
				conn.hMSet(serializer.serialize("ply:config:h"), hashes);
				return null;
			}
		});
		
	}*/

	@Override
	public void deletePlayerConfigList(final String... versions) {
		cacheRedis.execute(new RedisCallback<Object>(){

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection conn =(StringRedisConnection) connection;
				StringRedisSerializer serializer = new StringRedisSerializer();
				Set<String> keySet = new HashSet<String>(); 
				for (String version : versions) {
					keySet.add(String.format("ply:config:%s:h", version));
				}
				String[] keys = keySet.toArray(new String[keySet.size()]);
				conn.del(keys);
				return null;
			}
		});
	}
}
