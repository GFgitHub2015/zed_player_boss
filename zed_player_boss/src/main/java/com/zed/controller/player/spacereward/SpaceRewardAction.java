package com.zed.controller.player.spacereward;

import com.zed.common.util.CommUtil;
import com.zed.controller.system.BaseAction;
import com.zed.domain.player.spacereward.SpaceReward;
import com.zed.domain.system.Admin;
import com.zed.domain.system.OperationLog;
import com.zed.service.player.advertisement.AdvertisementDataService;
import com.zed.service.player.spacereward.SpaceRewardService;
import com.zed.system.GConstantAlert;
import com.zed.system.GConstantRedirect;
import com.zed.system.page.Page;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.*;

/**
 * @date : 2017年7月26日 下午7:09:24
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 网盘奖励
*/
@Controller("spaceRewardAction")
@Scope(value="prototype")
public class SpaceRewardAction extends BaseAction {
	private static final long serialVersionUID = 8732267003012212409L;

	private SpaceReward rewardData;

	private Page<SpaceReward> page = new Page<SpaceReward>();
	private String channel;
	private String id;
	private String paymentId;
	private String rewardChannels;//要给哪些渠道添加奖励
	private String sourceType;//徽章类型
	private String startDate;
	private String endDate;
	private String appName;
	private String sortedColumnName;
	private String sortedColumnValue;
	private List<Map<String,String>> appInfos = new ArrayList<Map<String,String>>();//所有的app信息
	private List<SpaceReward> gadges = new ArrayList<SpaceReward>();//所有的徽标信息
	
	@Resource(name="spaceRewardService")
	private SpaceRewardService spaceRewardService;

	@Resource(name="advertisementDataService")
	private AdvertisementDataService advertisementDataService;
	
	public String list(){
		Map<String,Object> map = new HashMap<String,Object>();
		page.setSorting("a.create_time desc ");
		if (!CommUtil.isEmpty(endDate)) {
			map.put("endDate", endDate+" 59:59:59");
		}
		if (!CommUtil.isEmpty(startDate)) {
			map.put("startDate", startDate+" 00:00:00");
		}
		if (!CommUtil.isEmpty(channel)) {
			map.put("channel", channel);
		}
		page.setParamsMap(map);
		spaceRewardService.findByPage(page);
		appInfos = advertisementDataService.getAllAppInfo();
		gadges = spaceRewardService.findBadgeList();
		return SUCCESS;
	}

	public String addPage(){
		gadges = spaceRewardService.findBadgeList();
		appInfos = advertisementDataService.getAllAppInfo();
		return SUCCESS;
	}
	
	public String addSpaceReward(){
		
		Admin sessionAdmin = getSessionAdmin();
		rewardData.setCreateUser(sessionAdmin.getAdminId());
		spaceRewardService.addSpaceReward(rewardData);
		String channels = rewardData.getChannel();
		if(CommUtil.isEmpty(channels)){
			setSuccessDispatch(GConstantAlert.GS_INSERT_SUCCESS,GConstantRedirect.GS_PLAYER_HOTVIDEO_LIST_ACTION);
			return GConstantRedirect.GS_OK; 
		}
		String[] channelsArr = channels.split(",");
		for (String channel : channelsArr) {
			OperationLog operationLog = new OperationLog(getIp(), "网盘奖励添加", "添加渠道:" + channel  ,new Date() , sessionAdmin.getAdminId());
			logService.add(operationLog);//记录操作日志
		}
		
		setSuccessDispatch(GConstantAlert.GS_INSERT_SUCCESS,GConstantRedirect.GS_PLAYER_HOTVIDEO_LIST_ACTION);
		return GConstantRedirect.GS_OK; 
	}

	/**
	 * @date : 2017年8月1日 下午1:51:39
	 * @author : Iris.Xiao
	 * @return
	 * @description : 删除
	*/
	public String deleteSpaceReward(){
		
		Admin sessionAdmin = getSessionAdmin();
		SpaceReward reward = spaceRewardService.getSpaceReward(id);
		if(reward==null){
			setSuccessDispatch(GConstantAlert.GS_LTE2003,GConstantRedirect.GS_PLAYER_HOTVIDEO_LIST_ACTION);
			return GConstantRedirect.GS_OK; 
		}
		spaceRewardService.deleteSpaceReward(reward);
		OperationLog operationLog = new OperationLog(getIp(), "网盘奖励删除", "id:" + id+",paymentId:"+reward.getPaymentId()  ,new Date() , sessionAdmin.getAdminId());
		logService.add(operationLog);//记录操作日志
		
		setSuccessDispatch(GConstantAlert.GS_DELETE_SUCCESS,GConstantRedirect.GS_PLAYER_HOTVIDEO_LIST_ACTION);
		return GConstantRedirect.GS_OK; 
	}
	
	public SpaceReward getRewardData() {
		return rewardData;
	}
	public void setRewardData(SpaceReward rewardData) {
		this.rewardData = rewardData;
	}
	public Page<SpaceReward> getPage() {
		return page;
	}
	public void setPage(Page<SpaceReward> page) {
		this.page = page;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getRewardChannels() {
		return rewardChannels;
	}

	public void setRewardChannels(String rewardChannels) {
		this.rewardChannels = rewardChannels;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
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
	
	public List<Map<String, String>> getAppInfos() {
		return appInfos;
	}

	public void setAppInfos(List<Map<String, String>> appInfos) {
		this.appInfos = appInfos;
	}

	public List<SpaceReward> getGadges() {
		return gadges;
	}

	public void setGadges(List<SpaceReward> gadges) {
		this.gadges = gadges;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	@Override
	public String doExecute() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
