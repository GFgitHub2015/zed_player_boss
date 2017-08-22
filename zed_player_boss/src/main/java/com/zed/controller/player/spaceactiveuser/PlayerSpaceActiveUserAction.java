package com.zed.controller.player.spaceactiveuser;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.zed.common.exception.AppErrorException;
import com.zed.common.util.CommUtil;
import com.zed.common.util.DateUtil;
import com.zed.controller.system.BaseAction;
import com.zed.domain.player.spaceactiveuser.PlayerSpaceActiveUser;
import com.zed.domain.system.Admin;
import com.zed.listener.Log;
import com.zed.service.player.advertisement.AdvertisementDataService;
import com.zed.service.player.spaceactiveuser.PlayerSpaceActiveUserService;
import com.zed.system.page.Page;

/**
 * @date : 2017年5月10日 下午4:37:25
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 
*/
@Controller("playerSpaceActiveUserAction")
@Scope(value="prototype")
public class PlayerSpaceActiveUserAction extends BaseAction {
	
	private static final long serialVersionUID = 3572238078033483766L;
	private Page<PlayerSpaceActiveUser> page = new Page<PlayerSpaceActiveUser>();
	private String uid ;//主键
	private String statDate ;//统计日期
	private String channel ;//channel
	private String startDate;
	private String endDate;
	private String appName;
	private Long activeCount ;//日活跃数
	private Long adgActiveCount ;//日增活跃数
	private PlayerSpaceActiveUser activeUser;
	private Double activeCountScale ;//活跃用户比例
	@Resource(name="playerSpaceActiveUserService")
	private PlayerSpaceActiveUserService playerSpaceActiveUserService;
	@Resource(name="advertisementDataService")
	private AdvertisementDataService advertisementDataService;
	

	/**
	 * @date : 2017年5月10日 下午3:31:42
	 * @author : Iris.Xiao
	 * @param page
	 * @description : 某个站长的活跃用户数据
	*/
	public String singleMasterStatList(){
		if (channel!=null) {
			page.getParamsMap().put("channel", channel);
		}
		if (startDate!=null) {
			page.getParamsMap().put("startDate", startDate);
		}
		if (endDate!=null) {
			page.getParamsMap().put("endDate", endDate);
		}
		page.setSorting("a.stat_date desc ");
		playerSpaceActiveUserService.findByPage(page);
		List<PlayerSpaceActiveUser> list = page.getResult();
		if(list!=null&&list.size()>0){
			appName = list.get(0).getAppname();
		}
		return SUCCESS; 
	}
	
	/**
	 * @date : 2017年5月10日 下午3:00:17
	 * @author : Iris.Xiao
	 * @param page
	 * @description : 查询统计数据,昨天数据,七天数据和30天数据
	*/
	public String masterStatList(){
		if (!CommUtil.isEmpty(appName)) {
			page.getParamsMap().put("appname", appName);
		}

		Admin sessionAdmin =  getSessionAdmin();
		playerSpaceActiveUserService.addActiveUserAuto(page,sessionAdmin);
		
		return SUCCESS;
	}
	
	/**
	 * @date : 2017年5月10日 下午3:34:58
	 * @author : Iris.Xiao
	 * @param activeUser
	 * @description : 增
	*/
	public String addSpaceActiveUserStat(){
		Admin sessionAdmin =  getSessionAdmin();
		if(CommUtil.isEmpty(activeUser.getChannel())){
			throw new AppErrorException("站长信息为空");
		}
		activeUser.setUid(CommUtil.getUUID());
		activeUser.setUpdateUser(sessionAdmin.getAdminId());
		activeUser.setUpdateTime(DateUtil.getCurTime());
		playerSpaceActiveUserService.addSpaceActiveUserStat(activeUser);
		return "addSpaceActiveUserStat";
	}
	

