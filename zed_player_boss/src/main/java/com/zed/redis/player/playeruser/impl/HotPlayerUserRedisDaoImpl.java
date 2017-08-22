package com.zed.redis.player.playeruser.impl;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.zed.redis.player.playeruser.IHotPlayerUserRedisDao;
import com.zed.util.RedisKey;

/**
 * @date : 2017年2月13日 上午11:22:52
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 热门用户
*/
@Repository("hotPlayerUserRedisDaoImpl")
public class HotPlayerUserRedisDaoImpl implements IHotPlayerUserRedisDao {
	@Resource(name="cacheRedis")
	private RedisTemplate<String,String> cacheRedis;

	/**
	 * @date : 2017年2月13日 上午11:21:47
	 * @author : Iris.Xiao
	 * @param userId
	 * @description : 禁用
	*/
	public void disableHotUser(String userId){
		cacheRedis.opsForSet().add(RedisKey.KEY_PLAYER_HOT_USER_DISABLED_SET, userId);
		//再删除排序结果
		cacheRedis.opsForZSet().remove(RedisKey.KEY_PLAYER_HOT_USER_SORT_ZSET, userId);
	}

	/**
	 * @date : 2017年2月13日 上午11:21:47
	 * @author : Iris.Xiao
	 * @param userId
	 * @description : 启用
	*/
	public void enableHotUser(String userId){
		cacheRedis.opsForSet().remove(RedisKey.KEY_PLAYER_HOT_USER_DISABLED_SET, userId);
		//把关注数添加排序结果集中
		Double score = cacheRedis.opsForZSet().score(RedisKey.KEY_PLAYER_HOT_USER_ZSET, userId);
		if(score!=null){
			cacheRedis.opsForZSet().add(RedisKey.KEY_PLAYER_HOT_USER_SORT_ZSET, userId, score);
		}
	}
	
	/**
	 * @date : 2017年2月13日 上午11:25:06
	 * @author : Iris.Xiao
	 * @param userId
	 * @return
	 * @description : 是否已经禁用
	*/
	public boolean disabledFlag(String userId){
		return cacheRedis.opsForSet().isMember(RedisKey.KEY_PLAYER_HOT_USER_DISABLED_SET, userId);
	}
	
	
	/**
	 * @date : 2017年2月13日 上午11:25:06
	 * @author : Iris.Xiao
	 * @param userId
	 * @return
	 * @description : 得到所有禁用的
	*/
	public Set<String> getAll(){
		return cacheRedis.opsForSet().members(RedisKey.KEY_PLAYER_HOT_USER_DISABLED_SET);
	}
}
