package com.zed.redis.player.rk.impl;

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

import com.zed.common.util.JsonUtils;
import com.zed.domain.player.recommendkeyword.PlayerRecommendKeyWord;
import com.zed.redis.player.rk.PlayerRecommendKeyWordRedisDao;
import com.zed.util.ConstantType;

@Repository("playerRecommendKeyWordRedisDao")
public class PlayerRecommendKeyWordRedisDaoImpl implements PlayerRecommendKeyWordRedisDao {
	@Resource(name="cacheRedis")
	private RedisTemplate<String,String> cacheRedis;
	@Override
	public Boolean exist(final String areaCode, final String keyWord) {
		return cacheRedis.execute(new RedisCallback<Boolean>(){

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection conn =(StringRedisConnection) connection;
				Boolean hExist = conn.hExists(String.format("ply:keyword:rk:%s:h", areaCode), keyWord);
				Long zExist = conn.zRank(String.format("ply:keyword:rk:%s:z", areaCode), keyWord);
				Boolean flag = hExist&&(zExist!=null);
				if (!flag) {
					conn.hDel(String.format("ply:keyword:rk:%s:h", areaCode), keyWord);
					conn.zRem(String.format("ply:keyword:rk:%s:z", areaCode), keyWord);
				}
				return flag;
			}
		});
	}


	@Override
	public void add(final PlayerRecommendKeyWord playerRecommendKeyWord) {
		cacheRedis.execute(new RedisCallback<Object>(){
			
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection conn =(StringRedisConnection) connection;
				if (playerRecommendKeyWord.getStatus()==ConstantType.KeyWordType.START.getStatus()) {
					conn.zAdd(String.format("ply:keyword:rk:%s:z", playerRecommendKeyWord.getAreaCode()), playerRecommendKeyWord.getSort(), playerRecommendKeyWord.getKeyword());
					conn.hSet(String.format("ply:keyword:rk:%s:h", playerRecommendKeyWord.getAreaCode()), playerRecommendKeyWord.getKeyword(), JsonUtils.objToJson(playerRecommendKeyWord));
				}
				return null;
			}
		});
	}

	/**
	 * 更新推荐热词的缓存信息
	 * 1、获取所有的推荐热词信息
	 * 2、判断是否存在该对象的主键keywordId
	 * 3、若存在就删除该hashKey的 field和值
	 * 4、将新的推荐热词对象放到该hashKey下
	 */
	@Override
	public void update(final PlayerRecommendKeyWord playerRecommendKeyWord) {
		cacheRedis.execute(new RedisCallback<Object>(){

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection conn =(StringRedisConnection) connection;
				if (playerRecommendKeyWord.getStatus()==ConstantType.KeyWordType.START.getStatus()) {
					conn.hSet(String.format("ply:keyword:rk:%s:h", playerRecommendKeyWord.getAreaCode()), playerRecommendKeyWord.getKeyword(), JsonUtils.objToJson(playerRecommendKeyWord));
					conn.zAdd(String.format("ply:keyword:rk:%s:z", playerRecommendKeyWord.getAreaCode()), playerRecommendKeyWord.getSort(), playerRecommendKeyWord.getKeyword());
				}else{
					conn.hDel(String.format("ply:keyword:rk:%s:h", playerRecommendKeyWord.getAreaCode()), playerRecommendKeyWord.getKeyword());
					conn.zRem(String.format("ply:keyword:rk:%s:z", playerRecommendKeyWord.getAreaCode()), playerRecommendKeyWord.getKeyword());
				}
				return null;
			}
		});
	}

	@Override
	public void delete(final PlayerRecommendKeyWord playerRecommendKeyWord) {
		cacheRedis.execute(new RedisCallback<Object>(){

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection conn =(StringRedisConnection) connection;
				conn.hDel(String.format("ply:keyword:rk:%s:h", playerRecommendKeyWord.getAreaCode()), playerRecommendKeyWord.getKeyword());
				conn.zRem(String.format("ply:keyword:rk:%s:z", playerRecommendKeyWord.getAreaCode()), playerRecommendKeyWord.getKeyword());
				return null;
			}
		});
	}

	@Override
	public void delete(final Set<String> areaCodeSet) {
		cacheRedis.execute(new RedisCallback<Object>(){

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				for (String areaCode : areaCodeSet) {
					StringRedisConnection conn =(StringRedisConnection) connection;
					conn.del(String.format("ply:keyword:rk:%s:h", areaCode), String.format("ply:keyword:rk:%s:z", areaCode));
				}
				return null;
			}

		});
	}
	
}
