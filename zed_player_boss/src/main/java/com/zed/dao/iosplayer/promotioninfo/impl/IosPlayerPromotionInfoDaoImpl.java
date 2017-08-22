package com.zed.dao.iosplayer.promotioninfo.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zed.common.util.CommUtil;
import com.zed.dao.iosplayer.promotioninfo.IosPlayerPromotionInfoDao;
import com.zed.dao.player.base.abstractdao.AbstractPlayerPageDao;
import com.zed.domain.iosplayer.promotioninfo.IosPlayerPromotionInfo;
import com.zed.exception.AppValidationException;

/**
 * @date : 2017年5月18日 上午10:14:32
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 
*/
@Repository("iosPlayerPromotionInfoDao")
public class IosPlayerPromotionInfoDaoImpl <T>  extends AbstractPlayerPageDao<IosPlayerPromotionInfo> implements IosPlayerPromotionInfoDao<IosPlayerPromotionInfo> {

	@Override
	public List<IosPlayerPromotionInfo> findAll() throws AppValidationException  {
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
	public IosPlayerPromotionInfo findTopByCountryCode(String countryCode) throws AppValidationException {
		return find("findTopByCountryCode", countryCode);
	}
}
