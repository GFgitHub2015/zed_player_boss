package com.zed.controller.player.playeruser;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zed.controller.system.BaseAction;
import com.zed.domain.account.account.Account;
import com.zed.domain.player.playeruser.PlayerUser;
import com.zed.domain.system.Admin;
import com.zed.domain.system.OperationLog;
import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.service.account.account.AccountService;
import com.zed.service.player.playeruser.PlayerUserService;
import com.zed.service.system.operationlog.OperationLogService;
import com.zed.system.ExceptionConstants;
import com.zed.system.GConstantAlert;
import com.zed.system.GConstantRedirect;
import com.zed.system.page.Page;
import com.zed.util.CommUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Controller("playerUserAction")
@Scope(value = "prototype")
public class PlayerUserAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private Page<HashMap> page = new Page<HashMap>();
	private PlayerUser playerUser;
	private String userRoleStatus;
	private String userId;
	private String status;
	private String shareStatus;
	
	@Resource(name="playerUserService")
	private PlayerUserService playerUserService;
	@Resource(name="accountService")
	private AccountService accountService;
	@Autowired
	protected OperationLogService operationLogService;
	
	public String list() {
		try {
			HashMap map = new HashMap();
			page.setSorting(" create_time DESC"); // 排序
			if (!CommUtil.isEmpty(userRoleStatus)) {
				map.put("userRoleStatus", userRoleStatus);
			}
			if (!CommUtil.isEmpty(userId)) {
				map.put("userId", userId);
			}
			if (!CommUtil.isEmpty(status)) {
				map.put("status", status);
			}
			page.setParamsMap(map);
			page = playerUserService.findByPage(page);
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerUserAction[list] failed: ",ex);
			}
			return ERROR;
		}
	}
	
	public String add() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			playerUser.setCreateTime(new Timestamp(newdate.getTime()));
			OperationLog operationLog = new OperationLog(getIp(), "网盘用户管理", "添加网盘用户信息:"+playerUser.getNickName(), newdate, sessionAdmin.getAdminId());
			playerUserService.add(playerUser);
			operationLogService.add(operationLog);
			setSuccessDispatch(GConstantAlert.GS_INSERT_SUCCESS,GConstantRedirect.GS_PLAYER_USER_LIST_ACTION);
			return GConstantRedirect.GS_OK;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerUserAction[add] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String addPage() {
		return SUCCESS;
	}

	public String updatePage() {
		try {
			playerUser = playerUserService.findById(userId);
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerUserAction[updatePage] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String update() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			playerUser.setUpdateTime(new Timestamp(newdate.getTime()));
			OperationLog operationLog = new OperationLog(getIp(), "网盘用户管理", "修改网盘用户信息:"+playerUser.getNickName(), newdate, sessionAdmin.getAdminId());
			playerUserService.update(playerUser,sessionAdmin.getAdminId());
			operationLogService.add(operationLog);
			setSuccessDispatch(GConstantAlert.GS_UPDATE_SUCCESS,GConstantRedirect.GS_PLAYER_USER_LIST_ACTION);
			return GConstantRedirect.GS_OK;

		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerUserAction[update] failed: ",ex);
			}
			return ERROR;
		}

	}

	public String detail() {
		try {
			playerUser = playerUserService.findById(userId);
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerUserAction[detail] failed: ",ex);
			}
			return ERROR;
		}
	}
	
	public String updateStatus() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			PlayerUser pu = playerUserService.findById(userId);
			pu.setStatus(Integer.valueOf(status));
			OperationLog oLog = new OperationLog(getIp(), "网盘用户管理", "修改网盘用户使用状态:" + pu.getNickName()+(status.equals("1")?"启用":"禁用"),newdate , sessionAdmin.getAdminId());
			playerUserService.update(pu,sessionAdmin.getAdminId());
			logService.add(oLog);//记录操作日志
			if (status.equals("1")) {
				setSuccessDispatch(GConstantAlert.GS_START_SUCCESS,GConstantRedirect.GS_PLAYER_USER_LIST_ACTION);
			}else{
				setSuccessDispatch(GConstantAlert.GS_FORBIDDEN_SUCCESS,GConstantRedirect.GS_PLAYER_USER_LIST_ACTION);
			}
			return GConstantRedirect.GS_OK;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerUserAction[updateStatus] failed: ",ex);
			}
			return ERROR;
		}

	}
	
	public String updateShareFileStatus(){
		Admin sessionAdmin = null;
		try {
			
			if (shareStatus.equals("1")) {
				setSuccessDispatch(GConstantAlert.GS_FILE_PUBLIC_SUCCESS,GConstantRedirect.GS_PLAYER_USER_LIST_ACTION);
			}else{
				setSuccessDispatch(GConstantAlert.GS_FILE_UNPUBLIC_SUCCESS,GConstantRedirect.GS_PLAYER_USER_LIST_ACTION);
			}
			return GConstantRedirect.GS_OK;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerUserAction[updateShareFileStatus] failed: ",ex);
			}
			return ERROR;
		}
	}
	
	public String getUserInfo() {
		PrintWriter out = null;
		String result = "";
		try{
			Account account = accountService.findById(userId);
			out = response.getWriter();
			String fieldValue = request.getParameter("fieldValue");
			String fieldId = request.getParameter("fieldId");
			JSONObject json = new JSONObject();
			Boolean flag = Boolean.TRUE;
			if (!CommUtil.isEmpty(fieldValue)) {
				PlayerUser pu = playerUserService.findById(fieldValue);
				if (pu!=null) {
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
				Log.getLogger(this.getClass()).error("PlayerUserAction[getUserInfo] failed: ",ex);
			}
		}finally{
			out.print(result);
			out.flush();
			out.close();
			return NONE;
		}
	}
	
	public String delete() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			String[] userIds=userId.split(",");
			List<OperationLog> logList = new ArrayList<OperationLog>();
			for (String id : userIds) {
				PlayerUser pu = playerUserService.findById(id);
				OperationLog operationLog = new OperationLog(getIp(), "网盘用户管理", "删除网盘用户:"+pu.getNickName(), newdate, sessionAdmin.getAdminId());
				logList.add(operationLog);
			}
			playerUserService.delete(userIds);
			
			if (logList!=null && logList.size()>0 && !logList.isEmpty()) {
				logService.addBatch(logList);//记录操作日志
			}
			setSuccessDispatch(GConstantAlert.GS_DELETE_SUCCESS,GConstantRedirect.GS_PLAYER_USER_LIST_ACTION);
			return GConstantRedirect.GS_OK;

		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerUserAction[delete] failed: ",ex);
			}
			return ERROR;
		}
	}

	@Override
	public String doExecute() throws Exception {
		return null;
	}
	public Page<HashMap> getPage() {
		return page;
	}
	public void setPage(Page<HashMap> page) {
		this.page = page;
	}

	public PlayerUser getPlayerUser() {
		return playerUser;
	}

	public void setPlayerUser(PlayerUser playerUser) {
		this.playerUser = playerUser;
	}

	public String getUserRoleStatus() {
		return userRoleStatus;
	}

	public void setUserRoleStatus(String userRoleStatus) {
		this.userRoleStatus = userRoleStatus;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getShareStatus() {
		return shareStatus;
	}

	public void setShareStatus(String shareStatus) {
		this.shareStatus = shareStatus;
	}
	
}
