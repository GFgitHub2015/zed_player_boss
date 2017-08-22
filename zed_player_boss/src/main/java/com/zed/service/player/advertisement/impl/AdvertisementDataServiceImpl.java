package com.zed.service.player.advertisement.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zed.common.util.CommUtil;
import com.zed.dao.player.advertisement.AdvertisementDataDao;
import com.zed.domain.player.advertisement.AdvertisementData;
import com.zed.domain.player.sysparam.SysParam;
import com.zed.service.player.advertisement.AdvertisementDataService;
import com.zed.service.player.sysparam.ISysParamService;
import com.zed.system.page.Page;

/**
 * @date : 2017年6月6日 下午4:00:35
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 
*/
@Service("advertisementDataService")
public class AdvertisementDataServiceImpl implements AdvertisementDataService{
	@Resource(name="advertisementDataDao")
	private AdvertisementDataDao<AdvertisementData> advertisementDataDao;
	@Resource(name="sysParamService")
	private ISysParamService sysParamService;
	

	/**
	 * @date : 2017年6月6日 下午4:24:44
	 * @author : Iris.Xiao
	 * @param page
	 * @description : 以平台为维度,统计各个app数据
	*/
	public void listPlatformStat(Page<AdvertisementData> page){
		advertisementDataDao.listPlatformStat(page);
	}
	
	/**
	 * @date : 2017年6月6日 下午4:30:55
	 * @author : Iris.Xiao
	 * @param page
	 * @description : 以app为维度统计各平台的数据
	*/
	public void listAppStat(Page<AdvertisementData> page){
		advertisementDataDao.listAppStat(page);
	}
	
	/**
	 * @date : 2017年6月8日 上午9:38:15
	 * @author : Iris.Xiao
	 * @param page
	 * @return
	 * @description : 查询总和
	*/
	public AdvertisementData listStatTotal(Page<AdvertisementData> page){
		return advertisementDataDao.listStatTotal(page);
	}
	
	
	/**
	 * @date : 2017年6月8日 上午10:12:42
	 * @author : Iris.Xiao
	 * @param params
	 * @return
	 * @description : 根据appname查找渠道
	*/
	public List<String> getChannelByAppname(String appname){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("appname", appname);
		List<AdvertisementData> list = advertisementDataDao.getChannelByAppname(params);
		List<String> result = new ArrayList<String>();
		if(list!=null&&list.size()>0){
			for (AdvertisementData advertisementData : list) {
				result.add(advertisementData.getSiteId());
			}
		}
		return result;
	}
	
	/**
	 * @date : 2017年6月8日 下午2:31:19
	 * @author : Iris.Xiao
	 * @param channel
	 * @param scale
	 * @param activeCountScale 
	 * @param ecpmMax 
	 * @param ecpmMin 
	 * @description : 修改app的比例
	*/
	public void updateAppScale(String channel, Double earningsScale, Double ecpmMin, Double ecpmMax, Double activeCountScale,String userId){
		SysParam sysParamApp = sysParamService.getSysParam("appname", channel);
		String paramType = "";
		if(sysParamApp!=null){
			paramType = sysParamApp.getParamType();
			if(CommUtil.isEmpty(paramType)){
				paramType = channel;
			}
		}
		updateSingleParam(earningsScale, "earningsScale", channel, userId, paramType, "收入比例");
		updateSingleParam(ecpmMin, "ecpmMin", channel, userId, paramType, "ecpm最小值");
		updateSingleParam(ecpmMax, "ecpmMax", channel, userId, paramType, "ecpm最大值");
		updateSingleParam(activeCountScale, "activeCountScale", channel, userId, paramType, "活跃用户比例");
	}
	
	/**
	 * @date : 2017年6月15日 上午11:18:44
	 * @author : Iris.Xiao
	 * @param paramValue
	 * @param paramName
	 * @param channel
	 * @param userId
	 * @param paramType
	 * @param desc
	 * @description : 修改参数
	*/
	public void updateSingleParam(Double paramValue,String paramName,String channel,String userId,String paramType,String desc ){
		if(paramValue!=null){
			SysParam sysParam = sysParamService.getSysParam(paramName, channel);
			if(sysParam==null){
				sysParam = new SysParam();
				sysParam.setCountryCode(channel);
				sysParam.setCreateUser(userId);
				sysParam.setParamDec(desc);
				sysParam.setParamId(CommUtil.getUUID());
				sysParam.setParamName(paramName);
				sysParam.setParamValue(String.valueOf(paramValue));
				sysParam.setUpdateUser(userId);
				sysParam.setUpdateTime(sysParam.getCreateTime());
				sysParam.setParamType(paramType);
				sysParamService.addSysParam(sysParam);
			}else{
				sysParam.setParamValue(String.valueOf(paramValue));
				sysParamService.updateSysParam(sysParam);
			}
		}
	}

