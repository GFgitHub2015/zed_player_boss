package com.zed.redis.player.promotioninfo;

import java.util.Set;

import com.zed.common.util.redis.RedisKey;
import com.zed.domain.player.playerpromotioninfo.PlayerPromotionInfo;

public interface PlayerPromotionInfoRedisDao {
	
/*	public void addPlayerPromotionInfo(PlayerPromotionInfo playerPromotionInfo);
	
	public void deletePlayerPromotionInfo();*/
	
	/**
	 * @date : 2016年12月30日 上午11:41:47
	 * @author : Iris.Xiao
	 * @description : 和addPlayerPromotionInfo所有区别,addPlayerPromotionInfo只保存一份数据,现在的是保存所有数据
	*/
	public void addPromotionInfoNew(PlayerPromotionInfo playerPromotionInfo);

	/**
	 * @date : 2016年12月30日 上午11:41:47
	 * @author : Iris.Xiao
	 * @description : 删除缓存
	*/
	public void deletePromotionInfoNew(String uid);
	
	/**
	 * @date : 2016年12月28日 下午4:49:37
	 * @author : Iris.Xiao
	 * @param uid
	 * @description : 添加到热门列表和活动的排序结果中
	*/
	public void addHotVideoAndPmtList(PlayerPromotionInfo playerPromotionInfo);
	
	/**
	 * @date : 2016年12月28日 下午4:49:37
	 * @author : Iris.Xiao
	 * @param uid
	 * @description :删除热门列表和活动的排序结果
	*/
	public void deleteHotVideoAndPmtList(PlayerPromotionInfo playerPromotionInfo);
	
	/**
	 * @date : 2016年12月28日 下午4:49:37
	 * @author : Iris.Xiao
	 * @param uid
	 * @description : 置顶
	*/
	public void addPmtTop(PlayerPromotionInfo playerPromotionInfo);
	
	/**
	 * @date : 2016年12月28日 下午4:49:37
	 * @author : Iris.Xiao
	 * @param uid
	 * @description :删除置顶
	*/
	public void deletePmtTop(PlayerPromotionInfo playerPromotionInfo);
	
	/**
	 * @date : 2016年12月28日 下午4:49:37
	 * @author : Iris.Xiao
	 * @param countryCode 国家码
	 * @description : 得到置顶
	*/
	public  Set<String> getPmtTop(String countryCode);
	
}
