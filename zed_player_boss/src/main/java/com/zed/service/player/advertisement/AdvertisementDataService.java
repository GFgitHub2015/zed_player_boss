package com.zed.service.player.advertisement;

import java.util.List;
import java.util.Map;

import com.zed.domain.player.advertisement.AdvertisementData;
import com.zed.system.page.Page;

/**
 * @date : 2017年6月6日 下午4:00:11
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 
*/
public interface AdvertisementDataService {

	/**
	 * @date : 2017年6月6日 下午4:24:44
	 * @author : Iris.Xiao
	 * @param page
	 * @description : 以平台为维度,统计各个app数据
	*/
	void listPlatformStat(Page<AdvertisementData> page);
	
	
	/**
	 * @date : 2017年6月6日 下午4:30:55
	 * @author : Iris.Xiao
	 * @param page
	 * @description : 以app为维度统计各平台的数据
	*/
	void listAppStat(Page<AdvertisementData> page);
	
	/**
	 * @date : 2017年6月8日 上午9:38:15
	 * @author : Iris.Xiao
	 * @param page
	 * @return
	 * @description : 查询总和
	*/
	public AdvertisementData listStatTotal(Page<AdvertisementData> page);
	
	
	/**
	 * @date : 2017年6月8日 上午10:12:42
	 * @author : Iris.Xiao
	 * @param params
	 * @return
	 * @description : 根据appname查找渠道
	*/
	public List<String> getChannelByAppname(String appname);

	/**
	 * @date : 2017年6月8日 下午2:31:19
	 * @author : Iris.Xiao
	 * @param channel
	 * @param earningsScale
	 * @param activeCountScale 
	 * @param ecpmMax 
	 * @param ecpmMin 
	 * @description : 修改app的比例
	*/
	void updateAppScale(String channel, Double earningsScale, Double ecpmMin, Double ecpmMax, Double activeCountScale,String userId);
	

	/**
	 * @date : 2017年6月8日 下午6:29:34
	 * @author : Iris.Xiao
	 * @return
	 * @description : 查找所有所有app的channel和名字信息,channel放到siteid里面,名字放到sitename里面
	*/
	public List<Map<String,String>> getAllAppInfo();


	/**
	 * @date : 2017年6月8日 下午6:28:53
	 * @author : Iris.Xiao
	 * @return
	 * @description : 查找所有平台名
	*/
	public List<String> getAllPlatForm();
	
	/**
	 * @date : 2017年6月8日 下午7:08:17
	 * @author : Iris.Xiao
	 * @param params
	 * @return
	 * @description : 日期为维度得到统计数据
	*/
	public List<AdvertisementData> getDateStat(Map<String,Object> params);

	/**
	 * @date : 2017年6月13日 下午4:23:48
	 * @author : Iris.Xiao
	 * @param page
	 * @description : app广告位详情
	*/
	public List<AdvertisementData> listAppSiteDetail(Map<String,Object> params);
	/**
	 * @date : 2017年6月13日 下午4:26:27
	 * @author : Iris.Xiao
	 * @param params
	 * @return
	 * @description : app广告位详情
	*/
	public List<AdvertisementData> listAppSiteTotal(Map<String, Object> params);

	/**
	 * @date : 2017年6月15日 上午9:55:58
	 * @author : Iris.Xiao
	 * @param channel
	 * @return
	 * @description : 得到相关比例
	*/
	Map<String, String> getAppScales(String channel);

	/**
	 * @date : 2017年6月15日 下午3:15:15
	 * @author : Iris.Xiao
	 * @param page
	 * @description : 广告位列表
	*/
	void listSites(Page<AdvertisementData> page);


	/**
	 * @date : 2017年6月15日 下午4:41:15
	 * @author : Iris.Xiao
	 * @param uid
	 * @param siteName
	 * @description : 修改广告位名称
	*/
	void updateSiteName(String siteId,String uid, String siteName);
	/**
	 * @date : 2017年6月15日 下午4:41:15
	 * @author : Iris.Xiao
	 * @param uid
	 * @param siteName
	 * @description : 修改广告位渠道
	*/
	void updateSiteChannel(String uid, String channel);
}
