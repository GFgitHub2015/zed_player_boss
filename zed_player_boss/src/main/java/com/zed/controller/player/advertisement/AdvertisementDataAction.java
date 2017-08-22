package com.zed.controller.player.advertisement;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zed.common.util.CommUtil;
import com.zed.controller.system.BaseAction;
import com.zed.domain.player.advertisement.AdvertisementData;
import com.zed.domain.system.Admin;
import com.zed.listener.Log;
import com.zed.service.player.advertisement.AdvertisementDataService;
import com.zed.system.page.Page;
import com.zed.util.DateUtil;

/**
 * @date : 2017年6月6日 下午3:56:39
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 
*/
@Controller("advertisementDataAction")
@Scope(value="prototype")
public class AdvertisementDataAction extends BaseAction {
	private static final long serialVersionUID = 5545003542198428745L;
	@Resource(name="advertisementDataService")
	private AdvertisementDataService advertisementDataService;

	private Page<AdvertisementData> page = new Page<AdvertisementData>();
	private AdvertisementData adDataTotal;//总和数据
	private String adPlatform ;//广告平台:inmobi,facebook,airpush,exo
	private String channel;
	private String startDate;
	private String endDate;
	private String appName;
	private Double scale;
	private String statType;//统计方式
	private String sortedColumnName;
	private String sortedColumnValue;
	private List<Map<String,String>> appInfos = new ArrayList<Map<String,String>>();//所有的app信息
	private List<String> adPlatforms = new ArrayList<String>();//所有平台信息
	private List<AdvertisementData> siteTotal = new ArrayList<AdvertisementData>();//广告位详情
	private Double earningsScale ;//收入比例
	private Double activeCountScale ;//活跃用户比例
	private Double ecpmMin ;//ecpm最小值
	private Double ecpmMax ;//ecpm最大值
	private String siteTypeId;
	private String siteTypeName;
	private String siteName;
	private String uid;
	private String siteId;
	/**
	 * @date : 2017年6月6日 下午4:24:44
	 * @author : Iris.Xiao
	 * @param page
	 * @description : 以平台为维度,统计各个app数据
	*/
	public String listPlatformStat( ){
		Map<String,Object> map = new HashMap<String,Object>();
		String columName = "";
		//排序
		if(!CommUtil.isEmpty(sortedColumnName)&&!CommUtil.isEmpty(sortedColumnValue)){
			if("earningsSort".equals(sortedColumnName)){
				columName = "earnings";
			}else if ("adRequestsSort".equals(sortedColumnName)){
				columName = "ad_requests";
			}else if ("adResponseSort".equals(sortedColumnName)){
				columName = "ad_response";
			}else if ("adImpressionsSort".equals(sortedColumnName)){
				columName = "ad_impressions";
			}else if ("clicksSort".equals(sortedColumnName)){
				columName = "clicks";
			}
			page.setSorting(columName+" "+sortedColumnValue);
		}else{
			page.setSorting("earnings desc ");
		}
		Calendar cal = Calendar.getInstance();
		if (CommUtil.isEmpty(endDate)) {
			cal.add(Calendar.DATE, -1);
			endDate = DateUtil.dateShort2String(cal.getTime());
		}
		if (CommUtil.isEmpty(startDate)) {
			cal.add(Calendar.DATE, -6);
			startDate = DateUtil.dateShort2String(cal.getTime());
		}
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		if(!CommUtil.isEmpty(appName)){
			List<String> channels = advertisementDataService.getChannelByAppname(appName);
			//处理空集合
			if(channels.isEmpty()){
				channels.add("-1");
			}
			map.put("channels", channels);
		}
		
		page.setParamsMap(map);
		page.setPageSize(99999);
		advertisementDataService.listPlatformStat(page);
		List<AdvertisementData> list = page.getResult();
		calcRate(list);
		adDataTotal = advertisementDataService.listStatTotal(page);
		return SUCCESS;
	}
	
