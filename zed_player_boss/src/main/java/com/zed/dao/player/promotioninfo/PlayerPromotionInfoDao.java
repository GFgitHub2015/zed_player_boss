package com.zed.dao.player.promotioninfo;

import java.io.Serializable;
import java.util.List;

import com.zed.dao.PageDao;
import com.zed.domain.player.playerpromotioninfo.PlayerPromotionInfo;
import com.zed.exception.AppValidationException;

public interface PlayerPromotionInfoDao<T extends Serializable> extends PageDao<PlayerPromotionInfo> {

	public List<PlayerPromotionInfo> findAll() throws AppValidationException;
	
	/**
	 * @date : 2016年12月29日 下午7:06:20
	 * @author : Iris.Xiao
	 * @param id
	 * @param topFlag
	 * @throws AppValidationException
	 * @description : 修改置顶状态
	*/
	public void updateTopFlag(String id,int topFlag) throws AppValidationException;
	
	/**
	 * @date : 2016年12月29日 下午7:35:53
	 * @author : Iris.Xiao
	 * @description : 是否有置顶的记录
	*/
	public int hasTopFlag() throws AppValidationException;
	/**
	 * 根据国家码获取置顶记录
	 * @param countryCode
	 * @return
	 * @throws AppValidationException
	 */
	public PlayerPromotionInfo findTopByCountryCode(String countryCode) throws AppValidationException;
}
