package com.zed.dao.player.advertisement;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.zed.dao.PageDao;
import com.zed.domain.player.advertisement.AdvertisementData;
import com.zed.system.page.Page;

/**
 * @date : 2017年6月6日 下午3:58:23
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 
*/
public interface AdvertisementDataDao <T extends Serializable> extends PageDao<AdvertisementData> {

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
	public List<AdvertisementData> getChannelByAppname(Map<String,Object> params);

//	/**
//	 * @date : 2017年6月8日 下午2:32:15
//	 * @author : Iris.Xiao
//	 * @param channel
//	 * @param scale
//	 * @description : 修改app的比例
//	*/
//	void updateAppScale(String channel, Double scale);
	
	/**
	 * @date : 2017年6月8日 下午6:28:53
	 * @author : Iris.Xiao
	 * @return
	 * @description : 查找所有平台名
	*/
	List<AdvertisementData> getAllPlatForm();
	
	/**
	 * @date : 2017年6月8日 下午7:08:17
	 * @author : Iris.Xiao
	 * @param params
	 * @return
	 * @description : 日期为维度得到统计数据
	*/
	public List<AdvertisementData> getDateStat(Map<String,Object> params);

	/**
	 * @date : 2017年6月13日 下午4:26:27
	 * @author : Iris.Xiao
	 * @param params
	 * @return
	 * @description : app广告位详情
	*/
	public List<AdvertisementData> listAppSiteDetail(Map<String, Object> params);
	/**
	 * @date : 2017年6月13日 下午4:26:27
	 * @author : Iris.Xiao
	 * @param params
	 * @return
	 * @description : app广告位详情
	*/
	public List<AdvertisementData> listAppSiteTotal(Map<String, Object> params);

	/**
	 * @date : 2017年6月15日 下午3:16:35
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
	void updateSiteName(String siteId,String uid, String siteTypeId,String siteName);
	/**
	 * @date : 2017年6月15日 下午4:41:15
	 * @author : Iris.Xiao
	 * @param uid
	 * @param siteName
	 * @description : 修改广告位渠道
	*/
	void updateSiteChannel(String uid, String channel);
	
	/**
	 * @date : 2017年6月15日 下午5:08:29
	 * @author : Iris.Xiao
	 * @param siteTypeName
	 * @return
	 * @description : 得到
	*/
	AdvertisementData getSiteTypeName(String siteTypeName);
	
	/**
	 * @date : 2017年6月15日 下午5:08:29
	 * @author : Iris.Xiao
	 * @param siteTypeName
	 * @return
	 * @description : 
	*/
	void insertSiteTypeName(AdvertisementData data);
}