	/**
	 * @date : 2017年6月6日 下午4:24:44
	 * @author : Iris.Xiao
	 * @param page
	 * @description : 以平台维度统计,展示一个平台下各个app的数据
	*/
	public void listAppStatAjax(){
		Map<String,Object> map = new HashMap<String,Object>();
		String columName = "";
		//排序
		if(!CommUtil.isEmpty(sortedColumnName)&&!CommUtil.isEmpty(sortedColumnValue)){
			if("earningsSort".equals(sortedColumnName)){
				columName = "earnings";
			}else if ("adRequestsSort".equals(sortedColumnName)){
				columName = "ad_requests";
			}else if ("adResponseSort".equals(sortedColumnName)){
				columName = "ad_response";
			}else if ("adImpressionsSort".equals(sortedColumnName)){
				columName = "ad_impressions";
			}else if ("clicksSort".equals(sortedColumnName)){
				columName = "clicks";
			}
			page.setSorting(columName+" "+sortedColumnValue);
		}else{
			page.setSorting("earnings desc");
		}
		Calendar cal = Calendar.getInstance();
		if (CommUtil.isEmpty(endDate)) {
			cal.add(Calendar.DATE, -1);
			endDate = DateUtil.dateShort2String(cal.getTime());
		}
		if (CommUtil.isEmpty(startDate)) {
			cal.add(Calendar.DATE, -6);
			startDate = DateUtil.dateShort2String(cal.getTime());
		}
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		if(!CommUtil.isEmpty(appName)){
			List<String> channels = advertisementDataService.getChannelByAppname(appName);
			map.put("channels", channels);
		}

		if (!CommUtil.isEmpty(adPlatform)) {
			map.put("adPlatform", adPlatform);
		}

		page.setPageSize(99999);
		page.setParamsMap(map);
		advertisementDataService.listAppStat(page);
		List<AdvertisementData> list = page.getResult();
		calcRate(list);
		try {
			outputResultList(list, SUCCESS, "");
		} catch (IOException ex) {
			ex.printStackTrace();
 			Log.getLogger(this.getClass()).error("listAppStatAjax failed: "+adPlatform,ex);
		}
	}
	
	/**
	 * @date : 2017年6月6日 下午4:30:55
	 * @author : Iris.Xiao
	 * @param page
	 * @description : 以app为维度统计各平台的数据
	*/
	public String listAppStat( ){
		Map<String,Object> map = new HashMap<String,Object>();
		String columName = "";
		//排序
		if(!CommUtil.isEmpty(sortedColumnName)&&!CommUtil.isEmpty(sortedColumnValue)){
			if("earningsSort".equals(sortedColumnName)){
				columName = "earnings";
			}else if ("adRequestsSort".equals(sortedColumnName)){
				columName = "ad_requests";
			}else if ("adResponseSort".equals(sortedColumnName)){
				columName = "ad_response";
			}else if ("adImpressionsSort".equals(sortedColumnName)){
				columName = "ad_impressions";
			}else if ("clicksSort".equals(sortedColumnName)){
				columName = "clicks";
			}
			page.setSorting(columName+" "+sortedColumnValue);
		}else{
			page.setSorting("earnings desc ");
		}
		Calendar cal = Calendar.getInstance();
		if (CommUtil.isEmpty(endDate)) {
			cal.add(Calendar.DATE, -1);
			endDate = DateUtil.dateShort2String(cal.getTime());
		}
		if (CommUtil.isEmpty(startDate)) {
			cal.add(Calendar.DATE, -6);
			startDate = DateUtil.dateShort2String(cal.getTime());
		}
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		if(!CommUtil.isEmpty(appName)){
			List<String> channels = advertisementDataService.getChannelByAppname(appName);
			map.put("channels", channels);
		}

		page.setParamsMap(map);
		advertisementDataService.listAppStat(page);
		List<AdvertisementData> list = page.getResult();
		calcRate(list);
		adDataTotal = advertisementDataService.listStatTotal(page);
		return SUCCESS;
	}
	