	/**
	 * @date : 2017年6月8日 下午6:29:34
	 * @author : Iris.Xiao
	 * @return
	 * @description : 查找所有所有app的channel和名字信息
	*/
	public List<Map<String,String>> getAllAppInfo(){
		Map<String,Object> params = new HashMap<String,Object>();
		List<AdvertisementData> list = advertisementDataDao.getChannelByAppname(params);
		List<Map<String,String>> result = new ArrayList<Map<String,String>>();
		if(list!=null&&list.size()>0){
			for (AdvertisementData ad : list) {
				Map<String,String> appinfo = new HashMap<String,String>();
				appinfo.put("channel", ad.getSiteId());
				appinfo.put("appname", ad.getSiteName());
				result.add(appinfo);
			}
		}
		return result;
	}


	/**
	 * @date : 2017年6月8日 下午6:28:53
	 * @author : Iris.Xiao
	 * @return
	 * @description : 查找所有平台名
	*/
	public List<String> getAllPlatForm(){
		List<AdvertisementData> list = advertisementDataDao.getAllPlatForm();
		List<String> result = new ArrayList< String>();
		if(list!=null&&list.size()>0){
			for (AdvertisementData ad : list) {
				result.add(ad.getAdPlatform());
			}
		}
		return result;
	}
	
	/**
	 * @date : 2017年6月8日 下午7:08:17
	 * @author : Iris.Xiao
	 * @param params
	 * @return
	 * @description : 日期为维度得到统计数据
	*/
	public List<AdvertisementData> getDateStat(Map<String,Object> params){
		List<AdvertisementData> list = advertisementDataDao.getDateStat(params);
		return list;
	}
	

	/**
	 * @date : 2017年6月13日 下午4:23:48
	 * @author : Iris.Xiao
	 * @param page
	 * @description : app广告位详情
	*/
	public List<AdvertisementData> listAppSiteDetail(Map<String,Object> params){
		return advertisementDataDao.listAppSiteDetail(params);
	}
	
	/**
	 * @date : 2017年6月13日 下午4:23:48
	 * @author : Iris.Xiao
	 * @param page
	 * @description : app广告位详情
	*/
	public List<AdvertisementData> listAppSiteTotal(Map<String,Object> params){
		return advertisementDataDao.listAppSiteTotal(params);
	}

	/**
	 * @date : 2017年6月15日 上午9:55:58
	 * @author : Iris.Xiao
	 * @param channel
	 * @return
	 * @description : 得到相关比例
	*/
	public Map<String, String> getAppScales(String channel){

		Map<String, String> params = sysParamService.getParamsByChannel(channel);
		Map<String, String> result = new HashMap<String, String>();
		List<String> scalesList = new ArrayList<String>();
		scalesList.add("earningsScale");
		scalesList.add("activeCountScale");
		scalesList.add("ecpmMin");
		scalesList.add("ecpmMax");
		result.put("earningsScale", params.get("earningsScale")==null?"1":params.get("earningsScale"));
		result.put("activeCountScale", params.get("activeCountScale")==null?"1":params.get("activeCountScale"));
		result.put("ecpmMin", params.get("ecpmMin")==null?"0.6":params.get("ecpmMin"));
		result.put("ecpmMax", params.get("ecpmMax")==null?"1.2":params.get("ecpmMax"));
		return result;
	}
	

	/**
	 * @date : 2017年6月15日 下午3:15:15
	 * @author : Iris.Xiao
	 * @param page
	 * @description : 广告位列表
	*/
	public void listSites(Page<AdvertisementData> page){
		advertisementDataDao.listSites(page);
	}
	

	/**
	 * @date : 2017年6月15日 下午4:41:15
	 * @author : Iris.Xiao
	 * @param uid
	 * @param siteName
	 * @description : 修改广告位名称
	*/
	public void updateSiteName(String siteId,String uid, String siteName){
		AdvertisementData site = advertisementDataDao.getSiteTypeName(siteName);
		if(site==null){
			site = new AdvertisementData();
			site.setUid(CommUtil.getUUID());
			site.setSiteTypeName(siteName);
			advertisementDataDao.insertSiteTypeName(site);
		}
		advertisementDataDao.updateSiteName(siteId,uid,site.getUid(), null);//null先不更新siteName
	}
	
	/**
	 * @date : 2017年6月15日 下午4:41:15
	 * @author : Iris.Xiao
	 * @param uid
	 * @param channel
	 * @description : 修改广告位渠道
	*/
	public void updateSiteChannel(String uid, String channel){
		advertisementDataDao.updateSiteChannel(uid,channel);
	}
}
