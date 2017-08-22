package com.zed.service.player.promotioninfo.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.zed.common.ConstantType;
import com.zed.common.util.CommUtil;
import com.zed.common.util.StringUtil;
import com.zed.dao.player.promotioninfo.PlayerPromotionInfoDao;
import com.zed.domain.player.playerpromotioninfo.PlayerPromotionInfo;
import com.zed.exception.AppValidationException;
import com.zed.redis.player.promotioninfo.PlayerPromotionInfoRedisDao;
import com.zed.service.player.promotioninfo.PlayerPromotionInfoService;
import com.zed.system.page.Page;

@Service("playerPromotionInfoService")
public class PlayerPromotionInfoServiceImpl implements PlayerPromotionInfoService {
	
	@Resource(name="playerPromotionInfoDao")
	private PlayerPromotionInfoDao playerPromotionInfoDao;
	
	@Resource(name="playerPromotionInfoRedisDao")
	private PlayerPromotionInfoRedisDao playerPromotionInfoRedisDao;

	@Override
	public PlayerPromotionInfo findById(String playerPromotionInfoId) throws AppValidationException {
		return (PlayerPromotionInfo) playerPromotionInfoDao.findById(playerPromotionInfoId);
	}

	@Override
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException {
		return playerPromotionInfoDao.findByPage(page);
	}

	@Override
	public void add(PlayerPromotionInfo playerPromotionInfo) throws AppValidationException {
		if(null != playerPromotionInfo) {
			if (null == playerPromotionInfo.getCreateTime()) {
				playerPromotionInfo.setCreateTime(new Date());
			}
			String topFlagIds="";
			//如果是需要置顶,则改变现有置顶的结果
			if(playerPromotionInfo.getTopFlag()==1){
				//再查询是否有置顶记录
				Page<HashMap> topFlagPage = new Page<HashMap>();
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("topFlag", "1");
				param.put("countryCode", playerPromotionInfo.getCountryCode());
				topFlagPage.setParamsMap(param);
				//查询是否有置顶记录
				findByPage(topFlagPage);
				List<HashMap> topFlagList = topFlagPage.getResult();
				if(topFlagList!=null&&topFlagList.size()>0){
					for (HashMap hashMap : topFlagList) {
						topFlagIds+=hashMap.get("id").toString()+",";
					}
					//把其它修改为不置顶
					if(!"".equals(topFlagIds)){
						updateTopFlag(null, topFlagIds, 1);
					}
				}
			}
			playerPromotionInfoDao.add(playerPromotionInfo);
//			updateCache(new Date(), playerPromotionInfo);
			//刷新热门推荐列表缓存
			updateVideoPmtCache(playerPromotionInfo);
		}
	}

	@Override
	public void update(PlayerPromotionInfo playerPromotionInfo) throws AppValidationException {

		String [] idToDelCache = new String[]{playerPromotionInfo.getId()};
		this.deleteVideoPmtCache(idToDelCache);
		playerPromotionInfoDao.update(playerPromotionInfo);
		
		String topFlagIds="";
		//如果是需要置顶,则改变现有置顶的结果
		if(playerPromotionInfo.getTopFlag()==1){
			//再查询是否有置顶记录
			Page<HashMap> topFlagPage = new Page<HashMap>();
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("topFlag", "1");
			param.put("countryCode", playerPromotionInfo.getCountryCode());
			topFlagPage.setParamsMap(param);
			//查询是否有置顶记录
			findByPage(topFlagPage);
			List<HashMap> topFlagList = topFlagPage.getResult();
			if(topFlagList!=null&&topFlagList.size()>0){
				for (HashMap hashMap : topFlagList) {
					if(!playerPromotionInfo.getId().equals(hashMap.get("id").toString())){
						topFlagIds+=hashMap.get("id").toString()+",";
					}
				}
				//把其它修改为不置顶
				if(!"".equals(topFlagIds)){
					updateTopFlag(null, topFlagIds, 1);
				}
			}
		}
		//刷新热门推荐列表缓存
		updateVideoPmtCache(playerPromotionInfo);
	}

	@Override
	public void delete(String[] playerPromotionInfoIds) throws AppValidationException {
		//刷新热门推荐列表缓存
		deleteVideoPmtCache(playerPromotionInfoIds);
		playerPromotionInfoDao.delete(playerPromotionInfoIds);
	}

	@Override
	public void updateStatus(PlayerPromotionInfo playerPromotionInfo) throws AppValidationException {
		playerPromotionInfoDao.update(playerPromotionInfo);	
		//刷新热门推荐列表缓存
		updateVideoPmtCache(playerPromotionInfo);
	}