	/**
	 * @date : 2017年6月9日 上午11:13:44
	 * @author : Iris.Xiao
	 * @return
	 * @description : app详情
	*/
	public String listAppDetail( ){
		Map<String,Object> map = new HashMap<String,Object>();
		String columName = "";
		//排序
		if(!CommUtil.isEmpty(sortedColumnName)&&!CommUtil.isEmpty(sortedColumnValue)){
			if("earningsSort".equals(sortedColumnName)){
				columName = "earnings";
			}else if ("adRequestsSort".equals(sortedColumnName)){
				columName = "ad_requests";
			}else if ("adResponseSort".equals(sortedColumnName)){
				columName = "ad_response";
			}else if ("adImpressionsSort".equals(sortedColumnName)){
				columName = "ad_impressions";
			}else if ("clicksSort".equals(sortedColumnName)){
				columName = "clicks";
			}
			page.setSorting(columName+" "+sortedColumnValue);
		}else{
			page.setSorting("earnings desc ");
		}

		Calendar cal = Calendar.getInstance();
		if (CommUtil.isEmpty(endDate)) {
			cal.add(Calendar.DATE, -1);
			endDate = DateUtil.dateShort2String(cal.getTime());
		}
		if (CommUtil.isEmpty(startDate)) {
			cal.add(Calendar.DATE, -6);
			startDate = DateUtil.dateShort2String(cal.getTime());
		}
		if (!CommUtil.isEmpty(channel)) {
			map.put("channel", channel);
		}
		if (!CommUtil.isEmpty(adPlatform)) {
			map.put("adPlatform", adPlatform);
		}
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		
		page.setPageSize(99999);
		page.setParamsMap(map);
		advertisementDataService.listPlatformStat(page);
		List<AdvertisementData> list = page.getResult();//平台维度的数据
		calcRate(list);
		adDataTotal = advertisementDataService.listStatTotal(page);
		map.put("sorting", page.getSorting());
//		siteTotal = advertisementDataService.listAppSiteDetail(map);
		siteTotal = advertisementDataService.listAppSiteTotal(map);
		calcRate(siteTotal);
		appInfos = advertisementDataService.getAllAppInfo();
		return SUCCESS;
	}
	

	/**
	 * @date : 2017年6月13日 下午4:15:51
	 * @author : Iris.Xiao
	 * @description : 广告位详情
	*/
	public void listAppSiteDetailAjax(){
		Map<String,Object> map = new HashMap<String,Object>();
		String columName = "";
		//排序
		if(!CommUtil.isEmpty(sortedColumnName)&&!CommUtil.isEmpty(sortedColumnValue)){
			if("earningsSort".equals(sortedColumnName)){
				columName = "earnings";
			}else if ("adRequestsSort".equals(sortedColumnName)){
				columName = "ad_requests";
			}else if ("adResponseSort".equals(sortedColumnName)){
				columName = "ad_response";
			}else if ("adImpressionsSort".equals(sortedColumnName)){
				columName = "ad_impressions";
			}else if ("clicksSort".equals(sortedColumnName)){
				columName = "clicks";
			}
			page.setSorting(columName+" "+sortedColumnValue);
		}else{
			page.setSorting("earnings desc");
		}
		Calendar cal = Calendar.getInstance();
		if (CommUtil.isEmpty(endDate)) {
			cal.add(Calendar.DATE, -1);
			endDate = DateUtil.dateShort2String(cal.getTime());
		}
		if (CommUtil.isEmpty(startDate)) {
			cal.add(Calendar.DATE, -6);
			startDate = DateUtil.dateShort2String(cal.getTime());
		}
		map.put("startDate", startDate);
		map.put("endDate", endDate);

		if (!CommUtil.isEmpty(adPlatform)) {
			map.put("adPlatform", adPlatform);
		}
		if (!CommUtil.isEmpty(channel)) {
			map.put("channel", channel);
		}
		page.setPageSize(99999);
		page.setParamsMap(map);
		map.put("sorting", page.getSorting());
		List<AdvertisementData> list = advertisementDataService.listAppSiteDetail(map);
		calcRate(list);
		try {
			outputResultList(list, SUCCESS, "");
		} catch (IOException ex) {
			ex.printStackTrace();
 			Log.getLogger(this.getClass()).error("listAppStatAjax failed: "+adPlatform,ex);
		}
	}
	
