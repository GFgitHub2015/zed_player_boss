package com.zed.redis.player.video.impl;

import java.util.ArrayList;
import java.util.HashMap;
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

import com.zed.common.util.CommUtil;
import com.zed.common.util.StringUtil;
import com.zed.domain.player.video.PlayerVideoDestFile;
import com.zed.redis.player.video.PlayerVideoDestFileRedisDao;

@Repository("playerVideoDestFileRedisDao")
public class PlayerVideoDestFileRedisDaoImpl implements PlayerVideoDestFileRedisDao {

	@Resource(name="cacheRedis")
	private RedisTemplate<String,String> cacheRedis;
	
	@Override
	public PlayerVideoDestFile getPlayerVideoDestFile(String fileId) {
		Object object = cacheRedis.opsForHash().get("ply:video:destFile:h", fileId);
		if (!StringUtil.isEmpty(object)) {
			return PlayerVideoDestFile.getPlayerVideoDestFile((String)object);
		}
		return null;
	}

	@Override
	public void addPlayerVideoDestFile(PlayerVideoDestFile playerVideoDestFile) {
		cacheRedis.opsForHash().put("ply:video:destFile:h", playerVideoDestFile.getFileId(), playerVideoDestFile.toJson());
	}

	@Override
	public void deletePlayerVideoDestFile(final String fileId) {
		cacheRedis.opsForHash().delete("ply:video:destFile:h", fileId);
	}

	@Override
	public List<PlayerVideoDestFile> findDestFileIdBySourceFileId(final String sourceFileId) {
		return cacheRedis.execute(new RedisCallback<List<PlayerVideoDestFile>>(){

			@Override
			public List<PlayerVideoDestFile> doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection conn =(StringRedisConnection) connection;
				StringRedisSerializer serializer = new StringRedisSerializer();
				Set<byte[]> setBytes = conn.sMembers(serializer.serialize(String.format("ply:destfile:%s:s", sourceFileId)));
				List<PlayerVideoDestFile> result = new ArrayList<PlayerVideoDestFile>();
				for (byte[] bs : setBytes) {
					String fileId = serializer.deserialize(bs);
					String str = conn.hGet("ply:video:destFile:h", fileId);
					if (!CommUtil.isEmpty(str)) {
						PlayerVideoDestFile playerVideoDestFile = PlayerVideoDestFile.getPlayerVideoDestFile(str);
						if (playerVideoDestFile != null) {
							result.add(playerVideoDestFile);
						}
					}
				}
				return result;
			}
		});
	}

	@Override
	public void addDestFileIdWithSourceFileId(final List<PlayerVideoDestFile> playerVideoDestFileList) {
		cacheRedis.execute(new RedisCallback<Object>(){

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection conn =(StringRedisConnection) connection;
				StringRedisSerializer serializer = new StringRedisSerializer();
				Map<byte[], byte[]> hashes = new HashMap<byte[], byte[]>();
				for (PlayerVideoDestFile playerVideoDestFile : playerVideoDestFileList) {
					hashes.put(serializer.serialize(playerVideoDestFile.getFileId()), serializer.serialize(playerVideoDestFile.toJson()));
					conn.sAdd(serializer.serialize(String.format("ply:destfile:%s:s", playerVideoDestFile.getSourceFileId())), serializer.serialize(playerVideoDestFile.getFileId()));
				}
				conn.hMSet(serializer.serialize("ply:video:destFile:h"), hashes);
				return null;
			}
		});
	}

	@Override
	public void deleteBySourceFileId(final String sourceFileId) {
		cacheRedis.opsForSet().getOperations().delete(String.format("ply:destfile:%s:s", sourceFileId));
	}

}
