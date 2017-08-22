package com.zed.redis.player.hk.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisZSetCommands.Tuple;
import org.springframework.data.redis.connection.StringRedisConnection.StringTuple;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;

import com.zed.common.util.JsonUtils;
import com.zed.common.util.redis.RedisKey;
import com.zed.domain.player.hotkeyword.PlayerHotKeyword;
import com.zed.redis.player.hk.PlayerHotKeyWordRedisDao;
import com.zed.util.ConstantType;

@Repository("playerHotKeyWordRedisDao")
public class PlayerHotKeyWordRedisDaoImpl implements PlayerHotKeyWordRedisDao {
	@Resource(name="cacheRedis")
	private RedisTemplate<String,String> cacheRedis;
	/**
	 * 获取一定数量的搜索热词，最好从搜索引擎中获取
	 * @return
	 */
	/*@Override
	public List<PlayerHotKeyword> findTopPlayerHotKeyword(final Integer topSize) {
		return cacheRedis.execute(new RedisCallback<List<PlayerHotKeyword>>(){

			@Override
			public List<PlayerHotKeyword> doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection conn =(StringRedisConnection) connection;
				Set<Tuple> typedTupleSet = connection.zRangeWithScores(new StringRedisSerializer().serialize(RedisKey.KEY_PLAYER_KEYWORD_HK_ZSET), 0, topSize);
				Iterator<Tuple> it = typedTupleSet.iterator();
				String[] array = new String[typedTupleSet.size()];
				int i = 0;
				while(it.hasNext()){
					Tuple t = it.next();
					array[i++] = new StringRedisSerializer().deserialize(t.getValue());
				}
				List<String> objectStrList = new LinkedList<String>();
				if (array.length>0) {
					objectStrList = conn.hMGet(RedisKey.KEY_PLAYER_KEYWORD_HK_HASH, array);
				}
				List<PlayerHotKeyword> prkList = new LinkedList<PlayerHotKeyword>();
				for (String string : objectStrList) {
					prkList.add(JsonUtils.jsonToObj(string,PlayerHotKeyword.class));
				}
				return prkList;
			}
		});
	}
	*//**
	 * 更新搜索热词，如果存在，就更新搜索热词的热度，并更新缓存中的热词对象。如果不存在，就在缓存中添加热词对象，并添加到热词队列中
	 *//*
	@Override
	public void zIncrease(final String areaCode, final String keyWord) {
		cacheRedis.execute(new RedisCallback<Object>(){
			
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection conn =(StringRedisConnection) connection;
				if (exist(keyWord)) {
					Double score = conn.zIncrBy(RedisKey.KEY_PLAYER_KEYWORD_HK_ZSET, 1, keyWord);
					PlayerHotKeyword prk = JsonUtils.jsonToObj(conn.hGet(RedisKey.KEY_PLAYER_KEYWORD_HK_HASH, keyWord),PlayerHotKeyword.class);
					prk.setSort(score);
					prk.setUpdateTime(new Timestamp(new Date().getTime()));
					conn.hSet(RedisKey.KEY_PLAYER_KEYWORD_HK_HASH, keyWord, JsonUtils.objToJson(prk));
				} else {
					Double score = conn.zIncrBy(RedisKey.KEY_PLAYER_KEYWORD_HK_ZSET, 1, keyWord);
					PlayerHotKeyword prk = new PlayerHotKeyword();
					prk.setKeywordId(prk.generateId());
					prk.setDescription(keyWord);
					prk.setKeyword(keyWord);
					prk.setSort(score);
					prk.setStatus(1);
					prk.setAreaCode(areaCode);
					prk.setCreateTime(new Timestamp(new Date().getTime()));
					conn.hSet(RedisKey.KEY_PLAYER_KEYWORD_HK_HASH, keyWord, JsonUtils.objToJson(prk));
				}
				return null;
			}
		});
		
	}
	*//**
	 * 获取搜索热词的热度
	 *//*
	@Override
	public Double getScore(String keyWord) {
		return cacheRedis.opsForZSet().score(RedisKey.KEY_PLAYER_KEYWORD_HK_ZSET, keyWord);
	}
	
	*//**
	 * 判断搜索热词是否存在
	 * @param HashKey
	 * @param keyWord
	 * @return
	 *//*
	@Override
	public Boolean exist(final String keyWord){
		return cacheRedis.execute(new RedisCallback<Boolean>(){

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				return ((StringRedisConnection) connection).hExists(RedisKey.KEY_PLAYER_KEYWORD_HK_HASH, keyWord);
			}
		});
	}
	
	@Override
	public void add(final PlayerHotKeyword playerHotKeyword) {
		if (!exist(playerHotKeyword.getKeyword())&&playerHotKeyword.getStatus()==ConstantType.KeyWordType.START.getStatus()) {
			cacheRedis.execute(new RedisCallback<Object>(){
				
				@Override
				public Object doInRedis(RedisConnection connection) throws DataAccessException {
					StringRedisConnection conn =(StringRedisConnection) connection;
					StringRedisSerializer serializer = new StringRedisSerializer();
					conn.zAdd(serializer.serialize(RedisKey.KEY_PLAYER_KEYWORD_HK_ZSET), playerHotKeyword.getSort(), serializer.serialize(playerHotKeyword.getKeyword()));
					conn.hSet(serializer.serialize(RedisKey.KEY_PLAYER_KEYWORD_HK_HASH), serializer.serialize(playerHotKeyword.getKeyword()), serializer.serialize(JsonUtils.objToJson(playerHotKeyword)));
					return null;
				}
			});
		}
	}

	@Override
	public void addBatch(List<PlayerHotKeyword> playerHotKeyword) {
		final List<PlayerHotKeyword> playerHotKeywordList = new ArrayList<PlayerHotKeyword>();
		for (PlayerHotKeyword playerHotKeyword2 : playerHotKeyword) {
			if (!(exist(playerHotKeyword2.getKeyword())||existWordKeyId(playerHotKeyword2.getKeywordId()))&&playerHotKeyword2.getStatus()==ConstantType.KeyWordType.START.getStatus()) {
				playerHotKeywordList.add(playerHotKeyword2);
			}
		}
		if (!playerHotKeywordList.isEmpty() || playerHotKeywordList.size()>0) {
			cacheRedis.execute(new RedisCallback<Object>(){
				
				@Override
				public Object doInRedis(RedisConnection connection) throws DataAccessException {
					StringRedisConnection conn =(StringRedisConnection) connection;
					StringRedisSerializer serializer = new StringRedisSerializer();
					Map<byte[], byte[]> hashes = new HashMap<byte[], byte[]>();
					for (PlayerHotKeyword phk : playerHotKeywordList) {
						hashes.put(serializer.serialize(phk.getKeyword()), serializer.serialize(JsonUtils.objToJson(phk)));
						conn.zAdd(serializer.serialize(RedisKey.KEY_PLAYER_KEYWORD_HK_ZSET), phk.getSort(), serializer.serialize(phk.getKeyword()));
					}
					conn.hMSet(serializer.serialize(RedisKey.KEY_PLAYER_KEYWORD_HK_HASH), hashes);
					return null;
				}
			});
		}
	}
	
	*//**
	 * 更新搜索热词的缓存信息
	 * 1、获取所有的搜索热词信息
	 * 2、判断是否存在该对象的主键keywordId
	 * 3、若存在就删除该hashKey的 field和值
	 * 4、将新的搜索热词对象放到该hashKey下
	 *//*
	@Override
	public void update(final PlayerHotKeyword playerHotKeyword) {
		cacheRedis.execute(new RedisCallback<Object>(){

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection conn =(StringRedisConnection) connection;
				StringRedisSerializer serializer = new StringRedisSerializer();
				if (existWordKeyId(playerHotKeyword.getKeywordId())) {
					Map<byte[], byte[]> map = conn.hGetAll(serializer.serialize(RedisKey.KEY_PLAYER_KEYWORD_HK_HASH));
					for (Map.Entry<byte[], byte[]> entry : map.entrySet()) {
						PlayerHotKeyword prk = JsonUtils.jsonToObj(serializer.deserialize(entry.getValue()),PlayerHotKeyword.class);
						String keywordId = prk.getKeywordId();
						if (playerHotKeyword.getKeywordId().equals(keywordId)) {
							conn.hDel(serializer.serialize(RedisKey.KEY_PLAYER_KEYWORD_HK_HASH), entry.getKey());
							conn.zRem(serializer.serialize(RedisKey.KEY_PLAYER_KEYWORD_HK_ZSET), serializer.serialize(prk.getKeyword()));
							if (playerHotKeyword.getStatus()==ConstantType.KeyWordType.START.getStatus()) {
								conn.hSet(serializer.serialize(RedisKey.KEY_PLAYER_KEYWORD_HK_HASH), serializer.serialize(playerHotKeyword.getKeyword()), serializer.serialize(JsonUtils.objToJson(playerHotKeyword)));
								conn.zAdd(serializer.serialize(RedisKey.KEY_PLAYER_KEYWORD_HK_ZSET), playerHotKeyword.getSort(), serializer.serialize(playerHotKeyword.getKeyword()));
							}
						}
					}
				}else{
					if (playerHotKeyword.getStatus()==ConstantType.KeyWordType.START.getStatus()) {
						conn.hSet(serializer.serialize(RedisKey.KEY_PLAYER_KEYWORD_HK_HASH), serializer.serialize(playerHotKeyword.getKeyword()), serializer.serialize(JsonUtils.objToJson(playerHotKeyword)));
						conn.zAdd(serializer.serialize(RedisKey.KEY_PLAYER_KEYWORD_HK_ZSET), playerHotKeyword.getSort(), serializer.serialize(playerHotKeyword.getKeyword()));
					}
				}
				return null;
			}
		});
	}

	@Override
	public void delete(final String wordKeyId) {
		cacheRedis.execute(new RedisCallback<Object>(){

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection conn =(StringRedisConnection) connection;
				StringRedisSerializer serializer = new StringRedisSerializer();
				Map<byte[], byte[]> map = conn.hGetAll(serializer.serialize(RedisKey.KEY_PLAYER_KEYWORD_HK_HASH));
				for (Map.Entry<byte[], byte[]> entry : map.entrySet()) {
					PlayerHotKeyword prk = JsonUtils.jsonToObj(serializer.deserialize(entry.getValue()),PlayerHotKeyword.class);
					String id = prk.getKeywordId();
					if (wordKeyId.equals(id)) {
						conn.hDel(RedisKey.KEY_PLAYER_KEYWORD_HK_HASH, serializer.deserialize(entry.getKey()));
						conn.zRem(RedisKey.KEY_PLAYER_KEYWORD_HK_ZSET, prk.getKeyword());
					}
				}
				return null;
			}
		});
	}

	@Override
	public void delete(final String... wordKeyIds) {
		cacheRedis.execute(new RedisCallback<Object>(){

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection conn =(StringRedisConnection) connection;
				StringRedisSerializer serializer = new StringRedisSerializer();
				Map<byte[], byte[]> map = conn.hGetAll(serializer.serialize(RedisKey.KEY_PLAYER_KEYWORD_HK_HASH));
				List<String> fieldsList = new ArrayList<String>();
				for (String prkId : wordKeyIds) {
					for (Map.Entry<byte[], byte[]> entry : map.entrySet()) {
						PlayerHotKeyword prk = JsonUtils.jsonToObj(serializer.deserialize(entry.getValue()),PlayerHotKeyword.class);
						String id = prk.getKeywordId();
						if (prkId.equals(id)) {
							fieldsList.add(prk.getKeyword());
						}
					}
				}
				if (!fieldsList.isEmpty()||fieldsList.size()>0) {
					String[] fields = fieldsList.toArray(new String[fieldsList.size()]);
					conn.hDel(RedisKey.KEY_PLAYER_KEYWORD_HK_HASH, fields);
					conn.zRem(RedisKey.KEY_PLAYER_KEYWORD_HK_ZSET, fields);
				}
				return null;
			}
		});
	}

	@Override
	public Boolean existWordKeyId(final String wordKeyId) {
		return cacheRedis.execute(new RedisCallback<Boolean>(){

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection conn =(StringRedisConnection) connection;
				StringRedisSerializer serializer = new StringRedisSerializer();
				Map<byte[], byte[]> map = conn.hGetAll(serializer.serialize(RedisKey.KEY_PLAYER_KEYWORD_HK_HASH));
				for (Map.Entry<byte[], byte[]> entry : map.entrySet()) {
					PlayerHotKeyword prk = JsonUtils.jsonToObj(serializer.deserialize(entry.getValue()),PlayerHotKeyword.class);
					String id = prk.getKeywordId();
					if(wordKeyId.equals(id)) {
						return true;
					}
				}
				return false;
			}
		});
	}
	
	@Override
	public Boolean updateAllCache(final List<PlayerHotKeyword> playerHotKeywordList) {
		return cacheRedis.execute(new RedisCallback<Boolean>(){

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				Boolean flag = Boolean.FALSE;
				StringRedisConnection conn =(StringRedisConnection) connection;
				conn.del(RedisKey.KEY_PLAYER_KEYWORD_HK_HASH, RedisKey.KEY_PLAYER_KEYWORD_HK_ZSET);
				StringRedisSerializer serializer = new StringRedisSerializer();
				Map<byte[], byte[]> hashes = new HashMap<byte[], byte[]>();
				for (PlayerHotKeyword phk : playerHotKeywordList) {
					hashes.put(serializer.serialize(phk.getKeyword()), serializer.serialize(JsonUtils.objToJson(phk)));
					flag = conn.zAdd(serializer.serialize(RedisKey.KEY_PLAYER_KEYWORD_HK_ZSET), phk.getSort(), serializer.serialize(phk.getKeyword()));
				}
				conn.hMSet(serializer.serialize(RedisKey.KEY_PLAYER_KEYWORD_HK_HASH), hashes);
				return flag;
			}
		});
	}

	@Override
	public List<PlayerHotKeyword> findByPage(final Integer pageNumber,final Integer pageSize){
		return cacheRedis.execute(new RedisCallback<List<PlayerHotKeyword>>(){
			@Override
			public List<PlayerHotKeyword> doInRedis(RedisConnection connection) throws DataAccessException {
//				Long pageSize = 50l;
				StringRedisConnection conn =(StringRedisConnection) connection;
				Set<String> keywordSets = conn.zRange(RedisKey.KEY_PLAYER_KEYWORD_HK_ZSET, pageNumber*pageSize, (pageNumber+1)*pageSize-1);
				List<PlayerHotKeyword> phkList = new LinkedList<PlayerHotKeyword>();
				List<String> objectStrList = conn.hMGet(RedisKey.KEY_PLAYER_KEYWORD_HK_HASH, keywordSets.toArray(new String [keywordSets.size()]));
				for (String hkey : objectStrList) {
					if (StringUtils.isNotBlank(hkey)) {
						phkList.add(JsonUtils.jsonToObj(hkey,PlayerHotKeyword.class));
					}else{
						phkList.add(null);
					}
				}
				return phkList;
			}
		});	
	}
	*/
}
