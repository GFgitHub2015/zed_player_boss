package com.zed.redis.player.push.impl;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.zed.domain.player.push.PlayerTaskParams;
import com.zed.redis.player.push.PlayerPushTaskRedisDao;
import com.zed.util.JsonUtils;
@Repository("playerPushTaskRedisDao")
public class PlayerPushTaskRedisDaoImpl implements PlayerPushTaskRedisDao {
	@Resource(name="cacheRedis")
	private RedisTemplate<String,String> cacheRedis;

	@Override
	public void addPlayerTaskParams(final PlayerTaskParams playerTaskParams) {
		cacheRedis.execute(new RedisCallback<Object>(){

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection conn =(StringRedisConnection) connection;
				String key = String.format("ply:push:task:%s:params", playerTaskParams.getTaskId());
				conn.set(key, JsonUtils.objToJson(playerTaskParams));
				conn.expire(key, 60*60*24*20000);
				return null;
			}
		});
	}


	@Override
	public void deleteByTaskId(final String ...taskIds) {
		cacheRedis.execute(new RedisCallback<Object>(){

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection conn =(StringRedisConnection) connection;
				Set<String> keySet = new HashSet<String>();
				for (String taskId : taskIds) {
					String key = String.format("ply:push:task:%s:params", taskId);
					keySet.add(key);
				}
				if (!keySet.isEmpty()) {
					String [] keys = keySet.toArray(new String[keySet.size()]);
					conn.del(keys);
				}
				return null;
			}
		});
		
	}


	@Override
	public PlayerTaskParams getByTaskId(final String taskId) {
		return cacheRedis.execute(new RedisCallback<PlayerTaskParams>(){

			@Override
			public PlayerTaskParams doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection conn =(StringRedisConnection) connection;
				String key = String.format("ply:push:task:%s:params", taskId);
				String objectStr = conn.get(key);
				if (StringUtils.isNotBlank(objectStr)) {
					PlayerTaskParams ptp = PlayerTaskParams.getPlayerTaskParams(objectStr);
					return ptp;
				}
				return null;
			}
		});
	}

}