	/**
	 * @date : 2017年5月10日 下午3:34:58
	 * @author : Iris.Xiao
	 * @param activeUser
	 * @description : 增
	*/
	public String addSpaceActiveUserStatPage(){
		if(CommUtil.isEmpty(channel)){
			throw new AppErrorException("站长信息为空");
		}
		String minDate = playerSpaceActiveUserService.getMinStatDate(channel);

		if(!CommUtil.isEmpty(minDate)){
			Date date = DateUtil.parseDate(minDate, DateUtil.YYYY_MM_DD);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DATE, -1);
			statDate =  DateUtil.dateShort2String(cal.getTime());
		}
		return SUCCESS;
	}
	
	/**
	 * @date : 2017年5月10日 下午3:34:52
	 * @author : Iris.Xiao
	 * @param activeUser
	 * @description : 改
	*/
	public void updateSpaceActiveUserStat(){
    	response.setContentType("text/html;charset=utf-8");
    	String returnResult="";
    	Map<String,Object> result = new HashMap<String,Object>();
    	result.put("result", false);
        try {
    		Admin sessionAdmin =  getSessionAdmin();
        	PlayerSpaceActiveUser updateActiveUser = new PlayerSpaceActiveUser();
        	if(!CommUtil.isEmpty(uid)){
            	updateActiveUser.setUid(uid);
            	updateActiveUser.setActiveCount(activeCount);
            	updateActiveUser.setUpdateUser(sessionAdmin.getAdminId());
            	updateActiveUser.setUpdateTime(DateUtil.getCurTime());
            	playerSpaceActiveUserService.updateSpaceActiveUserStat(updateActiveUser);
            	result.put("result", true);
        	}
        	returnResult = JSON.toJSONString(result);
			response.getWriter().println(returnResult);
	        response.getWriter().flush();
	        response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * @date : 2017-06-16 14:31:48
	 * @author : Akai
	 * @param activeUser
	 * @description : 改
	*/
	public void updateSpaceAdgActiveUserStat(){
    	response.setContentType("text/html;charset=utf-8");
    	String returnResult="";
    	Map<String,Object> result = new HashMap<String,Object>();
    	result.put("result", false);
        try {
    		Admin sessionAdmin =  getSessionAdmin();
        	PlayerSpaceActiveUser updateActiveUser = new PlayerSpaceActiveUser();
        	if(!CommUtil.isEmpty(uid)){
            	updateActiveUser.setUid(uid);
            	updateActiveUser.setAdgActiveCount(adgActiveCount);
            	updateActiveUser.setUpdateUser(sessionAdmin.getAdminId());
            	updateActiveUser.setUpdateTime(DateUtil.getCurTime());
            	playerSpaceActiveUserService.updateAdDataAdgActiveCount(updateActiveUser);
            	result.put("result", true);
        	}
        	returnResult = JSON.toJSONString(result);
			response.getWriter().println(returnResult);
	        response.getWriter().flush();
	        response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @date : 2017年5月10日 下午3:34:44
	 * @author : Iris.Xiao
	 * @param uid
	 * @description : 删
	*/
	public String deleteSpaceActiveUserStat(){
		playerSpaceActiveUserService.deleteSpaceActiveUserStat(uid);
		return SUCCESS;
	}
	

	/**
	 * @date : 2017年5月10日 下午3:43:17
	 * @author : Iris.Xiao
	 * @param statDate
	 * @param webMasterId
	 * @return
	 * @description : 判断站长某日期数据是否存在
	*/
	public void existsSpaceActiveUserStat(){
    	response.setContentType("text/html;charset=utf-8");
		Page<PlayerSpaceActiveUser> page = new Page<PlayerSpaceActiveUser>();
		page.getParamsMap().put("statDate", statDate);
		page.getParamsMap().put("channel", channel);
    	String returnResult="";
    	Map<String,Object> result = new HashMap<String,Object>();
    	result.put("result", true);
    	playerSpaceActiveUserService.findByPage(page);
		List<PlayerSpaceActiveUser> list = page.getResult();
		if(list==null||list.size()==0){
			result.put("result", false);
		}
		Date curstat = DateUtil.parseDate(statDate, null);
		Date curCal = new Date();
		if(curstat.after(curCal)){//如果填今天之后的日期也不允许
			result.put("result", true);
		}
		try {
			returnResult = JSON.toJSONString(result);
			response.getWriter().println(returnResult);
		    response.getWriter().flush();
		    response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	
	/**
	 * @date : 2017年6月8日 下午2:29:12
	 * @author : Iris.Xiao
	 * @description : 修改app的比例
	*/
	public void updateAppScale(){
		if(activeCountScale==null){
			activeCountScale=1D;
		}
		if(activeCountScale>1){
			activeCountScale=activeCountScale/100;
		}
		Admin sessionAdmin =  getSessionAdmin();
		advertisementDataService.updateAppScale(channel,null,null,null,activeCountScale,sessionAdmin.getAdminId());
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			outputResult(map, SUCCESS, "");
		} catch (IOException ex) {
			ex.printStackTrace();
 			Log.getLogger(this.getClass()).error("updateAppScale failed: "+channel,ex);
		}
	}
	
	public Page<PlayerSpaceActiveUser> getPage() {
		return page;
	}
	public void setPage(Page<PlayerSpaceActiveUser> page) {
		this.page = page;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getStatDate() {
		return statDate;
	}
	public void setStatDate(String statDate) {
		this.statDate = statDate;
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

	public PlayerSpaceActiveUser getActiveUser() {
		return activeUser;
	}

	public void setActiveUser(PlayerSpaceActiveUser activeUser) {
		this.activeUser = activeUser;
	}

	public Long getActiveCount() {
		return activeCount;
	}

	public void setActiveCount(Long activeCount) {
		this.activeCount = activeCount;
	}

	public Double getActiveCountScale() {
		return activeCountScale;
	}

	public void setActiveCountScale(Double activeCountScale) {
		this.activeCountScale = activeCountScale;
	}

	@Override
	public String doExecute() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getAdgActiveCount() {
		return adgActiveCount;
	}

	public void setAdgActiveCount(Long adgActiveCount) {
		this.adgActiveCount = adgActiveCount;
	}
	
	
}
