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
import com.zed.domain.player.logicalfile.PlayerLogicalDownloadTimes;
import com.zed.redis.player.logicalfile.PlayerLogicalDownloadTimesRedisDao;

@Repository("playerLogicalDownloadTimesRedisDao")
public class PlayerLogicalDownloadTimesRedisDaoImpl implements PlayerLogicalDownloadTimesRedisDao {
	@Resource(name="cacheRedis")
	private RedisTemplate<String,String> cacheRedis;

	@Override
	public void zIncrease(final String fileId) {
		cacheRedis.execute(new RedisCallback<Object>(){
			
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection conn =(StringRedisConnection) connection;
				Double score = conn.zIncrBy("ply:downloadtimes:z", 1, fileId);
				PlayerLogicalDownloadTimes pld = null;
				String objectStr = conn.hGet("ply:downloadtimes:h", fileId);
				if (!StringUtil.isEmpty(objectStr)) {
					pld = JsonUtils.jsonToObj(objectStr, PlayerLogicalDownloadTimes.class);
				}else{
					pld = new PlayerLogicalDownloadTimes();
					pld.setFileId(fileId);
				}
				pld.setTimes(score);
				conn.hSet("ply:downloadtimes:h", fileId, JsonUtils.objToJson(pld));
				return null;
			}
		});
	}

	@Override
	public PlayerLogicalDownloadTimes getPlayerLogicalDownloadTimes(final String fileId) {
		return cacheRedis.execute(new RedisCallback<PlayerLogicalDownloadTimes>(){
			
			@Override
			public PlayerLogicalDownloadTimes doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection conn =(StringRedisConnection) connection;
				String objectStr = conn.hGet("ply:downloadtimes:h", fileId);
				if (StringUtil.isEmpty(objectStr)) {
					PlayerLogicalDownloadTimes pld = JsonUtils.jsonToObj(objectStr, PlayerLogicalDownloadTimes.class);
					return pld;
				}
				return null;
			}
		});
	}

	@Override
	public void addPlayerLogicalDownloadTimes(final PlayerLogicalDownloadTimes playerLogicalDownloadTimes) {
		cacheRedis.execute(new RedisCallback<Object>(){
			
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection conn =(StringRedisConnection) connection;
				conn.zAdd("ply:downloadtimes:z", playerLogicalDownloadTimes.getTimes(), playerLogicalDownloadTimes.getFileId());
				conn.hSet("ply:downloadtimes:h", playerLogicalDownloadTimes.getFileId(), JsonUtils.objToJson(playerLogicalDownloadTimes));
				return null;
			}
		});
	}

	@Override
	public Boolean exist(final String sourceFileId) {
		return cacheRedis.execute(new RedisCallback<Boolean>(){

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				return ((StringRedisConnection) connection).hExists("ply:downloadtimes:h", sourceFileId);
			}
		});
	}
	
}
