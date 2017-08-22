package com.zed.service.iosplayer.promotioninfo;

import java.util.HashMap;
import java.util.List;

import com.zed.domain.iosplayer.promotioninfo.IosPlayerPromotionInfo;
import com.zed.exception.AppValidationException;
import com.zed.system.page.Page;

/**
 * @date : 2017年5月18日 上午10:17:13
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 
*/
public interface IosPlayerPromotionInfoService {
	
	public IosPlayerPromotionInfo findById(String playerPromotionInfoId) throws AppValidationException;
	
	public List<IosPlayerPromotionInfo> findById(String []playerPromotionInfoId) throws AppValidationException;

	public Page<IosPlayerPromotionInfo> findByPage(Page<IosPlayerPromotionInfo> page) throws AppValidationException;

	public void add(IosPlayerPromotionInfo playerPromotionInfo) throws AppValidationException;
	
	public void update(IosPlayerPromotionInfo playerPromotionInfo) throws AppValidationException;
	
	public void delete(String[] playerPromotionInfoIds) throws AppValidationException;
	//修改状态
	public void updateStatus(IosPlayerPromotionInfo playerPromotionInfo)  throws AppValidationException;
	//批量修改状态
	public void updateStatus(List<IosPlayerPromotionInfo> playerPromotionInfoList)  throws AppValidationException;
	
//	public void updateJob() throws AppValidationException;
	
	public List<IosPlayerPromotionInfo> findAll() throws AppValidationException;


	/**
	 * @date : 2016年12月29日 下午8:15:08
	 * @author : Iris.Xiao
	 * @param infoId 要修改的记录
	 * @param topFlagIds 已经置顶的记录
	 * @param topFlag 
	 * @throws AppValidationException
	 * @description : 修改置顶状态
	*/
	public void updateTopFlag(String infoId,String topFlagIds,int topFlag) throws AppValidationException;

	/**
	 * @date : 2017年1月17日 下午3:42:47
	 * @author : Iris.Xiao
	 * @param srcUrl
	 * @param cdnUrl
	 * @return
	 * @description : 替换为cdn加速地址
	*/
	public String replaceCdnUrl(String srcUrl,String cdnUrl);
	
	
	public IosPlayerPromotionInfo findTopByCountryCode(String countryCode) throws AppValidationException;
	
	
	/**
	 * @date : 2017年5月22日 下午2:45:43
	 * @author : Iris.Xiao
	 * @param countryCode
	 * @description : 删除缓存
	*/
	public void deletePageList(String countryCode);
}
