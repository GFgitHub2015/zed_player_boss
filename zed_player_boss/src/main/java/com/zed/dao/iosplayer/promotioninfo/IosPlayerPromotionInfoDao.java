package com.zed.dao.iosplayer.promotioninfo;

import java.io.Serializable;
import java.util.List;

import com.zed.dao.PageDao;
import com.zed.domain.iosplayer.promotioninfo.IosPlayerPromotionInfo;
import com.zed.exception.AppValidationException;

/**
 * @date : 2017年5月18日 上午10:10:02
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 
*/
public interface IosPlayerPromotionInfoDao<T extends Serializable> extends PageDao<IosPlayerPromotionInfo> {

	public List<IosPlayerPromotionInfo> findAll() throws AppValidationException;
	
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
	public IosPlayerPromotionInfo findTopByCountryCode(String countryCode) throws AppValidationException;


}
