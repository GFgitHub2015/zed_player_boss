package com.zed.redis.player.promotioninfo.impl;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.zed.common.ConstantType.HotVideoPmtKeyType;
import com.zed.common.ConstantType.HotVideoPmtResultType;
import com.zed.common.util.DateUtil;
import com.zed.common.util.EntityUtils;
import com.zed.common.util.JsonUtils;
import com.zed.domain.player.playerpromotioninfo.PlayerPromotionInfo;
import com.zed.redis.player.promotioninfo.PlayerPromotionInfoRedisDao;
import com.zed.util.RedisKey;

@Repository("playerPromotionInfoRedisDao")
public class PlayerPromotionInfoRedisDaoImpl implements PlayerPromotionInfoRedisDao {
	@Resource(name="cacheRedis")
	private RedisTemplate<String,String> cacheRedis;
/*	
	@Override
	public void addPlayerPromotionInfo(PlayerPromotionInfo playerPromotionInfo) {
		cacheRedis.opsForValue().set("ply:promotioninfo:h", playerPromotionInfo.toJson());
	}

	@Override
	public void deletePlayerPromotionInfo() {
		cacheRedis.delete("ply:promotioninfo:h");		
	}
	*/
	/**
	 * @date : 2016年12月30日 上午11:41:47
	 * @author : Iris.Xiao
	 * @description : 和addPlayerPromotionInfo所有区别,addPlayerPromotionInfo只保存一份数据,现在的是保存所有数据
	*/
	public void addPromotionInfoNew(PlayerPromotionInfo playerPromotionInfo){
		String key = String.format(RedisKey.KEY_PLAYER_PROMOTION_HASH, playerPromotionInfo.getId());
		Map<String,String > map = EntityUtils.objectToHash(playerPromotionInfo);
		cacheRedis.opsForHash().putAll(key, map);
	}

	/**
	 * @date : 2016年12月30日 上午11:41:47
	 * @author : Iris.Xiao
	 * @description : 删除缓存
	*/
	public void deletePromotionInfoNew(String uid){
		String key = String.format(RedisKey.KEY_PLAYER_PROMOTION_HASH, uid);
		cacheRedis.delete(key);
	}

	/**
	 * @date : 2016年12月28日 下午4:49:37
	 * @author : Iris.Xiao
	 * @param uid
	 * @description : 添加到热门列表和活动的排序结果中
	*/
	public void addHotVideoAndPmtList(final PlayerPromotionInfo playerPromotionInfo){
		
		cacheRedis.execute(new RedisCallback<Object>(){

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection conn =(StringRedisConnection) connection;
				String uid = playerPromotionInfo.getId();
				double startTime = playerPromotionInfo.getUpTime().getTime();
				String zkey = String.format(RedisKey.KEY_PLAYER_HOT_VIDEO_PROMOTION_LOCAL_ZSET, playerPromotionInfo.getCountryCode());
				String dataStr = getVideoAndPmtValue(uid);
				conn.zAdd(zkey, startTime, dataStr);
				//设置过期时间的key
				String ekey = String.format(RedisKey.KEY_PLAYER_HOT_VIDEO_PMT_EXPIRE_HASH,  uid);
				conn.set(ekey, dataStr);
				//再设置过期时间
				long time = playerPromotionInfo.getDownTime().getTime()-DateUtil.getCurTime().getTime();
				conn.expire(ekey, time/1000);
//				conn.expireAt(ekey, playerPromotionInfo.getDownTime().getTime());
//				cacheRedis.expire(ekey, time, TimeUnit.MILLISECONDS);
				return null;
			}
		});
		
	}
	
	
	/**
	 * @date : 2016年12月28日 下午4:49:37
	 * @author : Iris.Xiao
	 * @param uid
	 * @description :删除热门列表和活动的排序结果
	*/
	public void deleteHotVideoAndPmtList(final PlayerPromotionInfo playerPromotionInfo){
		cacheRedis.execute(new RedisCallback<Object>(){

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection conn =(StringRedisConnection) connection;
				String zkey = String.format(RedisKey.KEY_PLAYER_HOT_VIDEO_PROMOTION_LOCAL_ZSET, playerPromotionInfo.getCountryCode());
				String dataStr = getVideoAndPmtValue(playerPromotionInfo.getId());
				conn.zRem(zkey, dataStr);
				String ekey = String.format(RedisKey.KEY_PLAYER_HOT_VIDEO_PMT_EXPIRE_HASH,  playerPromotionInfo.getId());
				conn.del(ekey);
				return null;
			}
		});
	}
	

	/**
	 * @date : 2016年12月28日 下午4:49:37
	 * @author : Iris.Xiao
	 * @param uid
	 * @description : 置顶
	*/
	public void addPmtTop(PlayerPromotionInfo playerPromotionInfo){
		String uid = playerPromotionInfo.getId();
//		String zkey = RedisKey.KEY_PLAYER_PROMOTION_TOP_SET;
		String zkey = String.format(RedisKey.KEY_PLAYER_PROMOTION_TOP_LOCAL_SET,playerPromotionInfo.getCountryCode());
		cacheRedis.opsForSet().add(zkey, uid);
	}
	
	
	/**
	 * @date : 2016年12月28日 下午4:49:37
	 * @author : Iris.Xiao
	 * @param uid
	 * @description :删除置顶
	*/
	public void deletePmtTop(PlayerPromotionInfo playerPromotionInfo){
//		String zkey = RedisKey.KEY_PLAYER_PROMOTION_TOP_SET;
		String zkey = String.format(RedisKey.KEY_PLAYER_PROMOTION_TOP_LOCAL_SET,playerPromotionInfo.getCountryCode());
		cacheRedis.opsForSet().remove(zkey, playerPromotionInfo.getId());
	}
	
	/**
	 * @date : 2016年12月28日 下午4:49:37
	 * @author : Iris.Xiao
	 * @param uid
	 * @description : 得到置顶
	*/
	public Set<String> getPmtTop(String countryCode){
		String zkey = String.format(RedisKey.KEY_PLAYER_PROMOTION_TOP_LOCAL_SET,countryCode);
//		String zkey = RedisKey.KEY_PLAYER_PROMOTION_TOP_SET;
		return cacheRedis.opsForSet().members(zkey);
	}
	
	
	/**
	 * @date : 2017年2月15日 下午6:40:49
	 * @author : Iris.Xiao
	 * @param uid
	 * @return
	 * @description : 统一得到排序的值,类似"{\"dataType\":\"hotVideo\",\"dataUid\":\"6a4fad0115e945e9ada1e5a0f0218c8c\"}",
	 * 之前用hashmap,datatype和datauid顺序可能会乱
	*/
	public String getVideoAndPmtValue(String uid){
		Map<String,Object> data = new LinkedHashMap<String,Object>();
		data.put(HotVideoPmtKeyType.DATA_TYPE.getValue(), HotVideoPmtResultType.PROMOTION.getValue());
		data.put(HotVideoPmtKeyType.DATA_UID.getValue(), uid);
		String dataStr = JsonUtils.getJsonStrByMap(data);
		return dataStr;
	}
}
