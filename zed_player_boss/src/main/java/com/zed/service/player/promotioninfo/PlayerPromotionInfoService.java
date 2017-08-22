package com.zed.service.player.promotioninfo;

import java.util.HashMap;
import java.util.List;

import com.zed.domain.player.playerpromotioninfo.PlayerPromotionInfo;
import com.zed.exception.AppValidationException;
import com.zed.system.page.Page;

public interface PlayerPromotionInfoService {
	
	public PlayerPromotionInfo findById(String playerPromotionInfoId) throws AppValidationException;
	
	public List<PlayerPromotionInfo> findById(String []playerPromotionInfoId) throws AppValidationException;

	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException;

	public void add(PlayerPromotionInfo playerPromotionInfo) throws AppValidationException;
	
	public void update(PlayerPromotionInfo playerPromotionInfo) throws AppValidationException;
	
	public void delete(String[] playerPromotionInfoIds) throws AppValidationException;
	//修改状态
	public void updateStatus(PlayerPromotionInfo playerPromotionInfo)  throws AppValidationException;
	//批量修改状态
	public void updateStatus(List<PlayerPromotionInfo> playerPromotionInfoList)  throws AppValidationException;
	
//	public void updateJob() throws AppValidationException;
	
	public List<PlayerPromotionInfo> findAll() throws AppValidationException;


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
	
	
	public PlayerPromotionInfo findTopByCountryCode(String countryCode) throws AppValidationException;
}
