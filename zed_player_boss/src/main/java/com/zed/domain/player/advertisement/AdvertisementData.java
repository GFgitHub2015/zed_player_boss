package com.zed.domain.player.advertisement;

import com.zed.domain.common.base.BaseModel;
/**
 * @date : 2017年6月6日 下午3:59:22
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 
*/
public class AdvertisementData extends BaseModel {

	private static final long serialVersionUID = 1964800626716535600L;
	private String uid ;//主键
	private Long adRequests ;//广告请求
	private Long adResponse ;//广告响应
	private Double earnings ;//收入
	private String siteId ;//广告属性id
	private String siteName ;//广告属性名称
	private Long clicks ;//点击量
	private Double costPerClick ;//点击单价
	private Double ctr ;//点击率
	private Long adImpressions ;//广告展示量
	private Double ecpm ;//每日千次展示收益
	private String synchDate ;//数据同步日期
	private String adPlatform ;//广告平台:inmobi,facebook,airpush,exo
	private Double scale;//比例
	private Double fillRate;//填充率

	private Double earningsScale ;//收入
	private Long adImpressionsScale ;//广告展示量
	private Long activeCount;//活跃数
	
	private String siteTypeId;
	private String siteTypeName;
	private String appName;
	private String channel;
	
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
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

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public Long getActiveCount() {
		return activeCount;
	}

	public void setActiveCount(Long activeCount) {
		this.activeCount = activeCount;
	}

	public Double getFillRate() {
		return fillRate;
	}

	public void setFillRate(Double fillRate) {
		this.fillRate = fillRate;
	}

	public Double getEarningsScale() {
		return earningsScale;
	}

	public void setEarningsScale(Double earningsScale) {
		this.earningsScale = earningsScale;
	}

	public Long getAdImpressionsScale() {
		return adImpressionsScale;
	}

	public void setAdImpressionsScale(Long adImpressionsScale) {
		this.adImpressionsScale = adImpressionsScale;
	}

	public Double getScale() {
		return scale;
	}

	public void setScale(Double scale) {
		this.scale = scale;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Long getAdRequests() {
		return adRequests;
	}

	public void setAdRequests(Long adRequests) {
		this.adRequests = adRequests;
	}

	public Long getAdResponse() {
		return adResponse;
	}

	public void setAdResponse(Long adResponse) {
		this.adResponse = adResponse;
	}

	public Double getEarnings() {
		return earnings;
	}

	public void setEarnings(Double earnings) {
		this.earnings = earnings;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public Long getClicks() {
		return clicks;
	}

	public void setClicks(Long clicks) {
		this.clicks = clicks;
	}

	public Double getCostPerClick() {
		return costPerClick;
	}

	public void setCostPerClick(Double costPerClick) {
		this.costPerClick = costPerClick;
	}

	public Double getCtr() {
		return ctr;
	}

	public void setCtr(Double ctr) {
		this.ctr = ctr;
	}

	public Long getAdImpressions() {
		return adImpressions;
	}

	public void setAdImpressions(Long adImpressions) {
		this.adImpressions = adImpressions;
	}

	public Double getEcpm() {
		return ecpm;
	}

	public void setEcpm(Double ecpm) {
		this.ecpm = ecpm;
	}

	public String getSynchDate() {
		return synchDate;
	}

	public void setSynchDate(String synchDate) {
		this.synchDate = synchDate;
	}

	public String getAdPlatform() {
		return adPlatform;
	}

	public void setAdPlatform(String adPlatform) {
		this.adPlatform = adPlatform;
	}

}
