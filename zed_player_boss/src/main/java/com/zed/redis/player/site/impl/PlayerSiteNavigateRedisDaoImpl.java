package com.zed.redis.player.site.impl;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;

import com.zed.common.util.JsonUtils;
import com.zed.domain.player.site.PlayerSiteNavigate;
import com.zed.redis.player.site.PlayerSiteNavigateRedisDao;

@Repository("playerSiteNavigateRedisDao")
public class PlayerSiteNavigateRedisDaoImpl implements PlayerSiteNavigateRedisDao {
	@Resource(name="cacheRedis")
	private RedisTemplate<String,String> cacheRedis;

	/*@Override
	public List<Map<String, Object>> findTopPlayerSiteNavigate(final Integer topSize) {
		return cacheRedis.execute(new RedisCallback<List<Map<String, Object>>>(){

			@Override
			public List<Map<String, Object>> doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection conn =(StringRedisConnection) connection;
				Set<Tuple> typedTupleSet = connection.zRevRangeWithScores(new StringRedisSerializer().serialize("ply:site:id:z"), 0, topSize-1);
				Iterator<Tuple> it = typedTupleSet.iterator();
				String[] array = new String[typedTupleSet.size()];
				int i = 0;
				while(it.hasNext()){
					Tuple t = it.next();
					array[i++] = new StringRedisSerializer().deserialize(t.getValue());
				}
				List<String> objectStrList = new LinkedList<String>();
				if (array.length>0) {
					objectStrList = conn.hMGet("ply:site:info:h", array);
				}
				List<Map<String, Object>> prkList = new LinkedList<Map<String, Object>>();
				for (String string : objectStrList) {
					prkList.add(JsonUtils.jsonToObj(string,PlayerSiteNavigate.class).forMap());
				}
				return prkList;
			}
		});
	}
*/
	/*@Override
	public void addBatch(final List<PlayerSiteNavigate> playerSiteNavigateList) {
			cacheRedis.execute(new RedisCallback<Object>(){
				
				@Override
				public Object doInRedis(RedisConnection connection) throws DataAccessException {
					StringRedisConnection conn =(StringRedisConnection) connection;
					StringRedisSerializer serializer = new StringRedisSerializer();
					Map<byte[], byte[]> hashes = new HashMap<byte[], byte[]>();
					for (PlayerSiteNavigate playerSiteNavigate : playerSiteNavigateList) {
						hashes.put(serializer.serialize(playerSiteNavigate.getSiteNavigateId()), serializer.serialize(JsonUtils.objToJson(playerSiteNavigate)));
						conn.zAdd(serializer.serialize("ply:site:id:z"), playerSiteNavigate.getSort(), serializer.serialize(playerSiteNavigate.getSiteNavigateId()));
					}
					conn.hMSet(serializer.serialize("ply:site:info:h"), hashes);
					return null;
				}
			});
	}*/
	@Override
	public void addPlayerSiteNavigate(final PlayerSiteNavigate playerSiteNavigate) {
		cacheRedis.execute(new RedisCallback<Object>(){
			
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection conn =(StringRedisConnection) connection;
				StringRedisSerializer serializer = new StringRedisSerializer();
				conn.zAdd(serializer.serialize(String.format("ply:site:%s:id:z",playerSiteNavigate.getCountryCode())), playerSiteNavigate.getSort(), serializer.serialize(playerSiteNavigate.getSiteNavigateId()));
				conn.hSet(serializer.serialize(String.format("ply:site:%s:info:h",playerSiteNavigate.getCountryCode())), serializer.serialize(playerSiteNavigate.getSiteNavigateId()), serializer.serialize(JsonUtils.objToJson(playerSiteNavigate)));
				return null;
			}
		});
		
	}

	/*@Override
	public void deleteByIds(final String... siteNavigateIds) {
		cacheRedis.execute(new RedisCallback<Object>(){
			
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection conn =(StringRedisConnection) connection;
				conn.hDel("ply:site:info:h", siteNavigateIds);
				conn.zRem("ply:site:id:z", siteNavigateIds);
				return null;
			}
		});
	}*/

	@Override
	public void deleteByCountryCodes(final String... countryCodes) {
		cacheRedis.execute(new RedisCallback<Object>(){
			
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection conn =(StringRedisConnection) connection;
				for (String countryCode : countryCodes) {
					conn.del(String.format("ply:site:%s:info:h", countryCode), String.format("ply:site:%s:id:z", countryCode));
				}
				return null;
			}
		});
	}
	
}
