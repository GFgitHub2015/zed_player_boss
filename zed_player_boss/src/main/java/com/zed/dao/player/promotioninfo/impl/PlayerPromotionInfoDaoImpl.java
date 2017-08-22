package com.zed.dao.player.promotioninfo.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zed.common.util.CommUtil;
import com.zed.dao.player.base.abstractdao.AbstractPlayerPageDao;
import com.zed.dao.player.promotioninfo.PlayerPromotionInfoDao;
import com.zed.domain.player.playerpromotioninfo.PlayerPromotionInfo;
import com.zed.exception.AppValidationException;

@Repository("playerPromotionInfoDao")
public class PlayerPromotionInfoDaoImpl<T>  extends AbstractPlayerPageDao<PlayerPromotionInfo> implements PlayerPromotionInfoDao<PlayerPromotionInfo> {

	@Override
	public List<PlayerPromotionInfo> findAll() throws AppValidationException  {
		return findList("findAll");
	}

	/**
	 * @date : 2016年12月29日 下午7:06:20
	 * @author : Iris.Xiao
	 * @param id
	 * @param topFlag
	 * @throws AppValidationException
	 * @description : 修改置顶状态
	*/
	public void updateTopFlag(String id,int topFlag) throws AppValidationException {
		Map<String,Object> param = new HashMap<String,Object>();
		if(!CommUtil.isEmpty(id)){
			param.put("id", id);
		}
		param.put("topFlag", topFlag);
		getSqlSessionTemplate().update("updateTopFlag", param);
	}
	
	/**
	 * @date : 2016年12月29日 下午7:35:53
	 * @author : Iris.Xiao
	 * @description : 是否有置顶的记录
	*/
	public int hasTopFlag() throws AppValidationException {
		Object obj= this.findOne("hasTopFlag");
		if(obj==null){
			return 0;
		}else{
			return Integer.parseInt(obj.toString());
		}
	}

	@Override
	public PlayerPromotionInfo findTopByCountryCode(String countryCode) throws AppValidationException {
		return find("findTopByCountryCode", countryCode);
	}
}