	/**
	 * @date : 2017年6月6日 下午4:31:55
	 * @author : Iris.Xiao
	 * @param page
	 * @description :  以app为维度,统计一个app下不同平台的数据
	*/
	public void listPlatformStatAjax( ){

		Map<String,Object> map = new HashMap<String,Object>();
		String columName = "";
		//排序
		if(!CommUtil.isEmpty(sortedColumnName)&&!CommUtil.isEmpty(sortedColumnValue)){
			if("earningsSort".equals(sortedColumnName)){
				columName = "earnings";
			}else if ("adRequestsSort".equals(sortedColumnName)){
				columName = "ad_requests";
			}else if ("adResponseSort".equals(sortedColumnName)){
				columName = "ad_response";
			}else if ("adImpressionsSort".equals(sortedColumnName)){
				columName = "ad_impressions";
			}else if ("clicksSort".equals(sortedColumnName)){
				columName = "clicks";
			}
			page.setSorting(columName+" "+sortedColumnValue);
		}else{
			page.setSorting("a.ad_platform desc ");
		}
		Calendar cal = Calendar.getInstance();
		if (CommUtil.isEmpty(endDate)) {
			cal.add(Calendar.DATE, -1);
			endDate = DateUtil.dateShort2String(cal.getTime());
		}
		if (CommUtil.isEmpty(startDate)) {
			cal.add(Calendar.DATE, -6);
			startDate = DateUtil.dateShort2String(cal.getTime());
		}
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		if(!CommUtil.isEmpty(appName)){
			List<String> channels = advertisementDataService.getChannelByAppname(appName);
			map.put("channels", channels);
		}

		if (!CommUtil.isEmpty(channel)) {
			map.put("channel", channel);
		}

		page.setParamsMap(map);
		advertisementDataService.listPlatformStat(page);
		List<AdvertisementData> list = page.getResult();
		calcRate(list);
		try {
			outputResultList(list, SUCCESS, "");
		} catch (IOException ex) {
			ex.printStackTrace();
 			Log.getLogger(this.getClass()).error("listAppStatAjax failed: "+adPlatform,ex);
		}
	}
	
	public String chartIndex(){
		appInfos = advertisementDataService.getAllAppInfo();
		adPlatforms = advertisementDataService.getAllPlatForm();
		return SUCCESS;
	}
	

	/**
	 * @date : 2017年6月6日 下午4:31:55
	 * @author : Iris.Xiao
	 * @param page
	 * @description :  图表数据
	*/
	public void chartStatAjax( ){

		Map<String,Object> map = new HashMap<String,Object>();
		
		Calendar cal = Calendar.getInstance();
		if (CommUtil.isEmpty(endDate)) {
			cal.add(Calendar.DATE, -1);
			endDate = DateUtil.dateShort2String(cal.getTime());
		}
		if (CommUtil.isEmpty(startDate)) {
			cal.add(Calendar.DATE, -6);
			startDate = DateUtil.dateShort2String(cal.getTime());
		}
		map.put("startDate", startDate);
		map.put("endDate", endDate);

		if (!CommUtil.isEmpty(channel)) {
			map.put("channel", channel);
		}
		if (!CommUtil.isEmpty(adPlatform)) {
			map.put("adPlatform", adPlatform);
		}
		List<AdvertisementData> list = advertisementDataService.getDateStat(map);
		Map<String,List<?>> result  = new HashMap<String,List<?>>();
		if(list!=null&&list.size()>0){
			List<Long> adRequests = new ArrayList<Long>();
			List<Double> earnings = new ArrayList<Double>();
			List<Long> adImpressions = new ArrayList<Long>();
			List<Double> ecpmList = new ArrayList<Double>();
			List<String> dates = new ArrayList<String>();
			Double ecpm= 0D;
			for (AdvertisementData ad : list) {
				dates.add(ad.getSynchDate());
				adRequests.add(ad.getAdRequests());
				earnings.add(ad.getEarnings());
				adImpressions.add(ad.getAdImpressions());
				if(ad.getEarnings()!=null&&ad.getEarnings()>0&&ad.getAdImpressions()!=null&&ad.getAdImpressions()>0){
					ecpm = new BigDecimal(ad.getEarnings()*1000/ad.getAdImpressions()).setScale(2, RoundingMode.HALF_UP).doubleValue();
					ecpmList.add(ecpm);
				}else{
					ecpmList.add(0D);
				}
			}
			result.put("adRequests", adRequests);
			result.put("earnings", earnings);
			result.put("adImpressions", adImpressions);
			result.put("ecpms", ecpmList);
			result.put("dates", dates);
		}
		try {
			outputResult(result, SUCCESS, "");
		} catch (IOException ex) {
			ex.printStackTrace();
 			Log.getLogger(this.getClass()).error("listAppStatAjax failed: "+adPlatform,ex);
		}
	}
	
