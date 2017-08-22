package com.zed.redis.player.logicalfile.impl;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.zed.common.util.JsonUtils;
import com.zed.common.util.StringUtil;
import com.zed.domain.player.logicalfile.PlayerLogicalPlayTimes;
import com.zed.redis.player.logicalfile.PlayerLogicalPlayTimesRedisDao;

@Repository("playerLogicalPlayTimesRedisDao")
public class PlayerLogicalPlayTimesRedisDaoImpl implements PlayerLogicalPlayTimesRedisDao {
	@Resource(name="cacheRedis")
	private RedisTemplate<String,String> cacheRedis;

	@Override
	public void zIncrease(final String fileId) {
		cacheRedis.execute(new RedisCallback<Object>(){
			
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection conn =(StringRedisConnection) connection;
				Double score = conn.zIncrBy("ply:playtimes:z", 1, fileId);
				PlayerLogicalPlayTimes ppt = null;
				String objectStr = conn.hGet("ply:playtimes:h", fileId);
				if (!StringUtil.isEmpty(objectStr)) {
					ppt = JsonUtils.jsonToObj(objectStr, PlayerLogicalPlayTimes.class);
				}else{
					ppt = new PlayerLogicalPlayTimes();
					ppt.setFileId(fileId);
				}
				ppt.setTimes(score);
				conn.hSet("ply:playtimes:h", fileId, JsonUtils.objToJson(ppt));
				return null;
			}
		});
	}

	@Override
	public PlayerLogicalPlayTimes getPlayerLogicalPlayTimes(final String fileId) {
		return cacheRedis.execute(new RedisCallback<PlayerLogicalPlayTimes>(){
			
			@Override
			public PlayerLogicalPlayTimes doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection conn =(StringRedisConnection) connection;
				String objectStr = conn.hGet("ply:playtimes:h", fileId);
				if (!StringUtil.isEmpty(objectStr)) {
					PlayerLogicalPlayTimes plp = JsonUtils.jsonToObj(objectStr, PlayerLogicalPlayTimes.class);
					return plp;
				}
				return null;
			}
		});
	}

	@Override
	public void addPlayerLogicalPlayTimes(final PlayerLogicalPlayTimes playerLogicalPlayTimes) {
		cacheRedis.execute(new RedisCallback<Object>(){
			
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection conn =(StringRedisConnection) connection;
				conn.zAdd("ply:playtimes:z", playerLogicalPlayTimes.getTimes(), playerLogicalPlayTimes.getFileId());
				conn.hSet("ply:playtimes:h", playerLogicalPlayTimes.getFileId(), JsonUtils.objToJson(playerLogicalPlayTimes));
				return null;
			}
		});
	}

	@Override
	public Boolean exist(final String fileId) {
		return cacheRedis.execute(new RedisCallback<Boolean>(){

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				return ((StringRedisConnection) connection).hExists("ply:playtimes:h", fileId);
			}
		});
	}
	
}
