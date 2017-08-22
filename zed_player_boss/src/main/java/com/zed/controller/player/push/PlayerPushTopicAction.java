package com.zed.controller.player.push;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zed.controller.system.BaseAction;
import com.zed.domain.player.country.PlayerCountry;
import com.zed.domain.player.push.PlayerPushTopic;
import com.zed.domain.system.Admin;
import com.zed.domain.system.OperationLog;
import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.service.player.country.PlayerCountryService;
import com.zed.service.player.push.PlayerPushTopicService;
import com.zed.system.ExceptionConstants;
import com.zed.system.GConstantAlert;
import com.zed.system.GConstantRedirect;
import com.zed.system.page.Page;
import com.zed.util.CommUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller("playerPushTopicAction")
@Scope(value="prototype")
public class PlayerPushTopicAction  extends BaseAction{
	private static final long serialVersionUID = -5743317279525428294L;
	private PlayerPushTopic playerPushTopic;
	private Page<PlayerPushTopic> page = new Page<PlayerPushTopic>();
	private String topicId ;							//主题标识
	private String status ;								//1表示有效，-1表示废弃（删除）
	private String typeValue;							//主题类型对应的值，如类型为版本，则值为版本编号；类型为渠道值为渠道编号；类型为语言，值为语言代码
	private String topicName;							//TOPIC名称规则为/topics/${TOPIC_ID}
	private String topicType ;							//主题类型 1:版本，2：渠道，3：语言
	private String areaCode;							//国家码
	private List<PlayerCountry> playerCountryList;							//所有的地区国家码集合
	@Resource(name="playerPushTopicService")
	private PlayerPushTopicService playerPushTopicService;
	@Resource(name="playerCountryService")
	private PlayerCountryService playerCountryService;
	public String list(){
		
		try {
			HashMap map = new HashMap();
			page.setSorting(" topic_type ASC"); // 排序
			if (!CommUtil.isEmpty(topicType)) {
				map.put("topicType", topicType);
			}
			if (!CommUtil.isEmpty(status)) {
				map.put("status", status);
			}
			if (!CommUtil.isEmpty(areaCode)) {
				map.put("countryCode", areaCode);
			}
			page.setParamsMap(map);
			playerPushTopicService.findByPage(page);
			playerCountryList = playerCountryService.findAll();
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerPushTopicAction[list] failed: ",ex);
			}
			return ERROR;
		}
	}
	
	public String add() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			playerPushTopicService.add(playerPushTopic);
			OperationLog oLog = new OperationLog(getIp(), "推送主题管理列表", "添加推送主题：ID："+playerPushTopic.getTopicId()+"， 主题名称：" + playerPushTopic.getTopicName()+"， 国家码："+playerPushTopic.getCountryCode()+"， 类型："+ playerPushTopic.getTopicType()+"， 类型对应值："+playerPushTopic.getTypeValue(),newdate , sessionAdmin.getAdminId());
			logService.add(oLog);//记录操作日志
			setSuccessDispatch(GConstantAlert.GS_INSERT_SUCCESS,GConstantRedirect.GS_PLAYER_PUSH_TOPIC_LIST_ACTION);
			return GConstantRedirect.GS_OK;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerPushTopicAction[add] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String addPage() {
		try {
			playerCountryList = playerCountryService.findAll();
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerPushTopicAction[addPage] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String updatePage() {
		try {
			playerPushTopic = playerPushTopicService.findById(topicId);
			playerCountryList = playerCountryService.findAll();
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerPushTopicAction[updatePage] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String update() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			playerPushTopicService.update(playerPushTopic);
			OperationLog oLog = new OperationLog(getIp(), "推送主题管理列表", "修改推送主题：ID："+playerPushTopic.getTopicId()+"， 主题名称："  + playerPushTopic.getTopicName()+"， 国家码："+playerPushTopic.getCountryCode()+"， 类型："+ playerPushTopic.getTopicType()+"， 类型对应值："+playerPushTopic.getTypeValue() ,newdate , sessionAdmin.getAdminId());
			logService.add(oLog);//记录操作日志
			setSuccessDispatch(GConstantAlert.GS_UPDATE_SUCCESS,GConstantRedirect.GS_PLAYER_PUSH_TOPIC_LIST_ACTION);
			return GConstantRedirect.GS_OK;

		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerPushTopicAction[update] failed: ",ex);
			}
			return ERROR;
		}

	}

	public String delete() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			String[] topicIds=topicId.split(",");
			List<OperationLog> operationLogList = new ArrayList<OperationLog>();
			for (String id : topicIds) {
				PlayerPushTopic hkw = playerPushTopicService.findById(id);
				OperationLog oLog = new OperationLog(getIp(), "推送主题管理列表", "删除推送主题：ID："+hkw.getTopicId()+"， 主题名称："   + hkw.getTopicName()+"， 国家码："+hkw.getCountryCode()+"， 类型："+ hkw.getTopicType()+"， 类型对应值："+hkw.getTypeValue() ,newdate , sessionAdmin.getAdminId());
				operationLogList.add(oLog);
			}
			playerPushTopicService.delete(topicIds);
			
			if (operationLogList!=null && operationLogList.size()>0 && !operationLogList.isEmpty()) {
				logService.addBatch(operationLogList);//记录操作日志
			}
			setSuccessDispatch(GConstantAlert.GS_DELETE_SUCCESS,GConstantRedirect.GS_PLAYER_PUSH_TOPIC_LIST_ACTION);
			return GConstantRedirect.GS_OK;

		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerPushTopicAction[delete] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String detail() {
		try {
			playerPushTopic = playerPushTopicService.findById(topicId);
			playerCountryList = playerCountryService.findAll();
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerPushTopicAction[detail] failed: ",ex);
			}
			return ERROR;
		}
	}
	
	public String updateStatus() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			PlayerPushTopic hkd = playerPushTopicService.findById(topicId);
			hkd.setStatus(Integer.valueOf(status));
			OperationLog oLog = new OperationLog(getIp(), "推送主题管理列表", "推送主题状态修改:" + hkd.getTopicName()+", 国家码:"+hkd.getCountryCode()+", 类型:"+ hkd.getTopicType()+", 类型对应值:"+hkd.getTypeValue() +", 状态:"+(status.equals("1")?"启用":"禁用"),newdate , sessionAdmin.getAdminId());
			playerPushTopicService.updateStatus(hkd);
			logService.add(oLog);//记录操作日志
			if (status.equals("1")) {
				setSuccessDispatch(GConstantAlert.GS_START_SUCCESS,GConstantRedirect.GS_PLAYER_PUSH_TOPIC_LIST_ACTION);
			}else{
				setSuccessDispatch(GConstantAlert.GS_FORBIDDEN_SUCCESS,GConstantRedirect.GS_PLAYER_PUSH_TOPIC_LIST_ACTION);
			}
			return GConstantRedirect.GS_OK;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerPushTopicAction[updateStatus] failed: ",ex);
			}
			return ERROR;
		}

	}
	
	
	/**
	 * flag ：true 表示不存在相同的国家码false 表示存在相同的国家码
	 * @return
	 */
	public String getFlag() {
		PrintWriter out = null;
		String result = "";
		try{
			out = response.getWriter();
			String fieldValue = request.getParameter("fieldValue");
			String fieldId = request.getParameter("fieldId");
			JSONObject json = new JSONObject();
			Boolean flag = Boolean.TRUE;
			if (!CommUtil.isEmpty(fieldValue)) {
				PlayerPushTopic hkd = playerPushTopicService.findById(fieldValue);
				if (hkd != null) {
					flag = Boolean.FALSE;
				}
			}
			JSONArray ja = new JSONArray();
			ja.add(0,fieldId);
			ja.add(1,flag);
			json.put("data", ja.toString());
			result = json.toString();
		}catch(Exception ex){
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerPushTopicAction[getFlag] failed: ",ex);
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
	public PlayerPushTopic getPlayerPushTopic() {
		return playerPushTopic;
	}
	public void setPlayerPushTopic(PlayerPushTopic playerPushTopic) {
		this.playerPushTopic = playerPushTopic;
	}
	public Page<PlayerPushTopic> getPage() {
		return page;
	}
	public void setPage(Page<PlayerPushTopic> page) {
		this.page = page;
	}
	public String getTopicId() {
		return topicId;
	}
	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTypeValue() {
		return typeValue;
	}
	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}
	public String getTopicName() {
		return topicName;
	}
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	public String getTopicType() {
		return topicType;
	}
	public void setTopicType(String topicType) {
		this.topicType = topicType;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public List<PlayerCountry> getPlayerCountryList() {
		return playerCountryList;
	}
	public void setPlayerCountryList(List<PlayerCountry> playerCountryList) {
		this.playerCountryList = playerCountryList;
	}
}