	/**
	 * @date : 2017年6月8日 下午2:29:12
	 * @author : Iris.Xiao
	 * @description : 修改app的比例
	*/
	public void updateAppScale(){
		if(earningsScale==null){
			earningsScale=1D;
		}
		if(ecpmMin==null){
			ecpmMin=0.6D;
		}
		if(ecpmMax==null){
			ecpmMax=1.2D;
		}
		Admin sessionAdmin =  getSessionAdmin();
		advertisementDataService.updateAppScale(channel,earningsScale,ecpmMin,ecpmMax,null,sessionAdmin.getAdminId());
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			outputResult(map, SUCCESS, "");
		} catch (IOException ex) {
			ex.printStackTrace();
 			Log.getLogger(this.getClass()).error("listPlatformStatWithAppDetail failed: "+adPlatform,ex);
		}
	}
	
	/**
	 * @date : 2017年6月15日 上午9:53:30
	 * @author : Iris.Xiao
	 * @description : 得到app相关比例
	*/
	public void getAppScales(){
		
		Map<String,String> map = null;
		if(!CommUtil.isEmpty(channel)){
			map = advertisementDataService.getAppScales(channel);
		}
		try {
			outputResult(map, SUCCESS, "");
		} catch (IOException ex) {
			ex.printStackTrace();
 			Log.getLogger(this.getClass()).error("listPlatformStatWithAppDetail failed: "+adPlatform,ex);
		}
	}
	

	/**
	 * @date : 2017年6月15日 下午3:08:13
	 * @author : Iris.Xiao
	 * @return
	 * @description : 列出广告位
	*/
	public String listSites( ){
		Map<String,Object> map = new HashMap<String,Object>();
		String columName = "";
		//排序
		if(!CommUtil.isEmpty(sortedColumnName)&&!CommUtil.isEmpty(sortedColumnValue)){
			if("siteTypeSort".equals(sortedColumnName)){
				columName = "b.site_type_name";
			}else if ("appNameSort".equals(sortedColumnName)){
				columName = "c.param_value";
			}else if ("channelSort".equals(sortedColumnName)){
				columName = "a.channel";
			}
			page.setSorting(columName+" "+sortedColumnValue);
		}else{
			page.setSorting("a.channel DESC ");
		}
		if(!CommUtil.isEmpty(appName)){
			map.put("appName", appName);
		}
		if(!CommUtil.isEmpty(channel)){
			map.put("channel", channel);
		}
		if(!CommUtil.isEmpty(siteName)){
			map.put("siteName", siteName);
		}
		page.setParamsMap(map);
		advertisementDataService.listSites(page);
		appInfos = advertisementDataService.getAllAppInfo();
		return SUCCESS;
	}
	

	/**
	 * @date : 2017年6月8日 下午2:29:12
	 * @author : Iris.Xiao
	 * @description : 修改广告位信息
	*/
	public void updateSiteName(){
		advertisementDataService.updateSiteName(siteId,uid,siteName);
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			outputResult(map, SUCCESS, "");
		} catch (IOException ex) {
			ex.printStackTrace();
 			Log.getLogger(this.getClass()).error("updateSiteName failed: "+adPlatform,ex);
		}
	}


	/**
	 * @date : 2017年6月8日 下午2:29:12
	 * @author : Iris.Xiao
	 * @description : 修改广告位渠道
	*/
	public void updateSiteChannel(){
		advertisementDataService.updateSiteChannel(uid,channel);
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			outputResult(map, SUCCESS, "");
		} catch (IOException ex) {
			ex.printStackTrace();
 			Log.getLogger(this.getClass()).error("updateSiteChannel failed: "+adPlatform,ex);
		}
	}

	/**
	 * @date : 2017年6月8日 下午4:29:59
	 * @author : Iris.Xiao
	 * @param list
	 * @description : 计算填充率,ecpm,点击率等
	*/
	public void calcRate(List<AdvertisementData> list ){
		if(list!=null&&list.size()>0){
			Long adRequestsTotal=0L ;//广告请求
			Double earningsTotal=0D  ;//收入
			Long clicksTotal=0L  ;//点击量
			Long adImpressionsTotal=0L  ;//广告展示量
			BigDecimal bd = null;
			Double tempD = 0D;//转换为Double的临时变量
			for (AdvertisementData ad : list) {
				adImpressionsTotal=ad.getAdImpressions();
				adRequestsTotal=ad.getAdRequests();
				earningsTotal=ad.getEarnings();
				clicksTotal=ad.getClicks();
				if(adImpressionsTotal!=null&&adImpressionsTotal>0){
					if(adRequestsTotal>0){
						tempD = adImpressionsTotal*10*0.1/adRequestsTotal;
						bd = new BigDecimal(tempD).setScale(2, RoundingMode.HALF_UP);
						ad.setFillRate(bd.doubleValue());//填充率
					}else{
						ad.setFillRate(0D);//填充率
					}
					if(clicksTotal!=null&&clicksTotal>0){
						tempD = clicksTotal*10*0.1/adImpressionsTotal;
						bd = new BigDecimal(tempD).setScale(2, RoundingMode.HALF_UP);
						ad.setCtr(bd.doubleValue());//点击率
					}else{
						ad.setCtr(0D);//点击率
					}
					if(earningsTotal!=null&&earningsTotal>0){
						bd = new BigDecimal(earningsTotal*1000/adImpressionsTotal).setScale(2, RoundingMode.HALF_UP);
						ad.setEcpm(bd.doubleValue());//ecpm
					}else{
						ad.setEcpm(0D);//ecpm
					}
				}else{
					ad.setCtr(0D);
					ad.setEcpm(0D);
					ad.setFillRate(0D);
				}
			}
		}
	}
	
	public String getSiteTypeId() {
		return siteTypeId;
	}

	public void setSiteTypeId(String siteTypeId) {
		this.siteTypeId = siteTypeId;
	}

	public String getSiteTypeName() {
		return siteTypeName;
	}

	public void setSiteTypeName(String siteTypeName) {
		this.siteTypeName = siteTypeName;
	}

	public Page<AdvertisementData> getPage() {
		return page;
	}

	public void setPage(Page<AdvertisementData> page) {
		this.page = page;
	}

	public String getAdPlatform() {
		return adPlatform;
	}

	public void setAdPlatform(String adPlatform) {
		this.adPlatform = adPlatform;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public Double getScale() {
		return scale;
	}

	public void setScale(Double scale) {
		this.scale = scale;
	}

	public String doExecute() throws Exception {
		return null;
	}

	public String getSortedColumnName() {
		return sortedColumnName;
	}

	public void setSortedColumnName(String sortedColumnName) {
		this.sortedColumnName = sortedColumnName;
	}

	public String getSortedColumnValue() {
		return sortedColumnValue;
	}

	public void setSortedColumnValue(String sortedColumnValue) {
		this.sortedColumnValue = sortedColumnValue;
	}

	public String getStatType() {
		return statType;
	}

	public void setStatType(String statType) {
		this.statType = statType;
	}

	public AdvertisementData getAdDataTotal() {
		return adDataTotal;
	}

	public void setAdDataTotal(AdvertisementData adDataTotal) {
		this.adDataTotal = adDataTotal;
	}

	public List<Map<String, String>> getAppInfos() {
		return appInfos;
	}

	public void setAppInfos(List<Map<String, String>> appInfos) {
		this.appInfos = appInfos;
	}

	public List<String> getAdPlatforms() {
		return adPlatforms;
	}

	public void setAdPlatforms(List<String> adPlatforms) {
		this.adPlatforms = adPlatforms;
	}

	public List<AdvertisementData> getSiteTotal() {
		return siteTotal;
	}

	public void setSiteTotal(List<AdvertisementData> siteTotal) {
		this.siteTotal = siteTotal;
	}

	public Double getEarningsScale() {
		return earningsScale;
	}

	public void setEarningsScale(Double earningsScale) {
		this.earningsScale = earningsScale;
	}

	public Double getActiveCountScale() {
		return activeCountScale;
	}

	public void setActiveCountScale(Double activeCountScale) {
		this.activeCountScale = activeCountScale;
	}

	public Double getEcpmMin() {
		return ecpmMin;
	}

	public void setEcpmMin(Double ecpmMin) {
		this.ecpmMin = ecpmMin;
	}

	public Double getEcpmMax() {
		return ecpmMax;
	}

	public void setEcpmMax(Double ecpmMax) {
		this.ecpmMax = ecpmMax;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

}