	/**
	 * @date : 2016年12月30日 上午11:32:03
	 * @author : Iris.Xiao
	 * @param playerPromotionInfo
	 * @description : 刷新热门推荐列表数据
	*/
	private void updateVideoPmtCache(PlayerPromotionInfo playerPromotionInfo) {
		playerPromotionInfoRedisDao.deletePromotionInfoNew(playerPromotionInfo.getId());
		if (playerPromotionInfo.getStatus()==ConstantType.CommonType.START.getStatus()) {
			playerPromotionInfoRedisDao.addPromotionInfoNew(playerPromotionInfo);
		}
		
		//先直接删除推荐列表数据,避免活动从原来的热门活动变为普通活动,导致之后刷新缓存不能执行
		playerPromotionInfoRedisDao.deleteHotVideoAndPmtList(playerPromotionInfo);
		//再判断是否要对热门推荐列表数据进行刷新
		Long promoType = playerPromotionInfo.getPromoType();
		if(promoType!=null&&1==promoType){//热门推荐
			Date nowDate = new Date();
			//要在上架时间范围内,且不是禁用
			if ( playerPromotionInfo.getDownTime().after(nowDate)
					&&playerPromotionInfo.getStatus()==ConstantType.CommonType.START.getStatus()) {
				playerPromotionInfoRedisDao.addHotVideoAndPmtList(playerPromotionInfo);
			}
		}
		//置顶的存一份
		playerPromotionInfoRedisDao.deletePmtTop(playerPromotionInfo);
		if(playerPromotionInfo.getTopFlag()==1&&playerPromotionInfo.getStatus()==ConstantType.CommonType.START.getStatus()){
			playerPromotionInfoRedisDao.addPmtTop(playerPromotionInfo);
		}
	}

	/**
	 * @date : 2016年12月30日 上午11:39:05
	 * @author : Iris.Xiao
	 * @param playerPromotionInfoIds
	 * @description : 删除热门推荐列表数据
	*/
	private void deleteVideoPmtCache(String[] playerPromotionInfoIds) {
		for (String infoid : playerPromotionInfoIds) {
			playerPromotionInfoRedisDao.deletePromotionInfoNew(infoid);
			PlayerPromotionInfo playerPromotionInfo = this.findById(infoid);
			if (playerPromotionInfo != null) {
				playerPromotionInfoRedisDao.deleteHotVideoAndPmtList(playerPromotionInfo);
				if (playerPromotionInfo.getTopFlag()==1l) {
					playerPromotionInfoRedisDao.deletePmtTop(playerPromotionInfo);
				}
			}
		}
	}
	
	@Override
	public List<PlayerPromotionInfo> findAll() throws AppValidationException {
		return playerPromotionInfoDao.findAll();
	}


	/**
	 * @date : 2016年12月29日 下午8:15:08
	 * @author : Iris.Xiao
	 * @param infoId 要修改的记录
	 * @param topFlagIds 已经置顶的记录
	 * @param topFlag 
	 * @throws AppValidationException
	 * @description : 修改置顶状态
	*/
	public void updateTopFlag(String infoId,String topFlagIds,int topFlag) throws AppValidationException{
		//如果是1则是要置顶,先更新其它为不置顶,再置顶
		if(!CommUtil.isEmpty(topFlagIds)&&!",".equals(topFlagIds)){
			String[] arrIds = topFlagIds.split(",");
			for (String id : arrIds) {
				if(!CommUtil.isEmpty(id)){
					PlayerPromotionInfo pmt = this.findById(id);
					pmt.setTopFlag(Long.parseLong(String.valueOf(0L)));
					this.update(pmt);
					//刷新热门推荐列表缓存
					updateVideoPmtCache(pmt);
				}
			}
		}
		if(!StringUtil.isEmpty(infoId)){
			PlayerPromotionInfo pmt = this.findById(infoId);
			pmt.setTopFlag(Long.parseLong(String.valueOf(topFlag)));
			this.update(pmt);
			//刷新热门推荐列表缓存
			updateVideoPmtCache(pmt);
		}
	}
	

	/**
	 * @date : 2017年1月17日 下午3:42:47
	 * @author : Iris.Xiao
	 * @param srcUrl
	 * @param cdnUrl
	 * @return
	 * @description : 替换为cdn加速地址
	*/
	public String replaceCdnUrl(String srcUrl,String cdnUrl){
		if(CommUtil.isEmpty(srcUrl)){
			return null;
		}
		if(CommUtil.isEmpty(cdnUrl)){
			return srcUrl;
		}
		srcUrl = srcUrl.substring(srcUrl.lastIndexOf("/")+1,srcUrl.length());
		if(!cdnUrl.endsWith("/")){
			cdnUrl =cdnUrl+"/";
		}
		return cdnUrl+srcUrl;
	}

	@Override
	public PlayerPromotionInfo findTopByCountryCode(String countryCode) throws AppValidationException {
		PlayerPromotionInfo ppi = null;
		Set<String> topId = playerPromotionInfoRedisDao.getPmtTop(countryCode);
		if (topId == null||topId.isEmpty()) {
			ppi = playerPromotionInfoDao.findTopByCountryCode(countryCode);
			if (ppi != null) {
				playerPromotionInfoRedisDao.addPmtTop(ppi);
			}
		}else{
			Iterator<String> ids = topId.iterator();
			int size = topId.size();
			int index = 0;
			while(ids.hasNext()&&index<size){
				String id = ids.next();
				if (StringUtils.isNotBlank(id)) {
					ppi = this.findById(id);
					break;
				}
				index++;
			}
		}
		return ppi;
	}

	@Override
	public List<PlayerPromotionInfo> findById(String[] playerPromotionInfoId) throws AppValidationException {
		List<PlayerPromotionInfo> result = new ArrayList<PlayerPromotionInfo>();
		for (String id : playerPromotionInfoId) {
			PlayerPromotionInfo ppi = this.findById(id);
			if (ppi != null) {
				result.add(ppi);
			}
		}
		return result;
	}

	@Override
	public void updateStatus(List<PlayerPromotionInfo> playerPromotionInfoList) throws AppValidationException {
		for (PlayerPromotionInfo playerPromotionInfo : playerPromotionInfoList) {
			this.updateStatus(playerPromotionInfo);
		}
	}
}
