package com.zed.redis.server.modelinfo.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;

import com.zed.domain.server.modelinfo.PlayerModelInfo;
import com.zed.redis.server.modelinfo.PlayerModelInfoRedisDao;

@Repository("playerModelInfoRedisDao")
public class PlayerModelInfoRedisDaoImpl implements PlayerModelInfoRedisDao {
	@Resource(name="cacheRedis")
	private RedisTemplate<String,String> cacheRedis;
	
	@Override
	public void addPlayerModelInfo(PlayerModelInfo playerModelInfo) {
		cacheRedis.opsForHash().put("ply:modelinfo:h", playerModelInfo.getModel(), playerModelInfo.toJson());
	}

	@Override
	public void addPlayerModelInfoList(final List<PlayerModelInfo> playerModelInfoList) {
		cacheRedis.execute(new RedisCallback<Object>(){
			
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection conn =(StringRedisConnection) connection;
				StringRedisSerializer serializer = new StringRedisSerializer();
				Map<byte[], byte[]> hashes = new HashMap<byte[], byte[]>();
				for (PlayerModelInfo pmi : playerModelInfoList) {
					hashes.put(serializer.serialize(pmi.getModel()), serializer.serialize(pmi.toJson()));
				}
				conn.hMSet(serializer.serialize("ply:modelinfo:h"), hashes);
				return null;
			}
		});
		
	}

	@Override
	public void deletePlayerModelInfoList(final String... models) {
		cacheRedis.execute(new RedisCallback<Object>(){

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection conn =(StringRedisConnection) connection;
				for (String model : models) {
					conn.hDel("ply:modelinfo:h", model);
				}
				return null;
			}
		});
		
	}

	@Override
	public void deleteAll() {
		cacheRedis.delete("ply:modelinfo:h");		
	}
}
