package com.zed.dao.player.advertisement.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zed.common.util.CommUtil;
import com.zed.dao.player.advertisement.AdvertisementDataDao;
import com.zed.dao.player.base.abstractdao.AbstractPlayerPageDao;
import com.zed.domain.player.advertisement.AdvertisementData;
import com.zed.system.page.Page;

/**
 * @date : 2017年6月6日 下午3:58:52
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 
*/
@Repository("advertisementDataDao")
public class AdvertisementDataDaoImpl<T> extends AbstractPlayerPageDao<AdvertisementData>implements AdvertisementDataDao<AdvertisementData> {
	/**
	 * @date : 2017年6月6日 下午4:24:44
	 * @author : Iris.Xiao
	 * @param page
	 * @description : 以平台为维度,统计各个app数据
	*/
	public void listPlatformStat(Page<AdvertisementData> page){
		this.findByPage("listPlatformStat", "listPlatformStatCount", page);
	}
	
	/**
	 * @date : 2017年6月6日 下午4:30:55
	 * @author : Iris.Xiao
	 * @param page
	 * @description : 以app为维度统计各平台的数据
	*/
	public void listAppStat(Page<AdvertisementData> page){
		this.findByPage("listAppStat", "listAppStatCount", page);
	}
	
	
	/**
	 * @date : 2017年6月8日 上午9:38:15
	 * @author : Iris.Xiao
	 * @param page
	 * @return
	 * @description : 查询总和
	*/
	public AdvertisementData listStatTotal(Page<AdvertisementData> page){
		return (AdvertisementData)this.findOne("listStatTotal", page.getParamsMap());
	}
	
	/**
	 * @date : 2017年6月8日 上午10:12:42
	 * @author : Iris.Xiao
	 * @param params
	 * @return
	 * @description : 根据appname查找渠道
	*/
	public List<AdvertisementData> getChannelByAppname(Map<String,Object> params){
		return this.findList("getChannelByAppname", params);
	}
	

//	/**
//	 * @date : 2017年6月8日 下午2:32:15
//	 * @author : Iris.Xiao
//	 * @param channel
//	 * @param scale
//	 * @description : 修改app的比例
//	*/
//	public void updateAppScale(String channel, Double scale){
//		Map<String,Object> params = new HashMap<String,Object>();
//		params.put("channel", channel);
//		params.put("scale", scale);
//		this.update("updateAppScale", params);
//	}
	
	/**
	 * @date : 2017年6月8日 下午6:28:53
	 * @author : Iris.Xiao
	 * @return
	 * @description : 查找所有平台名
	*/
	public List<AdvertisementData> getAllPlatForm(){
		Map<String,Object> params = new HashMap<String,Object>();
		return this.findList("getAllPlatForm", params);
	}
	

	/**
	 * @date : 2017年6月8日 下午7:08:17
	 * @author : Iris.Xiao
	 * @param params
	 * @return
	 * @description : 日期为维度得到统计数据
	*/
	public List<AdvertisementData> getDateStat(Map<String,Object> params){
		return this.findList("getDateStat", params);
	}
	
	/**
	 * @date : 2017年6月13日 下午4:26:27
	 * @author : Iris.Xiao
	 * @param params
	 * @return
	 * @description : app广告位详情
	*/
	public List<AdvertisementData> listAppSiteDetail(Map<String, Object> params){
		return this.findList("listAppSiteDetail", params);
	}

	/**
	 * @date : 2017年6月13日 下午4:26:27
	 * @author : Iris.Xiao
	 * @param params
	 * @return
	 * @description : app广告位详情
	*/
	public List<AdvertisementData> listAppSiteTotal(Map<String, Object> params){
		return this.findList("listAppSiteTotal", params);
	}
	/**
	 * @date : 2017年6月15日 下午3:16:35
	 * @author : Iris.Xiao
	 * @param page
	 * @description : 广告位列表
	*/
	public void listSites(Page<AdvertisementData> page){
		this.findByPage("listSites", "listSitesCount", page);
	}
	

	/**
	 * @date : 2017年6月15日 下午4:41:15
	 * @author : Iris.Xiao
	 * @param uid
	 * @param siteName
	 * @description : 修改广告位名称
	*/
	public void updateSiteName(String siteId,String uid, String siteTypeId,String siteName){
		Map<String,Object> param = new HashMap<String,Object>();
		if(!CommUtil.isEmpty(uid)){
			param.put("uid",uid);
		}
		if(!CommUtil.isEmpty(siteId)){
			param.put("siteId",siteId);
		}
		if(!CommUtil.isEmpty(siteName)){
			param.put("siteName",siteName);
		}
		param.put("siteTypeId",siteTypeId);
		this.update("updateSiteName", param);
	}
	/**
	 * @date : 2017年6月15日 下午4:41:15
	 * @author : Iris.Xiao
	 * @param uid
	 * @param siteName
	 * @description : 修改广告位渠道
	*/
	public void updateSiteChannel(String uid, String channel){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("uid", uid);
		param.put("channel",channel);
		this.update("updateSiteChannel", param);
	}
	
	/**
	 * @date : 2017年6月15日 下午5:08:29
	 * @author : Iris.Xiao
	 * @param siteTypeName
	 * @return
	 * @description : 得到类型
	*/
	public AdvertisementData getSiteTypeName(String siteTypeName){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("siteTypeName", siteTypeName);
		List<AdvertisementData> list = this.findList("getSiteTypeName", param);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * @date : 2017年6月15日 下午5:08:29
	 * @author : Iris.Xiao
	 * @param siteTypeName
	 * @return
	 * @description : 
	*/
	public void insertSiteTypeName(AdvertisementData data){
		this.add("insertSiteTypeName", data);
	}
}
