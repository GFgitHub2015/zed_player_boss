package com.zed.controller.player.push;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zed.controller.system.BaseAction;
import com.zed.domain.player.country.PlayerCountry;
import com.zed.domain.player.push.PlayerPushTask;
import com.zed.domain.player.push.PlayerPushTopic;
import com.zed.domain.player.push.PlayerTaskParams;
import com.zed.domain.system.Admin;
import com.zed.domain.system.OperationLog;
import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.service.player.clientinfo.PlayerClientInfoService;
import com.zed.service.player.country.PlayerCountryService;
import com.zed.service.player.push.PlayerPushTaskService;
import com.zed.service.player.push.PlayerTaskParamsService;
import com.zed.system.ExceptionConstants;
import com.zed.system.GConstantAlert;
import com.zed.system.GConstantRedirect;
import com.zed.system.page.Page;
import com.zed.util.CommUtil;
import com.zed.util.DateUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller("playerPushTaskAction")
@Scope(value="prototype")
public class PlayerPushTaskAction  extends BaseAction{
	private static final long serialVersionUID = -5743317279525428294L;
	private PlayerTaskParams playerTaskParams;
	private Page<HashMap> page = new Page<HashMap>();
	private String taskId;								//任务ID
	private String reminderType ;						//0表示所有，1表示震动，2表示响铃
	private String creater ;							//创建人
	private String updater ;							//修改人
	private String title ;								//标题
	private String content ;							//发送内容
	private String status ;								//任务状态 -1表示删除，0表示未调度，1表示调度中，2表示调度成功，3调度失败，4暂停
	private String type ;								//任务类型 1001表示有新的影片提醒
	private String targetType ;							//PUSH目标类型，1指定用户推送，2按查询指定用户推送
	private List<PlayerCountry> playerCountryList;		//所有的地区国家码集合
	private List<String> languageList;					//所有的系统语言集合
	private List<String> appVersionList;				//所有的app版本集合
	private List<String> channelList;					//所有的渠道集合
	@Resource(name="playerPushTaskService")
	private PlayerPushTaskService playerPushTaskService;
	@Resource(name="playerCountryService")
	private PlayerCountryService playerCountryService;
	@Resource(name="playerTaskParamsService")
	private PlayerTaskParamsService playerTaskParamsService;
	@Resource(name="playerClientInfoService")
	private PlayerClientInfoService playerClientInfoService;
	
	
	public String list(){
		try {
			HashMap map = new HashMap();
			page.setSorting(" create_time DESC"); // 排序
			if (!CommUtil.isEmpty(title)) {
				map.put("title", title);
			}
			if (!CommUtil.isEmpty(content)) {
				map.put("content", content);
			}
			if (!CommUtil.isEmpty(type)) {
				map.put("type", type);
			}
			if (!CommUtil.isEmpty(status)) {
				map.put("status", status);
			}
			if (!CommUtil.isEmpty(reminderType)) {
				map.put("reminderType", reminderType);
			}
			if (!CommUtil.isEmpty(targetType)) {
				map.put("targetType", targetType);
			}
			page.setParamsMap(map);
			page = playerPushTaskService.findByPage(page);
			playerCountryList = playerCountryService.findAll();
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerPushTaskAction[list] failed: ",ex);
			}
			return ERROR;
		}
	}
	
	public String add() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			playerTaskParams.setTaskId(playerTaskParams.generateId());
			playerTaskParams.setCreater(sessionAdmin.getAdminId());
			playerTaskParams.setCreateTime(new Timestamp(newdate.getTime()));
			playerTaskParamsService.addPlayerTaskParams(playerTaskParams);
			OperationLog oLog = new OperationLog(getIp(), "推送主题管理列表", "添加推送主题:taskId:" + playerTaskParams.getTaskId()+", 标题:"+ playerTaskParams.getTitle()+", 国家码:"+playerTaskParams.getAreaCode()+", 类型:"+ playerTaskParams.getType()==null?"":playerTaskParams.getType().equals("1001")?"影片推荐":playerTaskParams.getType().equals("1002")?"影片推荐":"",newdate , sessionAdmin.getAdminId());
			logService.add(oLog);//记录操作日志
			setSuccessDispatch(GConstantAlert.GS_INSERT_SUCCESS,GConstantRedirect.GS_PLAYER_PUSH_TASK_LIST_ACTION);
			return GConstantRedirect.GS_OK;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerPushTaskAction[add] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String addPage() {
		try {
			languageList = playerClientInfoService.findLanguageList();
			appVersionList = playerClientInfoService.findAppVersionList();
			channelList = playerClientInfoService.findChannelList();
			playerCountryList = playerCountryService.findAll();
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerPushTaskAction[addPage] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String updatePage() {
		try {
			playerTaskParams = playerTaskParamsService.findByTaskId(taskId);
			if (playerTaskParams==null) {
				playerTaskParamsService.deletePlayerTaskParams(taskId);
				setResultDispatch(GConstantAlert.GS_NOT_EXSIT, GConstantRedirect.GS_PLAYER_PUSH_TASK_LIST_ACTION);
				return GConstantRedirect.GS_RESULT;
			} else {
				if (playerTaskParams.getStatus()==PlayerPushTask.Status.SUCCESS.getStatus()) {
					//已完成推送的消息无法被修改
					setResultDispatch(GConstantAlert.GS_SEND_SUCCESS, GConstantRedirect.GS_PLAYER_PUSH_TASK_LIST_ACTION);
					return GConstantRedirect.GS_RESULT;
				} else {
					languageList = playerClientInfoService.findLanguageList();
					appVersionList = playerClientInfoService.findAppVersionList();
					channelList = playerClientInfoService.findChannelList();
					playerCountryList = playerCountryService.findAll();
					return SUCCESS;
				}
			}
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerPushTaskAction[updatePage] failed: ",ex);
			}
			return ERROR;
		}
	}
	

	public String update() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			playerTaskParams.setUpdater(sessionAdmin.getAdminId());
			playerTaskParamsService.updatePlayerTaskParams(playerTaskParams);
			OperationLog oLog = new OperationLog(getIp(), "推送主题管理列表", "更新推送主题:taskId:" + playerTaskParams.getTaskId()+", 标题:"+ playerTaskParams.getTitle()+", 国家码:"+playerTaskParams.getAreaCode()+", 类型:"+ playerTaskParams.getType()==null?"":playerTaskParams.getType().equals("1001")?"影片推荐":playerTaskParams.getType().equals("1002")?"影片推荐":"",newdate , sessionAdmin.getAdminId());
			logService.add(oLog);//记录操作日志
			setSuccessDispatch(GConstantAlert.GS_UPDATE_SUCCESS,GConstantRedirect.GS_PLAYER_PUSH_TASK_LIST_ACTION);
			return GConstantRedirect.GS_OK;

		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerPushTaskAction[update] failed: ",ex);
			}
			return ERROR;
		}

	}

	//复用界面
	public String reusePage() {
		try {
			playerTaskParams = playerTaskParamsService.findByTaskId(taskId);
			if (playerTaskParams==null) {
				playerTaskParamsService.deletePlayerTaskParams(taskId);
				setResultDispatch(GConstantAlert.GS_NOT_EXSIT, GConstantRedirect.GS_PLAYER_PUSH_TASK_LIST_ACTION);
				return GConstantRedirect.GS_RESULT;
			} else {
				languageList = playerClientInfoService.findLanguageList();
				appVersionList = playerClientInfoService.findAppVersionList();
				channelList = playerClientInfoService.findChannelList();
				playerCountryList = playerCountryService.findAll();
			}
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerPushTaskAction[reusePage] failed: ",ex);
			}
			return ERROR;
		}
	}
	//复用
	public String updateReuse() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			playerTaskParams.setTaskId(playerTaskParams.generateId());
			playerTaskParams.setCreater(sessionAdmin.getAdminId());
			playerTaskParams.setCreateTime(new Timestamp(newdate.getTime()));
			playerTaskParamsService.addPlayerTaskParams(playerTaskParams);
			OperationLog oLog = new OperationLog(getIp(), "推送主题管理列表", "复用推送主题:taskId:" + playerTaskParams.getTaskId()+", 标题:"+ playerTaskParams.getTitle()+", 国家码:"+playerTaskParams.getAreaCode()+", 类型:"+ playerTaskParams.getType()==null?"":playerTaskParams.getType().equals("1001")?"影片推荐":playerTaskParams.getType().equals("1002")?"影片推荐":"",newdate , sessionAdmin.getAdminId());
			logService.add(oLog);//记录操作日志
			setSuccessDispatch(GConstantAlert.GS_INSERT_SUCCESS,GConstantRedirect.GS_PLAYER_PUSH_TASK_LIST_ACTION);
			return GConstantRedirect.GS_OK;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerPushTaskAction[updateReuse] failed: ",ex);
			}
			return ERROR;
		}
	}
	
	public String delete() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			String[] taskIds=taskId.split(",");
			List<OperationLog> operationLogList = new ArrayList<OperationLog>();
			for (String id : taskIds) {
				PlayerTaskParams ptp = playerTaskParamsService.findByTaskId(id);
				if (ptp != null) {
					OperationLog oLog = new OperationLog(getIp(), "推送主题管理列表", "删除推送主题:taskId:" + ptp.getTaskId()+", 标题:"+ ptp.getTitle()+", 国家码:"+ptp.getAreaCode()+", 类型:"+ ptp.getType()==null?"":ptp.getType().equals("1001")?"影片推荐":ptp.getType().equals("1002")?"影片推荐":"",newdate , sessionAdmin.getAdminId());
					operationLogList.add(oLog);
				}
			}
			playerTaskParamsService.deletePlayerTaskParams(taskIds);
			
			if (operationLogList!=null && operationLogList.size()>0 && !operationLogList.isEmpty()) {
				logService.addBatch(operationLogList);//记录操作日志
			}
			setSuccessDispatch(GConstantAlert.GS_DELETE_SUCCESS,GConstantRedirect.GS_PLAYER_PUSH_TASK_LIST_ACTION);
			return GConstantRedirect.GS_OK;

		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerPushTaskAction[delete] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String detail() {
		try {
			playerTaskParams = playerTaskParamsService.findByTaskId(taskId);
			if (playerTaskParams==null) {
				playerTaskParamsService.deletePlayerTaskParams(taskId);
				setResultDispatch(GConstantAlert.GS_NOT_EXSIT, GConstantRedirect.GS_PLAYER_PUSH_TASK_LIST_ACTION);
				return GConstantRedirect.GS_RESULT;
			} else {
				languageList = playerClientInfoService.findLanguageList();
				appVersionList = playerClientInfoService.findAppVersionList();
				channelList = playerClientInfoService.findChannelList();
				playerCountryList = playerCountryService.findAll();
			}
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerPushTaskAction[detail] failed: ",ex);
			}
			return ERROR;
		}
	}
	
	public String updateStatus() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			/*PlayerPushTopic hkd = playerPushTopicService.findById(topicId);
			hkd.setStatus(Integer.valueOf(status));
			OperationLog oLog = new OperationLog(getIp(), "推送主题管理列表", "推送主题状态修改:" + hkd.getTopicName()+", 国家码:"+hkd.getCountryCode()+", 类型:"+ hkd.getTopicType()+", 类型对应值:"+hkd.getTypeName() +", 状态:"+(status.equals("1")?"启用":"禁用"),newdate , sessionAdmin.getAdminId());
			playerPushTopicService.updateStatus(hkd);
			logService.add(oLog);//记录操作日志
*/			if (status.equals("1")) {
				setSuccessDispatch(GConstantAlert.GS_START_SUCCESS,GConstantRedirect.GS_PLAYER_PUSH_TASK_LIST_ACTION);
			}else{
				setSuccessDispatch(GConstantAlert.GS_FORBIDDEN_SUCCESS,GConstantRedirect.GS_PLAYER_PUSH_TASK_LIST_ACTION);
			}
			return GConstantRedirect.GS_OK;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerPushTaskAction[updateStatus] failed: ",ex);
			}
			return ERROR;
		}

	}
	
	public String getMinDate() {
		PrintWriter out = null;
		String result = "";
		try{
			out = response.getWriter();
			JSONObject json = new JSONObject();
			Boolean flag = Boolean.TRUE;
			String countryCode = request.getParameter("countryCode");
			//设置指定国家的当地时区的指定时间
			String zoneId = playerCountryService.findZoneIdByCountryCode(countryCode);
			Date zonedate = new Date();
			if (StringUtils.isNotBlank(zoneId)) {
				zonedate = DateUtil.string2TimezoneDefault(zonedate, zoneId);
			}
			
			String dateStr = DateUtil.date2String(zonedate);
			json.put("minDate", dateStr);
			result = json.toString();
		}catch(Exception ex){
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerPushTaskAction[getMinDate] failed: ",ex);
			}
		}finally{
			out.print(result);
			out.flush();
			out.close();
			return NONE;
		}
	} 
	
	
	public String doExecute() throws Exception {
		return null;
	}
	public PlayerTaskParams getPlayerTaskParams() {
		return playerTaskParams;
	}

	public void setPlayerTaskParams(PlayerTaskParams playerTaskParams) {
		this.playerTaskParams = playerTaskParams;
	}

	public Page<HashMap> getPage() {
		return page;
	}
	public void setPage(Page<HashMap> page) {
		this.page = page;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public String getUpdater() {
		return updater;
	}
	public void setUpdater(String updater) {
		this.updater = updater;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getReminderType() {
		return reminderType;
	}
	public void setReminderType(String reminderType) {
		this.reminderType = reminderType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTargetType() {
		return targetType;
	}
	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}
	public List<PlayerCountry> getPlayerCountryList() {
		return playerCountryList;
	}
	public void setPlayerCountryList(List<PlayerCountry> playerCountryList) {
		this.playerCountryList = playerCountryList;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public List<String> getLanguageList() {
		return languageList;
	}

	public void setLanguageList(List<String> languageList) {
		this.languageList = languageList;
	}

	public List<String> getAppVersionList() {
		return appVersionList;
	}

	public void setAppVersionList(List<String> appVersionList) {
		this.appVersionList = appVersionList;
	}

	public List<String> getChannelList() {
		return channelList;
	}

	public void setChannelList(List<String> channelList) {
		this.channelList = channelList;
	}
}
